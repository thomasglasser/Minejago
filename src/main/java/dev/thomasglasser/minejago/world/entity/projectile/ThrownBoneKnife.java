package dev.thomasglasser.minejago.world.entity.projectile;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.world.entity.projectile.ThrownSword;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ThrownBoneKnife extends ThrownSword implements ItemSupplier {
    public ThrownBoneKnife(EntityType<? extends ThrownSword> entity, Level level) {
        super(entity, level);
    }

    public ThrownBoneKnife(EntityType<? extends ThrownSword> entityType, double x, double y, double z, Level level, ItemStack pickupItemStack, float baseDamage, @Nullable SoundEvent hitGroundSound, @Nullable SoundEvent returnSound) {
        super(entityType, x, y, z, level, pickupItemStack, baseDamage, hitGroundSound, returnSound);
    }

    public ThrownBoneKnife(EntityType<? extends ThrownSword> entityType, LivingEntity shooter, Level level, ItemStack pickupItemStack, float baseDamage, @Nullable SoundEvent hitGroundSound, @Nullable SoundEvent returnSound) {
        super(entityType, shooter, level, pickupItemStack, baseDamage, hitGroundSound, returnSound);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return MinejagoItems.BONE_KNIFE.toStack();
    }

    @Override
    public ItemStack getItem() {
        return getPickupItemStackOrigin();
    }
}
