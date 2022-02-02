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

import net.runelite.api.MenuAction;
import net.runelite.client.ui.overlay.OverlayMenuEntry;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
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

    private final Map<UUID, PanelComponent> panelMap = new HashMap<>();

    @Inject
    private PartyOverlay(final PartyPlayPlugin plugin, final PartyState state, final PartyService party, final PartyPlayConfig config)
    {
        super(plugin);
        this.plugin = plugin;
        this.state = state;
        this.party = party;
        this.config = config;
        panelComponent.setBorder(new Rectangle());
        panelComponent.setGap(new Point(0, ComponentConstants.STANDARD_BORDER / 2));
        getMenuEntries().add(new OverlayMenuEntry(MenuAction.RUNELITE_OVERLAY, "Leave", "Party"));
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final Map<UUID, PartyStateInfo> partyDataMap = plugin.getPartyStateInfoMap();
        if (partyDataMap.isEmpty())
        {
            return null;
        }

        panelComponent.setBackgroundColor(null);

        synchronized (partyDataMap)
        {
            partyDataMap.forEach((uuid, stateInfo) ->
            {
                boolean isSelf = party.getLocalMember() != null && party.getLocalMember().getMemberId().equals(uuid);

                if (!config.includeSelf() && isSelf)
                {
                    return;
                }

                PanelComponent panel = panelMap.get(uuid);

                if (panel == null) {
                    panel = new PanelComponent();
                    panelMap.put(uuid, panel);
                }

                panel.getChildren().clear();

                final TitleComponent name = TitleComponent.builder()
                        .text(stateInfo.getUserId())
                        .color(config.recolorNames() ? ColorUtil.fromObject(stateInfo.getUserId()): Color.WHITE)
                        .build();
                panel.getChildren().add(name);

                if(stateInfo.getActivity() != null) {
                    final TitleComponent activity = TitleComponent.builder()
                            .text(stateInfo.getActivity())
                            .color(ColorUtil.fromObject(stateInfo.getActivity()))
                            .build();
                    panel.getChildren().add(activity);
                }

                if(stateInfo.getLocation() != null) {
                    final TitleComponent location = TitleComponent.builder()
                            .text(stateInfo.getLocation())
                            .color(ColorUtil.fromObject(stateInfo.getLocation()))
                            .build();
                    panel.getChildren().add(location);
                }

                panelComponent.getChildren().add(panel);
            });
        }

        return super.render(graphics);
    }
}
