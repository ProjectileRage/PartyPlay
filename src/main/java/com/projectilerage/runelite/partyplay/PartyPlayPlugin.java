package com.projectilerage.runelite.partyplay;

import com.google.common.base.Strings;
import com.google.inject.Provides;
import com.projectilerage.runelite.partyplay.ui.components.DynamicInfoBoxComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.FontType;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.PartyChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.ws.PartyService;
import net.runelite.client.ws.WSClient;
import net.runelite.http.api.ws.messages.party.UserJoin;
import net.runelite.http.api.ws.messages.party.UserPart;
import net.runelite.http.api.ws.messages.party.UserSync;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.time.temporal.ChronoUnit;
import java.util.*;

@PluginDescriptor(
        name = "Party Play",
        description = "Makes Runescape a little less single-player",
        tags = {"action", "activity", "status"}
)
@Slf4j
@Singleton
public class PartyPlayPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private InfoBoxManager infoBoxManager;

    @Inject
    private ConfigManager configManager;

    @Inject
    private ItemManager itemManager;

    @Inject
    private PartyPlayConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private PartyService partyService;

    @Inject
    private PartyOverlay partyOverlay;

    @Inject
    private PartyState state;

    @Inject
    private TooltipManager tooltipManager;

    @Inject
    private WSClient wsClient;

    private final Map<Skill, Integer> skillExp = new HashMap<>();

    @Getter(AccessLevel.PACKAGE)
    private final Map<UUID, PartyStateInfo> partyStateInfoMap = Collections.synchronizedMap(new HashMap<>());

    private final Integer DEFAULT_SLAYER_ITEM = ItemID.SLAYER_HELMET;

    private GameEventType curArea;

    private boolean loginFlag;

    protected void startUp() throws Exception
    {
        wsClient.registerMessage(ActivityInfo.class);
        wsClient.registerMessage(SlayerInfo.class);
        state.reset();
        checkForGameStateUpdate();
        checkForAreaUpdate();
        overlayManager.add(partyOverlay);
    }

    protected void shutDown() throws Exception
    {
        overlayManager.remove(partyOverlay);
        wsClient.unregisterMessage(ActivityInfo.class);
        wsClient.unregisterMessage(SlayerInfo.class);
        state.reset();
        partyStateInfoMap.clear();
    }

    @Subscribe
    public void onUserJoin(final UserJoin event)
    {
        log.debug("onUserJoin: "  + partyService.getLocalMember());
        this.state.refresh();
        processSlayerConfig();
    }

    @Subscribe
    public void onUserSync(final UserSync event)
    {
        log.debug("onUserSync: " + partyService.getLocalMember());
        this.state.refresh();
        processSlayerConfig();
    }

    @Subscribe
    public void onUserPart(final UserPart event)
    {
        log.debug("onUserPart: " + partyService.getLocalMember());
        this.partyStateInfoMap.remove(event.getMemberId());
    }

    @Subscribe
    public void onPartyChanged(final PartyChanged event)
    {
        log.debug("onPartyChange: " + partyService.getLocalMember());
        this.partyStateInfoMap.clear();
        this.state.refresh();
        processSlayerConfig();
    }

    @Subscribe
    public void onConfigChanged(final ConfigChanged event) {
        if(event.getGroup().equals(PartyPlayConfig.GROUP)) {
            log.debug("Config changed; refreshing");
            this.state.refresh();
            processSlayerConfig();
        } else if(event.getGroup().equals("slayer")) {
            processSlayerConfig();
        }
    }

    @Provides
    public PartyPlayConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(PartyPlayConfig.class);
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        switch (event.getGameState())
        {
            case LOGIN_SCREEN:
                this.state.reset();
                checkForGameStateUpdate();
                return;
            case LOGGING_IN:
                loginFlag = true;
                break;
            case LOGGED_IN:
                if (loginFlag)
                {
                    loginFlag = false;
                    this.state.reset();
                    processSlayerConfig();
                    checkForGameStateUpdate();
                }
                checkForAreaUpdate();
                break;
        }
    }

    private void checkForGameStateUpdate() {
        final boolean isLoggedIn = client.getGameState() == GameState.LOGGED_IN;

        if (config.showMainMenu() || isLoggedIn)
        {
            state.triggerEvent(isLoggedIn
                    ? GameEventType.IN_GAME
                    : GameEventType.IN_MENU);
        }
    }

    @Subscribe
    public void onStatChanged(StatChanged statChanged)
    {
        final Skill skill = statChanged.getSkill();
        final int exp = statChanged.getXp();
        final Integer previous = skillExp.put(skill, exp);

        if (previous == null || previous >= exp)
        {
            return;
        }

        GameEventType event = GameEventType.fromSkill(skill);

        if(event != null){
            this.state.triggerEvent(event);
        }
    }

    @Schedule(
            period = 1,
            unit = ChronoUnit.MINUTES
    )
    public void trimStateEvents() {
        this.state.checkForTimeout();
    }

    @Schedule(
            period = 10,
            unit = ChronoUnit.SECONDS
    )
    public void maybeCheckForAreaUpdate() {
        if(!GameState.LOGGED_IN.equals(client.getGameState())) {
            return;
        }

        int regionId = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation()).getRegionID();
        if(curArea == null || Arrays.stream(curArea.getRegionIds()).noneMatch((oldRegionId) -> oldRegionId == regionId)) {
            log.debug("Area Update Check");
            this.checkForAreaUpdate();
        }
    }

    private void checkForAreaUpdate()
    {
        if (client.getLocalPlayer() == null)
        {
            return;
        }

        final int playerRegionID = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation()).getRegionID();

        if (playerRegionID == 0)
        {
            return;
        }

        final EnumSet<WorldType> worldType = client.getWorldType();

        if (worldType.contains(WorldType.DEADMAN))
        {
            this.state.triggerEvent(GameEventType.PLAYING_DEADMAN);
        }
        else if (WorldType.isPvpWorld(worldType))
        {
            this.state.triggerEvent(GameEventType.PLAYING_PVP);
        }

        GameEventType gameEventType = GameEventType.fromRegion(playerRegionID);

        // NMZ uses the same region ID as KBD. KBD is always on plane 0 and NMZ is always above plane 0
        // Since KBD requires going through the wilderness there is no EventType for it
        if (GameEventType.MG_NIGHTMARE_ZONE == gameEventType
                && client.getLocalPlayer().getWorldLocation().getPlane() == 0)
        {
            gameEventType = null;
        }

        if(gameEventType == null)
        {
            // Unknown region, reset to default in-game
            log.debug("Triggering In Game Event: " + playerRegionID);
            this.state.triggerEvent(GameEventType.IN_GAME);
            return;
        }

        log.debug("Triggering Area Event: " + gameEventType.getState());
        this.curArea = gameEventType;
        this.state.triggerEvent(gameEventType);
    }

    @Subscribe
    public void onActivityInfo(final ActivityInfo event)
    {
        setActivityInfo(event);
    }

    ActivityInfo getActivityInfo(UUID uuid) {
        PartyStateInfo partyStateInfo = partyStateInfoMap.get(uuid);

        return partyStateInfo != null ? partyStateInfo.getActivityInfo() : null;
    }

    void setActivityInfo(ActivityInfo activityInfo) {
        PartyStateInfo partyStateInfo = partyStateInfoMap.get(activityInfo.getMemberId());

        if (partyStateInfo != null) {
            partyStateInfo.setActivityInfo(activityInfo);
        } else {
            this.partyStateInfoMap.put(activityInfo.getMemberId(), new PartyStateInfo(activityInfo));
        }
    }

    public DynamicInfoBoxComponent getSlayerInfoBox(SlayerTask task) {
        if(task == null || task.getName() == null) {
            log.debug("PPD:: Slayer task null; Exiting Slayer InfoBox creation");
            return null;
        }

        int size = configManager.getConfiguration("runelite", "infoBoxSize", Integer.class);
        FontType font = configManager.getConfiguration("runelite", "infoboxFontType", FontType.class);
        boolean outline = configManager.getConfiguration("runelite", "infoBoxTextOutline", Boolean.class);

        int item = task.getItemSpriteId();
        BufferedImage rawImage = itemManager.getImage(item != 0 ? item : DEFAULT_SLAYER_ITEM);
        BufferedImage image =  ImageUtil.resizeImage(rawImage, size, size, true);
        DynamicInfoBoxComponent box = new DynamicInfoBoxComponent(client, tooltipManager);
        box.setImage(image);
        box.setFont(font.getFont());
        box.setOutline(outline);

        return box;
    }

    SlayerInfo getSlayerInfo(UUID uuid) {
        PartyStateInfo partyStateInfo = partyStateInfoMap.get(uuid);

        return partyStateInfo != null ? partyStateInfo.getSlayerInfo() : null;
    }

    void setSlayerInfo(SlayerInfo slayerInfo) {
        PartyStateInfo partyStateInfo = partyStateInfoMap.get(slayerInfo.getMemberId());

        if (partyStateInfo != null) {
            partyStateInfo.setSlayerInfo(slayerInfo);
        } else if(slayerInfo.getSlayerTask() != null) {
            this.partyStateInfoMap.put(slayerInfo.getMemberId(), new PartyStateInfo(slayerInfo));
        }
    }

    @Subscribe
    void onSlayerInfo(final SlayerInfo slayerInfo) {
        setSlayerInfo(slayerInfo);
    }

    void processSlayerConfig() {
        if(partyService.getLocalMember() == null || !state.containsEventType(GameEventType.TRAINING_SLAYER) || !config.showSlayerActivity()) {
            log.debug("PPD:: Slayer Event Non-applicable; Clearing Slayer state");
            clearSlayerState();
            return;
        }

        String taskName = configManager.getRSProfileConfiguration("slayer", "taskName");
        String amount = configManager.getRSProfileConfiguration("slayer", "amount");

        if(Strings.isNullOrEmpty(taskName) || Strings.isNullOrEmpty(amount)) {
            log.debug("PPD:: Null Slayer Event");
            return;
        }

        if(Integer.parseInt(amount) == 0) {
            log.debug("PPD:: Task completed. Clearing state");
            clearSlayerState();
            return;
        }

        SlayerInfo slayerInfo = getSlayerInfo(partyService.getLocalMember().getMemberId());
        if(slayerInfo == null) {
            slayerInfo = new SlayerInfo();
            slayerInfo.setMemberId(partyService.getLocalMember().getMemberId());
        }

        String initialAmount = configManager.getRSProfileConfiguration("slayer", "initialAmount");
        String taskLocation = configManager.getRSProfileConfiguration("slayer", "taskLocation");

        log.debug("PPD:: Slayer task left: " + amount);
        slayerInfo.setAmount(Integer.parseInt(amount));

        if(slayerInfo.getSlayerTask() == null || !taskName.equals(slayerInfo.getSlayerTask().getName())) {
            log.debug("PPD:: Slayer task name: " + taskName);
            slayerInfo.setSlayerTask(SlayerTask.getTask(taskName));
        }

        if(!Strings.isNullOrEmpty(initialAmount)) {
            log.debug("PPD:: Slayer task start: " + initialAmount);
            slayerInfo.setInitialAmount(Integer.parseInt(initialAmount));
        }

        if(!Strings.isNullOrEmpty(taskLocation)) {
            log.debug("PPD:: Slayer task location: " + taskLocation);
            slayerInfo.setLocation(taskLocation);
        }

        setSlayerInfo(slayerInfo);
        wsClient.send(slayerInfo);
    }

    void clearSlayerState() {
        if(partyService.getLocalMember() == null) {
            return;
        }

        UUID localId = partyService.getLocalMember().getMemberId();

        if (localId != null) {
            PartyStateInfo partyStateInfo = partyStateInfoMap.get(localId);
            if(partyStateInfo != null && partyStateInfo.getSlayerInfo() != null) {
                log.debug("PPD:: Removing slayer state");
                SlayerInfo slayerInfo = partyStateInfo.getSlayerInfo();
                slayerInfo.reset();
                wsClient.send(slayerInfo);
            }
        } else {
            log.debug("PPD:: Null Local Member; Can't clear Slayer state");
        }
    }
}
