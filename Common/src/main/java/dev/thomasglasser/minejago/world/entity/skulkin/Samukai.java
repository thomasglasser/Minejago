package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.MeleeSkeleton;
import dev.thomasglasser.minejago.world.entity.ai.behavior.RangedItemAttack;
import dev.thomasglasser.minejago.world.entity.projectile.ThrownBoneKnife;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.example.SBLSkeleton;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Samukai extends MeleeSkeleton implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Samukai(EntityType<? extends Samukai> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        setItemSlotAndDropWhenKilled(EquipmentSlot.CHEST, new ItemStack(MinejagoArmors.SAMUKAIS_CHESTPLATE.get()));
        setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MinejagoItems.BONE_KNIFE.get()));
        setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(MinejagoItems.BONE_KNIFE.get()));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes()
    {
        return AbstractSkeleton.createAttributes().add(Attributes.MAX_HEALTH, 50).add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        for (int i = -1; i < 3; i++)
        {
            ThrownBoneKnife thrownBoneKnife = new ThrownBoneKnife(this.level(), this, MinejagoItems.BONE_KNIFE.get().getDefaultInstance());
            double d = target.getX() - this.getX();
            double e = target.getY(0.3333333333333333) - thrownBoneKnife.getY() + i;
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            thrownBoneKnife.shoot(d, e + g * 0.2F, f, 1.6F, (float) (14 - this.level().getDifficulty().getId() * 4));
            this.playSound(MinejagoSoundEvents.BONE_KNIFE_THROW.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().addFreshEntity(thrownBoneKnife);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public BrainActivityGroup<? extends SBLSkeleton> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(), 	 // Invalidate the attack target if it's no longer applicable
                new FirstApplicableBehaviour<>( 																							  	 // Run only one of the below behaviours, trying each one in order
                        new RangedItemAttack<>(20, MinejagoItems.BONE_KNIFE.get()).startCondition(entity -> entity.isHolding(MinejagoItems.BONE_KNIFE.get()) && entity.getHealth() <= (entity.getMaxHealth() / 4.0f)),	 												 // Fire a bow, if holding one
                        new AnimatableMeleeAttack<>(0).whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false)))// Melee attack
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
