package net.glitchtechs.starapi.InventoryUtils;

import net.glitchtechs.starapi.MessageUtils.MessageColors;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryItems {

    public static void addItemToInventory(Inventory inventory, Material material, int amount, int slot, String itemName, String... lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemName != null) {
            itemMeta.setDisplayName(itemName);
        }

        if (lore != null && lore.length > 0) {
            for(int i = 0; i < lore.length; ++i) {
                lore[i] = MessageColors.hex(lore[i]);
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

}
