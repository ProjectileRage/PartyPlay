/*
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.projectilerage.runelite.partyplay;

import lombok.AllArgsConstructor;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Units;

@ConfigGroup(PartyPlayConfig.GROUP)
public interface PartyPlayConfig extends Config
{
	String GROUP = "partyplay";

	@ConfigItem(
			keyName = "includeSelf",
			name = "Include yourself",
			description = "Shows yourself in the panel as part of the party",
			position = 1
	)
	default boolean includeSelf()
	{
		return false;
	}

	@ConfigItem(
			keyName = "recolorNames",
			name = "Recolor names",
			description = "Recolor party members names based on unique color hash",
			position = 2
	)
	default boolean recolorNames()
	{
		return true;
	}

	@ConfigItem(
		keyName = "actionTimeout",
		name = "Activity timeout",
		description = "Configures after how long of not updating activity will be reset (in minutes)",
		position = 3
	)
	@Units(Units.MINUTES)
	default int actionTimeout()
	{
		return 5;
	}

	@ConfigItem(
			keyName = "showSlayerActivity",
			name="Slayer",
			description = "Show/share slayer activity information e.g. Assigned monster + count",
			position = 4
	)
	default boolean showSlayerActivity() { return true; }

	@ConfigItem(
		keyName = "showMainMenu",
		name = "Main Menu",
		description = "Share status when in main menu",
		position = 5
	)
	default boolean showMainMenu()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showSkillActivity",
		name = "Skilling",
		description = "Show/share activity while training skills",
		position = 6
	)
	default boolean showSkillingActivity()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showBossActivity",
		name = "Bosses",
		description = "Show/share activity and location while at bosses",
		position = 7
	)
	default boolean showBossActivity()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showCityActivity",
		name = "Cities",
		description = "Show/share activity and location while in cities",
		position = 8
	)
	default boolean showCityActivity()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showDungeonActivity",
		name = "Dungeons",
		description = "Show/share activity and location while in dungeons",
		position = 9
	)
	default boolean showDungeonActivity()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showMinigameActivity",
		name = "Minigames",
		description = "Show/share activity and location while in minigames",
		position = 10
	)
	default boolean showMinigameActivity()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showRaidingActivity",
		name = "Raids",
		description = "Show/share activity and location while in Raids",
		position = 11
	)
	default boolean showRaidingActivity()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showRegionsActivity",
		name = "Regions",
		description = "Show/share activity and location while in other regions",
		position = 12
	)
	default boolean showRegionsActivity()
	{
		return true;
	}
}
