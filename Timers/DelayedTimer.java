package net.glitchtechs.starapi.Timers;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static net.glitchtechs.starapi.Timers.ActiveTimers.activeTasks;

public class DelayedTimer {

    public static BukkitTask scheduleDelayedTask(Plugin plugin, long delay, Runnable task) {
        BukkitTask bukkitTask = new BukkitRunnable() {
            public void run() {
                task.run();
                activeTasks.remove(this);
            }
        }.runTaskLater(plugin, delay);
        activeTasks.put(bukkitTask, plugin);
        return bukkitTask;
    }

}
