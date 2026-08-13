package net.creeperucan.locatix.events;

import net.creeperucan.locatix.Locatix;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final Locatix plugin;
    public DeathListener(Locatix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Location location = player.getLocation();

        plugin.getDeathLocations().put(player.getUniqueId(), location);
    }
}
