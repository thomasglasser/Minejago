package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.util.MinejagoLevelUtils;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.DataHolder;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class SkulkinRaid {
	private static final int SECTION_RADIUS_FOR_FINDING_NEW_VILLAGE_CENTER = 2;
	private static final int ATTEMPT_RAID_FARTHEST = 0;
	private static final int ATTEMPT_RAID_CLOSE = 1;
	private static final int ATTEMPT_RAID_INSIDE = 2;
	private static final int VILLAGE_SEARCH_RADIUS = 32;
	private static final int RAID_TIMEOUT_TICKS = 48000;
	private static final int NUM_SPAWN_ATTEMPTS = 3;
	public static final String SKULKINS_BANNER_PATTERN_NAME = "block.minejago.skulkins_banner";
	private static final String RAIDERS_REMAINING = "event.minecraft.raid.raiders_remaining";
	public static final int VILLAGE_RADIUS_BUFFER = 16;
	private static final int POST_RAID_TICK_LIMIT = 40;
	private static final int DEFAULT_PRE_RAID_TICKS = 300;
	public static final int MAX_NO_ACTION_TIME = 2400;
	public static final int MAX_CELEBRATION_TICKS = 600;
	private static final int OUTSIDE_RAID_BOUNDS_TIMEOUT = 30;
	public static final int TICKS_PER_DAY = 24000;
	public static final int DEFAULT_MAX_SKULKINS_CURSE_LEVEL = 5;
	private static final int LOW_MOB_THRESHOLD = 2;
	public static final Component RAID_NAME_COMPONENT = Component.translatable("event.minejago.skulkin_raid");
	private static final Component VICTORY = Component.translatable("event.minecraft.raid.victory");
	private static final Component DEFEAT = Component.translatable("event.minecraft.raid.defeat");
	private static final Component RAID_BAR_VICTORY_COMPONENT = RAID_NAME_COMPONENT.copy().append(" - ").append(VICTORY);
	private static final Component RAID_BAR_DEFEAT_COMPONENT = RAID_NAME_COMPONENT.copy().append(" - ").append(DEFEAT);
	private static final int HERO_OF_THE_VILLAGE_DURATION = 48000;
	public static final int VALID_RAID_RADIUS_SQR = 9216;
	public static final int RAID_REMOVAL_THRESHOLD_SQR = 12544;
	private final Map<Integer, MeleeCompatibleSkeletonRaider> groupToLeaderMap = Maps.newHashMap();
	private final Map<Integer, Set<MeleeCompatibleSkeletonRaider>> groupRaiderMap = Maps.newHashMap();
	private long ticksActive;
	private BlockPos center;
	private final ServerLevel level;
	private boolean started;
	private final int id;
	private float totalHealth;
	private int skulkinsCurseLevel;
	private boolean active;
	private int groupsSpawned;
	private final ServerBossEvent raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.NOTCHED_10);
	private int postSkulkinRaidTicks;
	private int raidCooldownTicks;
	private final RandomSource random = RandomSource.create();
	private final int numGroups;
	private SkulkinRaid.SkulkinRaidStatus status;
	private int celebrationTicks;
	private Optional<BlockPos> waveSpawnPos = Optional.empty();
	private long time;
	private Vec3 escapePos;

	public SkulkinRaid(int i, ServerLevel serverLevel, BlockPos blockPos) {
		this.id = i;
		this.level = serverLevel;
		this.active = true;
		this.raidCooldownTicks = 300;
		this.raidEvent.setProgress(0.0F);
		this.center = blockPos;
		this.numGroups = this.getNumGroups(serverLevel.getDifficulty());
		this.status = SkulkinRaid.SkulkinRaidStatus.ONGOING;
	}

	public SkulkinRaid(ServerLevel serverLevel, CompoundTag compoundTag) {
		this.level = serverLevel;
		this.id = compoundTag.getInt("Id");
		this.started = compoundTag.getBoolean("Started");
		this.active = compoundTag.getBoolean("Active");
		this.ticksActive = compoundTag.getLong("TicksActive");
		this.skulkinsCurseLevel = compoundTag.getInt("BadOmenLevel");
		this.groupsSpawned = compoundTag.getInt("GroupsSpawned");
		this.raidCooldownTicks = compoundTag.getInt("PreSkulkinRaidTicks");
		this.postSkulkinRaidTicks = compoundTag.getInt("PostSkulkinRaidTicks");
		this.totalHealth = compoundTag.getFloat("TotalHealth");
		this.center = new BlockPos(compoundTag.getInt("CX"), compoundTag.getInt("CY"), compoundTag.getInt("CZ"));
		this.numGroups = compoundTag.getInt("NumGroups");
		this.status = SkulkinRaid.SkulkinRaidStatus.getByName(compoundTag.getString("Status"));
	}

	public boolean isOver() {
		return this.isVictory() || this.isLoss();
	}

	public boolean isBetweenWaves() {
		return this.hasFirstWaveSpawned() && this.getTotalRaidersAlive() == 0 && this.raidCooldownTicks > 0;
	}

	public boolean hasFirstWaveSpawned() {
		return this.groupsSpawned > 0;
	}

	public boolean isStopped() {
		return this.status == SkulkinRaid.SkulkinRaidStatus.STOPPED;
	}

	public boolean isVictory() {
		return this.status == SkulkinRaid.SkulkinRaidStatus.VICTORY;
	}

	public boolean isLoss() {
		return this.status == SkulkinRaid.SkulkinRaidStatus.LOSS;
	}

	public float getTotalHealth() {
		return this.totalHealth;
	}

	public Set<MeleeCompatibleSkeletonRaider> getAllMeleeCompatibleSkeletonRaiders() {
		Set<MeleeCompatibleSkeletonRaider> set = Sets.<MeleeCompatibleSkeletonRaider>newHashSet();

		for(Set<MeleeCompatibleSkeletonRaider> set2 : this.groupRaiderMap.values()) {
			set.addAll(set2);
		}

		return set;
	}

	public Level getLevel() {
		return this.level;
	}

	public boolean isStarted() {
		return this.started;
	}

	public int getGroupsSpawned() {
		return this.groupsSpawned;
	}

	private Predicate<ServerPlayer> validPlayer() {
		return serverPlayer -> {
			BlockPos blockPos = serverPlayer.blockPosition();
			return serverPlayer.isAlive() && ((SkulkinRaidsHolder)level).getSkulkinRaids().getNearbySkulkinRaid(blockPos, VALID_RAID_RADIUS_SQR) == this;
		};
	}

	private void updatePlayers() {
		Set<ServerPlayer> set = Sets.<ServerPlayer>newHashSet(this.raidEvent.getPlayers());
		List<ServerPlayer> list = this.level.getPlayers(this.validPlayer());

		for(ServerPlayer serverPlayer : list) {
			if (!set.contains(serverPlayer)) {
				this.raidEvent.addPlayer(serverPlayer);
			}
		}

		for(ServerPlayer serverPlayer : set) {
			if (!list.contains(serverPlayer)) {
				this.raidEvent.removePlayer(serverPlayer);
			}
		}
	}

	public int getMaxSkulkinsCurseLevel() {
		return DEFAULT_MAX_SKULKINS_CURSE_LEVEL;
	}

	public int getSkulkinsCurseLevel() {
		return this.skulkinsCurseLevel;
	}

	public void setSkulkinsCurseLevel(int skulkinsCurseLevel) {
		this.skulkinsCurseLevel = skulkinsCurseLevel;
	}

	public void absorbSkulkinsCurse(Player player) {
		if (player.hasEffect(MinejagoMobEffects.SKULKINS_CURSE.get())) {
			this.skulkinsCurseLevel += player.getEffect(MinejagoMobEffects.SKULKINS_CURSE.get()).getAmplifier() + 1;
			this.skulkinsCurseLevel = Mth.clamp(this.skulkinsCurseLevel, 0, this.getMaxSkulkinsCurseLevel());
		}

		player.removeEffect(MinejagoMobEffects.SKULKINS_CURSE.get());
	}

	public void stop() {
		this.active = false;
		this.raidEvent.removeAllPlayers();
		this.status = SkulkinRaid.SkulkinRaidStatus.STOPPED;
		this.time = 0;
	}

	public void tick() {
		if (!this.isStopped()) {
			if (this.status == SkulkinRaid.SkulkinRaidStatus.ONGOING) {
				boolean bl = this.active;
				this.active = this.level.hasChunkAt(this.center);
				if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
					this.stop();
					return;
				}

				if (bl != this.active) {
					this.raidEvent.setVisible(this.active);
				}

				if (!this.active) {
					return;
				}

				if (!level.isNight())
				{
					time = level.getDayTime() + 16000L;
				}
				else if (time == 0)
				{
					time = level.getDayTime();
				}

				level.setDayTime(time);

				if (!MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(level, center, 64)) {
					if (this.groupsSpawned > 0) {
						if (level.getEntitiesOfClass(Painting.class, AABB.ofSize(center.getCenter(), 32, 32, 32), painting -> painting.getVariant().is(Minejago.modLoc( "four_weapons")) && ((DataHolder)painting).getPersistentData().getBoolean("MapTakenByPlayer")).isEmpty())
							this.status = SkulkinRaid.SkulkinRaidStatus.LOSS;
						else
							this.status = SkulkinRaidStatus.VICTORY;
					} else {
						this.stop();
					}
				}

				++this.ticksActive;
				if (this.ticksActive >= RAID_TIMEOUT_TICKS) {
					this.stop();
					return;
				}

				int i = this.getTotalRaidersAlive();
				if (i == 0 && this.hasMoreWaves()) {
					if (this.raidCooldownTicks <= 0) {
						if (this.raidCooldownTicks == 0 && this.groupsSpawned > 0) {
							this.raidCooldownTicks = 300;
							this.raidEvent.setName(RAID_NAME_COMPONENT);
							return;
						}
					} else {
						boolean bl2 = this.waveSpawnPos.isPresent();
						boolean bl3 = !bl2 && this.raidCooldownTicks % 5 == 0;
						if (bl2 && !this.level.isPositionEntityTicking(this.waveSpawnPos.get())) {
							bl3 = true;
						}

						if (bl3) {
							int j = 0;
							if (this.raidCooldownTicks < 100) {
								j = 1;
							} else if (this.raidCooldownTicks < 40) {
								j = 2;
							}

							this.waveSpawnPos = this.getValidSpawnPos(j);
						}

						if (this.raidCooldownTicks == 300 || this.raidCooldownTicks % 20 == 0) {
							this.updatePlayers();
						}

						--this.raidCooldownTicks;
						this.raidEvent.setProgress(Mth.clamp((float)(300 - this.raidCooldownTicks) / 300.0F, 0.0F, 1.0F));
					}
				}

				if (this.ticksActive % 20L == 0L) {
					this.updatePlayers();
					this.updateRaiders();
					if (i > 0) {
						if (i <= 2) {
							this.raidEvent.setName(RAID_NAME_COMPONENT.copy().append(" - ").append(Component.translatable("event.minecraft.raid.raiders_remaining", i)));
						} else {
							this.raidEvent.setName(RAID_NAME_COMPONENT);
						}
					} else {
						this.raidEvent.setName(RAID_NAME_COMPONENT);
					}
				}

				boolean bl2 = false;
				int k = 0;

				while(this.shouldSpawnGroup()) {
					BlockPos blockPos = this.waveSpawnPos.isPresent() ? this.waveSpawnPos.get() : this.findRandomSpawnPos(k, 20);
					if (blockPos != null) {
						this.started = true;
						raidEvent.getPlayers().forEach(MinejagoCriteriaTriggers.SKULKIN_RAID_STARTED::trigger);
						this.spawnGroup(blockPos);
						if (!bl2) {
							this.playSound(blockPos);
							bl2 = true;
						}
					} else {
						++k;
					}

					if (k > 3) {
						this.stop();
						break;
					}
				}

				if (this.isStarted() && !this.hasMoreWaves() && i == 0) {
					if (this.postSkulkinRaidTicks < 40) {
						++this.postSkulkinRaidTicks;
					} else {
						this.status = SkulkinRaid.SkulkinRaidStatus.VICTORY;

						raidEvent.getPlayers().forEach(MinejagoCriteriaTriggers.SKULKIN_RAID_WON::trigger);
					}
				}

				this.setDirty();
			} else if (this.isOver()) {
				++this.celebrationTicks;
				if (this.celebrationTicks >= 600) {
					this.stop();
					return;
				}

				if (this.celebrationTicks % 20 == 0) {
					this.updatePlayers();
					this.raidEvent.setVisible(true);
					if (this.isVictory()) {
						this.raidEvent.setProgress(0.0F);
						this.raidEvent.setName(RAID_BAR_VICTORY_COMPONENT);
					} else {
						this.raidEvent.setName(RAID_BAR_DEFEAT_COMPONENT);
					}
				}
			}
		}
	}

	private Optional<BlockPos> getValidSpawnPos(int offsetMultiplier) {
		for(int i = 0; i < 3; ++i) {
			BlockPos blockPos = this.findRandomSpawnPos(offsetMultiplier, 1);
			if (blockPos != null) {
				return Optional.of(blockPos);
			}
		}

		return Optional.empty();
	}

	private boolean hasMoreWaves() {
		if (this.hasBonusWave()) {
			return !this.hasSpawnedBonusWave();
		} else {
			return !this.isFinalWave();
		}
	}

	private boolean isFinalWave() {
		return this.getGroupsSpawned() == this.numGroups;
	}

	private boolean hasBonusWave() {
		return this.skulkinsCurseLevel > 1;
	}

	private boolean hasSpawnedBonusWave() {
		return this.getGroupsSpawned() > this.numGroups;
	}

	private boolean shouldSpawnBonusGroup() {
		return this.isFinalWave() && this.getTotalRaidersAlive() == 0 && this.hasBonusWave();
	}

	private void updateRaiders() {
		Iterator<Set<MeleeCompatibleSkeletonRaider>> iterator = this.groupRaiderMap.values().iterator();
		Set<MeleeCompatibleSkeletonRaider> set = Sets.<MeleeCompatibleSkeletonRaider>newHashSet();

		while(iterator.hasNext()) {
			Set<MeleeCompatibleSkeletonRaider> set2 = iterator.next();

			for(MeleeCompatibleSkeletonRaider raider : set2) {
				BlockPos blockPos = raider.blockPosition();
				if (raider.isRemoved() || raider.level().dimension() != this.level.dimension() || this.center.distSqr(blockPos) >= 12544.0) {
					set.add(raider);
				} else if (raider.tickCount > 600) {
					if (this.level.getEntity(raider.getUUID()) == null) {
						set.add(raider);
					}

					if (!MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(level, center, 32) && raider.getNoActionTime() > 2400) {
						raider.setTicksOutsideSkulkinRaid(raider.getTicksOutsideSkulkinRaid() + 1);
					}

					if (raider.getTicksOutsideSkulkinRaid() >= 30) {
						set.add(raider);
					}
				}
			}
		}

		for(MeleeCompatibleSkeletonRaider raider2 : set) {
			this.removeFromSkulkinRaid(raider2, true);
		}
	}

	private void playSound(BlockPos pos) {
		float f = 13.0F;
		int i = 64;
		Collection<ServerPlayer> collection = this.raidEvent.getPlayers();
		long l = this.random.nextLong();

		for(ServerPlayer serverPlayer : this.level.players()) {
			Vec3 vec3 = serverPlayer.position();
			Vec3 vec32 = Vec3.atCenterOf(pos);
			double d = Math.sqrt((vec32.x - vec3.x) * (vec32.x - vec3.x) + (vec32.z - vec3.z) * (vec32.z - vec3.z));
			double e = vec3.x + 13.0 / d * (vec32.x - vec3.x);
			double g = vec3.z + 13.0 / d * (vec32.z - vec3.z);
			if (d <= 64.0 || collection.contains(serverPlayer)) {
				serverPlayer.connection.send(new ClientboundSoundPacket(SoundEvents.RAID_HORN, SoundSource.NEUTRAL, e, serverPlayer.getY(), g, 64.0F, 1.0F, l)); // TODO: Custom horn?
			}
		}
	}

	private void spawnGroup(BlockPos pos) {
		boolean bl = false;
		int i = this.groupsSpawned + 1;
		this.totalHealth = 0.0F;
		DifficultyInstance difficultyInstance = this.level.getCurrentDifficultyAt(pos);
		boolean bl2 = this.shouldSpawnBonusGroup();

		int j = this.getDefaultNumSpawns(i, bl2) + this.getPotentialBonusSpawns(this.random, i, difficultyInstance, bl2);

		if (this.groupsSpawned >= this.numGroups - 1) bl = spawnBosses(i, pos);
		System.out.println("GS" + groupsSpawned);
		System.out.println("NG" + numGroups);

		for(int l = 0; l < j; ++l) {
			MeleeCompatibleSkeletonRaider raider = MinejagoEntityTypes.SKULKIN.get().create(this.level);
			if (raider == null) {
				break;
			}

			if (!bl && raider.canBeLeader()) {
				raider.setPatrolLeader(true);
				this.setLeader(i, raider);
				bl = true;
			}

			this.joinSkulkinRaid(i, raider, pos, false);
			if (random.nextInt(10) + difficultyInstance.getDifficulty().getId() > 5) {
				Mob raider2;
//				if (MinejagoServerConfig.ENABLE_TECH.get())
//					// TODO: Bike
//				else
					raider2 = MinejagoEntityTypes.SKULKIN_HORSE.get().create(level);
				if (raider2 == null)
					break;
				raider2.setPos((double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5);
				raider2.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null, null);
				raider2.setOnGround(true);
				this.level.addFreshEntityWithPassengers(raider2);
				raider2.moveTo(pos, 0.0F, 0.0F);
				raider.startRiding(raider2);
			}
		}

		this.waveSpawnPos = Optional.empty();
		++this.groupsSpawned;
		this.updateBossbar();
		this.setDirty();
	}

	public void joinSkulkinRaid(int wave, MeleeCompatibleSkeletonRaider raider, @Nullable BlockPos pos, boolean isRecruited) {
		boolean bl = this.addWaveMob(wave, raider);
		if (bl) {
			raider.setCurrentSkulkinRaid(this);
			raider.setWave(wave);
			raider.setCanJoinSkulkinRaid(true);
			raider.setTicksOutsideSkulkinRaid(0);
			if (!isRecruited && pos != null) {
				raider.setPos((double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5);
				raider.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null, null);
				raider.setOnGround(true);
				this.level.addFreshEntityWithPassengers(raider);
			}
		}
	}

	public void updateBossbar() {
		this.raidEvent.setProgress(Mth.clamp(this.getHealthOfLivingMeleeCompatibleSkeletonRaiders() / this.totalHealth, 0.0F, 1.0F));
	}

	public float getHealthOfLivingMeleeCompatibleSkeletonRaiders() {
		float f = 0.0F;

		for(Set<MeleeCompatibleSkeletonRaider> set : this.groupRaiderMap.values()) {
			for(MeleeCompatibleSkeletonRaider raider : set) {
				f += raider.getHealth();
			}
		}

		return f;
	}

	private boolean shouldSpawnGroup() {
		return this.raidCooldownTicks == 0 && (this.groupsSpawned < this.numGroups || this.shouldSpawnBonusGroup()) && this.getTotalRaidersAlive() == 0;
	}

	public int getTotalRaidersAlive() {
		return this.groupRaiderMap.values().stream().mapToInt(Set::size).sum();
	}

	public void removeFromSkulkinRaid(MeleeCompatibleSkeletonRaider raider, boolean wanderedOutOfSkulkinRaid) {
		Set<MeleeCompatibleSkeletonRaider> set = (Set)this.groupRaiderMap.get(raider.getWave());
		if (set != null) {
			boolean bl = set.remove(raider);
			if (bl) {
				if (wanderedOutOfSkulkinRaid) {
					this.totalHealth -= raider.getHealth();
				}

				raider.setCurrentSkulkinRaid(null);
				this.updateBossbar();
				this.setDirty();
			}
		}
	}

	private void setDirty() {
		((SkulkinRaidsHolder)this.level).getSkulkinRaids().setDirty();
	}

	public static ItemStack getLeaderBannerInstance() {
		ItemStack itemStack = new ItemStack(Items.BLACK_BANNER);
		CompoundTag compoundTag = new CompoundTag();
		ListTag listTag = new BannerPattern.Builder()
				.addPattern(BannerPatterns.CROSS, DyeColor.RED)
				.addPattern(BannerPatterns.RHOMBUS_MIDDLE, DyeColor.BROWN)
				.addPattern(BannerPatterns.SKULL, DyeColor.WHITE)
				.toListTag();
		compoundTag.put("Patterns", listTag);
		BlockItem.setBlockEntityData(itemStack, BlockEntityType.BANNER, compoundTag);
		itemStack.hideTooltipPart(ItemStack.TooltipPart.ADDITIONAL);
		itemStack.setHoverName(Component.translatable(SKULKINS_BANNER_PATTERN_NAME).withStyle(ChatFormatting.GOLD));
		return itemStack;
	}

	@Nullable
	public MeleeCompatibleSkeletonRaider getLeader(int wave) {
		return this.groupToLeaderMap.get(wave);
	}

	@Nullable
	private BlockPos findRandomSpawnPos(int offsetMultiplier, int maxTry) {
		int i = offsetMultiplier == 0 ? 2 : 2 - offsetMultiplier;
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		for(int j = 0; j < maxTry; ++j) {
			float f = this.level.random.nextFloat() * (float) (Math.PI * 2);
			int k = this.center.getX() + Mth.floor(Mth.cos(f) * 32.0F * (float)i) + this.level.random.nextInt(5);
			int l = this.center.getZ() + Mth.floor(Mth.sin(f) * 32.0F * (float)i) + this.level.random.nextInt(5);
			int m = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, k, l);
			mutableBlockPos.set(k, m, l);
			if (!MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(level, mutableBlockPos, 32) || offsetMultiplier >= 2) {
				int n = 10;
				if (this.level.hasChunksAt(mutableBlockPos.getX() - 10, mutableBlockPos.getZ() - 10, mutableBlockPos.getX() + 10, mutableBlockPos.getZ() + 10)
						&& this.level.isPositionEntityTicking(mutableBlockPos)
						&& (
						NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, this.level, mutableBlockPos, EntityType.RAVAGER)
								|| this.level.getBlockState(mutableBlockPos.below()).is(Blocks.SNOW) && this.level.getBlockState(mutableBlockPos).isAir()
				)) {
					return mutableBlockPos;
				}
			}
		}

		return null;
	}

	private boolean addWaveMob(int wave, MeleeCompatibleSkeletonRaider raider) {
		return this.addWaveMob(wave, raider, true);
	}

	public boolean addWaveMob(int wave, MeleeCompatibleSkeletonRaider raider, boolean isRecruited) {
		this.groupRaiderMap.computeIfAbsent(wave, integer -> Sets.newHashSet());
		Set<MeleeCompatibleSkeletonRaider> set = this.groupRaiderMap.get(wave);
		MeleeCompatibleSkeletonRaider raider2 = null;

		for(MeleeCompatibleSkeletonRaider raider3 : set) {
			if (raider3.getUUID().equals(raider.getUUID())) {
				raider2 = raider3;
				break;
			}
		}

		if (raider2 != null) {
			set.remove(raider2);
			set.add(raider);
		}

		set.add(raider);
		if (isRecruited) {
			this.totalHealth += raider.getHealth();
		}

		this.updateBossbar();
		this.setDirty();
		return true;
	}

	public void setLeader(int wave, MeleeCompatibleSkeletonRaider raider) {
		this.groupToLeaderMap.put(wave, raider);
		raider.setItemSlot(EquipmentSlot.HEAD, getLeaderBannerInstance());
		raider.setDropChance(EquipmentSlot.HEAD, 2.0F);
	}

	public void removeLeader(int wave) {
		this.groupToLeaderMap.remove(wave);
	}

	public BlockPos getCenter() {
		return this.center;
	}

	private void setCenter(BlockPos center) {
		this.center = center;
	}

	public int getId() {
		return this.id;
	}

	private int getDefaultNumSpawns(int wave, boolean shouldSpawnBonusGroup) {
		int[] spawns = new int[]{0, 4, 3, 3, 4, 4, 4, 2};
		return shouldSpawnBonusGroup ? spawns[this.numGroups] : spawns[wave];
	}

	private int getPotentialBonusSpawns(RandomSource random, int wave, DifficultyInstance difficulty, boolean shouldSpawnBonusGroup) {
		Difficulty difficulty2 = difficulty.getDifficulty();
		boolean bl = difficulty2 == Difficulty.EASY;
		boolean bl2 = difficulty2 == Difficulty.NORMAL;
		int i;
		if (bl) {
			i = random.nextInt(2);
		} else if (bl2) {
			i = 1;
		} else {
			i = 2;
		}
		return i > 0 ? random.nextInt(i + 1) : 0;
	}

	public boolean isActive() {
		return this.active;
	}

	public CompoundTag save(CompoundTag compound) {
		compound.putInt("Id", this.id);
		compound.putBoolean("Started", this.started);
		compound.putBoolean("Active", this.active);
		compound.putLong("TicksActive", this.ticksActive);
		compound.putInt("BadOmenLevel", this.skulkinsCurseLevel);
		compound.putInt("GroupsSpawned", this.groupsSpawned);
		compound.putInt("PreSkulkinRaidTicks", this.raidCooldownTicks);
		compound.putInt("PostSkulkinRaidTicks", this.postSkulkinRaidTicks);
		compound.putFloat("TotalHealth", this.totalHealth);
		compound.putInt("NumGroups", this.numGroups);
		compound.putString("Status", this.status.getName());
		compound.putInt("CX", this.center.getX());
		compound.putInt("CY", this.center.getY());
		compound.putInt("CZ", this.center.getZ());
		return compound;
	}

	public int getNumGroups(Difficulty difficulty) {
		switch(difficulty) {
			case EASY:
				return 3;
			case NORMAL:
				return 5;
			case HARD:
				return 7;
			default:
				return 0;
		}
	}

	public float getEnchantOdds() {
		int i = this.getSkulkinsCurseLevel();
		if (i == 2) {
			return 0.1F;
		} else if (i == 3) {
			return 0.25F;
		} else if (i == 4) {
			return 0.5F;
		} else {
			return i == 5 ? 0.75F : 0.0F;
		}
	}

	private boolean spawnBosses(int i, BlockPos pos)
	{
		boolean bl = false;
		Samukai samukai = MinejagoEntityTypes.SAMUKAI.get().create(this.level);
		if (samukai == null) {
			return false;
		}
		if (samukai.canBeLeader()) {
			samukai.setPatrolLeader(true);
			this.setLeader(i, samukai);
			bl = true;
		}
		this.joinSkulkinRaid(i, samukai, pos, false);

		Nuckal nuckal = MinejagoEntityTypes.NUCKAL.get().create(this.level);
		if (nuckal != null)
			this.joinSkulkinRaid(i, nuckal, pos, false);

		Kruncha kruncha = MinejagoEntityTypes.KRUNCHA.get().create(this.level);
		if (kruncha != null)
			this.joinSkulkinRaid(i, kruncha, pos, false);

		if (MinejagoServerConfig.ENABLE_SKULKIN_RAIDS.get())
		{
			List<MeleeCompatibleSkeletonRaider> raiders = new ArrayList<>();
			if (nuckal != null) raiders.add(nuckal);
			if (kruncha != null) raiders.add(kruncha);

			SkullTruck truck = MinejagoEntityTypes.SKULL_TRUCK.get().create(level);
			if (truck == null)
				return false;
			truck.setPos((double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5);
			truck.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null, null);
			truck.setOnGround(true);
			this.level.addFreshEntityWithPassengers(truck);
			truck.moveTo(pos, 0.0F, 0.0F);

			samukai.startRiding(truck);

			for (MeleeCompatibleSkeletonRaider raider : raiders)
			{
				raider.startRiding(truck);
			}
		}
		else
		{
			List<MeleeCompatibleSkeletonRaider> raiders = new ArrayList<>();
			raiders.add(samukai);
			if (nuckal != null) raiders.add(nuckal);
			if (kruncha != null) raiders.add(kruncha);

			for (MeleeCompatibleSkeletonRaider raider : raiders)
			{
				Mob horse = MinejagoEntityTypes.SKULKIN_HORSE.get().create(level);
				if (horse == null)
					break;
				horse.setPos((double)pos.getX() + 0.5, (double)pos.getY() + 1.0, (double)pos.getZ() + 0.5);
				horse.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null, null);
				horse.setOnGround(true);
				this.level.addFreshEntityWithPassengers(horse);
				horse.moveTo(pos, 0.0F, 0.0F);
				raider.startRiding(horse);
			}
		}
		return bl;
	}

	public Vec3 getEscapePos()
	{
		return escapePos;
	}

	public void setEscapePos(Vec3 escapePos)
	{
		this.escapePos = escapePos;
	}

	enum SkulkinRaidStatus {
		ONGOING,
		VICTORY,
		LOSS,
		STOPPED;

		private static final SkulkinRaid.SkulkinRaidStatus[] VALUES = values();

		static SkulkinRaid.SkulkinRaidStatus getByName(String name) {
			for(SkulkinRaid.SkulkinRaidStatus raidStatus : VALUES) {
				if (name.equalsIgnoreCase(raidStatus.name())) {
					return raidStatus;
				}
			}

			return ONGOING;
		}

		public String getName() {
			return this.name().toLowerCase(Locale.ROOT);
		}
	}
}