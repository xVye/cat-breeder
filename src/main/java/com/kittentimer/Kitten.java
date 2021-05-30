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
package com.kittentimer;

import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.game.ItemManager;

import javax.inject.Inject;
import java.time.Duration;

public class Kitten
{
	public static final int HUNGER_TIME = 1440;
	public static final int HUNGER_WARN_TIME = 180;
	public static final int ATTENTION_TIME_DEFAULT = 1500;
	public static final int ATTENTION_WARN_TIME = 420;
	public static final int ATTENTION_STROKE_TIME = 1080;
	public static final int ATTENTION_2ND_STROKE_TIME = 1500;
	public static final int ATTENTION_WOOL_TIME = 3060;
	public static final int GROW_UP_TIME = 10800;

	@Getter
	private boolean isActive;

	@Getter
	private WorldPoint location;

	@Getter
	private KittenTile kittenTile;

	private NPC npcKitten;
	private KittenGrowthTimer growthTimer;

	@Getter
	@Inject
	private ItemManager itemManager;

	private final Client client;
	private final KittenTimerPlugin plugin;

	public Kitten(Client client, KittenTimerPlugin plugin, WorldPoint location)
	{
		this.client = client;
		this.plugin = plugin;
		this.location = location;
		isActive = false;

		kittenTile = new KittenTile(location);
	}

	public void setActive(boolean active)
	{
		setActive(active, null);
	}

	public void setActive(boolean active, NPC npcKitten)
	{
		this.npcKitten = npcKitten;
		isActive = active;
	}

	private void createTimer(Duration duration)
	{
		growthTimer = new KittenGrowthTimer(duration, itemManager.getImage(ItemID.PET_KITTEN_1556), plugin, isActive && plugin.getConfig().displayGrowUpTimer());
	}

	public long getTimeRemaining()
	{
		return Duration.between(growthTimer.getStartTime(), growthTimer.getEndTime()).getSeconds();
	}
}
