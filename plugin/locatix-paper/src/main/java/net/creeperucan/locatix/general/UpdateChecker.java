package net.creeperucan.locatix.general;

import net.creeperucan.locatix.Locatix;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

    private final Locatix plugin;
    public UpdateChecker(Locatix plugin) {
        this.plugin = plugin;
    }

    private boolean updateAvailable = false;
    private String latestVersion = "";

    private static final String HANGAR_SLUG = "Locatix";
    private static final String UPDATE_URL = "https://hangar.papermc.io/api/v1/projects/Creeperucan/" + HANGAR_SLUG + "/latestrelease";

    public void checkForUpdates() {

        updateAvailable = false;
        latestVersion = "";

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {

                URLConnection connection = new URL(UPDATE_URL).openConnection();

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("User-Agent", "Locatix-UpdateChecker");

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

                    String newestVersion = reader.readLine();
                    if (newestVersion == null || newestVersion.isBlank()) {
                        return;
                    }

                    latestVersion = newestVersion;
                    String currentVersion = plugin.getDescription().getVersion();


                    if (isNewerVersion(currentVersion, newestVersion)) {
                        updateAvailable = true;
                        Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("prefix") + plugin.getLanguageManager().getLang("new-plugin-update").replace("${version}", newestVersion));

                    } else {
                        updateAvailable = false;
                        Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("prefix") + plugin.getLanguageManager().getLang("plugin-up-to-date"));
                    }
                }

            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("prefix") + plugin.getLanguageManager().getLang("update-checker-error"));
                e.printStackTrace();
            }
        });
    }

    private boolean isNewerVersion(String current, String latest) {
        String[] currentParts = current.split("\\.");
        String[] latestParts = latest.split("\\.");

        int length = Math.max(currentParts.length, latestParts.length);

        for (int i = 0; i < length; i++) {
            int currentNum = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
            int latestNum = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;

            if (latestNum > currentNum) {
                return true;
            }

            if (latestNum < currentNum) {
                return false;
            }
        }

        return false;
    }

    public boolean isUpdateAvailable() { return updateAvailable; }
    public String getLatestVersion() { return latestVersion; }
    public String getCurrentVersion() { return plugin.getDescription().getVersion(); }
}
