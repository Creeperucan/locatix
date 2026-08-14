package net.creeperucan.locatix.event;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.general.Actionbar;
import net.creeperucan.locatix.general.Converter;
import net.creeperucan.locatix.general.CopyCoordinate;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Locatix.MODID, value = Dist.CLIENT)
public class KeyInputHandler {
	public static final String CATEGORY = "key.category.locatix.mod_key_category";

	public static final String COORDS_KEY = "key.locatix.coord_enabled";
	public static KeyMapping coordsKey;

	public static final String CONVERT_KEY = "key.locatix.coord_convert";
	public static KeyMapping convertKey;

	public static final String COPY_COORDINATE_KEY = "key.locatix.copy_coord";
	public static KeyMapping copyCoordinateKey;

	@SubscribeEvent
	public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
		coordsKey = new KeyMapping(
				COORDS_KEY,
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_F9,
				CATEGORY
		);

		convertKey = new KeyMapping(
				CONVERT_KEY,
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_F10,
				CATEGORY
		);

		copyCoordinateKey = new KeyMapping(
				COPY_COORDINATE_KEY,
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_V,
				CATEGORY
		);

		event.register(coordsKey);
		event.register(convertKey);
		event.register(copyCoordinateKey);
	}

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Post event) {
		if (coordsKey == null || convertKey == null || copyCoordinateKey == null) return;

		while (coordsKey.consumeClick()) {
			Actionbar.changeStatus();
		}

		while (convertKey.consumeClick()) {
			Converter.convertCoordinates();
		}

		while (copyCoordinateKey.consumeClick()) {
			CopyCoordinate.copyCoordinates();
		}
	}
}
