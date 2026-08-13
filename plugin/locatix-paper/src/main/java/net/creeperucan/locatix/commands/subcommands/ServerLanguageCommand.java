package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class ServerLanguageCommand {

    private final Locatix plugin;
    public ServerLanguageCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String language) {

        File langFile = new File(plugin.getDataFolder(), "lang/" + language + ".yml");
        if (!langFile.exists()) {
            if (sender instanceof Player player) {
                sender.sendMessage(getLanguage(sender, "lang-not-found").replace("${lang}", language));
            } else {
                sender.sendMessage(getLanguage(sender, "lang-not-found") .replace("${lang}", language));
            }
            return;
        }

        if (!sender.hasPermission("locatix.admin.serverlang")) {
            sender.sendMessage(getLanguage(sender,"no-permission"));
            return;
        }

        plugin.getConfig().set("lang", language);
        plugin.saveConfig(); plugin.getLanguageManager().reload();
        plugin.getLanguageManager().reload();

        String langName = plugin.getLanguageManager().getLanguageName(language);

        if (sender instanceof Player player) {
            sender.sendMessage(getLanguage(sender,"lang-changed-server").replace("${lang}", langName));
        } else {
            sender.sendMessage(getLanguage(sender,"lang-changed-server").replace("${lang}", langName));
        }
    }

    private String getLanguage(CommandSender sender, String key) {
        if (sender instanceof Player player) {
            return plugin.getLanguageManager().getLangClient(player, key);
        }

        return plugin.getLanguageManager().getLang(key);
    }
}
