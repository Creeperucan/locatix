package net.creeperucan.locatix.general;

import net.creeperucan.locatix.LocatixClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class Converter {
	private static final Minecraft client = Minecraft.getInstance();

	public static void convertCoordinates() {
		if (client.player == null) return;

		ResourceKey<Level> dimension = client.player.level().dimension();
		String title;
		if (dimension == Level.OVERWORLD) {
			title = "§cNether Coordinates";
		} else if (dimension == Level.NETHER) {
			title = "§aOverworld Coordinates";
		} else {
			return;
		}

		BlockPos pos = LocatixClient.getBlockPos();
		if (pos != null) {
			int x = pos.getX();
			int z = pos.getZ();

			int xDimension = dimension == Level.OVERWORLD ? x / 8 : x * 8;
			int zDimension = dimension == Level.OVERWORLD ? z / 8 : z * 8;

			String msg = title + ": §eX: §6" + xDimension + " §eZ: §6" + zDimension;
			client.player.displayClientMessage(Component.literal(msg), false);
		}
	}
}
