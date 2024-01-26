package net.glitch.starapi;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class ArmorBuilder {

    private ItemStack armorPiece;

    public ArmorBuilder(Material armorType) {
        this.armorPiece = new ItemStack(armorType);
    }

    public ArmorBuilder displayName(String displayName) {
        setItemDisplayName(displayName);
        return this;
    }

    public ArmorBuilder lore(String... lore) {
        setItemLore(lore);
        return this;
    }

    public ArmorBuilder amount(int amount) {
        setItemAmount(amount);
        return this;
    }

    public ArmorBuilder enchant(Enchantment enchantment, int level) {
        enchantItem(enchantment, level);
        return this;
    }

    public ArmorBuilder attribute(Attribute attribute, AttributeModifier modifier) {
        setItemAttribute(attribute, modifier);
        return this;
    }

    public ArmorBuilder flags(ItemFlag... flags) {
        setItemFlags(flags);
        return this;
    }

    public ArmorBuilder leatherColor(Color color) {
        if (armorPiece.getType() == Material.LEATHER_HELMET ||
                armorPiece.getType() == Material.LEATHER_CHESTPLATE ||
                armorPiece.getType() == Material.LEATHER_LEGGINGS ||
                armorPiece.getType() == Material.LEATHER_BOOTS) {
            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) getItemMeta();
            leatherMeta.setColor(color);
            armorPiece.setItemMeta(leatherMeta);
        }
        return this;
    }

    public ItemStack build() {
        return this.armorPiece;
    }

    private void setItemDisplayName(String displayName) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(displayName);
        armorPiece.setItemMeta(itemMeta);
    }

    private void setItemLore(String... lore) {
        ItemMeta itemMeta = getItemMeta();
        List<String> loreList = new ArrayList<>();
        for (String line : lore) {
            loreList.add(line);
        }
        itemMeta.setLore(loreList);
        armorPiece.setItemMeta(itemMeta);
    }

    private void setItemAmount(int amount) {
        armorPiece.setAmount(amount);
    }

    private void enchantItem(Enchantment enchantment, int level) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        armorPiece.setItemMeta(itemMeta);
    }

    private void setItemAttribute(Attribute attribute, AttributeModifier modifier) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addAttributeModifier(attribute, modifier);
        armorPiece.setItemMeta(itemMeta);
    }

    private void setItemFlags(ItemFlag... flags) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addItemFlags(flags);
        armorPiece.setItemMeta(itemMeta);
    }

    private ItemMeta getItemMeta() {
        ItemMeta itemMeta = armorPiece.getItemMeta();
        if (itemMeta == null) {
            itemMeta = Bukkit.getItemFactory().getItemMeta(armorPiece.getType());
            armorPiece.setItemMeta(itemMeta);
        }
        return itemMeta;
    }
}
