package net.glitchtechs.starapi

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Ageable
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class MobBuilder private constructor() {
    private var type: EntityType? = null
    private var location: Location? = null
    private var customName: String? = null
    private val health = 0.0
    private val armorContents: MutableList<ItemStack?> = ArrayList()
    private var heldItem: ItemStack? = null
    private val potionEffects: MutableList<PotionEffect?> = ArrayList()
    private var showParticles = true
    private var isBaby = false

    fun type(type: EntityType?): MobBuilder {
        this.type = type
        return this
    }

    fun location(location: Location?): MobBuilder {
        this.location = location
        return this
    }

    fun customName(customName: String?): MobBuilder {
        this.customName = MessageUtils.hex(customName!!)
        return this
    }

    fun health(health: Double): MobBuilder {
        return this
    }

    fun armorContents(vararg materials: Material): MobBuilder {
        val var2 = materials
        val var3 = materials.size

        for (var4 in 0 until var3) {
            val material = var2[var4]
            armorContents.add(ItemStack(material))
        }

        return this
    }

    fun heldItem(material: Material?): MobBuilder {
        this.heldItem = ItemStack(material!!)
        return this
    }

    fun showParticles(showParticles: Boolean): MobBuilder {
        this.showParticles = showParticles
        return this
    }

    fun potionEffect(type: PotionEffectType?, durationSeconds: Int, amplifier: Int): MobBuilder {
        val potionEffect = PotionEffect(type!!, durationSeconds * 20, amplifier, false, this.showParticles)
        potionEffects.add(potionEffect)
        return this
    }

    fun isBaby(isBaby: Boolean): MobBuilder {
        this.isBaby = isBaby
        return this
    }

    fun spawn(): LivingEntity {
        this.validate()
        val spawnedEntity = location!!.world!!.spawnEntity(location!!, type!!)
        check(spawnedEntity is LivingEntity) { "Spawned entity is not a LivingEntity" }
        val mob = spawnedEntity
        if (this.customName != null) {
            mob.customName = customName
            mob.isCustomNameVisible = true
        }

        if (this.health > 0.0) {
            mob.health = health
        }

        val equipment = mob.equipment
        if (equipment != null) {
            var i = 0

            val var5: Iterator<*> = armorContents.iterator()
            while (var5.hasNext()) {
                val armor = var5.next() as ItemStack
                equipment.armorContents = (armorContents.toTypedArray<ItemStack?>() as Array<ItemStack?>?)!!
                ++i
            }
        }

        if (this.heldItem != null) {
            equipment!!.setItemInMainHand(this.heldItem)
        }

        val var7: Iterator<*> = potionEffects.iterator()

        while (var7.hasNext()) {
            val effect = var7.next() as PotionEffect
            mob.addPotionEffect(effect)
        }

        if (mob is Ageable) {
            val ageable = mob
            ageable.setAdult()
            if (this.isBaby) {
                ageable.setBaby()
            }
        }

        return mob
    }

    private fun validate() {
        if (this.type == null || this.location == null) {
            throw IllegalStateException("Type and location must be set before spawning the mob.")
        }
    }

    companion object {
        fun create(): MobBuilder {
            return MobBuilder()
        }
    }
}