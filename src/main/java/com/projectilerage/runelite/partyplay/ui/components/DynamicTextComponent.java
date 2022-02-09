package com.projectilerage.runelite.partyplay.ui.components;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.runelite.client.config.FontType;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.TextComponent;

import java.awt.*;


@Builder
@Setter
public class DynamicTextComponent implements LayoutableRenderableEntity {
    private String text;

    @Builder.Default
    private Color color = Color.WHITE;

    @Builder.Default
    private Font font = FontType.REGULAR.getFont();

    @Builder.Default
    private Point preferredLocation = new Point();

    @Builder.Default
    private Dimension preferredSize = new Dimension(ComponentConstants.STANDARD_WIDTH, 0);

    @Builder.Default
    @Getter
    private final Rectangle bounds = new Rectangle();

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final int baseX = preferredLocation.x;
        final int baseY = preferredLocation.y;
        final FontMetrics metrics = graphics.getFontMetrics(font);
        final TextComponent textComponent = new TextComponent();
        final int x = Math.max(baseX, baseX + ((preferredSize.width - metrics.stringWidth(text)) / 2));
        final int y = baseY + metrics.getHeight();

        textComponent.setFont(font);
        textComponent.setText(text);
        textComponent.setColor(color);
        textComponent.setPosition(new Point(x, y));
        final Dimension dimension = textComponent.render(graphics);

        bounds.setLocation(preferredLocation);
        bounds.setSize(new Dimension(dimension.width + (x - baseX) * 2, dimension.height));
        return bounds.getSize();
    }
}
