package net.creeperucan.locatix.general;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class Actionbar {
    private static final Minecraft client = Minecraft.getInstance();
    static Boolean status = true;

    public static void getCoordinates() {
        if (client.player == null) {
            return;
        }

		int x = client.player.getBlockX();
		int y = client.player.getBlockY();
		int z = client.player.getBlockZ();
		String msg = String.format("§eX: §6%d §eY: §6%d §eZ: §6%d", x, y, z);

		client.gui.setOverlayMessage(Component.literal(msg), true);
	}

	public static void changeStatus() {
		status = !status;
	}
	public static boolean getStatus() {
		return status;
	}
}
