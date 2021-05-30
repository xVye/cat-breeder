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

public abstract class KittenMessage
{
	/**
	 * Chat messages.
	 */
	public static final String CHAT_NEW_KITTEN = "Gertrude gives you another kitten.";
	public static final String CHAT_STROKE = "That cat sure loves to be stroked.";
	public static final String CHAT_BALL_OF_WOOL = "That kitten loves to play with that ball of wool. I think itis its favourite.";
	public static final String CHAT_HUNGRY = "I think it's hungry!";
	public static final String CHAT_REALLY_HUNGRY = "I think it's really hungry!";
	public static final String CHAT_ATTENTION = "I think it wants some attention.";
	public static final String CHAT_GROWN_UP = "Your kitten has grown into a healthy cat that can hunt for itself.";

	/**
	 * Game messages.
	 */
	public static final String GAME_FEED = "The kitten gobbles up the fish.";
	public static final String GAME_STROKE = "You softly stroke your cat.";
	public static final String GAME_HUNGRY = "Your kitten is hungry.";
	public static final String GAME_REALLY_HUNGRY = "Your kitten is very hungry.";
	public static final String GAME_ATTENTION = "Your kitten wants attention.";
	public static final String GAME_ATTENTION_SECOND = "Your kitten really wants attention.";
	public static final String GAME_RUN_AWAY = "The cat has run away.";

	/**
	 * Examine.
	 */
	public static final String NPC_EXAMINE = "A friendly little pet.";
}
