package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationCommand {

    private final Locatix plugin;
    public LocationCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLanguageManager().getLang("player-only"));
            return;
        }

        if (!sender.hasPermission("locatix.player.location")) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"no-permission"));
            return;
        }

        if (plugin.getPlayerDataManager().isCoordinatesDisabled(player.getUniqueId())) {
            plugin.getPlayerDataManager().setCoordinatesDisabled(player.getUniqueId(), false);
            player.sendMessage(plugin.getLanguageManager().getLangClient(player,"coordinates-enabled"));
        } else {
            plugin.getPlayerDataManager().setCoordinatesDisabled(player.getUniqueId(), true);
            player.sendMessage(plugin.getLanguageManager().getLangClient(player,"coordinates-disabled"));
        }
    }
}
