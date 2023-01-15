package dev.thomasglasser.minejago.world.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Character extends AgeableMob implements SmartBrainOwner<Character>
{
    public Character(EntityType<? extends Character> entityType, Level level) {
        super(entityType, level);
        this.getNavigation().setCanFloat(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.FOLLOW_RANGE)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
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
    public List<ExtendedSensor<Character>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new HurtBySensor<>()); 							// Keep track of nearby players
    }

    @Override
    public BrainActivityGroup<Character> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new SetWalkTargetToAttackTarget<>(),
                new LookAtTargetSink(40, 300), 														// Look at the look target
                new MoveToWalkTarget<>(),
                new FloatToSurfaceOfFluid<Character>().startCondition(this::shouldFloatToSurfaceOfFluid));																					// Move to the current walk target
    }

    @Override
    public BrainActivityGroup<Character> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<Character>( 				// Run only one of the below behaviours, trying each one in order. Include explicit generic typing because javac is silly
                        new TargetOrRetaliate<Character>().alertAlliesWhen((owner, attacker) -> true).isAllyIf(this::shouldHelp).whenStarting(this::onTargetOrRetaliate),						// Set the attack target
                        new SetPlayerLookTarget<>(),					// Set the look target to a nearby player if available
                        new SetRandomLookTarget<>()), 					// Set the look target to a random nearby location
                new OneRandomBehaviour<>( 								// Run only one of the below behaviours, picked at random
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Don't walk anywhere
    }

    @Override
    public BrainActivityGroup<Character> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(), 	 // Invalidate the attack target if it's no longer applicable
                new FirstApplicableBehaviour<>( 																							  	 // Run only one of the below behaviours, trying each one in order
                        new AnimatableMeleeAttack<>(0).whenStarting(entity -> setAggressive(true)).whenStarting(entity -> setAggressive(false)))// Melee attack
        );
    }

    public void onTargetOrRetaliate(Character character) {}

    public boolean shouldFloatToSurfaceOfFluid(Character character)
    {
        return true;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.setCanPickUpLoot(true);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public boolean shouldHelp(Character victim, LivingEntity helper)
    {
        LivingEntity helperTarget = null;

        if (helper instanceof SmartBrainOwner<?>)
        {
            helperTarget = BrainUtils.getTargetOfEntity(helper);
        }
        else if (helper instanceof Mob mob)
        {
            helperTarget = mob.getTarget();
        }

        return helperTarget == null && helper instanceof Character;
    }
}
