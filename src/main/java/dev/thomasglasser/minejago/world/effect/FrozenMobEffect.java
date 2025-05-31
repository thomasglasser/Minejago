package dev.thomasglasser.minejago.world.effect;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.tslat.effectslib.api.ExtendedMobEffect;
import org.jetbrains.annotations.Nullable;

public class FrozenMobEffect extends ExtendedMobEffect {
    public static final String TAG_FROZEN = "Frozen";

    public FrozenMobEffect(int color) {
        super(MobEffectCategory.HARMFUL, color);

        ResourceLocation modifier = Minejago.modLoc("frozen");
        addAttributeModifier(Attributes.ATTACK_SPEED, modifier, -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.BLOCK_BREAK_SPEED, modifier, -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, modifier, -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.BURNING_TIME, modifier, -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, modifier, -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.JUMP_STRENGTH, modifier, -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, modifier, -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(Attributes.GRAVITY, modifier, 2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, modifier, 2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onApplication(@Nullable MobEffectInstance effectInstance, @Nullable Entity source, LivingEntity entity, int amplifier) {
        super.onApplication(effectInstance, source, entity, amplifier);
//        CompoundTag entityData = TommyLibServices.ENTITY.getPersistentData(entity);
//        entityData.putBoolean(TAG_FROZEN, true);
//        TommyLibServices.ENTITY.mergePersistentData(entity, entityData, true);
        Level level = entity.level();
        if (!level.isClientSide())
            level.playSound(null, entity.blockPosition(), SoundEvents.SNOW_GOLEM_DEATH, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public boolean tick(LivingEntity entity, @Nullable MobEffectInstance effectInstance, int amplifier) {
        entity.setTicksFrozen(effectInstance != null ? effectInstance.isInfiniteDuration() ? Integer.MAX_VALUE : effectInstance.getDuration() : 20);
        return true;
    }

    @Override
    public boolean onRemove(MobEffectInstance effectInstance, LivingEntity entity) {
        onExpiry(effectInstance, entity);
        return true;
    }

    @Override
    public void onExpiry(MobEffectInstance effectInstance, LivingEntity entity) {
        super.onExpiry(effectInstance, entity);
        entity.setTicksFrozen(0);
//        TommyLibServices.ENTITY.removePersistentData(entity, true, TAG_FROZEN);
        Level level = entity.level();
        if (!level.isClientSide())
            level.playSound(null, entity.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public boolean shouldTickEffect(@Nullable MobEffectInstance effectInstance, @Nullable LivingEntity entity, int ticksRemaining, int amplifier) {
        return ticksRemaining > 0;
    }
}
