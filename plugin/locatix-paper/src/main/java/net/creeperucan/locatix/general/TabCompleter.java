package net.creeperucan.locatix.general;

import net.creeperucan.locatix.Locatix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    private final Locatix plugin;
    public TabCompleter(Locatix plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> completions = new ArrayList<>();

        // All Commands
        if (args.length == 1) {
            if (sender.hasPermission("locatix.player.help"))
                completions.add("help");

            if (sender.hasPermission("locatix.player.location"))
                completions.add("coordinates");

            if (sender.hasPermission("locatix.player.lastdeath"))
                completions.add("lastdeath");

            if (sender.hasPermission("locatix.player.share"))
                completions.add("share");

            if (sender.hasPermission("locatix.player.convert"))
                completions.add("convert");

            if (sender.hasPermission("locatix.player.lang"))
                completions.add("lang");

            if (sender.hasPermission("locatix.admin.reload"))
                completions.add("reload");

            if (sender.hasPermission("locatix.admin.resetconfig"))
                completions.add("resetconfig");

            if (sender.hasPermission("locatix.admin.serverlang"))
                completions.add("serverlang");

            if (sender.hasPermission("locatix.admin.panel"))
                completions.add("adminpanel");

            if (sender.hasPermission("locatix.admin.get"))
                completions.add("get");

            return completions;
        }

        // Share Command
        if (args.length == 2 && args[0].equalsIgnoreCase("share")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }

            return completions;
        }

        // Get Command
        if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }

            return completions;
        }

        // Convert Command
        if (args.length == 2 && args[0].equalsIgnoreCase("convert")) {
            completions.add("nether");
            completions.add("world");
            return completions;
        }

        // Language/Server Language Command
        if (args.length == 2 && args[0].equalsIgnoreCase("lang") || args[0].equalsIgnoreCase("serverlang")) {
            File langFolder = new File(plugin.getDataFolder(), "lang");
            File[] files = langFolder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".yml")) {
                        completions.add(file.getName().replace(".yml", ""));
                    }
                }
            }

            return completions;
        }

        return completions;
    }
}
