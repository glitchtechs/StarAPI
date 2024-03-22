package net.glitchtechs.starapi;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class Abilities {

    public static void teleportPlayer(Player player, double distance) {
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        Vector direction = playerLocation.getDirection().multiply(distance);
        Location destination = playerLocation.add(direction);

        Location targetLocation = getSafeDestination(destination);
        if (targetLocation != null) {
            player.teleport(targetLocation);
        }
    }

    public static void createExplosion(Location center, double radius, double damage) {
        World world = center.getWorld();

        List<Entity> nearbyEntities = (List<Entity>) world.getNearbyEntities(center, radius, radius, radius);
        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                ((LivingEntity) entity).damage(damage);
            }
        }

        world.createExplosion(center.getX(), center.getY(), center.getZ(), 0.0f, false, false);
    }

    public static void multiShot(Player player, int arrowAmount, double damage, boolean isCritical){
        arrowAmount = Math.max(0, arrowAmount);

        ItemStack arrowStack = new ItemStack(Material.ARROW, arrowAmount);

        if (!player.getInventory().containsAtLeast(arrowStack, arrowAmount)) {
            return;
        }
        player.getInventory().removeItem(arrowStack);

        for (int i = 0; i < arrowAmount; i++){
            Arrow arrow = player.launchProjectile(Arrow.class, player.getLocation().getDirection());
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);

            arrow.setDamage(damage);
            arrow.setCritical(isCritical);
        }
    }


    private static Location getSafeDestination(Location destination) {
        World world = destination.getWorld();
        Vector direction = destination.getDirection();

        for (double i = 0.0; i < 1.0; i += 0.1) {
            Location checkLocation = destination.clone().add(direction.clone().multiply(i));
            if (checkLocation.getBlock().getType() != Material.AIR) {
                return checkLocation.subtract(direction.clone().multiply(0.2));
            }
        }
        return destination;
    }

}
