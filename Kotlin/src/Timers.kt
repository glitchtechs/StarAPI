package net.glitchtechs.starapi

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask


object Timers {
    private val activeTasks: MutableMap<BukkitTask, Plugin> = mutableMapOf()

    fun timer(delay: Long, period: Long, task: Runnable): BukkitTask {
        return object : BukkitRunnable() {
            override fun run() {
                task.run()
            }
        }.runTaskTimer(JavaPlugin.getProvidingPlugin(Timers::class.java), delay, period)
    }

    fun scheduleDelayedTask(plugin: Plugin, delay: Long, task: Runnable): BukkitTask {
        val bukkitTask = object : BukkitRunnable() {
            override fun run() {
                task.run()
                val removedTask = activeTasks.entries.find { it.key.equals(this) }
                removedTask?.let { activeTasks.remove(it.key) }
            }
        }.runTaskLater(plugin, delay)
        activeTasks[bukkitTask] = plugin
        return bukkitTask
    }

    fun cancelTask(task: BukkitTask) {
        task.cancel()
        activeTasks.remove(task)
    }

    fun isTaskActive(task: BukkitTask): Boolean {
        return activeTasks.containsKey(task)
    }
}