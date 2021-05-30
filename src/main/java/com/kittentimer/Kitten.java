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

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.client.game.ItemManager;

import javax.inject.Inject;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Kitten
{
	public enum KittenID
	{
		// Black - Gray - White
		KITTEN_1555(1555, ItemID.PET_KITTEN),
		CAT_1561(1561, ItemID.PET_CAT),

		// White
		KITTEN_1556(1556, ItemID.PET_KITTEN_1556),
		CAT_1562(1562, ItemID.PET_CAT_1562),

		// Yellow
		KITTEN_1557(1557, ItemID.PET_KITTEN_1557),
		CAT_1563(1563, ItemID.PET_CAT_1563),

		// Black
		KITTEN_1558(1558, ItemID.PET_KITTEN_1558),
		CAT_1564(1564, ItemID.PET_CAT_1565),

		// Brown
		KITTEN_1559(1559, ItemID.PET_KITTEN_1559),
		CAT_1565(1565, ItemID.PET_CAT_1565),

		// Gray
		KITTEN_1560(1560, ItemID.PET_KITTEN_1560),
		CAT_1566(1566, ItemID.PET_CAT_1566);

		private static final Map<Integer, KittenID> kittens = new HashMap<>();
		private final int id;
		private final int itemSpriteId;

		static
		{
			for (KittenID kitten : values())
			{
				kittens.put(kitten.getId(), kitten);
			}
		}

		KittenID(int id, int itemSpriteId)
		{
			this.id = id;
			this.itemSpriteId = itemSpriteId;
		}

		public int getItemSpriteId()
		{
			return itemSpriteId;
		}

		public int getId()
		{
			return id;
		}

		public static KittenID find(int id)
		{
			return kittens.get(id);
		}
	}

	public static final int HUNGER_TIME = 1440;
	public static final int HUNGER_WARN_TIME = 180;
	public static final int ATTENTION_TIME_DEFAULT = 1500;
	public static final int ATTENTION_WARN_TIME = 420;
	public static final int ATTENTION_STROKE_TIME = 1080;
	public static final int ATTENTION_2ND_STROKE_TIME = 1500;
	public static final int ATTENTION_WOOL_TIME = 3060;
	public static final int GROW_UP_TIME = 10800;

	private NPC npcKitten;
	private KittenID kittenID;
	private boolean isActive;
	private KittenGrowthTimer growthTimer;

	@Inject
	private ItemManager itemManager;

	private final Client client;
	private final KittenTimerPlugin plugin;

	public Kitten(Client client, KittenTimerPlugin plugin)
	{
		this.client = client;
		this.plugin = plugin;
		isActive = false;
	}

	public boolean getActive()
	{
		return isActive;
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
		growthTimer = new KittenGrowthTimer(duration, itemManager.getImage(kittenID.itemSpriteId), plugin, isActive && plugin.getConfig().displayGrowUpTimer());
	}

	public KittenID getId()
	{
		return kittenID;
	}

	public long getTimeRemaining()
	{
		return Duration.between(growthTimer.getStartTime(), growthTimer.getEndTime()).getSeconds();
	}
}
