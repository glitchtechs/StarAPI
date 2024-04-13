package net.glitchtechs.starapi.Timers;

import org.bukkit.scheduler.BukkitTask;

import static net.glitchtechs.starapi.Timers.ActiveTimers.activeTasks;

public class CancelTimer {

    public static void cancelTask(BukkitTask task) {
        task.cancel();
        activeTasks.remove(task);
    }

}
