package com.catbreeder;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("cat_breeder")
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
}
