package net.glitchtechs.starapi

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack
import kotlin.math.max


object Abilities {
    fun teleportPlayer(player: Player, distance: Double) {
        val playerLocation = player.location
        val world = player.world

        val direction = playerLocation.direction.multiply(distance)
        val destination = playerLocation.add(direction)

        val targetLocation = getSafeDestination(destination)
        if (targetLocation != null) {
            player.teleport(targetLocation)
        }
    }

    fun createExplosion(center: Location, radius: Double, damage: Double) {
        val world = center.world

        val nearbyEntities = world!!.getNearbyEntities(center, radius, radius, radius) as List<Entity>
        for (entity in nearbyEntities) {
            if (entity is LivingEntity && entity !is Player) {
                entity.damage(damage)
            }
        }

        world.createExplosion(center.x, center.y, center.z, 0.0f, false, false)
    }

    fun multiShot(player: Player, arrowAmount: Int, damage: Double, isCritical: Boolean) {
        var arrowAmount = arrowAmount
        arrowAmount = max(0.0, arrowAmount.toDouble()).toInt()

        val arrowStack = ItemStack(Material.ARROW, arrowAmount)

        if (!player.inventory.containsAtLeast(arrowStack, arrowAmount)) {
            return
        }
        player.inventory.removeItem(arrowStack)

        for (i in 0 until arrowAmount) {
            val arrow = player.launchProjectile(Arrow::class.java, player.location.direction)
            arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED

            arrow.damage = damage
            arrow.isCritical = isCritical
        }
    }


    private fun getSafeDestination(destination: Location): Location {
        val world = destination.world
        val direction = destination.direction

        var i = 0.0
        while (i < 1.0) {
            val checkLocation = destination.clone().add(direction.clone().multiply(i))
            if (checkLocation.block.type != Material.AIR) {
                return checkLocation.subtract(direction.clone().multiply(0.2))
            }
            i += 0.1
        }
        return destination
    }
}