package net.glitchtechs.starapi;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryUtils {

    public static void addItemToInventory(Inventory inventory, Material material, int amount, int slot, String itemName, String... lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemName != null) {
            itemMeta.setDisplayName(itemName);
        }

        if (lore != null && lore.length > 0) {
            for(int i = 0; i < lore.length; ++i) {
                lore[i] = MessageUtils.hex(lore[i]);
            }

            itemMeta.setLore(Arrays.asList(lore));
        }

        itemStack.setItemMeta(itemMeta);
        if (slot >= 0 && slot < inventory.getSize()) {
            inventory.setItem(slot, itemStack);
        } else {
            inventory.addItem(new ItemStack[]{itemStack});
        }

    }

    public static void addAniToInventory(Inventory inventory, Material[] animationMaterials, int amount, int slot, int animationSpeedTicks, String itemName, String... lore) {
        Material initialMaterial = animationMaterials[0];
        ItemStack itemStack = new ItemStack(initialMaterial, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemName != null) {
            itemMeta.setDisplayName(itemName);
        }

        if (lore != null && lore.length > 0) {
            for (int i = 0; i < lore.length; ++i) {
                lore[i] = MessageUtils.hex(lore[i]);
            }

            itemMeta.setLore(Arrays.asList(lore));
        }

        itemStack.setItemMeta(itemMeta);
        if (slot >= 0 && slot < inventory.getSize()) {
            inventory.setItem(slot, itemStack);
        } else {
            inventory.addItem(new ItemStack[]{itemStack});
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getProvidingPlugin(InventoryUtils.class), () -> {
            Material nextMaterial = animationMaterials[0];
            System.arraycopy(animationMaterials, 1, animationMaterials, 0, animationMaterials.length - 1);
            animationMaterials[animationMaterials.length - 1] = nextMaterial;

            updateAnimatedItem(inventory, slot, nextMaterial, amount, itemName, lore);
        }, 0, animationSpeedTicks);
    }

    private static void updateAnimatedItem(Inventory inventory, int slot, Material material, int amount, String itemName, String... lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemName != null) {
            itemMeta.setDisplayName(itemName);
        }

        if (lore != null && lore.length > 0) {
            for (int i = 0; i < lore.length; ++i) {
                lore[i] = MessageUtils.hex(lore[i]);
            }

            itemMeta.setLore(Arrays.asList(lore));
        }

        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }

    public static void addFillerToInventory(Inventory inventory, Material fillerMaterial) {
        ItemStack fillerItem = new ItemStack(fillerMaterial);
        ItemMeta fillerMeta = fillerItem.getItemMeta();

        fillerMeta.setDisplayName(MessageUtils.hex("&7"));

        fillerMeta.setLore(null);

        fillerItem.setItemMeta(fillerMeta);

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null || inventory.getItem(slot).getType() == Material.AIR) {
                inventory.setItem(slot, fillerItem);
            }
        }
    }

    public static String clickedInvName(InventoryView inventoryView) {
        String inventoryName = inventoryView.getTitle();
        return inventoryName;
    }
}