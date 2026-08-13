package net.creeperucan.locatix.general;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GUILoader {

    public Inventory createInventory(int size, String title) {
        return Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', title));
    }

    public ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if (lore != null && !lore.isEmpty()) {
            List<String> coloredLore = new ArrayList<>();
            for (String line : lore) {
                coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
            }

            meta.setLore(coloredLore);
        }

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createPlayerHead(OfflinePlayer player, String name, List<String> lore) {

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta == null) {
            return head;
        }

        meta.setOwningPlayer(player);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if (lore != null && !lore.isEmpty()) {

            List<String> coloredLore = new ArrayList<>();
            for (String line : lore) {
                coloredLore.add(ChatColor.translateAlternateColorCodes('&', line));
            }

            meta.setLore(coloredLore);
        }

        head.setItemMeta(meta);
        return head;
    }

    public ItemStack createCustomHead(String texture, String name, List<String> lore) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();

        if (meta == null) {
            return head;
        }

        try {
            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures", texture));

            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if (lore != null && !lore.isEmpty()) {
            List<String> coloredLore = new ArrayList<>();
            for (String line : lore) {
                coloredLore.add(
                        ChatColor.translateAlternateColorCodes('&', line)
                );
            }

            meta.setLore(coloredLore);
        }

        head.setItemMeta(meta);
        return head;
    }

    public void setCustomHead(
            Inventory inventory,
            int slot,
            String texture,
            String name,
            List<String> lore
    ) {
        inventory.setItem(slot, createCustomHead(texture, name, lore));
    }

    public void fill(Inventory inventory, Material material) {
        ItemStack filler = new ItemStack(material);
        ItemMeta meta = filler.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(" ");
            filler.setItemMeta(meta);
        }

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, filler);
            }
        }
    }

    public void setItem(Inventory inventory, int slot, Material material, String name, List<String> lore) {
        inventory.setItem(slot,createItem(material, name, lore));
    }

    public void setPlayerHead(Inventory inventory, int slot, OfflinePlayer player, String name, List<String> lore) {
        inventory.setItem(slot, createPlayerHead(player, name, lore));
    }
}
