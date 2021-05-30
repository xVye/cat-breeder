package com.kittentimer;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("kittenTimer")
public interface KittenTimerConfig extends Config
{
	@ConfigItem(
		keyName = "catShowInteractionTimer",
		name = "Display interaction timer",
		description = "Toggle to show the interaction timer"
	)
	default boolean displayInteractionTimer()
	{
		return true;
	}

	@ConfigItem(
		keyName = "catShowGrowUpTimer",
		name = "Display grow-up timer",
		description = "Toggle to show the grow-up timer"
	)
	default boolean displayGrowUpTimer()
	{
		return false;
	}

	@ConfigItem(
		keyName = "catShowNotifications",
		name = "Enable notifications",
		description = "Toggle to recieve notifications"
	)
	default boolean displayNotifications()
	{
		return false;
	}
}
