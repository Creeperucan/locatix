package net.creeperucan.locatix;

import net.creeperucan.locatix.debug.DebugEntry;
import net.creeperucan.locatix.event.KeyInputHandler;
import net.creeperucan.locatix.general.Actionbar;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;

public class LocatixClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		DebugScreenEntries.register(KeyInputHandler.CATEGORY, new DebugEntry());
		KeyInputHandler.registerKeys();

		ClientTickEvents.START_CLIENT_TICK.register(client -> {
			if (!Actionbar.getStatus()) return;
			Actionbar.getCoordinates();
		});

		System.out.println(Locatix.MOD_ID + " client initialized");
	}

	public static BlockPos getBlockPos() {
		LocalPlayer player = Minecraft.getInstance().player;
		return player != null ? player.blockPosition() : null;
	}
}
