package com.projectilerage.runelite.partyplay;

import net.runelite.client.ui.overlay.components.*;
import net.runelite.client.ui.overlay.components.ComponentOrientation;

import java.awt.*;

class SplitComponentWrapper {
    final DynamicSplitComponent split;
    final DynamicPanelComponent firstPanel;
    final DynamicPanelComponent secondPanel;

    SplitComponentWrapper() {
        split = DynamicSplitComponent.builder()
                .orientation(ComponentOrientation.HORIZONTAL)
                .preferredSize(new Dimension(ComponentConstants.STANDARD_WIDTH * 11, ComponentConstants.STANDARD_WIDTH * 15))
                .build();
        firstPanel = new DynamicPanelComponent();
        firstPanel.setGap(new Point(0, ComponentConstants.STANDARD_BORDER / 2));
        split.setFirst(firstPanel);
        secondPanel = new DynamicPanelComponent();
        secondPanel.setPadding(new Padding(0,0,10,0));
        secondPanel.setBackgroundColor(null);
        secondPanel.setOrientation(ComponentOrientation.HORIZONTAL);
        split.setSecond(secondPanel);
    }

    void clearChildren() {
        firstPanel.getChildren().clear();
        secondPanel.getChildren().clear();
    }
}
