package net.creeperucan.locatix.general;

import net.creeperucan.locatix.LocatixClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class Actionbar {
    private static final Minecraft client = Minecraft.getInstance();
    static Boolean status = true;

    public static void getCoordinates() {
		if (client.player == null) return;

		BlockPos pos = LocatixClient.getBlockPos();
		if (pos != null) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			String X = "§eX: §6" + x + " ";
			String Y = "§eY: §6" + y + " ";
			String Z = "§eZ: §6" + z + " ";
			String msg = X + Y + Z;

			client.gui.setOverlayMessage(Component.literal(msg), true);
		}
    }

	public static void changeStatus() {
		status = !status;
	}
	public static boolean getStatus() {
		return status;
	}
}
