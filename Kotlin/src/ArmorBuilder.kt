package net.glitchtechs.starapi

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta


class ArmorBuilder(armorType: Material?) {
    private val armorPiece = ItemStack(armorType!!)

    fun displayName(displayName: String): ArmorBuilder {
        this.setItemDisplayName(displayName)
        return this
    }

    fun lore(vararg lore: String?): ArmorBuilder {
        this.setItemLore(*lore as Array<String>)
        return this
    }

    fun amount(amount: Int): ArmorBuilder {
        this.setItemAmount(amount)
        return this
    }

    fun enchant(enchantment: Enchantment, level: Int): ArmorBuilder {
        this.enchantItem(enchantment, level)
        return this
    }

    fun attribute(attribute: Attribute, modifier: AttributeModifier): ArmorBuilder {
        this.setItemAttribute(attribute, modifier)
        return this
    }

    fun flags(vararg flags: ItemFlag?): ArmorBuilder {
        this.setItemFlags(*flags as Array<ItemFlag>)
        return this
    }

    fun leatherColor(color: Color?): ArmorBuilder {
        if (armorPiece.type == Material.LEATHER_HELMET || (armorPiece.type == Material.LEATHER_CHESTPLATE) || (armorPiece.type == Material.LEATHER_LEGGINGS) || (armorPiece.type == Material.LEATHER_BOOTS)) {
            val leatherMeta = itemMeta as LeatherArmorMeta?
            leatherMeta!!.setColor(color)
            armorPiece.setItemMeta(leatherMeta)
        }

        return this
    }

    fun build(): ItemStack {
        return this.armorPiece
    }

    private fun setItemDisplayName(displayName: String) {
        val itemMeta = this.itemMeta
        itemMeta!!.setDisplayName(displayName)
        armorPiece.setItemMeta(itemMeta)
    }

    private fun setItemLore(vararg lore: String) {
        val itemMeta = this.itemMeta ?: return
        val loreList: MutableList<String> = ArrayList()

        for (line in lore) {
            loreList.add(line)
        }

        itemMeta.lore = loreList
        armorPiece.itemMeta = itemMeta
    }


    private fun setItemAmount(amount: Int) {
        armorPiece.amount = amount
    }

    private fun enchantItem(enchantment: Enchantment, level: Int) {
        val itemMeta = this.itemMeta
        itemMeta!!.addEnchant(enchantment, level, true)
        armorPiece.setItemMeta(itemMeta)
    }

    private fun setItemAttribute(attribute: Attribute, modifier: AttributeModifier) {
        val itemMeta = this.itemMeta
        itemMeta!!.addAttributeModifier(attribute, modifier)
        armorPiece.setItemMeta(itemMeta)
    }

    private fun setItemFlags(vararg flags: ItemFlag) {
        val itemMeta = this.itemMeta
        itemMeta!!.addItemFlags(*flags)
        armorPiece.setItemMeta(itemMeta)
    }

    private val itemMeta: ItemMeta?
        get() {
            var itemMeta = armorPiece.itemMeta
            if (itemMeta == null) {
                itemMeta = Bukkit.getItemFactory().getItemMeta(armorPiece.type)
                armorPiece.setItemMeta(itemMeta)
            }

            return itemMeta
        }
}