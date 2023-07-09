package dev.thomasglasser.minejago.world.entity.dragon;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
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

public class Dragon extends AgeableMob implements GeoEntity, SmartBrainOwner<Dragon> {
    public static final RawAnimation LIFT = RawAnimation.begin().thenLoop("move.lift");

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private boolean isLiftingOff;
    private boolean isShooting;
    private boolean isFlying;

    public Dragon(EntityType<? extends Dragon> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.25f); // TODO: Fill in
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
                    else if (state.isMoving())
                    {
                        if (state.getAnimatable().onGround())
                            return state.setAndContinue(DefaultAnimations.WALK);
                        else if (isFlying)
                            return state.setAndContinue(DefaultAnimations.FLY);
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
                new NearbyLivingEntitySensor<>(),
                new NearbyPlayersSensor<>(),
                new HurtBySensor<>());
    }

    @Override
    public BrainActivityGroup<Dragon> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new SetWalkTargetToAttackTarget<>(),
                new LookAtTargetSink(40, 300), 														// Look at the look target
                new MoveToWalkTarget<>(),
                new FloatToSurfaceOfFluid<>());																					// Move to the current walk target
    }

    @Override
    public BrainActivityGroup<Dragon> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(                // Run only one of the below behaviours, trying each one in order. Include explicit generic typing because javac is silly
                        new TargetOrRetaliate<>(),                        // Set the attack target
                        new SetPlayerLookTarget<>(),                    // Set the look target to a nearby player if available
                        new SetRandomLookTarget<>(),
                        new SetRandomWalkTarget<>()), 					// Set the look target to a random nearby location
                new OneRandomBehaviour<>( 								// Run only one of the below behaviours, picked at random
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Don't walk anywhere
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null; // TODO
    }
}
