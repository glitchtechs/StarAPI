package net.glitchtechs.starapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

public class BlockPlacer {
    private final JavaPlugin plugin;
    private Material material;
    private Direction direction;
    private int length;
    private int height;
    private long delayBetweenBlocks;
    private BukkitTask task;

    public BlockPlacer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static BlockPlacer create(JavaPlugin plugin, Material material) {
        BlockPlacer blockPlacer = new BlockPlacer(plugin);
        blockPlacer.material = material;
        return blockPlacer;
    }

    public BlockPlacer setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public BlockPlacer setLength(int length) {
        this.length = length;
        return this;
    }

    public BlockPlacer setHeight(int height) {
        this.height = height;
        return this;
    }

    public BlockPlacer setDelayBetweenBlocks(long delay) {
        this.delayBetweenBlocks = delay;
        return this;
    }

    public void build(Location origin) {
        World world = origin.getWorld();
        AtomicInteger x = new AtomicInteger(origin.getBlockX());
        AtomicInteger y = new AtomicInteger(origin.getBlockY());
        AtomicInteger z = new AtomicInteger(origin.getBlockZ());

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (int j = 0; j < height; j++) {
                Block block = world.getBlockAt(x.get(), y.get() + j, z.get());
                block.setType(material);
            }

            switch (direction) {
                case EAST:
                    x.getAndIncrement();
                    break;
                case WEST:
                    x.getAndDecrement();
                    break;
                case SOUTH:
                    z.getAndIncrement();
                    break;
                case NORTH:
                    z.getAndDecrement();
                    break;
                case UP:
                    y.getAndIncrement();
                    break;
                case DOWN:
                    y.getAndDecrement();
                    break;
                case NORTHEAST:
                    x.getAndIncrement();
                    z.getAndDecrement();
                    break;
                case SOUTHEAST:
                    x.getAndIncrement();
                    z.getAndIncrement();
                    break;
                case SOUTHWEST:
                    x.getAndDecrement();
                    z.getAndIncrement();
                    break;
                case NORTHWEST:
                    x.getAndDecrement();
                    z.getAndDecrement();
                    break;
            }

            length--;

            if (length <= 0) {
                task.cancel();
            }
        }, delayBetweenBlocks, delayBetweenBlocks);
    }

    public enum Direction {
        EAST, WEST, SOUTH, NORTH, UP, DOWN, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST
    }
}
