package com.projectilerage.runelite.partyplay.ui.components;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.ComponentOrientation;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;

import java.awt.*;

@Setter
@Builder
public class DynamicSplitComponent implements LayoutableRenderableEntity
{
    @Getter
    private LayoutableRenderableEntity first;

    @Getter
    private LayoutableRenderableEntity second;

    @Builder.Default
    private Point preferredLocation = new Point();

    @Builder.Default
    private Dimension preferredSize = new Dimension(ComponentConstants.STANDARD_WIDTH, 0);

    @Builder.Default
    private ComponentOrientation orientation = ComponentOrientation.VERTICAL;

    @Builder.Default
    private Point gap = new Point(0, 0);

    @Builder.Default
    @Getter
    private final Rectangle bounds = new Rectangle();

    @Override
    public Dimension render(Graphics2D graphics)
    {
        first.setPreferredLocation(preferredLocation);
        first.setPreferredSize(preferredSize);

        final Dimension firstDimension = first.render(graphics);
        int x = 0, y = 0;

        if (orientation == net.runelite.client.ui.overlay.components.ComponentOrientation.VERTICAL)
        {
            y = firstDimension.height + gap.y;
            preferredSize.width = Math.max(firstDimension.width, preferredSize.width);
        }
        else
        {
            x = firstDimension.width + gap.x;
            preferredSize.height = Math.max(firstDimension.height, preferredSize.height);
        }

        second.setPreferredLocation(new Point(x + preferredLocation.x, y + preferredLocation.y));
        // Make the second component fit to whatever size is left after the first component is rendered
        second.setPreferredSize(new Dimension(preferredSize.width - x, preferredSize.height - y));

        // The total width/height need to be determined as they are now always the same as the
        // individual width/height (for example image width/height will just be the height of the image
        // and not the height of the area the image is in
        final Dimension secondDimension = second.render(graphics);
        int totalWidth, totalHeight;

        if (orientation == ComponentOrientation.VERTICAL)
        {
            totalWidth = Math.max(firstDimension.width, secondDimension.width);
            totalHeight = y + secondDimension.height;
        }
        else
        {
            totalHeight = Math.max(firstDimension.height, secondDimension.height);
            totalWidth = x + secondDimension.width;
        }

        final Dimension dimension = new Dimension(totalWidth, totalHeight);
        bounds.setLocation(preferredLocation);
        bounds.setSize(dimension);
        return dimension;
    }
}
