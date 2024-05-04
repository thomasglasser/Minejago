package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.character.Character;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.SkeletonTrapGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import org.jetbrains.annotations.Nullable;

public class SkulkinHorse extends SkeletonHorse implements Enemy
{

    public SkulkinHorse(EntityType<? extends SkulkinHorse> entityType, Level level) {
        super(entityType, level);
        this.skeletonTrapGoal = new SkulkinTrapGoal(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return SkeletonHorse.createAttributes().add(Attributes.ATTACK_DAMAGE, 2.0f).add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    protected PathNavigation createNavigation(Level level)
    {
        return new SmoothGroundNavigation(this, level);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return MinejagoEntityTypes.SKULKIN_HORSE.get().create(level);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(1, new RestrictSunGoal(this));
        this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.1));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Character.class, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            boolean bl = this.isSunBurnTick();
            if (bl) {
                ItemStack itemStack = this.getItemBySlot(EquipmentSlot.CHEST);
                if (!itemStack.isEmpty()) {
                    if (itemStack.isDamageableItem()) {
                        itemStack.setDamageValue(itemStack.getDamageValue() + this.random.nextInt(2));
                        if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) {
                            this.broadcastBreakEvent(EquipmentSlot.HEAD);
                            this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    bl = false;
                }

                if (bl) {
                    this.igniteForSeconds(8);
                }
            }
        }

        super.aiStep();
    }

    public void equipArmor(ItemStack itemStack) {
        if (itemStack.getItem() instanceof AnimalArmorItem) {
            setItemSlot(EquipmentSlot.BODY, itemStack);
        }
    }

    public static class SkulkinTrapGoal extends SkeletonTrapGoal
    {
        private final SkulkinHorse horse;

        public SkulkinTrapGoal(SkulkinHorse skeletonHorse) {
            super(skeletonHorse);
            horse = skeletonHorse;
        }

        @Override
        public void tick() {
            if (!horse.isWearingBodyArmor() && equipArmor(horse))
                horse.equipArmor(Items.IRON_HORSE_ARMOR.getDefaultInstance());
            super.tick();
        }

        @Override
        public AbstractHorse createHorse(DifficultyInstance difficulty) {
            SkulkinHorse skeletonHorse = MinejagoEntityTypes.SKULKIN_HORSE.get().create(this.horse.level());
            if (skeletonHorse != null) {
                skeletonHorse.finalizeSpawn((ServerLevel)this.horse.level(), difficulty, MobSpawnType.TRIGGERED, null);
                skeletonHorse.setPos(this.horse.getX(), this.horse.getY(), this.horse.getZ());
                skeletonHorse.invulnerableTime = 60;
                skeletonHorse.setPersistenceRequired();
                skeletonHorse.setTamed(true);
                skeletonHorse.equipSaddle(null);
                skeletonHorse.setAge(0);
                if (equipArmor(skeletonHorse))
                    skeletonHorse.equipArmor(Items.IRON_HORSE_ARMOR.getDefaultInstance());
            }

            return skeletonHorse;
        }

        @Override
        public Skeleton createSkeleton(DifficultyInstance difficulty, AbstractHorse horse) {
            Skulkin skeleton = MinejagoEntityTypes.SKULKIN.get().create(horse.level());
            if (skeleton != null) {
                skeleton.setVariant(Skulkin.Variant.BOW);
                skeleton.finalizeSpawn((ServerLevel)horse.level(), difficulty, MobSpawnType.TRIGGERED, null);
                skeleton.setPos(horse.getX(), horse.getY(), horse.getZ());
                skeleton.invulnerableTime = 60;
                skeleton.setPersistenceRequired();
                if (skeleton.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && equipArmor(skeleton)) {
                    skeleton.setItemSlot(EquipmentSlot.HEAD, Items.IRON_HELMET.getDefaultInstance());
                }

                skeleton.setItemSlot(
                        EquipmentSlot.MAINHAND,
                        EnchantmentHelper.enchantItem(
                                skeleton.level().enabledFeatures(),
                                skeleton.getRandom(),
                                disenchant(skeleton.getMainHandItem()),
                                (int)(5.0F + difficulty.getSpecialMultiplier() * (float)skeleton.getRandom().nextInt(18)),
                                false
                        )
                );
                skeleton.setItemSlot(
                        EquipmentSlot.HEAD,
                        EnchantmentHelper.enchantItem(
                                skeleton.level().enabledFeatures(),
                                skeleton.getRandom(),
                                disenchant(skeleton.getItemBySlot(EquipmentSlot.HEAD)),
                                (int)(5.0F + difficulty.getSpecialMultiplier() * (float)skeleton.getRandom().nextInt(18)),
                                false
                        )
                );
            }

            return skeleton;
        }

        private boolean equipArmor(Mob mob)
        {
            return (mob.level().isDay() && mob.level().canSeeSky(mob.blockPosition()));
        }
    }
}