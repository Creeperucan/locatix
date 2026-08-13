package net.creeperucan.locatix.gui;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.general.CustomHeadManager;
import net.creeperucan.locatix.general.GUILoader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class LanguageGUI {

    private final GUILoader guiLoader = new GUILoader();

    private final Locatix plugin;
    public LanguageGUI(Locatix plugin) {
        this.plugin = plugin;
    }

    public void open(Player admin) {
        String TITLE = plugin.getLanguageManager().getLangClient(admin,"lang-settings-panel");
        Inventory inv = guiLoader.createInventory(27, TITLE);
        guiLoader.fill(inv, Material.GRAY_STAINED_GLASS_PANE);

        List<String> lore = new ArrayList<>();
        lore = plugin.getLanguageManager().getLangListClient(admin, "lang-subtitle");

        // English
        guiLoader.setCustomHead(
                inv,
                11,
                CustomHeadManager.AMERICA,
                "&eEnglish",
                lore
        );

        // Turkish
        guiLoader.setCustomHead(
                inv,
                12,
                CustomHeadManager.TURKEY,
                "&eTürkçe",
                lore
        );

        // Spanish
        guiLoader.setCustomHead(
                inv,
                13,
                CustomHeadManager.SPAIN,
                "&eEspañol",
                lore
        );

        // German
        guiLoader.setCustomHead(
                inv,
                14,
                CustomHeadManager.GERMANY,
                "&eDeutsch",
                lore
        );

        // Greek
        guiLoader.setCustomHead(
                inv,
                15,
                CustomHeadManager.GREECE,
                "&eΕλληνικά",
                lore
        );

        // Close
        guiLoader.setCustomHead(
                inv,
                22,
                CustomHeadManager.BACK_ARROW,
                plugin.getLanguageManager().getLangClient(admin, "back-title"),
                plugin.getLanguageManager().getLangListClient(admin, "back-subtitle")
        );

        admin.openInventory(inv);
    }
}
