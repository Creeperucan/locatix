package net.creeperucan.locatix.events;

import net.creeperucan.locatix.Locatix;
import net.creeperucan.locatix.commands.subcommands.ReloadCommand;
import net.creeperucan.locatix.commands.subcommands.ServerLanguageCommand;
import net.creeperucan.locatix.gui.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class GUIListener implements Listener {

    private final PlayerListGUI playerListGUI;
    private final PlayerInfoGUI playerInfoGUI;
    private final LanguageGUI languageGUI;

    private final Locatix plugin;

    public GUIListener(Locatix plugin) {
        this.playerListGUI = new PlayerListGUI(plugin);
        this.playerInfoGUI = new PlayerInfoGUI(plugin);
        this.languageGUI = new LanguageGUI(plugin);

        this.plugin = plugin;
    }

    @EventHandler
    public void onClose(org.bukkit.event.inventory.InventoryCloseEvent event) {

        if (!(event.getPlayer() instanceof Player admin)) {
            return;
        }

        String TITLE = plugin.getLanguageManager().getLangClient(admin,"player-info-panel");
        if (event.getView().getTitle().equals(TITLE)) {
            plugin.getGuiManager().removePlayerInfoTarget(admin);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player admin)) {
            return;
        }

        String title = event.getView().getTitle();
        int slot = event.getRawSlot();

        // Admin Panel
        String ADMIN_TITLE = plugin.getLanguageManager().getLangClient(admin,"admin-panel");
        boolean reload = false;
        if (title.equals(ADMIN_TITLE)) {
            event.setCancelled(true);

            switch (slot) {
                case 11 -> playerListGUI.open(admin); // Player Manager
                case 13 -> languageGUI.open(admin); // Language Settings
                case 29-> reload = true; // Reload
                //case 31 ->
                case 33 -> admin.closeInventory(); // Close
            }

            if (reload != false) {
                ReloadCommand command = new ReloadCommand(plugin);
                command.execute(admin);
            }

            return;
        }

        // Player List
        String PLR_LIST_TITLE = plugin.getLanguageManager().getLangClient(admin,"player-list-panel");
        if (title.equals(PLR_LIST_TITLE)) {
            event.setCancelled(true);

            if (slot < 0 || slot >= event.getInventory().getSize()) {
                return;
            }

            if (event.getCurrentItem() == null) {
                return;
            }

            if (slot >= 0 && slot < Bukkit.getOnlinePlayers().size()) {
                Player target = new java.util.ArrayList<>(Bukkit.getOnlinePlayers()).get(slot);
                if (target != null) {
                    playerInfoGUI.open(admin, target);
                }
            }

            return;
        }

        // Lang GUI
        ServerLanguageCommand serverLanguageCommand = new ServerLanguageCommand(plugin);
        String LANG_TITLE = plugin.getLanguageManager().getLangClient(admin,"lang-settings-panel");
        String language = null;

        if (title.equals(LANG_TITLE)) {
            event.setCancelled(true);
            switch (slot) {
                case 11 -> language = "en_us";
                case 12 -> language = "tr_tr";
                case 13 -> language = "es_es";
                case 14 -> language = "de_de";
                case 15 -> language = "el_gr";
                case 22 -> new AdminPanelGUI(plugin).open(admin);  // Back
            }

            if (language != null) {
                serverLanguageCommand.execute(admin, language);
                new LanguageGUI(plugin).open(admin);
            }
            return;
        }

        // Player Info
        String PLR_INFO_TITLE = plugin.getLanguageManager().getLangClient(admin, "player-info-panel");
        Location teleportLoc = null;
        String targetName = null;
        if (title.equals(PLR_INFO_TITLE)) {
            event.setCancelled(true);
                if (!admin.hasPermission("locatix.admin.teleport")) {
                    admin.sendMessage(plugin.getLanguageManager().getLangClient(admin,"no-permission"));
                return;
            }

            UUID targetUUID = plugin.getGuiManager().getPlayerInfoTarget(admin);
            if (targetUUID == null) {
                admin.closeInventory();
                return;
            }

            Player target = Bukkit.getPlayer(targetUUID);
            if (target == null) {
                admin.sendMessage(plugin.getLanguageManager().getLangClient(admin, "player-not-found"));
                admin.closeInventory();
                return;
            }

            switch (slot) {

                case 11 -> {                                      // Teleport
                    teleportLoc = target.getLocation();
                    targetName = target.getName();
                }

                case 22 -> new AdminPanelGUI(plugin).open(admin); // Back
            }

            if (teleportLoc != null) {
                admin.teleport(teleportLoc);
                admin.sendMessage(plugin.getLanguageManager().getLangClient(admin,"player-teleported").replace("${player}", targetName));
            }
        }
    }
}
