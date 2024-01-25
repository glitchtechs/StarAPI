package net.glitch.starapi;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class MobBuilder {

    private EntityType type;
    private Location location;
    private String customName;
    private double health;
    private List<ItemStack> armorContents;
    private ItemStack heldItem;
    private List<PotionEffect> potionEffects;

    private MobBuilder() {
        this.armorContents = new ArrayList<>();
        this.potionEffects = new ArrayList<>();
    }

    public static MobBuilder create() {
        return new MobBuilder();
    }

    public MobBuilder type(EntityType type) {
        this.type = type;
        return this;
    }

    public MobBuilder location(Location location) {
        this.location = location;
        return this;
    }

    public MobBuilder customName(String customName) {
        this.customName = MessageUtils.hex(customName);
        return this;
    }

    public MobBuilder health(double health) {
        return this;
    }


    public MobBuilder armorContents(Material... materials) {
        for (Material material : materials) {
            this.armorContents.add(new ItemStack(material));
        }
        return this;
    }

    public MobBuilder heldItem(Material material) {
        this.heldItem = new ItemStack(material);
        return this;
    }

    private boolean showParticles = true;

    public MobBuilder showParticles(boolean showParticles) {
        this.showParticles = showParticles;
        return this;
    }

    public MobBuilder potionEffect(PotionEffectType type, int durationSeconds, int amplifier) {
        PotionEffect potionEffect = new PotionEffect(type, durationSeconds * 20, amplifier, false, showParticles);
        this.potionEffects.add(potionEffect);
        return this;
    }

    private boolean isBaby;

    public MobBuilder isBaby(boolean isBaby) {
        this.isBaby = isBaby;
        return this;
    }

    public LivingEntity spawn() {
        validate();

        Entity spawnedEntity = location.getWorld().spawnEntity(location, type);

        if (!(spawnedEntity instanceof LivingEntity)) {
            throw new IllegalStateException("Spawned entity is not a LivingEntity");
        }

        LivingEntity mob = (LivingEntity) spawnedEntity;

        if (customName != null) {
            mob.setCustomName(customName);
            mob.setCustomNameVisible(true);
        }

        if (health > 0) {
            mob.setHealth(health);
        }

        EntityEquipment equipment = mob.getEquipment();
        if (equipment != null) {
            int i = 0;
            for (ItemStack armor : armorContents) {
                equipment.setArmorContents(armorContents.toArray(new ItemStack[0]));
                i++;
            }
        }

        if (heldItem != null) {
            equipment.setItemInMainHand(heldItem);
        }

        for (PotionEffect effect : potionEffects) {
            mob.addPotionEffect(effect);
        }

        if (mob instanceof Ageable) {
            Ageable ageable = (Ageable) mob;
            ageable.setAdult();
            if (isBaby) {
                ageable.setBaby();
            }
        }

        return mob;
    }

    private void validate() {
        if (type == null || location == null) {
            throw new IllegalStateException("Type and location must be set before spawning the mob.");
        }
    }
}