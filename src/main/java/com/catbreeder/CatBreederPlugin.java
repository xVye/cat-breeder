package com.catbreeder;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WorldChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ImageUtil;

@Slf4j
@PluginDescriptor(
		name = "Cat Breeder",
		description = "Detailed information for raising kittens",
		tags = { "kitten", "cat", "breeding", "raising", "timer" }
)
public class CatBreederPlugin extends Plugin
{
	@Inject
	private Notifier notifier;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private CatBreederOverlay catBreederOverlay;

	@Inject
	private Client client;

	@Inject
	private CatBreederConfig config;

	@Inject
	private Kitten kitten;

	@Provides
	CatBreederConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CatBreederConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		infoBoxManager.addInfoBox(new CatBreederIndicator(ImageUtil.loadImageResource(getClass(), "icon.png"), this, config, client));
		kitten = new Kitten(client, this);
		kitten.updateActiveState();
	}

	@Override
	protected void shutDown() throws Exception
	{
		infoBoxManager.removeIf(t -> t instanceof CatBreederIndicator);

		if (kitten == null)
		{
			return;
		}
		kitten.setActive(false);
	}

	@Subscribe
	public void onWorldChanged(WorldChanged worldChanged)
	{
		if (kitten == null)
		{
			return;
		}
		kitten.updateActiveState();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (kitten == null)
		{
			return;
		}

		switch (gameStateChanged.getGameState()) {
			case LOGGED_IN:
				kitten.updateActiveState();
				break;
			case CONNECTION_LOST:
				kitten.setActive(false);
			default:
				break;
		}
	}

	public Kitten getKitten()
	{
		return kitten;
	}

	public boolean getKittenActive()
	{
		return kitten.getActive();
	}

	public CatBreederConfig getConfig()
	{
		return config;
	}
}
