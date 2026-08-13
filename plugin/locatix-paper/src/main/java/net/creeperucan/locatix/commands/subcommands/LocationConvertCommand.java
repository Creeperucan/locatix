package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.World;

public class LocationConvertCommand {

    private final Locatix plugin;
    public LocationConvertCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String mode) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLanguageManager().getLang("player-only"));
            return;
        }

        if (!sender.hasPermission("locatix.player.convert")) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"no-permission"));
            return;
        }

        Location location = player.getLocation();
        World.Environment environment = player.getWorld().getEnvironment();

        int x = location.getBlockX();
        int z = location.getBlockZ();

        String netherConvert = "&6X: &e" + x/8 + " &6Z: &e" + z/8;
        String worldConvert = "&6X: &e" + x*8 + " &6Z: &e" + z*8;

        netherConvert = ChatColor.translateAlternateColorCodes('&', netherConvert);
        worldConvert = ChatColor.translateAlternateColorCodes('&', worldConvert);

        if (mode.equalsIgnoreCase("nether")) {
            if (environment == World.Environment.NETHER) {
                player.sendMessage(plugin.getLanguageManager().getLangClient(player,"already-in-nether"));
                return;
            }

            //player.sendMessage("Test: " + "X: " + x + " Z: " + z);
            player.sendMessage(plugin.getLanguageManager().getLangClient(player,"nether-location") + netherConvert);

        } else if (mode.equalsIgnoreCase("world")) {
            if (environment == World.Environment.NORMAL) {
                player.sendMessage(plugin.getLanguageManager().getLangClient(player,"already-in-overworld"));
                return;
            }

            //player.sendMessage("Test: " + "X: " + x + " Z: " + z);
            player.sendMessage(plugin.getLanguageManager().getLangClient(player,"overworld-location") + worldConvert);
        }
    }
}
