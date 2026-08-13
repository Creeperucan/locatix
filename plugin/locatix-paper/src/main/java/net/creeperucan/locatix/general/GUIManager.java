package net.creeperucan.locatix.general;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager {
    private final Map<UUID, UUID> playerInfoTargets = new HashMap<>();

    // Target
    public void setPlayerInfoTarget(Player admin, Player target) {
        playerInfoTargets.put(admin.getUniqueId(), target.getUniqueId());
    }

    // Target UUID
    public UUID getPlayerInfoTarget(Player admin) {
        return playerInfoTargets.get(admin.getUniqueId());
    }

    // GUI Closed Clear
    public void removePlayerInfoTarget(Player admin) {
        playerInfoTargets.remove(admin.getUniqueId());
    }

    // GUI Control
    public boolean hasPlayerInfoTarget(Player admin) {
        return playerInfoTargets.containsKey(admin.getUniqueId());
    }
}
