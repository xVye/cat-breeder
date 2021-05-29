package com.kittentimer;

import lombok.Getter;
import lombok.Setter;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.Timer;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class KittenGrowthTimer extends Timer
{
    @Getter
    @Setter
    private boolean visible;

    public KittenGrowthTimer(Duration duration, BufferedImage image, Plugin plugin, boolean visible)
    {
        super(duration.toMillis(), ChronoUnit.MILLIS, image, plugin);
        setTooltip("Time until your kitten grows into a cat");
        this.visible = visible;
    }
}
