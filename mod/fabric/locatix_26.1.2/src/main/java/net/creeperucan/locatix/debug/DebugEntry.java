package net.creeperucan.locatix.debug;

import net.creeperucan.locatix.LocatixClient;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import org.jetbrains.annotations.Nullable;

public class DebugEntry implements DebugScreenEntry {
    private static final Minecraft client = Minecraft.getInstance();

    @Override
    public void display(DebugScreenDisplayer displayer, @Nullable Level level, @Nullable LevelChunk clientChunk, @Nullable LevelChunk serverChunk) {
        if (client.player == null || client.level == null) return;

        LocalPlayer player = client.player;
        BlockPos pos = LocatixClient.getBlockPos();

        if (pos != null) {
            int x = pos.getX();
            int z = pos.getZ();

            ResourceKey<Level> dimension = player.level().dimension();

            if (dimension == Level.NETHER) {
                displayer.addLine(String.format("§a[Locatix] §6Overworld (X, Z): §e%d, %d", x * 8, z * 8));
            } else if (dimension == Level.OVERWORLD) {
                displayer.addLine(String.format("§a[Locatix] §6Nether (X, Z): §e%d, %d", x / 8, z / 8));
            }
        }
    }

    @Override
    public boolean isAllowed(boolean reducedDebugInfo) {
        return true;
    }
}