package net.creeperucan.locatix.general;
import net.creeperucan.locatix.Locatix;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.InputStreamReader;

public class ConfigUpdater {

    private final Locatix plugin;
    public ConfigUpdater(Locatix plugin) {
        this.plugin = plugin;
    }

    public void updateConfig() {
        try {

            FileConfiguration config = plugin.getConfig();
            if (plugin.getResource("config.yml") == null) {
                Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("config-not-found"));
                return;
            }

            FileConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("config.yml")));
            boolean changed = false;

            for (String key : defaults.getKeys(true)) {
                //Bukkit.getConsoleSender().sendMessage("Checking: " + key);

                if (!config.isSet(key)) {
                    //Bukkit.getConsoleSender().sendMessage("Adding: " + key);
                    config.set(key, defaults.get(key));
                    changed = true;
                }
            }

            String configVersion = config.getString("config-version", "0.0.0");
            String pluginVersion = plugin.getDescription().getVersion();
            if (!configVersion.equals(pluginVersion)) {
                config.set("config-version", pluginVersion);
                changed = true;
            }

            if (changed) {
                plugin.saveConfig();
                plugin.reloadConfig();
                Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("config-updated"));
            }

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("config-update-failed"));
            e.printStackTrace();
        }
    }
}
