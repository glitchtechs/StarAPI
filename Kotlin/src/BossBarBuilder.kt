package net.glitchtechs.starapi

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*


class BossBarBuilder(private val plugin: JavaPlugin) {
    private val bossBars: MutableMap<UUID?, BossBar?>
    private var title: String? = null
    private var color: BarColor? = null
    private var style: BarStyle? = null
    private var progress = 0f
    private lateinit var flags: Array<BarFlag>

    init {
        bossBars = HashMap()
    }

    fun setTitle(title: String?): BossBarBuilder {
        this.title = title
        return this
    }

    fun setColor(color: BarColor?): BossBarBuilder {
        this.color = color
        return this
    }

    fun setStyle(style: BarStyle?): BossBarBuilder {
        this.style = style
        return this
    }

    fun setProgress(progress: Float): BossBarBuilder {
        this.progress = progress
        return this
    }

    fun setFlags(vararg flags: BarFlag): BossBarBuilder {
        this.flags = flags as Array<BarFlag>
        return this
    }

    fun create(player: Player): BossBarBuilder {
        val bossBar = color?.let { Bukkit.createBossBar(MessageUtils.hex(title!!), it, style ?: BarStyle.SOLID, *flags) }
        bossBar?.progress = progress.toDouble()
        bossBars[player.uniqueId] = bossBar
        bossBar?.addPlayer(player)
        return this
    }

    fun update(player: Player): BossBarBuilder {
        val bossBar = bossBars[player.uniqueId]
        if (bossBar != null) {
            bossBar.setTitle(MessageUtils.hex(title!!))
            bossBar.color = color!!
            bossBar.style = style!!
            bossBar.progress = progress.toDouble()
            bossBar.removeAll()
            bossBar.addPlayer(player)
        }

        return this
    }
}