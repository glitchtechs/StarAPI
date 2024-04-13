package net.glitchtechs.starapi.Abilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class Implosion {

    public static void create(Location center, double radius, double damage) {
        World world = center.getWorld();

        List<Entity> nearbyEntities = (List<Entity>) world.getNearbyEntities(center, radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                ((LivingEntity) entity).damage(damage);
            }
        }

        world.createExplosion(center.getX(), center.getY(), center.getZ(), 0.0f, false, false);
    }

}
