package net.glitchtechs.starapi

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.net.MalformedURLException
import java.net.URL
import java.util.*

object SkullUtils {

    fun addSkullToInventory(inventory: Inventory, url: String, amount: Int, slot: Int, itemName: String?, vararg lore: String) {
        val skullStack = ItemStack(Material.PLAYER_HEAD, amount)
        val skullMeta = skullStack.itemMeta as SkullMeta?
        val profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID())
        val textures = profile.textures

        try {
            textures.skin = URL("http://textures.minecraft.net/texture/$url")
        } catch (var11: MalformedURLException) {
            var11.printStackTrace()
        }

        profile.setTextures(textures)
        skullMeta!!.ownerProfile = profile
        if (itemName != null) {
            skullMeta!!.setDisplayName(itemName)
        }

        if (lore != null && lore.isNotEmpty()) {
            val hexLore = lore.mapNotNull { MessageUtils.hex(it) }
            skullMeta!!.lore = hexLore
        }


        skullStack.setItemMeta(skullMeta)
        if (slot >= 0 && slot < inventory.size) {
            inventory.setItem(slot, skullStack)
        } else {
            inventory.addItem(*arrayOf(skullStack))
        }
    }

    fun giveSkullToPlayer(player: Player, url: String, amount: Int, itemName: String?, vararg lore: String) {

        val skullStack = ItemStack(Material.PLAYER_HEAD, amount)
        val skullMeta = skullStack.itemMeta as SkullMeta?
        val profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID())
        val textures = profile.textures

        try {
            textures.skin = URL("http://textures.minecraft.net/texture/$url")
        } catch (var11: MalformedURLException) {
            var11.printStackTrace()
        }

        profile.setTextures(textures)
        skullMeta!!.ownerProfile = profile
        if (itemName != null) {
            skullMeta.setDisplayName(itemName)
        }

        if (lore != null && lore.isNotEmpty()) {
            val hexLore = lore.mapNotNull { MessageUtils.hex(it) }
            skullMeta?.lore = hexLore
        }

        skullStack.setItemMeta(skullMeta)
        player.inventory.addItem(skullStack)
    }
}
