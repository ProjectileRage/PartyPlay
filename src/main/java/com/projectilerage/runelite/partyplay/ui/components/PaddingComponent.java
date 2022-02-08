package com.projectilerage.runelite.partyplay.ui.components;

import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;

import java.awt.*;

class PaddingComponent implements LayoutableRenderableEntity {
    @Setter
    private Dimension padding = new Dimension();

    @Setter
    private Point preferredLocation;

    @Setter
    private Dimension preferredSize;

    @Getter
    private Rectangle bounds;

    PaddingComponent(Dimension padding) {
        this.padding = padding;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        this.bounds = new Rectangle(preferredLocation, padding);
        return padding;
    }
}
