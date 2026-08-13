package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand {

    private final Locatix plugin;
    public ReloadCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender) {

        if (!sender.hasPermission("locatix.admin.reload")) {
            sender.sendMessage(getLanguage(sender,"no-permission"));
            return;
        }

        plugin.reloadPlugin();
        sender.sendMessage(getLanguage(sender,"config-reload"));
    }

    private String getLanguage(CommandSender sender, String key) {
        if (sender instanceof Player player) {
            return plugin.getLanguageManager().getLangClient(player, key);
        }

        return plugin.getLanguageManager().getLang(key);
    }
}
