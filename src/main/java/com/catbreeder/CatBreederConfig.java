package com.catbreeder;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("catBreeder")
public interface CatBreederConfig extends Config
{
	@ConfigItem(
			keyName = "catShowTimer",
			name = "Display grow-up timer",
			description = "Toggle to show the grow-up timer"
	)
	default boolean displayTimer()
	{
		return true;
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
