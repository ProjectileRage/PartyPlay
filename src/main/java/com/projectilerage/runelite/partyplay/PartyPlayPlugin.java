package com.projectilerage.runelite.partyplay;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.api.WorldType;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.PartyChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ws.PartyService;
import net.runelite.client.ws.WSClient;
import net.runelite.http.api.ws.messages.party.UserJoin;
import net.runelite.http.api.ws.messages.party.UserPart;
import net.runelite.http.api.ws.messages.party.UserSync;

import javax.inject.Inject;
import javax.inject.Singleton;
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
    private WSClient wsClient;

    private final Map<Skill, Integer> skillExp = new HashMap<>();

    @Getter(AccessLevel.PACKAGE)
    private final Map<UUID, PartyStateInfo> partyStateInfoMap = Collections.synchronizedMap(new HashMap<>());

    private GameEventType curArea;

    private boolean loginFlag;

    protected void startUp() throws Exception
    {
        wsClient.registerMessage(PartyStateInfo.class);
        state.reset();
        checkForGameStateUpdate();
        checkForAreaUpdate();
        overlayManager.add(partyOverlay);
    }

    protected void shutDown() throws Exception
    {
        overlayManager.remove(partyOverlay);
        wsClient.unregisterMessage(PartyStateInfo.class);
        state.reset();
        partyStateInfoMap.clear();
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

        this.state.triggerEvent(GameEventType.fromSkill(skill));
    }

    @Schedule(
            period = 10,
            unit = ChronoUnit.SECONDS
    )
    public void maybeCheckForAreaUpdate() {
        if(client.getGameState() != GameState.LOGGED_IN) {
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
    public void onPartyStateInfo(final PartyStateInfo event)
    {
        this.partyStateInfoMap.put(event.getMemberId(), event);
    }

    @Subscribe
    public void onUserJoin(final UserJoin event)
    {
        log.debug("onUserJoin: "  + partyService.getLocalMember());
        this.state.refresh();
    }

    @Subscribe
    public void onUserSync(final UserSync event)
    {
        log.debug("onUserSync: " + partyService.getLocalMember());
        this.state.refresh();
    }

    @Subscribe
    public void onUserPart(final UserPart event)
    {
        log.debug("onUserPart: " + partyService.getLocalMember());
        this.partyStateInfoMap.remove(event.getMemberId());
        this.state.refresh();
    }

    @Subscribe
    public void onPartyChanged(final PartyChanged event)
    {
        log.debug("onPartyChange: " + partyService.getLocalMember());
        this.partyStateInfoMap.clear();
        this.state.refresh();
    }

    @Subscribe
    public void onConfigChanged(final ConfigChanged event) {
        if(event.getGroup().equals(PartyPlayConfig.GROUP)) {
            log.debug("Config changed; refreshing");
            this.state.refresh();
        }
    }

    @Provides
    public PartyPlayConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(PartyPlayConfig.class);
    }
}
