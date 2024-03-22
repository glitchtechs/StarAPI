package net.glitchtechs.starapi

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class StarAPI : JavaPlugin() {

    override fun onEnable() {
        logger.log(Level.WARNING, "")
        logger.log(Level.WARNING, "StarAPI is now running on your server. Any plugin that requires this to run is now running!")
        logger.log(Level.WARNING, "")
    }
}
