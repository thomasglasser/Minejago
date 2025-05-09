package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.entity.ai.behavior.FleeSkulkinRaidAndDespawn;
import dev.thomasglasser.minejago.world.entity.ai.behavior.PathfindToSkulkinRaid;
import dev.thomasglasser.minejago.world.entity.ai.behavior.SeekAndTakeNearbyBlockRaidItems;
import dev.thomasglasser.minejago.world.entity.ai.behavior.SeekAndTakeNearbyDroppedRaidItems;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.BowAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.AvoidSun;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.AvoidEntity;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.EscapeSun;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyItemsSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

public abstract class SkulkinRaider extends Skeleton implements SmartBrainOwner<SkulkinRaider> {
    @Nullable
    protected AbstractSkulkinRaid raid;
    private int wave;
    private int ticksOutsideRaid;

    public SkulkinRaider(EntityType<? extends SkulkinRaider> entityType, Level level) {
        super(entityType, level);
        navigation.setCanFloat(true);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothGroundNavigation(this, level);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        setCanPickUpLoot(true);
        return data;
    }

    @Override
    protected final void registerGoals() {}

    // Let's make sure we're definitely not using any goals
    @Override
    public final void reassessWeaponGoal() {}

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<? extends ExtendedSensor<? extends SkulkinRaider>> getSensors() {
        return ObjectArrayList.of(
                new NearbyItemsSensor<>(),
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<SkulkinRaider>()
                        .setPredicate((target, entity) -> target instanceof Player ||
                                target instanceof IronGolem ||
                                target instanceof Wolf ||
                                (target instanceof Turtle turtle && turtle.isBaby() && !turtle.isInWater()) ||
                                target.getType().is(MinejagoEntityTypeTags.NINJA_FRIENDS)));
    }

    @Override
    public BrainActivityGroup<? extends SkulkinRaider> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new AvoidSun<>(),
                new EscapeSun<>()
                        .cooldownFor(entity -> 20),
                new AvoidEntity<>()
                        .avoiding(entity -> entity instanceof Wolf),
                new LookAtTarget<>()
                        .runFor(entity -> entity.getRandom().nextIntBetweenInclusive(40, 300)),
                new StrafeTarget<>()
                        .stopStrafingWhen(entity -> !isHoldingBow(entity))
                        .startCondition(SkulkinRaider::isHoldingBow),
                new MoveToWalkTarget<>(),
                new FloatToSurfaceOfFluid<>(),
                new TargetOrRetaliate<>());
    }

    @Override
    public BrainActivityGroup<? extends SkulkinRaider> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>(),
                        new Idle<>()
                                .runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    public BrainActivityGroup<SkulkinRaider> getRaidTasks() {
        return new BrainActivityGroup<SkulkinRaider>(Activity.RAID)
                .onlyStartWithMemoryStatus(MinejagoMemoryModuleTypes.IN_RAID.get(), MemoryStatus.VALUE_PRESENT)
                .priority(10).behaviours(
                        new SeekAndTakeNearbyDroppedRaidItems<>().speedModifier(1.5F),
                        new SeekAndTakeNearbyBlockRaidItems<>(),
                        new FleeSkulkinRaidAndDespawn<>(),
                        new PathfindToSkulkinRaid<>());
    }

    @Override
    public Map<Activity, BrainActivityGroup<? extends SkulkinRaider>> getAdditionalTasks() {
        return Map.of(Activity.RAID, getRaidTasks());
    }

    @Override
    public List<Activity> getActivityPriorities() {
        return List.of(Activity.FIGHT, Activity.RAID, Activity.IDLE);
    }

    @Override
    public BrainActivityGroup<? extends SkulkinRaider> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>()
                        .startCondition(entity -> !isHoldingBow(entity) && (!entity.level().isDay() || (entity.isOnFire() && entity.level().canSeeSky(entity.blockPosition())))),
                new FirstApplicableBehaviour<>(
                        new BowAttack<>(20)
                                .startCondition(SkulkinRaider::isHoldingBow),
                        new AnimatableMeleeAttack<>(0)
                                .whenStarting(entity -> setAggressive(true))
                                .whenStopping(entity -> setAggressive(false))));
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        AbstractSkulkinRaid raid = getCurrentRaid();
        return raid != null && raid.isValidRaidItem(stack);
    }

    @Override
    public EquipmentSlot getEquipmentSlotForItem(ItemStack stack) {
        AbstractSkulkinRaid raid = getCurrentRaid();
        if (raid != null && raid.isValidRaidItem(stack))
            return EquipmentSlot.OFFHAND;
        return super.getEquipmentSlotForItem(stack);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        tickBrain(this);
    }

    private static boolean isHoldingBow(LivingEntity livingEntity) {
        return livingEntity.isHolding(stack -> stack.getItem() instanceof BowItem);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Wave", this.wave);
        if (this.raid != null) {
            pCompound.putInt("SkulkinRaidId", this.raid.getId());
        }
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.wave = pCompound.getInt("Wave");
        if (pCompound.contains("SkulkinRaidId", 3)) {
            if (this.level() instanceof ServerLevel) {
                this.raid = SkulkinRaidsHolder.of(level()).minejago$getSkulkinRaids().get(pCompound.getInt("SkulkinRaidId"));
            }

            if (this.raid != null) {
                this.raid.addWaveMob(this.wave, this, false);
            }
        }
    }

    public static boolean checkSpawnRules(EntityType<? extends SkulkinRaider> pPatrollingMonster, LevelAccessor pLevel, MobSpawnType spawnReason, BlockPos pPos, RandomSource pRandom) {
        return pLevel.getBrightness(LightLayer.BLOCK, pPos) <= 8 && checkAnyLightMonsterSpawnRules(pPatrollingMonster, pLevel, spawnReason, pPos, pRandom);
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return this.getCurrentRaid() == null && pDistanceToClosestPlayer > 16384.0D;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level() instanceof ServerLevel && this.isAlive()) {
            AbstractSkulkinRaid raid = this.getCurrentRaid();
            if (raid == null) {
                if (this.level().getGameTime() % 20L == 0L) {
                    AbstractSkulkinRaid raidAtPos = SkulkinRaidsHolder.of(this.level()).minejago$getSkulkinRaids().getSkulkinRaidAt(this.blockPosition());
                    if (raidAtPos != null && SkulkinRaids.canJoinSkulkinRaid(this, raidAtPos)) {
                        raidAtPos.joinRaid(raidAtPos.getGroupsSpawned(), this, null, true);
                    }
                }
            } else {
                LivingEntity livingEntity = this.getTarget();
                if (livingEntity != null && (livingEntity.getType() == EntityType.PLAYER || livingEntity.getType() == EntityType.IRON_GOLEM)) {
                    this.noActionTime = 0;
                }
            }
        }
    }

    @Override
    protected void updateNoActionTime() {
        this.noActionTime += 2;
    }

    @Override
    public void die(DamageSource damageSource) {
        if (this.level() instanceof ServerLevel) {
            AbstractSkulkinRaid raid = this.getCurrentRaid();
            if (raid != null) {
                raid.removeFromRaid(this, false);
            }
        }

        super.die(damageSource);
    }

    public void setCurrentRaid(@Nullable AbstractSkulkinRaid raid) {
        this.raid = raid;
        if (raid != null) {
            brain.setMemory(MinejagoMemoryModuleTypes.IN_RAID.get(), Unit.INSTANCE);
        } else if (brain.hasMemoryValue(MinejagoMemoryModuleTypes.IN_RAID.get())) {
            brain.setMemory(MinejagoMemoryModuleTypes.IN_RAID.get(), Optional.empty());
        }
    }

    @Nullable
    public AbstractSkulkinRaid getCurrentRaid() {
        return this.raid;
    }

    public boolean hasActiveRaid() {
        return this.getCurrentRaid() != null && this.getCurrentRaid().isActive();
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getWave() {
        return this.wave;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.getCurrentRaid() != null;
    }

    public int getTicksOutsideRaid() {
        return this.ticksOutsideRaid;
    }

    public void setTicksOutsideRaid(int ticksOutsideSkulkinRaid) {
        this.ticksOutsideRaid = ticksOutsideSkulkinRaid;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        AbstractSkulkinRaid raid = this.getCurrentRaid();
        if (this.hasActiveRaid() && raid != null) {
            raid.updateBossbar();
        }

        return super.hurt(source, amount);
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        return BrainUtils.getTargetOfEntity(this);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        BrainUtils.setMemory(this, MemoryModuleType.ATTACK_TARGET, target);
    }
}
