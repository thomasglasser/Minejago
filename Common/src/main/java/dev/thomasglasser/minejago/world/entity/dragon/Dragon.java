package dev.thomasglasser.minejago.world.entity.dragon;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.PlayerRideableFlying;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.entity.projectile.EarthBlast;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
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
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class Dragon extends TamableAnimal implements GeoEntity, SmartBrainOwner<Dragon>, PlayerRideableFlying, RangedAttackMob {
    public static final RawAnimation LIFT = RawAnimation.begin().thenPlay("move.lift");

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final TagKey<Power> acceptablePowers;
    private boolean isLiftingOff;
    private boolean isShooting;
    private Flight flight = Flight.HOVERING;
    private int flyingTicks = 0;
    private double bond;

    public Dragon(EntityType<? extends Dragon> entityType, Level level, ResourceKey<Power> power, TagKey<Power> acceptablePowers) {
        super(entityType, level);
        Services.DATA.setPowerData(new PowerData(power, false), this);
        navigation = new GroundPathNavigation(this, level)
        {
            @Override
            protected boolean canUpdatePath() {
                return super.canUpdatePath() || !isNoGravity();
            }
        };
        this.setMaxUpStep(1.0f);
        this.acceptablePowers = acceptablePowers;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ARMOR, 4.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.FLYING_SPEED, 2.0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(
                new AnimationController<>(this, "Walk/Idle/Fly/Lift", 0, state ->
                {
                    if (isLiftingOff)
                    {
                        return state.setAndContinue(LIFT);
                    }
                    else if (state.getAnimatable().isNoGravity())
                    {
                        return state.setAndContinue(DefaultAnimations.FLY);
                    }
                    else if (state.isMoving())
                    {
                        return state.setAndContinue(DefaultAnimations.WALK);
                    }
                    return state.setAndContinue(DefaultAnimations.IDLE);
                }),
                new AnimationController<>(this, "Shoot", 0, state ->
                {
                    if (isShooting)
                        return state.setAndContinue(DefaultAnimations.ATTACK_SHOOT);
                    else
                        return PlayState.STOP;
                })
        );
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    @Override
    public List<ExtendedSensor<Dragon>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<Dragon>().setPredicate((target, dragon) ->
                        {
                            if (target instanceof Enemy) return true;
                            if (target instanceof Character) return false;
                            if (target.isAlliedTo(dragon)) return false;
                            LivingEntity owner = dragon.getOwner();
                            if (owner == null)
                            {
                                if (target instanceof Player) return true;
                                return target instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() != null;
                            }
                            else
                            {
                                if (target.isAlliedTo(owner)) return false;
                                if (target instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() == dragon.getOwner()) return false;
                                if (target.getLastHurtByMob() != null && target.getLastHurtByMob().is(dragon.getOwner())) return true;
                                if (BrainUtils.hasMemory(target.getBrain(), MemoryModuleType.ATTACK_TARGET))
                                {
                                    return BrainUtils.getTargetOfEntity(target) == dragon.getOwner();
                                }
                                else if (target instanceof Mob mob)
                                {
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
                        new TargetOrRetaliate<>().attackablePredicate(entity -> entity.isAlive() && (!(entity instanceof Player player) || !player.isCreative() && !(this.getOwner() == player)))),                        // Set the attack target
                        new SetPlayerLookTarget<>(),                    // Set the look target to a nearby player if available
                        new SetRandomLookTarget<>(),
                new OneRandomBehaviour<>( 								// Run only one of the below behaviours, picked at random
                        new SetRandomWalkTarget<>().speedModifier(1), 				// Set the walk target to a nearby random pathable location
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Don't walk anywhere
    }

    @Override
    public BrainActivityGroup<? extends Dragon> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(), 	 // Invalidate the attack target if it's no longer applicable
                new FirstApplicableBehaviour<>( 																							  	 // Run only one of the below behaviours, trying each one in order
                        new AnimatableMeleeAttack<>(0).whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false))/*.startCondition(dragon -> BrainUtils.getTargetOfEntity(dragon) != null && BrainUtils.getTargetOfEntity(dragon).position().distanceTo(dragon.position()) < 20)*/, // Melee attack
                        new AnimatableRangedAttack<>(20))	 												 // Fire a bow, if holding one
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
                        if(!this.level().getBlockState(this.blockPosition().below()).isAir() && isNoGravity()){
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
            }
            else
            {
                Vec3 velocity = this.getDeltaMovement();
                this.setDeltaMovement(velocity.x, -getVerticalSpeed(), velocity.z);
                super.travel(pTravelVector);
            }
        }
    }

    public double getVerticalSpeed() {
        return 0.5;
    }

    @Override
    public float getSpeed() {
        if (isNoGravity())
            return (float) getAttributeValue(Attributes.FLYING_SPEED);
        return (float) getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        if(pY > 0.01 && pOnGround) {
            this.setNoGravity(false);
        }
    }

    @Override
    public void ascend() {
        if (flyingTicks == 0)
        {
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
    public void stop()
    {
        this.flight = Flight.HOVERING;
    }

    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        Registry<Power> powerRegistry = level().registryAccess().registryOrThrow(MinejagoRegistries.POWER);
        if (isOwnedBy(player)) player.startRiding(this);
        else if (powerRegistry.getOrThrow(Services.DATA.getPowerData(player).power()).is(acceptablePowers, powerRegistry)) {
            // TODO: improve taming and give DX suit
            tame(player);
        }
        return super.mobInteract(player, hand);
    }

    public int getPassengerCount(){
        return 2;
    }
    @Override
    protected boolean canAddPassenger(Entity pPassenger) {
        return this.getPassengers().size() < getPassengerCount();
    }
    
    public void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        Vec3 position = position();
        pPassenger.setPos(position.x, position.y + getPassengersRidingOffset(), position.z);
        pPassenger.setYBodyRot(getYRot());
    }

    @Override
    public void tick() {
        super.tick();
        if (isNoGravity()) flyingTicks++;
        if (flyingTicks > 30)
            isLiftingOff = false;
    }

    @Override
    public @NotNull Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        Vec3 vec33 = this.getViewVector(1.0F);
        double l = this.getX() - vec33.x;
        double m = this.getY(0.5) + 0.5;
        double n = this.getZ() - vec33.z;
        double o = target.getX() - l;
        double p = target.getY(0.5) - m;
        double q = target.getZ() - n;
        EarthBlast dragonFireball = new EarthBlast(level(), this, o, p, q);
        dragonFireball.moveTo(l, m, n, 0.0F, 0.0F);
        level().addFreshEntity(dragonFireball);
    }

    public double getPerceivedTargetDistanceSquareForMeleeAttack(LivingEntity entity) {
        return this.distanceToSqr(entity.position()) * 2.0;
    }
}
