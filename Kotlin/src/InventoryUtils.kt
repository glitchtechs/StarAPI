package net.glitchtechs.starapi

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

object InventoryUtils {
    fun addItemToInventory(inventory: Inventory, material: Material?, amount: Int, slot: Int, itemName: String?, vararg lore: String?) {
        val itemStack = ItemStack(material!!, amount)
        val itemMeta = itemStack.itemMeta
        if (itemName != null) {
            itemMeta!!.setDisplayName(itemName)
        }

        if (lore != null && lore.isNotEmpty()) {
            val hexLore = lore.map { MessageUtils.hex(it!!) }
            itemMeta!!.lore = hexLore
        }


        itemStack.setItemMeta(itemMeta)
        if (slot >= 0 && slot < inventory.size) {
            inventory.setItem(slot, itemStack)
        } else {
            inventory.addItem(*arrayOf(itemStack))
        }
    }

    fun addAniToInventory(inventory: Inventory, animationMaterials: Array<Material?>, amount: Int, slot: Int, animationSpeedTicks: Int, itemName: String?, vararg lore: String?) {
        val initialMaterial = animationMaterials[0]
        val itemStack = ItemStack(initialMaterial!!, amount)
        val itemMeta = itemStack.itemMeta

        if (itemName != null) {
            itemMeta!!.setDisplayName(itemName)
        }

        if (lore != null && lore.isNotEmpty()) {
            val hexLore = lore.map { MessageUtils.hex(it!!) }
            itemMeta!!.lore = hexLore
        }


        itemStack.setItemMeta(itemMeta)
        if (slot >= 0 && slot < inventory.size) {
            inventory.setItem(slot, itemStack)
        } else {
            inventory.addItem(*arrayOf(itemStack))
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(JavaPlugin.getProvidingPlugin(InventoryUtils::class.java), {
            val nextMaterial = animationMaterials[0]
            System.arraycopy(animationMaterials, 1, animationMaterials, 0, animationMaterials.size - 1)
            animationMaterials[animationMaterials.size - 1] = nextMaterial
            updateAnimatedItem(inventory, slot, nextMaterial, amount, itemName, *lore as Array<out String>)
        }, 0, animationSpeedTicks.toLong())
    }

    private fun updateAnimatedItem(inventory: Inventory, slot: Int, material: Material?, amount: Int, itemName: String?, vararg lore: String) {
        val itemStack = ItemStack(material!!, amount)
        val itemMeta = itemStack.itemMeta

        if (itemName != null) {
            itemMeta!!.setDisplayName(itemName)
        }

        if (lore != null && lore.isNotEmpty()) {
            val hexLore = lore.map { MessageUtils.hex(it) }
            itemMeta!!.lore = hexLore
        }

        itemStack.setItemMeta(itemMeta)
        inventory.setItem(slot, itemStack)
    }

    fun addFillerToInventory(inventory: Inventory, fillerMaterial: Material?) {
        val fillerItem = ItemStack(fillerMaterial!!)
        val fillerMeta = fillerItem.itemMeta

        fillerMeta!!.setDisplayName(MessageUtils.hex("&7"))

        fillerMeta.lore = null

        fillerItem.setItemMeta(fillerMeta)

        for (slot in 0 until inventory.size) {
            if (inventory.getItem(slot) == null || inventory.getItem(slot)!!.type == Material.AIR) {
                inventory.setItem(slot, fillerItem)
            }
        }
    }

    fun clickedInvName(inventoryView: InventoryView): String {
        val inventoryName = inventoryView.title
        return inventoryName
    }
}