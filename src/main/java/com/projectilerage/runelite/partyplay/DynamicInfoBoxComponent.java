package com.projectilerage.runelite.partyplay;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.components.BackgroundComponent;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class DynamicInfoBoxComponent implements LayoutableRenderableEntity
{
    private static final int DEFAULT_SIZE = 32;

    private final TooltipManager tooltipManager;
    private final Client client;

    @Getter
    private String tooltip;

    @Getter
    private final Rectangle bounds = new Rectangle();

    private Point preferredLocation = new Point();

    private Dimension preferredSize = new Dimension(DEFAULT_SIZE, DEFAULT_SIZE);

    private String text;

    private Color color = Color.WHITE;

    private Font font;

    private boolean outline;

    private Color backgroundColor = ComponentConstants.STANDARD_BACKGROUND_COLOR;

    private BufferedImage image;

    DynamicInfoBoxComponent(Client client, TooltipManager tooltipManager) {
        this.client = client;
        this.tooltipManager = tooltipManager;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (image == null)
        {
            return new Dimension();
        }

        graphics.setFont(getSize() < DEFAULT_SIZE ? FontManager.getRunescapeSmallFont() : font);
        final int longestText = Strings.isNullOrEmpty(text) ? 0 : text.lines().mapToInt((line) -> graphics.getFontMetrics().stringWidth(line)).max().getAsInt();
        final int baseX = preferredLocation.x;
        final int baseY = preferredLocation.y;
        final int width = Math.max(image.getWidth(), Math.max(preferredSize.width, longestText));
        final int height = Math.max(image.getHeight(), preferredSize.height);
        final int calcX = Math.max(baseX, baseX + (width - image.getWidth(null)) / 2);
        final int calcY = Math.max(baseY, baseY + (height - image.getHeight(null)) / 2);

        final Rectangle bounds = new Rectangle(baseX, baseY, width, height);

        // Render background
        if(backgroundColor != null) {
            final BackgroundComponent backgroundComponent = new BackgroundComponent();
            backgroundComponent.setBackgroundColor(backgroundColor);
            backgroundComponent.setRectangle(bounds);
            backgroundComponent.render(graphics);
        }

        // Render image
        graphics.drawImage(
                image,
                calcX,
                calcY,
                null);

        // Render caption
        if (!Strings.isNullOrEmpty(text))
        {
            List<String> lines = text.lines().collect(Collectors.toList());
            int y = calcY + image.getHeight() - graphics.getFontMetrics().getHeight() * lines.size();
            int x = (calcX + image.getWidth() / 2);

            for(String line : lines) {
                final TextComponent textComponent = new TextComponent();
                textComponent.setColor(color);
                textComponent.setOutline(outline);
                textComponent.setText(line);
                textComponent.setPosition(new Point(x - graphics.getFontMetrics().stringWidth(line) / 2, y));
                textComponent.render(graphics);
                y += graphics.getFontMetrics().getHeight();
            }
        }



        this.bounds.setBounds(bounds);

        if(!Strings.isNullOrEmpty(tooltip)) {
            // Handle tooltips
            final Point mouse = new Point(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY());
            // Create intersection rectangle
            Rectangle intersectionRectangle = new Rectangle(getBounds());
            intersectionRectangle.translate(Math.abs(graphics.getClipBounds().x), Math.abs(graphics.getClipBounds().y));

            if (intersectionRectangle.contains(mouse))
            {
                tooltipManager.add(new Tooltip(tooltip));
            }
        }

        return bounds.getSize();
    }

    private int getSize()
    {
        return Math.max(preferredSize.width, preferredSize.height);
    }
}
