package net.creeperucan.locatix.expansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.creeperucan.locatix.Locatix;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {

    private final Locatix plugin;
    public PlaceholderAPI(Locatix plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "locatix";
    }

    @Override
    public String getAuthor() {
        return "Creeperucan";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {

        if (player == null) {
            return "";
        }

        Location loc = player.getLocation();

        switch (params.toLowerCase()) {

            case "x":
                return String.valueOf(player.getLocation().getBlockX());

            case "y":
                return String.valueOf(player.getLocation().getBlockY());

            case "z":
                return String.valueOf(player.getLocation().getBlockZ());

            case "world":
                String worldName = player.getWorld().getName();
                String displayWorld = plugin.getConfig().getString("world-names." + worldName, worldName);

                if (displayWorld  == null) {
                    return "Unknown";
                }

                return displayWorld;

            case "nether_coords":
                if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
                    return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
                } else if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                    return (loc.getBlockX() / 8) + ", " + loc.getBlockY() + ", " + (loc.getBlockZ() / 8);
                } {
            }

            case "overworld_coords":
                if (player.getWorld().getEnvironment() == World.Environment.NORMAL) {
                    return loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ();
                } else if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
                    return (loc.getBlockX() * 8) + ", " + loc.getBlockY() + ", " + (loc.getBlockZ() * 8);
                } {
            }

            case "lastdeath_coords":
                Location deathLoc = player.getLastDeathLocation();
                if (deathLoc != null) {
                    return deathLoc.getBlockX() + ", " + deathLoc.getBlockY() + ", " + deathLoc.getBlockZ();
                }

            case "lastdeath_world":
                Location deathWorldLoc = player.getLastDeathLocation();

                if (deathWorldLoc == null || deathWorldLoc.getWorld() == null) {
                    return "Unknown";
                }

                String deathWorldName = deathWorldLoc.getWorld().getName();
                String displayDeathWorld = plugin.getConfig().getString("world-names." + deathWorldName, deathWorldName);
                plugin.getLogger().info("Death world raw: " + deathWorldLoc.getWorld().getName());
                plugin.getLogger().info("Display world: " + displayDeathWorld);

                if (displayDeathWorld  == null) {
                    return "Unknown";
                }

                return displayDeathWorld;

            default:
                return null;
        }
    }
}
