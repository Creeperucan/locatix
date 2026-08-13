package net.creeperucan.locatix.commands.subcommands;

import net.creeperucan.locatix.Locatix;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand {

    private final Locatix plugin;
    public HelpCommand(Locatix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender) {

        if (!sender.hasPermission("locatix.player.help")) {
            sender.sendMessage(getLanguage(sender,"no-permission"));
            return;
        }

        sender.sendMessage(getLanguage(sender, "locatix-title"));

        // Admin Commands
        if (sender.hasPermission("locatix.admin.help")) {
            sender.sendMessage(
                getLanguage(sender,"locatix-help") + "\n" +
                getLanguage(sender,"locatix-reload") + "\n" +
                getLanguage(sender,"locatix-reset") + "\n" +
                getLanguage(sender,"locatix-adminpanel") + "\n" +
                getLanguage(sender,"locatix-serverlang") + "\n" +
                getLanguage(sender,"locatix-get") + "\n"
            );
        }

        // Player Commands
        sender.sendMessage(
                getLanguage(sender,"locatix-lang") + "\n" +
                getLanguage(sender,"locatix-lastdeath") + "\n" +
                getLanguage(sender,"locatix-coordinates") + "\n" +
                getLanguage(sender,"locatix-share") + "\n" +
                getLanguage(sender,"locatix-convert") + "\n" +
                getLanguage(sender,"locatix-title")
        );
    }

    private String getLanguage(CommandSender sender, String key) {
        if (sender instanceof Player player) {
            return plugin.getLanguageManager().getLangClient(player, key);
        }

        return plugin.getLanguageManager().getLang(key);
    }
}
