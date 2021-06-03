/*
 * Copyright (c) 2021, xVye
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.kittentimer.overlays;

import com.kittentimer.KittenTimerConfig;
import com.kittentimer.KittenTimerPlugin;
import com.kittentimer.followers.Kitten;
import com.kittentimer.utils.KittenID;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

@Slf4j
public class KittenSceneOverlay extends Overlay
{
	private static final int ICON_OFFSET_Z = 32;

	private final Client client;
	private final KittenTimerConfig config;
	private final KittenTimerPlugin plugin;

	public KittenSceneOverlay(Client client, KittenTimerConfig config, KittenTimerPlugin plugin)
	{
		this.client = client;
		this.config = config;
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D g)
	{
		Kitten kitten = plugin.getCurrentKitten();
		if (kitten != null && kitten.isOverlayActive())
		{
			renderNpcOverlay(g, kitten.getNpc(), config.getHighlightColor());
		}
		return null;
	}

	private void renderNpcOverlay(Graphics2D g, NPC actor, Color color)
	{
		NPCComposition npcComposition = actor.getTransformedComposition();
		if (npcComposition == null || !npcComposition.isInteractible() || actor.isDead())
		{
			return;
		}

		if (config.highlightTile())
		{
			int size = npcComposition.getSize();
			LocalPoint localPoint = actor.getLocalLocation();
			Polygon tilePoly = Perspective.getCanvasTileAreaPoly(client, localPoint, size);
			renderPoly(g, color, tilePoly);
			renderIcon(actor, localPoint, g);
		}
	}

	private void renderPoly(Graphics2D g, Color color, Shape polygon)
	{
		if (polygon != null)
		{
			g.setColor(color);
			g.setStroke(new BasicStroke(2));
			g.draw(polygon);
			g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
			g.fill(polygon);

			BufferedImage image = plugin.getItemManager().getImage(KittenID.getIconId(plugin.getCurrentKitten().getId()));
			g.drawImage(
				image,
				(int) polygon.getBounds2D().getCenterX() - image.getWidth() / 2,
				(int) polygon.getBounds2D().getCenterY() - image.getHeight(),
				null);
		}
	}

	private void renderIcon(final NPC npc, final LocalPoint localPoint, final Graphics2D g)
	{
		if (localPoint == null || npc.getTransformedComposition() == null)
		{
			return;
		}

		final Color color = config.getHighlightColor();
		final NPCComposition npcComp = npc.getTransformedComposition();
		final LocalPoint centerLocalPoint = new LocalPoint(
			localPoint.getX() + Perspective.LOCAL_TILE_SIZE * (npcComp.getSize() - 1) / 2,
			localPoint.getY() + Perspective.LOCAL_TILE_SIZE * (npcComp.getSize() - 1) / 2
		);

		final int kittenIconId = KittenID.getIconId(plugin.getCurrentKitten().getId());
		if (kittenIconId == 0)
		{
			log.info("KittenSceneOverlay.renderIcon: KittenID is 0!");
			return;
		}

		final BufferedImage image = plugin.getItemManager().getImage(kittenIconId);
		//OverlayUtil.renderActorOverlayImage(g, npc, image, Color.RED.brighter(), -ICON_OFFSET_Z);
	}
}
