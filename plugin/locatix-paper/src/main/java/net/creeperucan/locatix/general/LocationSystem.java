package net.creeperucan.locatix.general;

import net.creeperucan.locatix.Locatix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LocationSystem {

    private final Locatix plugin;
    private BukkitTask task;

    private final Map<UUID, BossBar> bossBars = new HashMap<>();

    private boolean showCoordinates;
    private boolean worldName;
    private String locationText;
    private String locationTextNoWorld;
    private int updateInterval;
    private List<String> disabledWorlds;

    private String displayType;

    private BarColor bossBarColor;
    private BarStyle bossBarStyle;

    public LocationSystem(Locatix plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        showCoordinates = plugin.getConfig().getBoolean("show-coordinates", true);
        worldName = plugin.getConfig().getBoolean("world-name", false);
        locationText = plugin.getConfig().getString("location-text", "&6X: &e${x} &6Y: &e${y} &6Z: &e${z} &7(${world})");
        locationTextNoWorld = plugin.getConfig().getString("location-text-no-world", "&6X: &e${x} &6Y: &e${y} &6Z: &e${z}");
        updateInterval = Math.max(1, plugin.getConfig().getInt("update-interval", 20));
        disabledWorlds = plugin.getConfig().getStringList("disabled-worlds");

        // Display Type
        displayType = plugin.getConfig().getString("display-type", "ACTIONBAR").toUpperCase();
        if (!displayType.equals("ACTIONBAR") && !displayType.equals("BOSSBAR")) {
            displayType = "ACTIONBAR";
            Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("invaild-display-type").replace("${type}", displayType));
        }

        // Bossbar
        try {
            bossBarColor = BarColor.valueOf(plugin.getConfig().getString("bossbar.color", "WHITE").toUpperCase());
        } catch (IllegalArgumentException e) {
            bossBarColor = BarColor.RED;
            Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("invaild-bossbar-color").replace("${color}", String.valueOf(bossBarColor)));
        }

        try {
            bossBarStyle = BarStyle.valueOf(plugin.getConfig().getString("bossbar.style", "SOLID").toUpperCase());
        } catch (IllegalArgumentException e) {
            bossBarStyle = BarStyle.SEGMENTED_10;
            Bukkit.getConsoleSender().sendMessage(plugin.getLanguageManager().getLang("invaild-bossbar-style").replace("${style}", String.valueOf(bossBarStyle)));
        }
    }

    public void start() {
        task = plugin.getServer().getScheduler().runTaskTimer(
                plugin,
                () -> {

                    if (!showCoordinates) {
                        return;
                    }

                    for (Player player : plugin.getServer().getOnlinePlayers()) {

                        if (plugin.getPlayerDataManager().isCoordinatesDisabled(player.getUniqueId())) {
                            BossBar bossBar = bossBars.remove(player.getUniqueId());

                            if (bossBar != null) {
                                bossBar.removeAll();
                            }

                            continue;
                        }

                        if (disabledWorlds.contains(player.getWorld().getName())) {
                            BossBar bossBar = bossBars.remove(player.getUniqueId());

                            if (bossBar != null) {
                                bossBar.removeAll();
                            }

                            continue;
                        }

                        Location location = player.getLocation();

                        int x = location.getBlockX();
                        int y = location.getBlockY();
                        int z = location.getBlockZ();

                        String coords;
                        if (worldName) {
                            coords = locationText.replace("${world}", player.getWorld().getName());
                        } else {
                            coords = locationTextNoWorld;
                        }

                        coords = coords
                                .replace("${x}", String.valueOf(x))
                                .replace("${y}", String.valueOf(y))
                                .replace("${z}", String.valueOf(z));

                        coords = ChatColor.translateAlternateColorCodes('&', coords);

                        if (displayType.equalsIgnoreCase("ACTIONBAR")) {
                            BossBar bossBar = bossBars.remove(player.getUniqueId());

                            if (bossBar != null) {
                                bossBar.removeAll();
                            }

                            player.sendActionBar(coords);

                        } else if (displayType.equalsIgnoreCase("BOSSBAR")) {
                            BossBar bossBar = bossBars.get(player.getUniqueId());

                            if (bossBar == null) {
                                bossBar = Bukkit.createBossBar(coords, bossBarColor, bossBarStyle);
                                bossBar.addPlayer(player);
                                bossBars.put(player.getUniqueId(), bossBar);
                            }

                            bossBar.setTitle(coords);
                            bossBar.setProgress(1.0);
                        }
                    }
                },
                0L,
                updateInterval
        );
    }

    public void stop() {
        if (task != null) {
            task.cancel();
        }

        for (BossBar bossBar : bossBars.values()) {
            bossBar.removeAll();
        }

        bossBars.clear();
    }

    public void reload() {
        stop();
        loadConfig();
        start();
    }
}
