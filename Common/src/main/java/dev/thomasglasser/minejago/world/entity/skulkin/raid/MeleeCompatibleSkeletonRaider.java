package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.ai.behavior.FleeSkulkinRaidAndDespawn;
import dev.thomasglasser.minejago.world.entity.ai.behavior.LongDistanceRaiderPatrol;
import dev.thomasglasser.minejago.world.entity.ai.behavior.ObtainNearbyRaidBanner;
import dev.thomasglasser.minejago.world.entity.ai.behavior.PathfindToSkulkinRaid;
import dev.thomasglasser.minejago.world.entity.ai.behavior.SeekAndTakeFourWeaponsMap;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.tommylib.api.world.entity.MeleeCompatibleSkeleton;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.example.SBLSkeleton;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public abstract class MeleeCompatibleSkeletonRaider extends MeleeCompatibleSkeleton
{
	protected static final EntityDataAccessor<Boolean> IS_CELEBRATING = SynchedEntityData.defineId(MeleeCompatibleSkeletonRaider.class, EntityDataSerializers.BOOLEAN);
	private static final Predicate<ItemStack> ALLOWED_STACKS = stack -> ItemStack.matches(stack, SkulkinRaid.getLeaderBannerInstance());
	private static final Predicate<ItemEntity> ALLOWED_ITEMS = itemEntity -> !itemEntity.hasPickUpDelay()
			&& itemEntity.isAlive()
			&& ALLOWED_STACKS.test(itemEntity.getItem());
	@Nullable
	private BlockPos patrolTarget;
	private boolean patrolLeader;
	private boolean patrolling;
	@Nullable
	protected SkulkinRaid raid;
	private int wave;
	private boolean canJoinSkulkinRaid;
	private int ticksOutsideSkulkinRaid;

	public MeleeCompatibleSkeletonRaider(EntityType<? extends MeleeCompatibleSkeleton> entityType, Level level)
	{
		super(entityType, level);
		navigation.setCanFloat(true);
	}

	@Override
	public List<? extends ExtendedSensor<? extends SBLSkeleton>> getSensors()
	{
		return ObjectArrayList.of(
				new NearbyLivingEntitySensor<SBLSkeleton>()
						.setPredicate((target, entity) ->
								target instanceof Character ||
								target instanceof Player)
		);
	}

	@Override
	public BrainActivityGroup<? extends SBLSkeleton> getCoreTasks()
	{
		return BrainActivityGroup.coreTasks(
				new Swim(1.0f),
				new LongDistanceRaiderPatrol<>().speedModifier(0.7F).leaderSpeedModifier(0.595F),
				new ObtainNearbyRaidBanner<>(ALLOWED_ITEMS, ALLOWED_STACKS),
				new PathfindToSkulkinRaid<>(),
				new SeekAndTakeFourWeaponsMap<>(),
				new FleeSkulkinRaidAndDespawn<>()
		).behaviours(super.getCoreTasks().getBehaviours().toArray(new Behavior[0]));
	}

	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		if (this.patrolTarget != null) {
			pCompound.put("PatrolTarget", NbtUtils.writeBlockPos(this.patrolTarget));
		}

		pCompound.putBoolean("PatrolLeader", this.patrolLeader);
		pCompound.putBoolean("Patrolling", this.patrolling);
		pCompound.putInt("Wave", this.wave);
		pCompound.putBoolean("CanJoinSkulkinRaid", this.canJoinSkulkinRaid);
		if (this.raid != null) {
			pCompound.putInt("SkulkinRaidId", this.raid.getId());
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("PatrolTarget")) {
			this.patrolTarget = NbtUtils.readBlockPos(pCompound.getCompound("PatrolTarget"));
		}

		this.patrolLeader = pCompound.getBoolean("PatrolLeader");
		this.patrolling = pCompound.getBoolean("Patrolling");
		this.wave = pCompound.getInt("Wave");
		this.canJoinSkulkinRaid = pCompound.getBoolean("CanJoinSkulkinRaid");
		if (pCompound.contains("SkulkinRaidId", 3)) {
			if (this.level() instanceof ServerLevel) {
				this.raid = ((SkulkinRaidsHolder)this.level()).getSkulkinRaids().get(pCompound.getInt("SkulkinRaidId"));
			}

			if (this.raid != null) {
				this.raid.addWaveMob(this.wave, this, false);
				if (this.isPatrolLeader()) {
					this.raid.setLeader(this.wave, this);
				}
			}
		}
	}

	public boolean canBeLeader() {
		return true;
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag)
	{
		this.setCanJoinSkulkinRaid(pReason != MobSpawnType.NATURAL);

		if (pReason != MobSpawnType.PATROL && pReason != MobSpawnType.EVENT && pReason != MobSpawnType.STRUCTURE && pLevel.getRandom().nextFloat() < 0.06F && this.canBeLeader()) {
			this.patrolLeader = true;
		}

		if (this.isPatrolLeader()) {
			this.setItemSlot(EquipmentSlot.HEAD, SkulkinRaid.getLeaderBannerInstance());
			this.setDropChance(EquipmentSlot.HEAD, 2.0F);
		}

		if (pReason == MobSpawnType.PATROL) {
			this.patrolling = true;
		}

		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	public static boolean checkSpawnRules(EntityType<? extends MeleeCompatibleSkeletonRaider> pPatrollingMonster, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
		return pLevel.getBrightness(LightLayer.BLOCK, pPos) <= 8 && checkAnyLightMonsterSpawnRules(pPatrollingMonster, pLevel, pSpawnType, pPos, pRandom);
	}

	public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
		return this.getCurrentSkulkinRaid() == null && (!this.patrolling || pDistanceToClosestPlayer > 16384.0D);
	}

	public void setPatrolTarget(BlockPos pPatrolTarget) {
		this.patrolTarget = pPatrolTarget;
		this.patrolling = true;
	}

	public BlockPos getPatrolTarget() {
		return this.patrolTarget;
	}

	public boolean hasPatrolTarget() {
		return this.patrolTarget != null;
	}

	public void setPatrolLeader(boolean pPatrolLeader) {
		this.patrolLeader = pPatrolLeader;
		this.patrolling = true;
	}

	public boolean isPatrolLeader() {
		return this.patrolLeader;
	}

	public void findPatrolTarget() {
		this.patrolTarget = this.blockPosition().offset(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
		this.patrolling = true;
	}

	public boolean isPatrolling() {
		return this.patrolling;
	}

	public void setPatrolling(boolean pPatrolling) {
		this.patrolling = pPatrolling;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(IS_CELEBRATING, false);
	}

	public boolean canJoinSkulkinRaid() {
		return this.canJoinSkulkinRaid;
	}

	public void setCanJoinSkulkinRaid(boolean canJoinSkulkinRaid) {
		this.canJoinSkulkinRaid = canJoinSkulkinRaid;
	}

	@Override
	public void aiStep() {
		if (this.level() instanceof ServerLevel && this.isAlive()) {
			SkulkinRaid raid = this.getCurrentSkulkinRaid();
			if (this.canJoinSkulkinRaid()) {
				if (raid == null) {
					if (this.level().getGameTime() % 20L == 0L) {
						SkulkinRaid raid2 = ((SkulkinRaidsHolder)this.level()).getSkulkinRaidAt(this.blockPosition());
						if (raid2 != null && SkulkinRaids.canJoinSkulkinRaid(this, raid2)) {
							raid2.joinSkulkinRaid(raid2.getGroupsSpawned(), this, null, true);
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

		super.aiStep();
	}

	@Override
	protected void updateNoActionTime() {
		this.noActionTime += 2;
	}

	@Override
	public void die(DamageSource damageSource) {
		if (this.level() instanceof ServerLevel) {
			Entity entity = damageSource.getEntity();
			SkulkinRaid raid = this.getCurrentSkulkinRaid();
			if (raid != null) {
				if (this.isPatrolLeader()) {
					raid.removeLeader(this.getWave());
				}

				raid.removeFromSkulkinRaid(this, false);
			}

			if (this.isPatrolLeader() && raid == null && ((SkulkinRaidsHolder)this.level()).getSkulkinRaidAt(this.blockPosition()) == null) {
				ItemStack itemStack = this.getItemBySlot(EquipmentSlot.HEAD);
				Player player = null;
				if (entity instanceof Player) {
					player = (Player)entity;
				} else if (entity instanceof Wolf wolf) {
					LivingEntity livingEntity = wolf.getOwner();
					if (wolf.isTame() && livingEntity instanceof Player) {
						player = (Player)livingEntity;
					}
				}

				if (!itemStack.isEmpty() && ItemStack.matches(itemStack, SkulkinRaid.getLeaderBannerInstance()) && player != null) {
					MobEffectInstance mobEffectInstance = player.getEffect(MinejagoMobEffects.SKULKINS_CURSE.get());
					int i = 1;
					if (mobEffectInstance != null) {
						i += mobEffectInstance.getAmplifier();
						player.removeEffectNoUpdate(MinejagoMobEffects.SKULKINS_CURSE.get());
					} else {
						--i;
					}

					i = Mth.clamp(i, 0, 4);
					MobEffectInstance mobEffectInstance2 = new MobEffectInstance(MinejagoMobEffects.SKULKINS_CURSE.get(), 120000, i, false, false, true);
					if (MinejagoServerConfig.enableSkulkinRaids) {
						player.addEffect(mobEffectInstance2);
					}
				}
			}
		}

		super.die(damageSource);
	}

	public boolean canJoinPatrol() {
		return !this.hasActiveSkulkinRaid();
	}

	public void setCurrentSkulkinRaid(@Nullable SkulkinRaid raid) {
		this.raid = raid;
	}

	@Nullable
	public SkulkinRaid getCurrentSkulkinRaid() {
		return this.raid;
	}

	public boolean hasActiveSkulkinRaid() {
		return this.getCurrentSkulkinRaid() != null && this.getCurrentSkulkinRaid().isActive();
	}

	public void setWave(int wave) {
		this.wave = wave;
	}

	public int getWave() {
		return this.wave;
	}

	public boolean isCelebrating() {
		return this.entityData.get(IS_CELEBRATING);
	}

	public void setCelebrating(boolean celebrating) {
		this.entityData.set(IS_CELEBRATING, celebrating);
	}

	@Override
	public void pickUpItem(ItemEntity itemEntity) {
		ItemStack itemStack = itemEntity.getItem();
		boolean bl = this.hasActiveSkulkinRaid() && this.getCurrentSkulkinRaid().getLeader(this.getWave()) != null;
		if (this.hasActiveSkulkinRaid() && !bl && ItemStack.matches(itemStack, SkulkinRaid.getLeaderBannerInstance())) {
			EquipmentSlot equipmentSlot = EquipmentSlot.HEAD;
			ItemStack itemStack2 = this.getItemBySlot(equipmentSlot);
			double d = (double)this.getEquipmentDropChance(equipmentSlot);
			if (!itemStack2.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < d) {
				this.spawnAtLocation(itemStack2);
			}

			this.onItemPickup(itemEntity);
			this.setItemSlot(equipmentSlot, itemStack);
			this.take(itemEntity, itemStack.getCount());
			itemEntity.discard();
			this.getCurrentSkulkinRaid().setLeader(this.getWave(), this);
			this.setPatrolLeader(true);
		} else {
			super.pickUpItem(itemEntity);
		}
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.getCurrentSkulkinRaid() != null;
	}

	public int getTicksOutsideSkulkinRaid() {
		return this.ticksOutsideSkulkinRaid;
	}

	public void setTicksOutsideSkulkinRaid(int ticksOutsideSkulkinRaid) {
		this.ticksOutsideSkulkinRaid = ticksOutsideSkulkinRaid;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (this.hasActiveSkulkinRaid()) {
			this.getCurrentSkulkinRaid().updateBossbar();
		}

		return super.hurt(source, amount);
	}
}
