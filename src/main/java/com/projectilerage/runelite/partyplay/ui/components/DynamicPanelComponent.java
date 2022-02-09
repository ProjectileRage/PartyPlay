package com.projectilerage.runelite.partyplay.ui.components;

import com.projectilerage.runelite.partyplay.ui.Padding;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.overlay.components.*;
import net.runelite.client.ui.overlay.components.ComponentOrientation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DynamicPanelComponent extends PanelComponent {
    @Getter
    private final Rectangle bounds = new Rectangle();

    @Setter
    private Padding padding = new Padding(0, 0, 0, 0);

    @Setter
    private Point preferredLocation = new Point();

    @Setter
    @Getter
    private Dimension preferredSize = new Dimension(ComponentConstants.STANDARD_WIDTH, 0);

    @Setter
    @Getter
    private Color backgroundColor = ComponentConstants.STANDARD_BACKGROUND_COLOR;

    @Getter
    private final List<LayoutableRenderableEntity> children = new ArrayList<>();

    @Setter
    private ComponentOrientation orientation = ComponentOrientation.VERTICAL;

    @Setter
    private Rectangle border = new Rectangle(
            ComponentConstants.STANDARD_BORDER,
            ComponentConstants.STANDARD_BORDER,
            ComponentConstants.STANDARD_BORDER,
            ComponentConstants.STANDARD_BORDER);

    @Setter
    private Point gap = new Point(0, 0);

    private final Dimension cachedDimensions = new Dimension();

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (children.isEmpty())
        {
            return new Dimension(0, 0);
        }

        int prefWidth = Math.max(preferredSize.width, this.cachedDimensions.width);
        int prefHeight = Math.max(preferredSize.height, this.cachedDimensions.height);

        // Render background
        if (backgroundColor != null)
        {
            final BackgroundComponent backgroundComponent = new BackgroundComponent();
            backgroundComponent.setRectangle(new Rectangle(preferredLocation, new Dimension(prefWidth, prefHeight)));
            backgroundComponent.setBackgroundColor(backgroundColor);
            backgroundComponent.render(graphics);
        }

        // Offset children
        final int baseX = preferredLocation.x + border.x + padding.getLeft();
        final int baseY = preferredLocation.y + border.y + padding.getTop();
        int totalHorizontalOffset = border.x + border.width + padding.getHorizontal();
        int totalVerticalOffset = border.y + border.height + padding.getVertical();
        int width = 0;
        int height = 0;
        int x = baseX;
        int y = baseY;

        // Create child preferred size
        final Dimension childPreferredSize = new Dimension(
                prefWidth - totalHorizontalOffset,
                prefHeight - totalVerticalOffset);

        // Calculate max width/height for infoboxes
        int totalHeight = 0;
        int totalWidth = 0;

        // Render all children
        for (final LayoutableRenderableEntity child : children)
        {
            // Correctly propagate child dimensions based on orientation and wrapping
            switch (orientation)
            {
                case VERTICAL:
                    child.setPreferredSize(new Dimension(childPreferredSize.width, 0));
                    break;
                case HORIZONTAL:
                    child.setPreferredSize(new Dimension(0, childPreferredSize.height));
                    break;
            }

            child.setPreferredLocation(new Point(x, y));
            final Dimension childDimension = child.render(graphics);

            if(orientation == ComponentOrientation.VERTICAL) {
                height += childDimension.height + gap.y;
                y = baseY + height;

                width = Math.max(childDimension.width, width);
            } else {
                width += childDimension.width + gap.x;
                x = baseX + width;

                height = Math.max(childDimension.height, height);
            }



            // Calculate total size
            totalWidth = Math.max(totalWidth, width);
            totalHeight = Math.max(totalHeight, height);
        }

        // Remove last child gap
        if (orientation == ComponentOrientation.HORIZONTAL)
        {
            totalWidth -= gap.x;
        }
        else // VERTICAL
        {
            totalHeight -= gap.y;
        }

        totalWidth += totalHorizontalOffset;
        totalHeight += totalVerticalOffset;

        // Cache children bounds
        this.cachedDimensions.setSize(totalWidth, totalHeight);
        // Calculate panel dimension

        // Cache bounds
        bounds.setLocation(preferredLocation);
        bounds.setSize(totalWidth, totalHeight);

        return bounds.getSize();
    }
}
