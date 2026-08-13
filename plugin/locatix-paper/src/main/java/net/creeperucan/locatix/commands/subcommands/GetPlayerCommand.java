package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.gui.PlayerInfoGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPlayerCommand {

    private final Locatix plugin;
    private final PlayerInfoGUI playerInfoGUI;

    public GetPlayerCommand(Locatix plugin) {
        this.plugin = plugin;
        this.playerInfoGUI = new PlayerInfoGUI(plugin);
    }

    public void execute(CommandSender sender, String plrName) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLanguageManager().getLang("player-only"));
            return;
        }
        if (!sender.hasPermission("locatix.admin.get")) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"no-permission"));
            return;
        }

        Player target = plugin.getServer().getPlayer(plrName);
        if (target == null) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"player-not-found"));
            return;
        }

        playerInfoGUI.open(player, target);
    }
}
