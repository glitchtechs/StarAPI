package net.glitchtechs.starapi

import com.google.common.base.Strings
import org.bukkit.ChatColor


object ProgressBar {
    fun getProgressBar(current: Int, max: Int, totalBars: Int, symbol: Char, completedColor: ChatColor, notCompletedColor: ChatColor): String {
        val percent = current.toFloat() / max.toFloat()
        val progressBars = (totalBars.toFloat() * percent).toInt()
        return Strings.repeat("" + completedColor + symbol, progressBars) + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars)
    }
}