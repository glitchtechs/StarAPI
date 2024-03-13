package net.glitchtechs.starapi;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class StarAPI extends JavaPlugin {
    public void onEnable() {
        this.getLogger().log(Level.WARNING, "");
        this.getLogger().log(Level.WARNING, "StarAPI is now running on your server any plugin that requires this to run is now running!");
        this.getLogger().log(Level.WARNING, "");
    }
}
