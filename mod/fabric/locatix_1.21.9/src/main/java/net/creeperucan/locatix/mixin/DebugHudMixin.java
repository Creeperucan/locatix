package net.creeperucan.locatix.mixin;

import net.creeperucan.locatix.event.KeyInputHandler;
import net.minecraft.client.gui.components.debug.DebugScreenEntryList;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(DebugScreenEntryList.class)
public class DebugHudMixin {

    @Shadow
    private Map<ResourceLocation, DebugScreenEntryStatus> allStatuses;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void locatix$enableByDefault(CallbackInfo ci) {
        this.allStatuses.put(KeyInputHandler.CATEGORY, DebugScreenEntryStatus.IN_F3);
    }
}