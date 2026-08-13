package net.creeperucan.locatix;

import net.creeperucan.locatix.commands.LocatixCommand;
import net.creeperucan.locatix.events.DeathListener;
import net.creeperucan.locatix.events.GUIListener;
import net.creeperucan.locatix.general.*;
import net.creeperucan.locatix.expansion.PlaceholderAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Locatix extends JavaPlugin {

    private PlayerDataManager playerDataManager;
    private LocationSystem locationSystem;
    private LanguageManager languageManager;
    private UpdateChecker updateChecker;
    private GUIManager guiManager;

    // Cooldown
    private final Map<UUID, Long> shareCooldowns = new HashMap<>();

    // DeathListener
    private final Map<UUID, Location> deathLocations = new HashMap<>();

    @Override
    public void onEnable() {

        try {

            // Config Save
            saveDefaultConfig();

            // Language
            languageManager = new LanguageManager(this);
            languageManager.load();

            // Config & Language Updater
            ConfigUpdater configUpdater = new ConfigUpdater(this);

            configUpdater.updateConfig();
            languageManager.updateLanguages();
            languageManager.reload();

            // Update Checker
            updateChecker = new UpdateChecker(this);
            if (getConfig().getBoolean("check-for-updates", true)) {
                updateChecker.checkForUpdates();
            }

            // GUI Manager
            guiManager = new GUIManager();

            // Listener
            getServer().getPluginManager().registerEvents(new DeathListener(this), this);
            getServer().getPluginManager().registerEvents(new GUIListener(this), this);

            // Player Data
            playerDataManager = new PlayerDataManager(this);
            playerDataManager.load();

            // Placeolder API
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                if (new PlaceholderAPI(this).register()) {
                    Bukkit.getConsoleSender().sendMessage(languageManager.getLang("prefix") + languageManager.getLang("placeholderapi-enabled"));
                } else {
                    Bukkit.getConsoleSender().sendMessage(languageManager.getLang("prefix") + languageManager.getLang("placeholderapi-failed"));
                }
            }

            // Commands Registiration
            if (getCommand("locatix") != null) {
                getCommand("locatix").setExecutor(new LocatixCommand(this));
                getCommand("locatix").setTabCompleter(new TabCompleter(this));
            } else {
                Bukkit.getConsoleSender().sendMessage(languageManager.getLang("prefix") + languageManager.getLang("locatix-not-found"));
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

            // Location System
            locationSystem = new LocationSystem(this);
            locationSystem.loadConfig();
            locationSystem.start();

            // Console Log
            if (languageManager != null) {
                Bukkit.getConsoleSender().sendMessage(languageManager.getLang("prefix") + languageManager.getLang("plugin-enabled"));
            } else {
                Bukkit.getConsoleSender().sendMessage("&aPlugin enabled.");
            }

        } catch (Exception e) {

            if (languageManager != null) {
                Bukkit.getConsoleSender().sendMessage(languageManager.getLang("prefix") + languageManager.getLang("plugin-failed"));
            } else {
                Bukkit.getConsoleSender().sendMessage("&cPlugin failed to enable.");
            }

            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable () {

        if (locationSystem != null) {
            locationSystem.stop();
        }

        if (playerDataManager != null) {
            playerDataManager.save();
        }

        // Console Log
        if (languageManager != null) {
            Bukkit.getConsoleSender().sendMessage(languageManager.getLang("prefix") + languageManager.getLang("plugin-disabled"));
        } else {
            Bukkit.getConsoleSender().sendMessage("&cPlugin disabled.");
        }
    }

    // Reload
    public void reloadPlugin() {
        new ConfigUpdater(this).updateConfig();
        reloadConfig();

        languageManager.updateLanguages();
        languageManager.reload();
        locationSystem.reload();
    }

    // Get
    public LocationSystem getLocationSystem() { return locationSystem; }
    public LanguageManager getLanguageManager() { return languageManager; }
    public Map<UUID, Location> getDeathLocations() { return deathLocations; }
    public Map<UUID, Long> getShareCooldowns() { return shareCooldowns; }

    public UpdateChecker getUpdateChecker() { return updateChecker; }
    public GUIManager getGuiManager() { return guiManager; }
    public PlayerDataManager getPlayerDataManager() { return playerDataManager; }
}
