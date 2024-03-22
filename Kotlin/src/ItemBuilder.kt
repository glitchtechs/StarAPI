package net.glitchtechs.starapi

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta


class ItemBuilder(material: Material?) {
    private val itemStack = ItemStack(material!!)

    fun amount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }

    fun displayName(displayName: String?): ItemBuilder {
        val itemMeta = this.itemMeta
        itemMeta!!.setDisplayName(displayName)
        itemStack.setItemMeta(itemMeta)
        return this
    }

    fun lore(vararg lore: String): ItemBuilder {
        val itemMeta = this.itemMeta
        val loreList: MutableList<String?> = ArrayList()
        val var4 = lore
        val var5 = lore.size

        for (var6 in 0 until var5) {
            val line = var4[var6]
            loreList.add(line)
        }

        itemMeta!!.lore = loreList
        itemStack.setItemMeta(itemMeta)
        return this
    }

    fun enchant(enchantment: Enchantment?, level: Int): ItemBuilder {
        val itemMeta = this.itemMeta
        itemMeta!!.addEnchant(enchantment!!, level, true)
        itemStack.setItemMeta(itemMeta)
        return this
    }

    fun attribute(attribute: Attribute?, modifier: AttributeModifier?): ItemBuilder {
        val itemMeta = this.itemMeta
        itemMeta!!.addAttributeModifier(attribute!!, modifier!!)
        itemStack.setItemMeta(itemMeta)
        return this
    }

    fun flags(vararg flags: ItemFlag?): ItemBuilder {
        val itemMeta = this.itemMeta
        itemMeta!!.addItemFlags(*flags)
        itemStack.setItemMeta(itemMeta)
        return this
    }

    fun build(): ItemStack {
        return this.itemStack
    }

    private val itemMeta: ItemMeta?
        get() {
            var itemMeta = itemStack.itemMeta
            if (itemMeta == null) {
                itemMeta = Bukkit.getItemFactory().getItemMeta(itemStack.type)
                itemStack.setItemMeta(itemMeta)
            }

            return itemMeta
        }
}