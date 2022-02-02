package com.projectilerage.runelite.PartyPlay;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PartyPlayPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PartyPlayPlugin.class);
		RuneLite.main(args);
	}
}