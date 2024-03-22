package net.glitchtechs.starapi

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*


class ActionBarBuilder(private val plugin: JavaPlugin) {
    private val actionBars: MutableMap<UUID?, Int?>
    private var message: String? = null

    init {
        this.actionBars = HashMap()
    }

    fun message(message: String?): ActionBarBuilder {
        this.message = MessageUtils.hex(message!!)
        return this
    }

    fun send(player: Player) {
        val taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, *TextComponent.fromLegacyText(this.message))
            actionBars.remove(player.uniqueId)
        }
        actionBars[player.uniqueId] = taskId
    }

    fun clear(player: Player) {
        val taskId = actionBars.remove(player.uniqueId)
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId)
        }
    }
}