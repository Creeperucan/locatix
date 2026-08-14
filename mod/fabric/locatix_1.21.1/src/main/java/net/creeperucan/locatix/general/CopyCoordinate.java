package net.creeperucan.locatix.general;

import net.creeperucan.locatix.LocatixClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class CopyCoordinate {
    private static final Minecraft client = Minecraft.getInstance();

    public static void copyCoordinates() {
        if (client.player == null) return;

        BlockPos pos = LocatixClient.getBlockPos();
        if (pos != null) {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            String formattedCoords = String.format("%d %d %d", x, y, z);
            client.keyboardHandler.setClipboard(formattedCoords);

            client.player.displayClientMessage(Component.translatable("text.locatix.copied"), false);
        }
    }
}
