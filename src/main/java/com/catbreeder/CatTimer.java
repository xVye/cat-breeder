package com.catbreeder;

import lombok.Getter;
import lombok.Setter;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CatTimer extends Timer
{
    @Getter
    @Setter
    private boolean visible;

    public CatTimer(Duration duration, BufferedImage image, Plugin plugin, boolean visible)
    {
        super(duration.toMillis(), ChronoUnit.MILLIS, image, plugin);
        setTooltip("Time until kitten needs attention or food");
        this.visible = visible;
    }

    @Override
    public Color getTextColor()
    {
        Duration timeLeft = Duration.between(Instant.now(), getEndTime());

        if (timeLeft.getSeconds() < 60)
        {
            return Color.RED.brighter();
        }

        return Color.WHITE;
    }

    @Override
    public boolean render()
    {
        return visible && super.render();
    }
}
