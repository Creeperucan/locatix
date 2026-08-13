package net.creeperucan.locatix.gui;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.general.GUILoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class PlayerListGUI {

    private final Locatix plugin;
    private final GUILoader guiLoader = new GUILoader();

    public PlayerListGUI(Locatix plugin) {
        this.plugin = plugin;
    }

    public void open(Player admin) {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        int size = ((players.size() / 9) + 1) * 9;

        if (size < 27) {
            size = 27;
        }

        if (size > 54) {
            size = 54;
        }

        String TITLE = plugin.getLanguageManager().getLangClient(admin,"player-list-panel");
        Inventory inv = guiLoader.createInventory(size, TITLE);

        int slot = 0;
        for (Player target : players) {

            Location loc = target.getLocation();

            String worldName = target.getWorld().getName();
            String displayWorld = plugin.getConfig().getString("world-names." + worldName, worldName);
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            List<String> lore = plugin.getLanguageManager().getLangListClient(admin, "player-list-subtitle").stream().map(line -> line
                    .replace("${world}", displayWorld)
                    .replace("${x}", String.valueOf(x))
                    .replace("${y}", String.valueOf(y))
                    .replace("${z}", String.valueOf(z))
            ).toList();

            guiLoader.setPlayerHead(
                    inv,
                    slot,
                    target,
                    "&e" + target.getName(),
                    lore
            );

            slot++;
        }

        guiLoader.fill(inv, org.bukkit.Material.GRAY_STAINED_GLASS_PANE);
        admin.openInventory(inv);
    }
}
