package net.glitchtechs.starapi

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard

class ScoreboardBuilder(private val player: Player, displayName: String, private val plugin: JavaPlugin) {
    private val scoreboard: Scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
    private val objective: Objective = scoreboard.registerNewObjective("dummy", "dummy", MessageUtils.hex(displayName))
    private val lines: MutableMap<Int, String> = HashMap()

    init {
        objective.displaySlot = DisplaySlot.SIDEBAR
    }

    fun setLine(score: Int, text: String): ScoreboardBuilder {
        lines[score] = text
        return this
    }

    fun build() {
        refresh()
    }

    private fun refresh() {
        objective.scoreboard?.resetScores(player.name)
        lines.entries.forEach { entry ->
            val line = MessageUtils.hex(entry.value)
            val score = objective.getScore(line)
            score.score = entry.key
        }
        player.scoreboard = scoreboard
    }

}
