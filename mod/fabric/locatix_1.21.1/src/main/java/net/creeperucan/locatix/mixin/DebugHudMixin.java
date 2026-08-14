package net.creeperucan.locatix.mixin;

import net.creeperucan.locatix.LocatixClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public class DebugHudMixin {

	private static final Minecraft client = Minecraft.getInstance();

	@Inject(method = "getGameInformation", at = @At("RETURN"))
	private void locatix$onGetLeftText(CallbackInfoReturnable<List<String>> cir) {
		List<String> list = cir.getReturnValue();

		if (list == null) return;
		if (client.player == null) return;

		LocalPlayer player = client.player;
		BlockPos pos = LocatixClient.getBlockPos();
		if (pos != null) {
			int x = pos.getX();
			int z = pos.getZ();

			ResourceKey<Level> dimension = player.level().dimension();

			list.add("");
			if (dimension == Level.NETHER) {
				list.add(String.format("§a[Locatix] §6Overworld (X, Z): §e%d, %d", x * 8, z * 8));
			} else if (dimension == Level.OVERWORLD) {
				list.add(String.format("§a[Locatix] §6Nether (X, Z): §e%d, %d", x / 8, z / 8));
			}
		}
	}
}