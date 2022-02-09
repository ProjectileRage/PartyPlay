/*
 * Copyright (c) 2019, Tomas Slusny <slusnucky@gmail.com>
 * Copyright (c) 2021, Jonathan Rousseau <https://github.com/JoRouss>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.projectilerage.runelite.partyplay;

import com.projectilerage.runelite.partyplay.ui.components.DynamicInfoBoxComponent;
import com.projectilerage.runelite.partyplay.ui.components.DynamicTextComponent;
import lombok.NonNull;
import net.runelite.api.MenuAction;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.ws.PartyService;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartyOverlay extends OverlayPanel
{
    private final PartyPlayPlugin plugin;
    private final PartyState state;
    private final PartyService party;
    private final PartyPlayConfig config;

    private final Map<UUID, SplitComponentWrapper>  splitMap = new HashMap<>();
    private final GameEventType longestEvent = GameEventType.REGION_DRILL_SERGEANT;

    private int width = 0;

    @Inject
    private PartyOverlay(final PartyPlayPlugin plugin, final PartyState state, final PartyService party, final PartyPlayConfig config)
    {
        super(plugin);
        this.plugin = plugin;
        this.state = state;
        this.party = party;
        this.config = config;
        this.setResizable(false);
        panelComponent.setBorder(new Rectangle());
        getMenuEntries().add(new OverlayMenuEntry(MenuAction.RUNELITE_OVERLAY, "Leave", "Party"));
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final Map<UUID, PartyStateInfo> partyDataMap = plugin.getPartyStateInfoMap();
        if (partyDataMap.isEmpty())
        {
            return new Dimension();
        }

        int width = graphics.getFontMetrics().stringWidth(longestEvent.getState());
        int height = graphics.getFontMetrics().getHeight() * 3;

        this.setPreferredSize(new Dimension(width, height));

        panelComponent.setBackgroundColor(null);

        synchronized (partyDataMap)
        {
            partyDataMap.forEach((uuid, stateInfo) ->
            {
                if(stateInfo == null || stateInfo.getActivityInfo() == null) {
                    return;
                }

                boolean isSelf = party.getLocalMember() != null && party.getLocalMember().getMemberId().equals(uuid);

                if (!config.includeSelf() && isSelf)
                {
                    return;
                }

               SplitComponentWrapper split = splitMap.get(uuid);

                if (split == null) {
                    split = new SplitComponentWrapper();
                    splitMap.put(uuid, split);
                }

                split.clearChildren();
                ActivityInfo activityInfo = stateInfo.getActivityInfo();

                final DynamicTextComponent name = DynamicTextComponent.builder()
                        .text(activityInfo.getUserId())
                        .color(config.recolorNames() ? ColorUtil.fromObject(activityInfo.getUserId()): Color.WHITE)
                        .build();
                split.firstPanel.getChildren().add(name);

                if(stateInfo.getActivityInfo().getActivity() != null) {
                    final DynamicTextComponent activity = DynamicTextComponent.builder()
                            .text(activityInfo.getActivity())
                            .color(ColorUtil.fromObject(activityInfo.getActivity()))
                            .build();
                    split.firstPanel.getChildren().add(activity);
                }

                if(stateInfo.getActivityInfo().getLocation() != null) {
                    final DynamicTextComponent location = DynamicTextComponent.builder()
                            .text(activityInfo.getLocation())
                            .color(ColorUtil.fromObject(activityInfo.getLocation()))
                            .build();
                    split.firstPanel.getChildren().add(location);
                }

                SlayerInfo slayerInfo = stateInfo.getSlayerInfo();

                if(slayerInfo != null && slayerInfo.getSlayerTask() != null && config.showSlayerActivity()) {
                    DynamicInfoBoxComponent box = plugin.getSlayerInfoBox(slayerInfo.getSlayerTask());

                    if(box != null) {
                        box.setText(Integer.toString(slayerInfo.getAmount()));
                        box.setBackgroundColor(null);
                        box.setTooltip(getSlayerTooltip(slayerInfo));
                        split.secondPanel.getChildren().add(box);
                    }
                }

                panelComponent.getChildren().add(split.split);
            });
        }

        return super.render(graphics);
    }

    static String getSlayerTooltip(@NonNull SlayerInfo slayerInfo) {
        String taskTooltip = ColorUtil.wrapWithColorTag("%s", new Color(157, 8, 8)) + "</br>";
        if (slayerInfo.getLocation() != null && !slayerInfo.getLocation().isEmpty())
        {
            taskTooltip += slayerInfo.getLocation() + "</br>";
        }

        if (slayerInfo.getInitialAmount() > 0)
        {
            taskTooltip += "</br>"
                    + ColorUtil.wrapWithColorTag("Start:", Color.YELLOW)
                    + " " + slayerInfo.getInitialAmount();
        }

        return String.format(taskTooltip, slayerInfo.getSlayerTask().getName());
    }
}
