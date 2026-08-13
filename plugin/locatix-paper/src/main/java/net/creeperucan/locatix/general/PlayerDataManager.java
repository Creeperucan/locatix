package net.creeperucan.locatix.general;

import net.creeperucan.locatix.Locatix;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerDataManager {
    private File file;
    private YamlConfiguration data;

    private final Locatix plugin;
    public PlayerDataManager(Locatix plugin) {
        this.plugin = plugin;
    }

    public void load() {

        File folder = new File(plugin.getDataFolder(), "data");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        file = new File(folder, "playerdata.yml");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    plugin.getLogger().info("playerdata.yml created.");
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create playerdata.yml");
                e.printStackTrace();
            }
        }

        data = YamlConfiguration.loadConfiguration(file);
    }

    public void reload() {
        data = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Language
    public String getLanguage(UUID uuid) {
        return data.getString("players." + uuid + ".language",plugin.getConfig().getString("lang", "en_us"));
    }

    public void setLanguage(UUID uuid, String language) {
        data.set("players." + uuid + ".language",language);
        save();
    }

    // Coordinates
    public boolean isCoordinatesDisabled(UUID uuid) {
        return data.getBoolean("players." + uuid + ".coordinates-disabled", false);
    }

    public void setCoordinatesDisabled(UUID uuid, boolean disabled) {
        data.set("players." + uuid + ".coordinates-disabled", disabled);
        save();
    }
}
