package net.creeperucan.locatix.general;

import net.creeperucan.locatix.Locatix;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.File;

public class LanguageManager {
    private final Map<String, FileConfiguration> languages = new HashMap<>();

    private final Locatix plugin;
    public LanguageManager(Locatix plugin) {
        this.plugin = plugin;
    }

    public static final String[] LANGUAGES = {
            "en_us",
            "tr_tr",
            "de_de",
            "es_es",
            "el_gr"
    };

    private void createLanguageFiles() {
        File langFolder = new File(plugin.getDataFolder(), "lang");
        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }

        for (String language : LANGUAGES) {
            File file = new File(plugin.getDataFolder(), "lang/" + language + ".yml");

            if (!file.exists()) {
                if (plugin.getResource("lang/" + language + ".yml") == null) {

                    Bukkit.getConsoleSender().sendMessage("&cLanguage resource missing from jar: &e" + language);
                    continue;
                }

                plugin.saveResource("lang/" + language + ".yml", false);
            }
        }
    }

    public void load() {
        createLanguageFiles();
        languages.clear();

        for (String language : LANGUAGES) {
            File file = new File(plugin.getDataFolder(), "lang/" + language + ".yml");

            try {
                languages.put(
                        language,
                        YamlConfiguration.loadConfiguration(file)
                );
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to load language: " + language);
                e.printStackTrace();
            }
        }
    }

    public void reload() {
        load();
    }

    // Server
    public String getLang(String key) {
        String defaultLang = plugin.getConfig().getString("lang", "en_us");
        FileConfiguration lang = languages.get(defaultLang);

        if (lang == null) {
            return key;
        }

        return ChatColor.translateAlternateColorCodes('&', lang.getString(key, key));
    }

    // Client
    public String getLangClient(Player player, String key) {
        String language = getLanguage(player);
        FileConfiguration lang = languages.get(language);

        if (lang == null) {
            return getLang(key);
        }

        return ChatColor.translateAlternateColorCodes('&', lang.getString(key, key));
    }

    public List<String> getLangListClient(Player player, String key) {
        String language = getLanguage(player);
        FileConfiguration lang = languages.get(language);

        if (lang == null) {
            return getLangList(key);
        }

        return lang.getStringList(key)
                .stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .toList();
    }

    public List<String> getLangList(String key) {
        String defaultLang = plugin.getConfig().getString("lang", "en_us");
        FileConfiguration lang = languages.get(defaultLang);

        if (lang == null) {
            return List.of();
        }

        return lang.getStringList(key);
    }

    public void setLanguage(Player player, String language) {
        if (!languages.containsKey(language)) {
            return;
        }

        plugin.getPlayerDataManager().setLanguage(
                player.getUniqueId(),
                language
        );
    }

    public String getLanguage(Player player) {
        return plugin.getPlayerDataManager().getLanguage(player.getUniqueId());
    }

    public boolean isValidLanguage(String language) {
        return languages.containsKey(language);
    }

    public String getLanguageName(String language) {
        FileConfiguration lang = languages.get(language);

        if (lang == null) {
            return language;
        }

        return lang.getString("lang", language);
    }

    public void updateLanguages() {
        for (String language : LANGUAGES) {
            try {

                File langFile = new File(plugin.getDataFolder(), "lang/" + language + ".yml");
                if (!langFile.exists()) {
                    continue;
                }

                if (plugin.getResource("lang/" + language + ".yml") == null) {
                    Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("lang-not-found").replace("${lang}", language));
                    continue;
                }

                YamlConfiguration current = YamlConfiguration.loadConfiguration(langFile);
                YamlConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource("lang/" + language + ".yml")));

                boolean changed = false;

                for (String key : defaults.getKeys(true)) {
                    if (!current.contains(key)) {
                        current.set(key, defaults.get(key));
                        changed = true;
                        Bukkit.getConsoleSender().sendMessage("Added missing language key: &e" + language + "&a-> &e" + key);
                    }
                }

                if (changed) {
                    current.save(langFile);
                    Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("lang-updated").replace("${lang}", language));
                }

            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("lang-update-failed").replace("${lang}", language));
                e.printStackTrace();
            }
        }
    }
}
