package net.creeperucan.locatix.general;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class CopyCoordinate {
    private static final Minecraft client = Minecraft.getInstance();

    public static void copyCoordinates() {
        if (client.player == null) {
            return;
        }

        int x = client.player.blockPosition().getX();
        int y = client.player.blockPosition().getY();
        int z = client.player.blockPosition().getZ();

        String formattedCoords = String.format("%d %d %d", x, y, z);

        client.keyboardHandler.setClipboard(formattedCoords);
        client.player.sendSystemMessage(Component.translatable("text.locatix.copied"));
    }
}
