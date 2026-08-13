package net.creeperucan.locatix.gui;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.general.CustomHeadManager;
import net.creeperucan.locatix.general.GUILoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class AdminPanelGUI {

    private final GUILoader guiLoader = new GUILoader();

    private final Locatix plugin;
    public AdminPanelGUI(Locatix plugin) {
        this.plugin = plugin;
    }

    public void open(Player admin) {
        String TITLE = plugin.getLanguageManager().getLangClient(admin,"admin-panel");
        Inventory inv = guiLoader.createInventory(36, TITLE);
        guiLoader.fill(inv, Material.GRAY_STAINED_GLASS_PANE);

        // Player Manager
        guiLoader.setItem(
                inv,
                11,
                Material.PLAYER_HEAD,
                plugin.getLanguageManager().getLangClient(admin, "player-manager-title"),
                plugin.getLanguageManager().getLangListClient(admin, "player-manager-subtitle")
        );

        // Language Settings
        guiLoader.setCustomHead(
                inv,
                13,
                CustomHeadManager.WORLD,
                plugin.getLanguageManager().getLangClient(admin, "lang-settings-title"),
                plugin.getLanguageManager().getLangListClient(admin, "lang-settings-subtitle")
        );

        // Plugin Settings
        guiLoader.setCustomHead(
                inv,
                15,
                CustomHeadManager.SETTINGS,
                "Soon",
                List.of(
                        " "
                )
        );

        // Reload
        guiLoader.setCustomHead(
                inv,
                29,
                CustomHeadManager.RELOAD,
                plugin.getLanguageManager().getLangClient(admin, "reload-plugin-title"),
                plugin.getLanguageManager().getLangListClient(admin, "reload-plugin-subtitle")
        );

        // Plugin Info
        String version = plugin.getDescription().getVersion();
        int online = plugin.getServer().getOnlinePlayers().size();
        List<String> lore = plugin.getLanguageManager().getLangListClient(admin, "plugin-info-subtitle").stream() .map(line -> line
                .replace("${version}", version)
                .replace("${online}", String.valueOf(online)))
                .toList();

        guiLoader.setCustomHead(
                inv,
                31,
                CustomHeadManager.INFO,
                plugin.getLanguageManager().getLangClient(admin, "plugin-info-title"),
                lore
        );

        // Close
        guiLoader.setCustomHead(
                inv,
                33,
                CustomHeadManager.CROSS,
                plugin.getLanguageManager().getLangClient(admin, "close-title"),
                plugin.getLanguageManager().getLangListClient(admin, "close-subtitle")
        );

        admin.openInventory(inv);
    }
}
