package net.glitchtechs.starapi;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class Timers {

    private static final Map<BukkitTask, Plugin> activeTasks = new HashMap<>();

    public static BukkitTask timer(long delay, long period, final Runnable task) {
        return (new BukkitRunnable() {
            public void run() {
                task.run();
            }
        }).runTaskTimer(JavaPlugin.getProvidingPlugin(Timers.class), delay, period);
    }

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

    public static void cancelTask(BukkitTask task) {
        task.cancel();
        activeTasks.remove(task);
    }

    public static boolean isTaskActive(BukkitTask task) {
        return activeTasks.containsKey(task);
    }
}
