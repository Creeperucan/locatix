package net.creeperucan.locatix.commands.subcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import net.creeperucan.locatix.Locatix;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathLocationCommand {

    private final Locatix plugin;
    public DeathLocationCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLanguageManager().getLang("player-only"));
            return;
        }

        if (!sender.hasPermission("locatix.player.lastdeath")) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"no-permission"));
            return;
        }

        Location location =  plugin.getDeathLocations().get(player.getUniqueId());

        if (location == null) {
            player.sendMessage(plugin.getLanguageManager().getLangClient(player,"no-death-location"));
            return;
        }

        boolean canTeleport = plugin.getConfig().getBoolean("last-death-teleport");

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        String deathLocation = "&6X: &e" + x + " &6Y: &e" + y + " &6Z: &e" + z;
        deathLocation = ChatColor.translateAlternateColorCodes('&', deathLocation);

        String worldName = location.getWorld().getName();
        String deathWorldName = plugin.getConfig().getString("world-names." + worldName, worldName);

        player.sendMessage(
                plugin.getLanguageManager().getLangClient(player,"last-death-title") + "\n" +
                plugin.getLanguageManager().getLangClient(player,"last-death-world").replace("${world}", deathWorldName)
        );

        if (canTeleport) {
            Component locationMessage = LegacyComponentSerializer.legacySection().deserialize(
                    plugin.getLanguageManager().getLangClient(player,"last-death-location") + deathLocation)
                    .clickEvent(ClickEvent.callback(audience -> {
                        player.teleport(location);
                        plugin.getDeathLocations().remove(player.getUniqueId());
                        player.sendMessage(plugin.getLanguageManager().getLangClient(player,"death-teleported"));
                    }))
                    .hoverEvent(HoverEvent.showText(LegacyComponentSerializer.legacySection().deserialize(
                            plugin.getLanguageManager().getLangClient(player,"death-location-hover")
                    )));

            player.sendMessage(locationMessage);
        } else {
            player.sendMessage(plugin.getLanguageManager().getLangClient(player,"last-death-location") + deathLocation);
        }

        player.sendMessage(plugin.getLanguageManager().getLangClient(player,"last-death-title"));
    }
}
