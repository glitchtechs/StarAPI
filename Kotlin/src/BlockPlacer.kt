package net.glitchtechs.starapi

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.util.concurrent.atomic.AtomicInteger


class BlockPlacer(private val plugin: JavaPlugin) {
    private var material: Material? = null
    private var direction: Direction? = null
    private var length = 0
    private var height = 0
    private var delayBetweenBlocks: Long = 0
    private var task: BukkitTask? = null

    fun setDirection(direction: Direction?): BlockPlacer {
        this.direction = direction
        return this
    }

    fun setLength(length: Int): BlockPlacer {
        this.length = length
        return this
    }

    fun setHeight(height: Int): BlockPlacer {
        this.height = height
        return this
    }

    fun setDelayBetweenBlocks(delay: Long): BlockPlacer {
        this.delayBetweenBlocks = delay
        return this
    }

    fun build(origin: Location) {
        val world = origin.world
        val x = AtomicInteger(origin.blockX)
        val y = AtomicInteger(origin.blockY)
        val z = AtomicInteger(origin.blockZ)

        task = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            for (j in 0 until height) {
                val block = world!!.getBlockAt(x.get(), y.get() + j, z.get())
                block.type = material!!
            }
            when (direction) {
                Direction.EAST -> x.getAndIncrement()
                Direction.WEST -> x.getAndDecrement()
                Direction.SOUTH -> z.getAndIncrement()
                Direction.NORTH -> z.getAndDecrement()
                Direction.UP -> y.getAndIncrement()
                Direction.DOWN -> y.getAndDecrement()
                Direction.NORTHEAST -> {
                    x.getAndIncrement()
                    z.getAndDecrement()
                }

                Direction.SOUTHEAST -> {
                    x.getAndIncrement()
                    z.getAndIncrement()
                }

                Direction.SOUTHWEST -> {
                    x.getAndDecrement()
                    z.getAndIncrement()
                }

                Direction.NORTHWEST -> {
                    x.getAndDecrement()
                    z.getAndDecrement()
                }

                null -> TODO()
            }
            length--
            if (length <= 0) {
                task!!.cancel()
            }
        }, delayBetweenBlocks, delayBetweenBlocks)
    }

    enum class Direction {
        EAST, WEST, SOUTH, NORTH, UP, DOWN, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST
    }

    companion object {
        fun create(plugin: JavaPlugin, material: Material?): BlockPlacer {
            val blockPlacer = BlockPlacer(plugin)
            blockPlacer.material = material
            return blockPlacer
        }
    }
}
