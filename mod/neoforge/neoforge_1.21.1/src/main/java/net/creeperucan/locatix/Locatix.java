package net.creeperucan.locatix;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(Locatix.MODID)
public class Locatix {
    public static final String MODID = "locatix";
    public static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info(MODID + ": Server Starting");
    }
}
