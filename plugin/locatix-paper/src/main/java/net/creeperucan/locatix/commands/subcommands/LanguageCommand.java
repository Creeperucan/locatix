package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class LanguageCommand {

    private final Locatix plugin;
    public LanguageCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String language) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(plugin.getLanguageManager().getLang("player-only"));
            return;
        }

        if (!sender.hasPermission("locatix.player.lang")) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"no-permission"));
            return;
        }

        File langFile = new File(plugin.getDataFolder(), "lang/" + language + ".yml");

        if (!langFile.exists()) {
            sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"lang-not-found").replace("${lang}", language));
            return;
        }

        plugin.getPlayerDataManager().setLanguage(player.getUniqueId(), language);

        String lang = plugin.getLanguageManager().getLangClient(player,"lang");
        player.sendMessage( plugin.getLanguageManager().getLangClient(player, "lang-changed-client").replace("${lang}", lang));
    }
}
