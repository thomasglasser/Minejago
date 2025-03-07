package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Spykor extends Spider implements GeoEntity {
    private static final EntityDataAccessor<Boolean> DATA_RESTING = SynchedEntityData.defineId(Spykor.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_FALLING = SynchedEntityData.defineId(Spykor.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Vector3f> DATA_RESTING_POS = SynchedEntityData.defineId(Spykor.class, EntityDataSerializers.VECTOR3);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Spykor(EntityType<? extends Spider> entityType, Level level) {
        super(entityType, level);
        noCulling = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Spider.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 64);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_RESTING, false);
        builder.define(DATA_FALLING, false);
        builder.define(DATA_RESTING_POS, Vec3.ZERO.toVector3f());
    }

    public boolean isResting() {
        return this.entityData.get(DATA_RESTING);
    }

    public void setResting(boolean resting) {
        this.entityData.set(DATA_RESTING, resting);
    }

    public boolean isFalling() {
        return this.entityData.get(DATA_FALLING);
    }

    public void setFalling(boolean falling) {
        this.entityData.set(DATA_FALLING, falling);
    }

    public Vec3 getRestingPos() {
        return new Vec3(this.entityData.get(DATA_RESTING_POS));
    }

    public void setRestingPos(Vec3 pos) {
        this.entityData.set(DATA_RESTING_POS, pos.toVector3f());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Armadillo.class, 6.0F, 1.0, 1.2, (p_320185_) -> !((Armadillo) p_320185_).isScared()));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8) {
            @Override
            public boolean canUse() {
                return super.canUse() && !isClimbing();
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();
        if (randomsource.nextInt(100) == 0) {
            Skeleton skulkin = MinejagoEntityTypes.SKULKIN.get().create(this.level());
            if (skulkin != null) {
                skulkin.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                skulkin.finalizeSpawn(level, difficulty, spawnType, null);
                skulkin.startRiding(this);
            }
        }

        spawnGroupData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);

        if (spawnGroupData == null) {
            spawnGroupData = new SpiderEffectsGroupData();
            if (level.getDifficulty() == Difficulty.HARD && randomsource.nextFloat() < 0.1F * difficulty.getSpecialMultiplier()) {
                ((SpiderEffectsGroupData) spawnGroupData).setRandomEffect(randomsource);
            }
        }

        if (spawnGroupData instanceof SpiderEffectsGroupData spider$spidereffectsgroupdata) {
            Holder<MobEffect> holder = spider$spidereffectsgroupdata.effect;
            if (holder != null) {
                this.addEffect(new MobEffectInstance(holder, -1));
            }
        }

        if (level.getBlockState(blockPosition()).isAir())
            setClimbing(true);

        return spawnGroupData;
    }

    @Override
    public void aiStep() {
        boolean flag = this.isSunBurnTick();
        if (flag) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
            if (!itemstack.isEmpty()) {
                if (itemstack.isDamageableItem()) {
                    Item item = itemstack.getItem();
                    itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                    if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                        this.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                        this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
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

    @Override
    public void tick() {
        super.tick();
        if (!level().getBlockState(blockPosition().above(2)).isAir() && level().getBlockState(blockPosition().below()).isAir() && isClimbing()) {
            setClimbing(false);
            if (!isResting() && !isFalling()) {
                setResting(true);
                setDeltaMovement(Vec3.ZERO);
                setRestingPos(position());
            }
        }
        if (isResting()) {
            if (getTarget() == null) {
                setPos(getRestingPos());
            } else {
                setResting(false);
                setFalling(true);
            }
        }
        if (isFalling()) {
            if (level().getBlockState(blockPosition().below()).isAir()) {
                setClimbing(false);
                resetFallDistance();
                setDeltaMovement(getDeltaMovement().scale(0.5));
            } else {
                setFalling(false);
                setRestingPos(Vec3.ZERO);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        // TODO: Walking, resting, and falling
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
