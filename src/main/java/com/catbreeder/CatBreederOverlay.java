package com.catbreeder;

import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;

public class CatBreederOverlay extends OverlayPanel
{
    public static final Color TITLE_COLOR = new Color(190, 190, 190);

    private final CatBreederPlugin plugin;

    @Inject
    public CatBreederOverlay(CatBreederPlugin plugin)
    {
        this.plugin = plugin;
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        setPriority(OverlayPriority.HIGHEST);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (!plugin.getConfig().displayTimer() || plugin.getKitten() == null || !plugin.getKittenActive())
        {
            return null;
        }

        return super.render(graphics);
    }
}
