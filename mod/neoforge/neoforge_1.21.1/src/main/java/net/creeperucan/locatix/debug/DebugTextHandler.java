package net.creeperucan.locatix.event;

import net.creeperucan.locatix.Locatix;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;

@EventBusSubscriber(modid = Locatix.MODID, value = Dist.CLIENT)
public class DebugTextHandler {
    @SubscribeEvent
    public static void onDebugText(CustomizeGuiOverlayEvent.DebugText event) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null || client.level == null) {
            return;
        }

        LocalPlayer player = client.player;

        int x = player.blockPosition().getX();
        int z = player.blockPosition().getZ();

        ResourceKey<Level> dimension = player.level().dimension();

        event.getLeft().add("");

        if (dimension == Level.NETHER) {
            event.getLeft().add(String.format(
                    "§a[Locatix] §6Overworld (X, Z): §e%d, %d",
                    x * 8,
                    z * 8
            ));
        } else if (dimension == Level.OVERWORLD) {
            event.getLeft().add(String.format(
                    "§a[Locatix] §6Nether (X, Z): §e%d, %d",
                    x / 8,
                    z / 8
            ));
        }
    }
}