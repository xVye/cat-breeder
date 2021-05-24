package com.catbreeder;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CatBreederPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CatBreederPlugin.class);
		RuneLite.main(args);
	}
}