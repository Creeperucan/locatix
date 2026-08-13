package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class ConfigResetCommand {

    private final Locatix plugin;
    public ConfigResetCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String confirm) {

        if (!sender.hasPermission("locatix.admin.resetconfig")) {
            sender.sendMessage(getLanguage(sender, "no-permission"));
            return;
        }

        if (!confirm.equalsIgnoreCase("confirm")) {
            sender.sendMessage(getLanguage(sender, "usage-command") + "/locatix resetconfig confirm");
            return;
        }

        File configFile = new File(plugin.getDataFolder(), "config.yml");

        if (configFile.exists() && !configFile.delete()) {
            sender.sendMessage(getLanguage(sender, "config-reset-error"));
            return;
        }

        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        plugin.getLanguageManager().reload();
        plugin.getLocationSystem().reload();

        sender.sendMessage(getLanguage(sender,"config-reset"));
    }

    private String getLanguage(CommandSender sender, String key) {
        if (sender instanceof Player player) {
            return plugin.getLanguageManager().getLangClient(player, key);
        }

        return plugin.getLanguageManager().getLang(key);
    }
}
