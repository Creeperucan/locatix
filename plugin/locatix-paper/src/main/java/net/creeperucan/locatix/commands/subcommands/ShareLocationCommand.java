package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ShareLocationCommand {

    private final Locatix plugin;
    public ShareLocationCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String plrName) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLanguageManager().getLang("player-only"));
            return;
        }

        if (!sender.hasPermission("locatix.player.share")) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"no-permission"));
            return;
        }

        // Cooldown Control
        long cooldown =  Math.max(0, plugin.getConfig().getLong("share-location-cooldown", 30));
        UUID uuid = player.getUniqueId();

        if (plugin.getShareCooldowns().containsKey(uuid)) {
            long lastUse = plugin.getShareCooldowns().get(uuid);
            long secondsLeft = cooldown -((System.currentTimeMillis() - lastUse) / 1000);

            if (secondsLeft > 0) {
                sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"share-location-cooldown").replace("${time}", Long.toString(secondsLeft)));
                return;
            }
        }

        // Player Control
        Player target = plugin.getServer().getPlayer(plrName);
        if (target == null) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"player-not-found"));
            return;
        }

        // Sender
        String myselfName = player.getName();
        if (plrName.equalsIgnoreCase(myselfName)) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"share-myself-error"));
            return;
        }

        Location myselfLocation = player.getLocation();
        int X = myselfLocation.getBlockX();
        int Y = myselfLocation.getBlockY();
        int Z = myselfLocation.getBlockZ();
        String world = myselfLocation.getWorld().getName();

        String location = " &6World: &e" + world + "&7| &6X: &e"  + X + " &6Y: &e" + Y + " &6Z: &e" + Z;
        location = ChatColor.translateAlternateColorCodes('&', location);

        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0f, 1.0f); // Notification Sound
        target.sendMessage(
                plugin.getLanguageManager().getLangClient(player,"share-location-notification").replace("${player}", myselfName) + "\n" +
                plugin.getLanguageManager().getLangClient(player,"share-location").replace("${player}", myselfName) + location
        );

        plugin.getShareCooldowns().put(
                player.getUniqueId(),
                System.currentTimeMillis()
        );
    }
}
