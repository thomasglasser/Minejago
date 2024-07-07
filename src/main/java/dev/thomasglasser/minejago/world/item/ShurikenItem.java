package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.tommylib.api.world.entity.projectile.ThrownSword;
import dev.thomasglasser.tommylib.api.world.item.ThrowableSwordItem;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class ShurikenItem extends ThrowableSwordItem {
    public ShurikenItem(Supplier<EntityType<? extends ThrownSword>> projectile, Tier pTier, Properties pProperties) {
        super(projectile, MinejagoSoundEvents.SHURIKEN_THROW, MinejagoSoundEvents.SHURIKEN_IMPACT, pTier, pProperties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 11;
    }
}
