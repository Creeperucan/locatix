package net.creeperucan.locatix.gui;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.general.CustomHeadManager;
import net.creeperucan.locatix.general.GUILoader;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class PlayerInfoGUI {


    private final GUILoader guiLoader = new GUILoader();

    private final Locatix plugin;
    public PlayerInfoGUI(Locatix plugin) {
        this.plugin = plugin;
    }

    public void open(Player admin, Player target) {
        plugin.getGuiManager().setPlayerInfoTarget(admin, target);

        String TITLE = plugin.getLanguageManager().getLangClient(admin,"player-info-panel");
        Inventory inv = guiLoader.createInventory(27, TITLE);
        Location loc = target.getLocation();

        guiLoader.setPlayerHead(
                inv,
                15,
                target,
                "&e" + target.getName(),
                List.of(
                        "",
                        "&6Ping: &e" + target.getPing(),
                        "&6Level: &e" + target.getLevel()
                )
        );


        String worldName = target.getWorld().getName();
        String displayWorld = plugin.getConfig().getString("world-names." + worldName, worldName);
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        List<String> lore = plugin.getLanguageManager().getLangListClient(admin, "teleport-subtitle").stream().map(line -> line
                        .replace("${world}", displayWorld)
                        .replace("${x}", String.valueOf(x))
                        .replace("${y}", String.valueOf(y))
                        .replace("${z}", String.valueOf(z))
                        ).toList();
        guiLoader.setItem(
                inv,
                11,
                Material.ENDER_PEARL,
                plugin.getLanguageManager().getLangClient(admin, "teleport-title"),
                lore
        );

        guiLoader.setCustomHead(
                inv,
                22,
                CustomHeadManager.BACK_ARROW,
                plugin.getLanguageManager().getLangClient(admin, "back-title"),
                plugin.getLanguageManager().getLangListClient(admin, "back-subtitle")
        );

        guiLoader.fill(inv, Material.GRAY_STAINED_GLASS_PANE);

        admin.openInventory(inv);
    }
}
