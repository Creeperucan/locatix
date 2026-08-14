package net.creeperucan.locatix.event;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.general.Actionbar;
import net.creeperucan.locatix.general.Converter;
import net.creeperucan.locatix.general.CopyCoordinate;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
	public static final ResourceLocation CATEGORY = ResourceLocation.fromNamespaceAndPath(Locatix.MOD_ID, "mod_key_category");
	public static final KeyMapping.Category MOD_KEY_CATEGORY = KeyMapping.Category.register(CATEGORY);

	public static final String COORDS_KEY = "key.locatix.coord_enabled";
	public static KeyMapping coordsKey;

	public static final String CONVERT_KEY = "key.locatix.coord_convert";
	public static KeyMapping convertKey;

	public static final String COPY_COORDINATE_KEY = "key.locatix.copy_coord";
	public static KeyMapping copyCoordinateKey;

	public static void registerKeys() {
		coordsKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
			COORDS_KEY,
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_F9,
			MOD_KEY_CATEGORY
		));

		convertKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
			CONVERT_KEY,
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_F10,
			MOD_KEY_CATEGORY
		));

		copyCoordinateKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
			COPY_COORDINATE_KEY,
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_V,
			MOD_KEY_CATEGORY
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (KeyInputHandler.coordsKey == null || KeyInputHandler.convertKey == null) { return; }

			while (coordsKey.consumeClick()) {
				Actionbar.changeStatus();
			}

			while (convertKey.consumeClick()) {
				Converter.convertCoordinates();
			}

			while (copyCoordinateKey.consumeClick()) {
				CopyCoordinate.copyCoordinates();
			}
		});
	}
}
