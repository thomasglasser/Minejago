package dev.thomasglasser.minejago.world.entity.character;

import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntitySerializers;
import dev.thomasglasser.minejago.world.entity.SpinjitzuDoer;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.InteractWithDoor;
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
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Character extends AgeableMob implements SmartBrainOwner<Character>, GeoEntity, SpinjitzuDoer {
    public static final RawAnimation SPINJITZU = RawAnimation.begin().thenPlay("move.spinjitzu");
    public static final RawAnimation MEDITATION_START = RawAnimation.begin().thenPlay("move.meditation.start");
    public static final RawAnimation MEDITATION_FLOAT = RawAnimation.begin().thenPlay("move.meditation.float");
    public static final RawAnimation MEDITATION_FINISH = RawAnimation.begin().thenPlay("move.meditation.finish");
    public static final RawAnimation CLIMB = RawAnimation.begin().thenPlay("move.climb");

    private static final EntityDataAccessor<MeditationStatus> DATA_MEDITATION_STATUS = SynchedEntityData.defineId(Character.class, MinejagoEntitySerializers.MEDITATION_STATUS.get());

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected boolean stopSpinjitzuOnNextStop;

    public Character(EntityType<? extends Character> entityType, Level level) {
        super(entityType, level);
        getNavigation().setCanFloat(true);
        getNavigation().getNodeEvaluator().setCanOpenDoors(true);
        setPersistenceRequired();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothGroundNavigation(this, level);
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
        super.customServerAiStep();
        this.tickBrain(this);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_MEDITATION_STATUS, MeditationStatus.NONE);
    }

    @Override
    public List<ExtendedSensor<Character>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new HurtBySensor<>(),
                new NearbyLivingEntitySensor<Character>()
                        .setPredicate((target, entity) -> !(target instanceof Creeper) &&
                                target instanceof Enemy));
    }

    @Override
    public BrainActivityGroup<Character> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new InteractWithDoor<>(),
                new FloatToSurfaceOfFluid<Character>().startCondition(this::shouldFloatToSurfaceOfFluid).whenStarting(this::onStartFloatingToSurfaceOfFluid).whenStopping(this::onStopFloatingToSurfaceOfFluid),
                new SetWalkTargetToAttackTarget<>(),
                new LookAtTargetSink(40, 300),
                new MoveToWalkTarget<Character>().whenStopping(this::onMoveToWalkTargetStopping));
    }

    @Override
    public BrainActivityGroup<Character> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new TargetOrRetaliate<Character>().alertAlliesWhen((owner, attacker) -> true).isAllyIf(this::shouldHelp).whenStarting(this::onTargetOrRetaliate),                        // Set the attack target
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)),
                        new SetRandomWalkTarget<Character>().avoidWaterWhen(this::shouldAvoidWater).startCondition(this::shouldSetRandomWalkTarget)));
    }

    @Override
    public BrainActivityGroup<Character> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(), 	 // Invalidate the attack target if it's no longer applicable
                new FirstApplicableBehaviour<>( 																							  	 // Run only one of the below behaviours, trying each one in order
                        new AnimatableMeleeAttack<>(8).whenStarting(entity -> setAggressive(true)).whenStopping(entity -> setAggressive(false)))// Melee attack
        );
    }

    public void onTargetOrRetaliate(Character character) {}

    public boolean shouldFloatToSurfaceOfFluid(Character character) {
        return true;
    }

    public void onStartFloatingToSurfaceOfFluid(Character character) {}

    public void onStopFloatingToSurfaceOfFluid(Character character) {}

    public boolean shouldSetRandomWalkTarget(Character character) {
        return true;
    }

    public boolean shouldAvoidWater(Character character) {
        return true;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        this.setCanPickUpLoot(true);
        if (spawnType == MobSpawnType.NATURAL || spawnType == MobSpawnType.CHUNK_GENERATION) {
            List<? extends Character> list = level.getEntitiesOfClass(this.getClass(), getBoundingBox().inflate(1024));
            list.remove(this);
            if (!list.isEmpty())
                discard();
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        List<? extends Character> list = level().getEntitiesOfClass(this.getClass(), getBoundingBox().inflate(1024));
        list.remove(this);
        return !list.isEmpty();
    }

    public boolean shouldHelp(Character victim, LivingEntity helper) {
        LivingEntity helperTarget = null;

        if (helper instanceof SmartBrainOwner<?>) {
            helperTarget = BrainUtils.getTargetOfEntity(helper);
        } else if (helper instanceof Mob mob) {
            helperTarget = mob.getTarget();
        }

        return helperTarget == null && victim.isAlliedTo(helper);
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else {
            return entity.getType().is(MinejagoEntityTypeTags.NINJA_FRIENDS) && this.getTeam() == null && entity.getTeam() == null;
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        return InteractionResult.CONSUME;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(DefaultAnimations.genericWalkController(this));
        controllerRegistrar.add(DefaultAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_SWING));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide() && this.isDoingSpinjitzu()) {
            if (tickCount % 20 == 0) {
                level().playSound(null, blockPosition(), MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), SoundSource.NEUTRAL);
                level().gameEvent(this, MinejagoGameEvents.SPINJITZU, blockPosition());
            }
            level().holderOrThrow(this.getData(MinejagoAttachmentTypes.ELEMENT).element()).value().borderParticle().ifPresent(particle -> MinejagoParticleUtils.renderSpinjitzuBorder(particle, this));
        }
        if (this.getHealth() < this.getMaxHealth() && this.tickCount % 20 == 0) {
            this.heal(1.0F);
        }
    }

    protected void onMoveToWalkTargetStopping(Character character) {
        if (character.stopSpinjitzuOnNextStop)
            character.setDoingSpinjitzu(false);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public boolean isDoingSpinjitzu() {
        return this.getData(MinejagoAttachmentTypes.SPINJITZU).active();
    }

    @Override
    public void setDoingSpinjitzu(boolean doingSpinjitzu) {
        new SpinjitzuData(true, doingSpinjitzu).save(this, !level().isClientSide);
    }

    public static boolean checkNaturalCharacterSpawnRules(EntityType<? extends Character> character, LevelAccessor level, MobSpawnType entitySpawnReason, BlockPos pos, RandomSource random) {
        List<Character> characters = new ArrayList<>();
        ServerLevel serverLevel = level instanceof Level ? (ServerLevel) level : level instanceof WorldGenRegion ? ((WorldGenRegion) level).getLevel() : null;
        if (serverLevel != null) {
            for (Entity entity : serverLevel.getEntities(null, AABB.ofSize(pos.getCenter(), 512, 512, 512))) {
                if (entity.getType() == character) {
                    characters.add((Character) entity);
                }
            }
        }
        return characters.isEmpty() && Mob.checkMobSpawnRules(character, level, entitySpawnReason, pos, random);
    }

    public void setMeditationStatus(MeditationStatus meditationStatus) {
        this.entityData.set(DATA_MEDITATION_STATUS, meditationStatus);
    }

    public MeditationStatus getMeditationStatus() {
        return this.entityData.get(DATA_MEDITATION_STATUS);
    }

    @Override
    public int getCurrentSwingDuration() {
        int baseSwingDuration = 18;
        if (MobEffectUtil.hasDigSpeed(this)) {
            return baseSwingDuration - (1 + MobEffectUtil.getDigSpeedAmplification(this));
        } else {
            return this.hasEffect(MobEffects.DIG_SLOWDOWN) ? baseSwingDuration + (1 + this.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 2 : baseSwingDuration;
        }
    }

    public enum MeditationStatus {
        STARTING,
        FLOATING,
        FINISHING,
        NONE
    }
}
