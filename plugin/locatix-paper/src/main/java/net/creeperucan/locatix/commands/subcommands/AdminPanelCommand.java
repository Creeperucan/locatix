package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.gui.AdminPanelGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminPanelCommand {

    private final Locatix plugin;
    private final AdminPanelGUI adminPanelGUI;

    public AdminPanelCommand(Locatix plugin) {
        this.plugin = plugin;
        this.adminPanelGUI = new AdminPanelGUI(plugin);
    }

    public void execute(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLanguageManager().getLang("player-only"));
            return;
        }

        if (!sender.hasPermission("locatix.admin.panel")) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"no-permission"));
            return;
        }

        adminPanelGUI.open(player);
    }
}
