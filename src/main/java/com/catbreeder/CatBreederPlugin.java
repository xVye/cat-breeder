package com.catbreeder;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WorldChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

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
	private ItemManager itemManager;

	@Inject
	private Client client;

	@Inject
	private CatBreederConfig config;

	@Getter
	private boolean active;

	@Getter
	private CatTimer currentTimer;

	@Provides
	CatBreederConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CatBreederConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		//overlayManager.add(catBreederOverlay);
		recheckActive();
	}

	@Override
	protected void shutDown() throws Exception
	{
		//overlayManager.removeIf(t -> t instanceof CatBreederOverlay);
		removeTimer();
		currentTimer = null;
		active = false;
	}

	private void removeTimer()
	{
		infoBoxManager.removeInfoBox(currentTimer);
		currentTimer = null;
	}

	private void createTimer(Duration duration)
	{
		removeTimer();
		BufferedImage image = itemManager.getImage(ItemID.PET_KITTEN);
		currentTimer = new CatTimer(duration, image, this, active && config.displayTimer());
		infoBoxManager.addInfoBox(currentTimer);
	}

	private void resetTimer(long seconds)
	{
		createTimer(Duration.ofSeconds(seconds));
		log.info("Resetting timer.");
	}

	private void recheckActive()
	{
		checkAreaNpcs(client.getNpcs());
		log.info("Rechecking area12345.");
	}

	private void checkAreaNpcs(List<NPC> npcs)
	{
		for (NPC npc : npcs)
		{
			if (npc == null)
			{
				continue;
			}

			if (isNpcMatch(npc))
			{
				log.info("Found cat, enabling timer.");
				active = true;
				resetTimer(1200); // TODO: Change time to cat interactions.
				break;
			}
		}
	}

	private boolean isNpcMatch(NPC npc)
	{
		return npc.getInteracting().equals(client.getLocalPlayer()) && Objects.requireNonNull(npc.getName()).contains("Kitten");
	}

	@Subscribe
	public void onWorldChanged(WorldChanged event)
	{
		if (!active)
		{
			return;
		}
		recheckActive();
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		switch (event.getGameState())
		{
			case HOPPING:
			case LOGGED_IN:
				recheckActive();
				break;
			case CONNECTION_LOST:
				active = false;
				break;
			default:
				break;
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		String key = event.getKey();
		if ("catShowTimer".equals(key) && currentTimer != null)
		{
			currentTimer.setVisible(active && config.displayTimer());
		}
	}

	public CatBreederConfig getConfig()
	{
		return config;
	}
}
