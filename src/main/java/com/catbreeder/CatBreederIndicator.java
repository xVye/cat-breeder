package com.catbreeder;

import net.runelite.api.Client;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBox;
import net.runelite.client.ui.overlay.infobox.InfoBoxPriority;

import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CatBreederIndicator extends InfoBox
{
    private final CatBreederPlugin plugin;
    private final CatBreederConfig config;
    private final Client client;

    public CatBreederIndicator(BufferedImage image, CatBreederPlugin plugin, CatBreederConfig config, Client client)
    {
        super(image, plugin);
        this.plugin = plugin;
        this.config = config;
        this.client = client;
        setTooltip("Cat timer");
        setPriority(InfoBoxPriority.HIGH);
    }

    @Override
    public String getText()
    {
        Kitten kitten = plugin.getKitten();
        if (kitten == null || kitten.getTimeRemaining() <= -1)
        {
            return "n/a";
        }
        return "";
    }

    @Override
    public Color getTextColor()
    {
        Color color = new Color(190, 190, 190);
        Kitten kitten = plugin.getKitten();

        if (kitten == null)
        {
            return new Color(125, 125, 125);
        }

        if (kitten.getTimeRemaining() <= 3000)
        {
            color = new Color(220, 55, 55);
        }

        return color;
    }
}
