package net.glitchtechs.starapi

import org.bukkit.ChatColor
import java.util.regex.Pattern

object MessageUtils {
    fun hex(message: String): String {
        var message = message
        val pattern = Pattern.compile("(#[a-fA-F0-9]{6})")

        var matcher = pattern.matcher(message)
        while (matcher.find()) {
            val hexCode = message.substring(matcher.start(), matcher.end())
            val replaceSharp = hexCode.replace('#', 'x')
            val ch = replaceSharp.toCharArray()
            val builder = StringBuilder("")
            val var7 = ch
            val var8 = ch.size

            for (var9 in 0 until var8) {
                val c = var7[var9]
                builder.append("&$c")
            }

            message = message.replace(hexCode, builder.toString())
            matcher = pattern.matcher(message)
        }

        return ChatColor.translateAlternateColorCodes('&', message).replace('&', '§')
    }
}
