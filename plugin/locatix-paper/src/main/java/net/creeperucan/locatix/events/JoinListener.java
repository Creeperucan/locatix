package net.creeperucan.locatix.events;

import net.creeperucan.locatix.Locatix;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final Locatix plugin;
    public JoinListener(Locatix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("locatix.admin") || !player.isOp()) {
            return;
        }

        if (plugin.getUpdateChecker().isUpdateAvailable()) {
            player.sendMessage(
                    plugin.getLanguageManager().getLangClient(player,"prefix") +
                    plugin.getLanguageManager().getLangClient(player,"new-plugin-update") +
                    plugin.getUpdateChecker().getLatestVersion()
            );
        }
    }
}
