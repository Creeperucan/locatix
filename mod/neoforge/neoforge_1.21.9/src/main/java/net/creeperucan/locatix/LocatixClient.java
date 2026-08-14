package net.creeperucan.locatix;

import net.creeperucan.locatix.general.Actionbar;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import net.minecraft.client.Minecraft;

@Mod(value = Locatix.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Locatix.MODID, value = Dist.CLIENT)
public class LocatixClient {

    public LocatixClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        Locatix.LOGGER.info(Locatix.MODID + ": Mod enabled");
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        if (!Actionbar.getStatus()) return;

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        Actionbar.getCoordinates();
    }


}
