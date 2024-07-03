package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.character.Character;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import org.jetbrains.annotations.Nullable;

public class SkulkinHorse extends SkeletonHorse implements Enemy {
    public SkulkinHorse(EntityType<? extends SkulkinHorse> entityType, Level level) {
        super(entityType, level);
        this.skeletonTrapGoal = new SkulkinTrapGoal(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return SkeletonHorse.createAttributes().add(Attributes.ATTACK_DAMAGE, 2.0f).add(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothGroundNavigation(this, level);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return MinejagoEntityTypes.SKULKIN_HORSE.get().create(level);
    }

    @Override
    protected void registerGoals() {
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
        boolean flag = this.isSunBurnTick();
        if (flag) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.BODY);
            if (!itemstack.isEmpty()) {
                if (itemstack.isDamageableItem()) {
                    Item item = itemstack.getItem();
                    itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                    if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                        this.onEquippedItemBroken(item, EquipmentSlot.BODY);
                        this.setItemSlot(EquipmentSlot.BODY, ItemStack.EMPTY);
                    }
                }

                flag = false;
            }

            if (flag) {
                this.igniteForSeconds(8.0F);
            }
        }

        super.aiStep();
    }

    public void equipArmor(ItemStack itemStack) {
        if (itemStack.getItem() instanceof AnimalArmorItem) {
            setItemSlot(EquipmentSlot.BODY, itemStack);
        }
    }

    public static class SkulkinTrapGoal extends SkeletonTrapGoal {
        private final SkulkinHorse horse;

        public SkulkinTrapGoal(SkulkinHorse skeletonHorse) {
            super(skeletonHorse);
            horse = skeletonHorse;
        }

        @Override
        public AbstractHorse createHorse(DifficultyInstance difficulty) {
            SkulkinHorse skeletonHorse = MinejagoEntityTypes.SKULKIN_HORSE.get().create(this.horse.level());
            if (skeletonHorse != null) {
                skeletonHorse.finalizeSpawn((ServerLevel) this.horse.level(), difficulty, MobSpawnType.TRIGGERED, null);
                skeletonHorse.setPos(this.horse.getX(), this.horse.getY(), this.horse.getZ());
                skeletonHorse.invulnerableTime = 60;
                skeletonHorse.setPersistenceRequired();
                skeletonHorse.setTamed(true);
                skeletonHorse.equipSaddle(Items.SADDLE.getDefaultInstance(), null);
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
                skeleton.finalizeSpawn((ServerLevel) horse.level(), difficulty, MobSpawnType.TRIGGERED, null);
                skeleton.setPos(horse.getX(), horse.getY(), horse.getZ());
                skeleton.invulnerableTime = 60;
                skeleton.setPersistenceRequired();
                if (skeleton.getItemBySlot(EquipmentSlot.HEAD).isEmpty() && equipArmor(skeleton)) {
                    skeleton.setItemSlot(EquipmentSlot.HEAD, Items.IRON_HELMET.getDefaultInstance());
                }

                this.enchant(skeleton, EquipmentSlot.MAINHAND, difficulty);
                this.enchant(skeleton, EquipmentSlot.HEAD, difficulty);
            }

            return skeleton;
        }

        private boolean equipArmor(Mob mob) {
            return (mob.level().isDay() && mob.level().canSeeSky(mob.blockPosition()));
        }
    }
}
