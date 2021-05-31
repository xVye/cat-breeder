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

import com.google.inject.Provides;
import com.kittentimer.followers.Kitten;
import com.kittentimer.utils.KittenID;
import java.time.Instant;
import java.util.Objects;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

@Slf4j
@PluginDescriptor(
	name = "Kitten Timer"
)
public class KittenTimerPlugin extends Plugin
{
	@Inject
	private Notifier notifier;

	@Inject
	private ConfigManager configManager;

	@Getter
	@Inject
	private ItemManager itemManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Client client;

	@Getter
	private Kitten currentKitten;

	private boolean canNotify;

	@Provides
	KittenTimerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(KittenTimerConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		canNotify = true;
		log.info(KittenTimerConfig.class.getName() + " startUp()");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info(KittenTimerConfig.class.getName() + "shutDown()");
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		Widget playerDialog = client.getWidget(WidgetInfo.DIALOG_PLAYER);
		Widget newKittenDialog = client.getWidget(229, 1);

		if (newKittenDialog != null)
		{
			String newKittenDialogText = Text.removeTags(newKittenDialog.getText()).trim();
			if (!newKittenDialogText.equals(KittenMessage.CHAT_NEW_KITTEN))
			{
				return;
			}
			Player localPlayer = client.getLocalPlayer();
			if (localPlayer == null)
			{
				return;
			}

			NPC npcInteracting = (NPC) localPlayer.getInteracting();
			if (npcInteracting == null)
			{
				return;
			}

			currentKitten = new Kitten(client.getLocalPlayer(), npcInteracting, Instant.now());
			return;
		}

		if (playerDialog == null)
		{
			return;
		}

		Widget[] staticChildren = playerDialog.getStaticChildren();
		for (Widget child : staticChildren) // TODO: Find the right child id
		{
			String dialogText = Text.removeTags(child.getText()).trim();
			if (dialogText.length() <= 1 || child.isSelfHidden())
			{
				continue;
			}

			switch (dialogText)
			{
				case KittenMessage.CHAT_STROKE: // Message shows up empty - GAME_MESSAGE ok!
					// TODO: Count strokes & compare with time left
					canNotify = false;
					notifier.notify("You stroke your kitten.");
					break;
				case KittenMessage.CHAT_HUNGRY: // Message shows up empty - GAME_MESSAGE ok!
					// TODO: Display world overlay
					canNotify = false;
					notifier.notify("Your kitten is hungry!");
					break;
				case KittenMessage.CHAT_REALLY_HUNGRY: // Message shows up empty - GAME_MESSAGE ok! (You kitten is very hungry.)
					// TODO: Display world overlay (RED?)
					canNotify = false;
					notifier.notify("Your kitten is REALLY hungry!");
					break;
				case KittenMessage.CHAT_ATTENTION: // Message shows up empty - GAME_MESSAGE ok!
					// TODO: Display world overlay
					canNotify = false;
					notifier.notify("Your kitten wants attention!");
					break;
				case KittenMessage.CHAT_BALL_OF_WOOL:
					// TODO: Update timer stuff
					canNotify = false;
					notifier.notify("Your kitten loves to play with that ball of wool.");
					break;
				case KittenMessage.CHAT_GROWN_UP:
					if (canNotify)
					{
						notifier.notify("Your kitten has grown up to a healthy cat!");
					}
					canNotify = false;
					break;
				default:
					log.info(dialogText);
					break;
			}
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (!event.getType().equals(ChatMessageType.GAMEMESSAGE) && !event.getType().equals(ChatMessageType.NPC_EXAMINE))
		{
			return;
		}

		int varKitten = client.getVarpValue(KittenVarPlayer.FOLLOWER.getId());
		if (varKitten == -1)
		{
			return;
		}

		if (!verifyFollower())
		{
			return;
		}

		String message = Text.removeTags(event.getMessage());
		switch (message)
		{
			case KittenMessage.GAME_FEED:
				// TODO: Timer stuff
				break;
			case KittenMessage.GAME_RUN_AWAY:
				if (currentKitten != null)
				{
					currentKitten = null;
				}

				if (canNotify)
				{
					notifier.notify(message);
				}
				break;
			case KittenMessage.NPC_EXAMINE:
				if (!verifyFollower())
				{
					currentKitten = null;
				}
				// currentKitten.getExamineText();
				break;
			default:
				break;
		}
	}

	@Subscribe
	public void onWidgetClosed(WidgetClosed event)
	{
		int groupId = event.getGroupId();
		if (groupId == WidgetInfo.DIALOG_PLAYER.getGroupId() || groupId == 229)
		{
			canNotify = true;
		}
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		log.info(event.toString());

		Widget widget = client.getWidget(event.getGroupId());
		if (widget == null)
		{
			return;
		}

		for (String str : widget.getActions())
		{
			log.info(str);
		}
	}

	private boolean verifyFollower()
	{
		NPC npcKitten = (NPC) Objects.requireNonNull(client.getLocalPlayer()).getInteracting();
		return KittenID.contains(npcKitten.getId());
	}
}
