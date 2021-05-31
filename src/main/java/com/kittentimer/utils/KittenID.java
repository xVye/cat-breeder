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
package com.kittentimer.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.ItemID;
import net.runelite.api.NpcID;

@AllArgsConstructor
@Getter
public enum KittenID
{
	DEFAULT_KITTEN(ItemID.FLUFFS_KITTEN, NpcID.KITTEN),
	KITTEN_2(ItemID.PET_KITTEN, NpcID.KITTEN_5591),
	KITTEN_3(ItemID.PET_KITTEN_1556, NpcID.KITTEN_5592),
	KITTEN_4(ItemID.PET_KITTEN_1557, NpcID.KITTEN_5593),
	KITTEN_5(ItemID.PET_KITTEN_1558, NpcID.KITTEN_5594),
	KITTEN_6(ItemID.PET_KITTEN_1559, NpcID.KITTEN_5595),
	KITTEN_7(ItemID.PET_KITTEN_1560, NpcID.KITTEN_5596),
	HELLKITTEN(ItemID.HELLKITTEN, NpcID.HELLKITTEN);

	private final int iconId;
	private final int id;

	public static boolean contains(int npcId)
	{
		for (KittenID kittenID : KittenID.values())
		{
			if (kittenID.getId() == npcId)
			{
				return true;
			}
		}
		return false;
	}

	public static int getIconId(int npcId)
	{
		for (KittenID kittenID : KittenID.values())
		{
			if (kittenID.getId() == npcId)
			{
				return kittenID.iconId;
			}
		}
		return 0;
	}
}
