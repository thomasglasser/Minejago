package dev.thomasglasser.minejago.world.entity.dragon;

import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.item.GoldenWeaponItem;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.tommylib.api.world.entity.PlayerRideableFlying;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class Dragon extends TamableAnimal implements GeoEntity, SmartBrainOwner<Dragon>, PlayerRideableFlying, RangedAttackMob {
    public static final RawAnimation LIFT = RawAnimation.begin().thenPlay("move.lift");
    private static final EntityDataAccessor<Boolean> DATA_SHOOTING = SynchedEntityData.defineId(Dragon.class, EntityDataSerializers.BOOLEAN);
    public static final int TICKS_PER_FLAP = 39;
    public static final double HEAL_BOND = 0.05;
    public static final double FOOD_BOND = 0.1;
    public static final double TREAT_BOND = 0.15;
    public static final double TALK_BOND = 1;

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Map<Player, Double> bond = new HashMap<>();
    private final TagKey<Power> acceptablePowers;

    private boolean isLiftingOff;
    private Flight flight = Flight.HOVERING;
    private int flyingTicks = 0;
    private double speedMultiplier = 1;

    public Dragon(EntityType<? extends Dragon> entityType, Level level, ResourceKey<Power> power, TagKey<Power> powers) {
        super(entityType, level);
        new PowerData(power, false).save(this);
        this.acceptablePowers = powers;
        setTame(false, false);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothGroundNavigation(this, level) {
            @Override
            protected boolean canUpdatePath() {
                return super.canUpdatePath() || !isNoGravity();
            }
        };
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ARMOR, 4.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.FLYING_SPEED, 2.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(
                new AnimationController<>(this, "Walk/Idle/Fly/Lift", 0, state -> {
                    if (isLiftingOff) {
                        return state.setAndContinue(LIFT);
                    } else if (state.getAnimatable().isNoGravity()) {
                        return state.setAndContinue(DefaultAnimations.FLY);
                    } else if (state.isMoving()) {
                        return state.setAndContinue(DefaultAnimations.WALK);
                    }
                    return state.setAndContinue(DefaultAnimations.IDLE);
                }),
                new AnimationController<>(this, "Shoot", 0, state -> {
                    if (isShooting())
                        return state.setAndContinue(DefaultAnimations.ATTACK_SHOOT);
                    else
                        return PlayState.STOP;
                }));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SHOOTING, false);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    @Override
    public List<ExtendedSensor<Dragon>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<Dragon>().setPredicate((target, dragon) -> {
                    if (target instanceof Enemy) return true;
                    if (target instanceof Character) return false;
                    if (target.isAlliedTo(dragon)) return false;
                    LivingEntity owner = dragon.getOwner();
                    if (owner == null) {
                        if (target instanceof Player player) {
                            if (getBond(player) < 0) return true;
                            if (player.getInventory().items.stream().anyMatch(stack -> stack.getItem() instanceof GoldenWeaponItem goldenWeaponItem && goldenWeaponItem.canPowerHandle(this.getData(MinejagoAttachmentTypes.POWER).power(), level()))) return true;
                        }
                        return target instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() instanceof Player player && getBond(player) < 0;
                    } else {
                        if (target.isAlliedTo(owner)) return false;
                        if (target instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() == dragon.getOwner()) return false;
                        if (target.getLastHurtByMob() != null && target.getLastHurtByMob().is(dragon.getOwner())) return true;
                        if (BrainUtils.hasMemory(target.getBrain(), MemoryModuleType.ATTACK_TARGET)) {
                            return BrainUtils.getTargetOfEntity(target) == dragon.getOwner();
                        } else if (target instanceof Mob mob) {
                            return mob.getTarget() == dragon.getOwner();
                        }
                    }

                    return false;
                }),
                new NearbyPlayersSensor<>(),
                new HurtBySensor<>());
    }

    @Override
    public BrainActivityGroup<Dragon> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new SetWalkTargetToAttackTarget<>(),
                new MoveToWalkTarget<>(),
                new LookAtTargetSink(40, 300), 														// Look at the look target
                new FloatToSurfaceOfFluid<>());																					// Move to the current walk target
    }

    @Override
    public BrainActivityGroup<Dragon> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(                // Run only one of the below behaviours, trying each one in order. Include explicit generic typing because javac is silly
                        new TargetOrRetaliate<>().attackablePredicate(entity -> entity.isAlive() && (!(entity instanceof Player player) || !player.isCreative() && !(this.getOwner() == player))),                        // Set the attack target
                        new SetPlayerLookTarget<>(),                    // Set the look target to a nearby player if available
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>( 								// Run only one of the below behaviours, picked at random
                        new SetRandomWalkTarget<>().speedModifier(1), 				// Set the walk target to a nearby random pathable location
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Don't walk anywhere
    }

    @Override
    public BrainActivityGroup<? extends Dragon> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((entity, target) -> target instanceof Player pl && (pl.isCreative() || pl.isSpectator() || (getBond(pl) >= 0 && !pl.getInventory().items.stream().anyMatch(stack -> stack.getItem() instanceof GoldenWeaponItem goldenWeaponItem && goldenWeaponItem.canPowerHandle(getData(MinejagoAttachmentTypes.POWER).power(), level()))))), 	 // Invalidate the attack target if it's no longer applicable
                new FirstApplicableBehaviour<>( 																							  	 // Run only one of the below behaviours, trying each one in order
                        new AnimatableMeleeAttack<>(0).whenStarting(entity -> setAggressive(false)), // Melee attack
                        new AnimatableRangedAttack<Dragon>(20).whenStarting(dragon -> dragon.setShooting(true)).whenStopping(dragon -> dragon.setShooting(false)))	 												 // Fire a bow, if holding one
        );
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle()) {
                Vec3 velocity = this.getDeltaMovement();
                switch (flight) {
                    case ASCENDING:
                        this.setDeltaMovement(velocity.x, getVerticalSpeed(), velocity.z);
                        break;
                    case DESCENDING:
                        this.setDeltaMovement(velocity.x, -getVerticalSpeed(), velocity.z);
                        if (!this.level().getBlockState(this.blockPosition().below()).isAir() && isNoGravity()) {
                            setNoGravity(false);
                            flyingTicks = 0;
                        }
                        break;
                    case HOVERING:
                        break;
                }
                LivingEntity livingentity = this.getControllingPassenger();
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }
                super.travel(new Vec3(f, pTravelVector.y, f1));
            } else {
                Vec3 velocity = this.getDeltaMovement();
                this.setDeltaMovement(velocity.x, -getVerticalSpeed(), velocity.z);
                super.travel(pTravelVector);
            }
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    protected int getMaxPassengers() {
        return 2;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    public double getVerticalSpeed() {
        return 0.5;
    }

    @Override
    public float getSpeed() {
        if (isNoGravity())
            return (float) (getAttributeValue(Attributes.FLYING_SPEED) * speedMultiplier);
        return (float) (getAttributeValue(Attributes.MOVEMENT_SPEED) * speedMultiplier);
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        if (pY > 0.01 && pOnGround) {
            this.setNoGravity(false);
        }
    }

    @Override
    public void ascend() {
        if (flyingTicks == 0) {
            isLiftingOff = true;
        }
        this.setNoGravity(true);
        this.flight = Flight.ASCENDING;
    }

    @Override
    public void descend() {
        this.flight = Flight.DESCENDING;
    }

    @Override
    public void stop() {
        this.flight = Flight.HOVERING;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND && !player.level().isClientSide) {
            ItemStack stack = player.getItemInHand(hand);
            boolean ownedBy = isOwnedBy(player);
            double bond = getBond(player);
            FocusData focusData = player.getData(MinejagoAttachmentTypes.FOCUS);
            if (isFood(stack)) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                if (ownedBy) {
                    if (this.getHealth() < this.getMaxHealth() && stack.get(DataComponents.FOOD) != null) {
                        this.heal(stack.get(DataComponents.FOOD).nutrition());
                        increaseBond(player, HEAL_BOND);
                        return InteractionResult.SUCCESS;
                    }
                }

                increaseBond(player, stack.is(MinejagoItemTags.DRAGON_TREATS) ? TREAT_BOND : FOOD_BOND);
                return InteractionResult.SUCCESS;
            } else if (bond <= 50 && focusData.getFocusLevel() >= FocusConstants.DRAGON_TALK_LEVEL && random.nextInt(10) < 2) {
                double i = TALK_BOND;
                if (level().holderOrThrow(player.getData(MinejagoAttachmentTypes.POWER).power()).is(acceptablePowers)) i += 1.5;
                increaseBond(player, i);
                player.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_DRAGON_TALK);
                return InteractionResult.SUCCESS;
            } else if ((bond >= 10 && focusData.getFocusLevel() >= FocusConstants.DRAGON_TAME_LEVEL) || player.getAbilities().instabuild) {
                if (ownedBy || hasControllingPassenger())
                    player.startRiding(this);
                else if (level().holderOrThrow(player.getData(MinejagoAttachmentTypes.POWER).power()).is(acceptablePowers) && focusData.getFocusLevel() >= 14) {
                    // TODO: give DX suit
                    player.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_DRAGON_TAME);
                    tame(player);
                    if (player.level() instanceof ServerLevel serverLevel) {
                        double d0 = this.random.nextGaussian() * 0.02D;
                        double d1 = this.random.nextGaussian() * 0.02D;
                        double d2 = this.random.nextGaussian() * 0.02D;
                        for (int i = 0; i < 5; i++) {
                            serverLevel.sendParticles(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), 1, d0, d1, d2, 1);
                        }
                        serverLevel.playSound(null, this.blockPosition(), SoundEvents.PLAYER_LEVELUP/*TODO:Tame,purr?*/, SoundSource.AMBIENT);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        super.positionRider(passenger, callback);
        clampRotation(this);
    }

    protected void clampRotation(Entity entityToUpdate) {
        entityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
        float g = Mth.clamp(f, -105.0F, 105.0F);
        entityToUpdate.yRotO += g - f;
        entityToUpdate.setYRot(entityToUpdate.getYRot() + g - f);
        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
    }

    @Override
    public void tick() {
        super.tick();
        if (isNoGravity()) flyingTicks++;
        if (flyingTicks > 30)
            isLiftingOff = false;
        if (getOwner() instanceof Player player && getBond(player) <= -10) {
            setTame(false, true);
            this.setOwnerUUID(null);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Player player)
            decreaseBond(player, amount * 2);
        return super.hurt(source, amount);
    }

    public double getPerceivedTargetDistanceSquareForMeleeAttack(LivingEntity entity) {
        return this.distanceToSqr(entity.position()) * 2.0;
    }

    public double increaseBond(Player player, double amount) {
        double oldBond = getBond(player);

        if (isOwnedBy(player)) amount *= 2;
        bond.put(player, oldBond + amount);
        recalculateBoosts();

        if (player.level() instanceof ServerLevel serverLevel) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            for (int i = 0; i < 5; i++) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), 1, d0, d1, d2, 1);
            }
            serverLevel.playSound(null, this.blockPosition(), SoundEvents.VILLAGER_YES/*TODO:Happy*/, SoundSource.AMBIENT);
        }

        return bond.get(player);
    }

    public double decreaseBond(Player player, double amount) {
        double oldBond = getBond(player);

        if (isOwnedBy(player)) amount /= 1.5;
        bond.put(player, oldBond - amount);
        recalculateBoosts();

        if (player.level() instanceof ServerLevel serverLevel) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            for (int i = 0; i < 5; i++) {
                serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), 1, d0, d1, d2, 1);
            }
            serverLevel.playSound(null, this.blockPosition(), SoundEvents.VILLAGER_NO /*TODO:Growl*/, SoundSource.AMBIENT);
        }

        return bond.get(player);
    }

    public double getBond(Player player) {
        if (bond.containsKey(player))
            return bond.get(player);
        bond.put(player, 0.0);
        return bond.get(player);
    }

    public Map<Player, Double> getBondMap() {
        return bond;
    }

    private void recalculateBoosts() {
        if (getOwner() instanceof Player player) {
            double bond = getBond(player);
            if (bond >= 100) {
                speedMultiplier = 2;
            } else if (bond >= 75) {
                speedMultiplier = 1.5;
            } else if (bond >= 50) {
                speedMultiplier = 1.25;
            } else if (bond >= 0) {
                speedMultiplier = 1;
            } else {
                speedMultiplier = 0.5;
            }
        }
    }

    @Override
    protected void onFlap() {
        super.onFlap();
        if (this.level().isClientSide && !this.isSilent() && getFlapSound() != null) {
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), getFlapSound(), this.getSoundSource(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
        }
    }

    @Override
    protected boolean isFlapping() {
        return flyingTicks > 1 && tickCount % TICKS_PER_FLAP == 0;
    }

    @Nullable
    public abstract SoundEvent getFlapSound();

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        Vec3 vec33 = this.getViewVector(1.0F);
        double l = this.getX() - vec33.x;
        double m = this.getY(0.5) + 0.5;
        double n = this.getZ() - vec33.z;
        double o = target.getX() - l;
        double p = target.getY(0.5) - m;
        double q = target.getZ() - n;
        AbstractHurtingProjectile projectile = getRangedAttackProjectile(o, p, q);
        projectile.moveTo(l, m, n, 0.0F, 0.0F);
        level().addFreshEntity(projectile);
        if (level() instanceof ServerLevel serverLevel && getShootSound() != null) {
            serverLevel.playSound(null, getOnPos(), getShootSound(), getSoundSource(), 1.0F, 1.0F);
        }
    }

    protected abstract AbstractHurtingProjectile getRangedAttackProjectile(double a, double b, double c);

    protected abstract SoundEvent getShootSound();

    public boolean isShooting() {
        return this.entityData.get(DATA_SHOOTING);
    }

    public void setShooting(boolean shooting) {
        this.entityData.set(DATA_SHOOTING, shooting);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(MinejagoItemTags.DRAGON_FOODS) || stack.is(MinejagoItemTags.DRAGON_TREATS);
    }
}
