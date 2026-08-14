package net.creeperucan.locatix.general;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class Converter {
	private static final Minecraft client = Minecraft.getInstance();

	public static void convertCoordinates() {
		if (client.player == null) {
			return;
		}

		ResourceKey<Level> dimension = client.player.level().dimension();

		int multiplier;
		String title;

		if (dimension == Level.OVERWORLD) {
			multiplier = 1;
			title = "§cNether Coordinates";
		} else if (dimension == Level.NETHER) {
			multiplier = 8;
			title = "§aOverworld Coordinates";
		} else {
			return;
		}

		int x = dimension == Level.OVERWORLD
				? client.player.blockPosition().getX() / 8
				: client.player.blockPosition().getX() * 8;

		int z = dimension == Level.OVERWORLD
				? client.player.blockPosition().getZ() / 8
				: client.player.blockPosition().getZ() * 8;

		String msg = title + ": §eX: §6" + x + " §eZ: §6" + z;

		client.player.displayClientMessage(Component.literal(msg), false);
	}
}
