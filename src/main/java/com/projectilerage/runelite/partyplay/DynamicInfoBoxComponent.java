package com.projectilerage.runelite.partyplay;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.components.BackgroundComponent;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.TextComponent;
import net.runelite.client.ui.overlay.infobox.InfoBox;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Slf4j
public class DynamicInfoBoxComponent implements LayoutableRenderableEntity
{
    private static final int SEPARATOR = 3;
    private static final int DEFAULT_SIZE = 32;

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
    @Getter
    private InfoBox infoBox;

    @Setter
    private int textOffset = 10;

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

        // Calculate dimensions
        final FontMetrics metrics = graphics.getFontMetrics();

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
        return bounds.getSize();
    }

    private int getSize()
    {
        return Math.max(preferredSize.width, preferredSize.height);
    }
}
