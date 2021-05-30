package com.kittentimer.overlays;

import com.kittentimer.Kitten;
import com.kittentimer.KittenTile;
import com.kittentimer.KittenTimerPlugin;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

public class KittenWorldOverlay extends Overlay
{
	private final Client client;
	private final KittenTimerPlugin plugin;

	@Inject
	public KittenWorldOverlay(Client client, KittenTimerPlugin plugin)
	{
		this.client = client;
		this.plugin = plugin;

		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!plugin.getConfig().displayOverlaySymbol())
		{
			return null;
		}

		Kitten kitten = plugin.getCurrentKitten();

		if (kitten != null && plugin.getCurrentKitten().isActive())
		{
			KittenTile kittenTile = kitten.getKittenTile();
			LocalPoint localPoint = new LocalPoint(kittenTile.getWorldPoint().getX(), kittenTile.getWorldPoint().getY());
			BufferedImage image = kitten.getItemManager().getImage(kittenTile.getIconID());
			OverlayUtil.renderTileOverlay(client, graphics, localPoint, image, Color.red.brighter());
		}

		return null;
	}
}
