package com.catbreeder;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;
import java.time.Instant;

public class CatBreederOverlay extends OverlayPanel
{
    private final Client client;
    private final CatBreederConfig config;
    private final CatBreederPlugin plugin;

    @Inject
    public CatBreederOverlay(Client client, CatBreederConfig config, CatBreederPlugin plugin)
    {
        this.client = client;
        this.config = config;
        this.plugin = plugin;

        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        setPriority(OverlayPriority.HIGHEST);
        setPosition(OverlayPosition.DYNAMIC);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        CatTimer timer = plugin.getCurrentTimer();
        if (!plugin.getConfig().displayTimer() || timer == null || Instant.now().compareTo(timer.getEndTime()) < 0)
        {
            return null;
        }

        return super.render(graphics);
    }
}
