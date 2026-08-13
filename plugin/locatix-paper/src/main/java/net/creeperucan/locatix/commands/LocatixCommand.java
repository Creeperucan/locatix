package net.creeperucan.locatix.commands;

import net.creeperucan.locatix.Locatix;

import net.creeperucan.locatix.commands.subcommands.*;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class LocatixCommand implements CommandExecutor {

    private final HelpCommand helpCommand;
    private final ReloadCommand reloadCommand;
    private final ConfigResetCommand configResetCommand;
    private final GetPlayerCommand getPlayerCommand;
    private final ServerLanguageCommand serverLang;
    private final AdminPanelCommand adminPanelCommand;

    private final LocationCommand locationCommand;
    private final LocationConvertCommand locationConvertCommand;
    private final DeathLocationCommand deathLocationCommand;
    private final ShareLocationCommand shareLocationCommand;
    private final LanguageCommand changeLang;

    private final Locatix plugin;
    public LocatixCommand(Locatix plugin) {
        this.plugin = plugin;

        this.helpCommand = new HelpCommand(plugin);
        this.reloadCommand = new ReloadCommand(plugin);
        this.configResetCommand = new ConfigResetCommand(plugin);
        this.getPlayerCommand = new GetPlayerCommand(plugin);
        this.serverLang = new ServerLanguageCommand(plugin);
        this.adminPanelCommand = new AdminPanelCommand(plugin);

        this.changeLang = new LanguageCommand(plugin);
        this.locationCommand = new LocationCommand(plugin);
        this.locationConvertCommand = new LocationConvertCommand(plugin);
        this.deathLocationCommand = new DeathLocationCommand(plugin);
        this.shareLocationCommand = new ShareLocationCommand(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = sender instanceof Player ? (Player) sender : null;

        if (args.length == 0) {
            helpCommand.execute(sender);
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "help" -> {
                helpCommand.execute(sender);
            }

            case "reload" -> {
                reloadCommand.execute(sender);
            }

            case "adminpanel" -> {
                adminPanelCommand.execute(sender);
            }

            case "resetconfig" -> {

                if (args.length < 2) {
                    sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"config-reset-warn"));
                    sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"usage-command") + "/locatix resetconfig confirm");
                    return true;
                }

                configResetCommand.execute(sender, args[1]);
            }

            case "lang" -> {
                if (args.length < 2) {
                    sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"usage-command") + "/locatix lang <en_us|tr_tr>");
                    return true;
                }

                changeLang.execute(sender, args[1]);
            }

            case "serverlang" -> {
                if (args.length < 2) {
                    sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"usage-command") + "/locatix serverlang <en_us|tr_tr>");
                    return true;
                }

                serverLang.execute(sender, args[1]);
            }

            case "get" -> {
                if (args.length < 2) {
                    sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"usage-command") + "/locatix get <player>");
                    return true;
                }

                getPlayerCommand.execute(sender, args[1]);
            }

            case "coordinates" -> {
                locationCommand.execute(sender);
            }

            case "lastdeath" -> {
                deathLocationCommand.execute(sender);
            }

            case "share" -> {
                if (args.length < 2) {
                    sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"usage-command") + "/locatix share <player>");
                    return true;
                }

                shareLocationCommand.execute(sender, args[1]);
            }

            case "convert" -> {
                if (args.length < 2) {
                    sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"usage-command") + "/locatix convert <nether|world>");
                    return true;
                }

                locationConvertCommand.execute(sender, args[1]);
            }

            default -> {
                sender.sendMessage(plugin.getLanguageManager().getLangClient(player,"use-help-command"));
            }
        }

        return true;
    }
}
