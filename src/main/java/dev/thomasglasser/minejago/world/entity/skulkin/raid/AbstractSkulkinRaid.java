package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import com.google.common.collect.Sets;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import dev.thomasglasser.minejago.core.registries.MinejagoBuiltInRegistries;
import dev.thomasglasser.minejago.network.ClientboundAddSkulkinRaidPayload;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSkulkinRaid {
    public static final String SKULKIN_REMAINING = "event.minejago.skulkin_raid.skulkin_remaining";
    public static final Component VICTORY_COMPONENT = Component.translatable("event.minejago.skulkin_raid.victory.full");
    public static final Component DEFEAT_COMPONENT = Component.translatable("event.minejago.skulkin_raid.defeat.full");
    public static final int TIME_MIDNIGHT = 18000;
    public static final int CENTER_RADIUS_BUFFER = 16;
    public static final int MAX_NO_ACTION_TIME = 2400;
    public static final int MAX_CELEBRATION_TICKS = 600;
    public static final int VALID_RAID_RADIUS = 96;
    public static final int VALID_RAID_RADIUS_SQR = 9216;
    public static final int RAID_REMOVAL_THRESHOLD_SQR = 12544;

    private static final int SECTION_RADIUS_FOR_FINDING_NEW_CENTER = 2;
    private static final int CENTER_SEARCH_RADIUS = 32;
    private static final int RAID_TIMEOUT_TICKS = 48000;
    private static final int NUM_SPAWN_ATTEMPTS = 20;
    private static final int POST_RAID_TICK_LIMIT = 40;
    private static final int DEFAULT_PRE_RAID_TICKS = 300;
    private static final int OUTSIDE_RAID_BOUNDS_TIMEOUT = 30;
    private static final int LOW_MOB_THRESHOLD = 2;

    protected final Map<Integer, Set<SkulkinRaider>> raidersByWave = new HashMap<>();

    private final ServerLevel level;
    private final int id;
    private final int numGroups;
    private final Component name;
    private final ServerBossEvent raidEvent;

    private boolean started;
    private boolean active;
    private long ticksActive;
    private float totalHealth;
    private int groupsSpawned;
    private int raidCooldownTicks;
    private int postRaidTicks;
    private int celebrationTicks;
    private SkulkinRaidStatus status;
    private Optional<BlockPos> waveSpawnPos = Optional.empty();
    private BlockPos center;
    private Vec3 escapePos;

    public AbstractSkulkinRaid(ServerLevel level, int id, BlockPos center, Component name) {
        this.level = level;
        this.id = id;
        this.numGroups = switch (level.getDifficulty()) {
            case EASY -> 3;
            case NORMAL -> 5;
            case HARD -> 7;
            default -> 0;
        };
        this.active = true;
        this.raidCooldownTicks = DEFAULT_PRE_RAID_TICKS;
        this.status = SkulkinRaidStatus.ONGOING;
        this.center = center;
        this.name = name;
        this.raidEvent = new ServerBossEvent(name, BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.NOTCHED_10);
        this.raidEvent.setPlayBossMusic(true);
        this.raidEvent.setProgress(0.0F);
        TommyLibServices.NETWORK.sendToAllClients(new ClientboundAddSkulkinRaidPayload(raidEvent.getId()), level.getServer());
    }

    public AbstractSkulkinRaid(ServerLevel level, CompoundTag compound, Component name) {
        this.level = level;
        this.id = compound.getInt("Id");
        this.numGroups = compound.getInt("NumGroups");
        this.started = compound.getBoolean("Started");
        this.active = compound.getBoolean("Active");
        this.ticksActive = compound.getLong("TicksActive");
        this.totalHealth = compound.getFloat("TotalHealth");
        this.groupsSpawned = compound.getInt("GroupsSpawned");
        this.raidCooldownTicks = compound.getInt("PreRaidTicks");
        this.postRaidTicks = compound.getInt("PostRaidTicks");
        this.status = SkulkinRaidStatus.getByName(compound.getString("Status"));
        this.center = new BlockPos(compound.getInt("CenterX"), compound.getInt("CenterY"), compound.getInt("CenterZ"));
        if (compound.contains("EscapeX", CompoundTag.TAG_DOUBLE))
            this.escapePos = new Vec3(compound.getDouble("EscapeX"), compound.getDouble("EscapeY"), compound.getDouble("EscapeZ"));
        this.name = name;
        this.raidEvent = new ServerBossEvent(name, BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.NOTCHED_10);
        this.raidEvent.setPlayBossMusic(true);
        TommyLibServices.NETWORK.sendToAllClients(new ClientboundAddSkulkinRaidPayload(this.raidEvent.getId()), level.getServer());
    }

    public ServerLevel getLevel() {
        return this.level;
    }

    public RandomSource getRandom() {
        return this.level.random;
    }

    public int getId() {
        return this.id;
    }

    public boolean isStarted() {
        return this.started;
    }

    public void setStarted() {
        this.started = true;
        this.raidEvent.getPlayers().forEach(serverPlayer -> MinejagoCriteriaTriggers.SKULKIN_RAID_STATUS_CHANGED.get().trigger(serverPlayer, SkulkinRaidTrigger.Status.STARTED));
    }

    public boolean isActive() {
        return this.active;
    }

    public int getGroupsSpawned() {
        return this.groupsSpawned;
    }

    public boolean isOngoing() {
        return this.status == SkulkinRaidStatus.ONGOING;
    }

    public boolean isVictory() {
        return this.status == SkulkinRaidStatus.VICTORY;
    }

    public void setVictory() {
        this.status = SkulkinRaidStatus.VICTORY;
        this.raidEvent.getPlayers().forEach(serverPlayer -> MinejagoCriteriaTriggers.SKULKIN_RAID_STATUS_CHANGED.get().trigger(serverPlayer, SkulkinRaidTrigger.Status.WON));
    }

    public boolean isDefeat() {
        return this.status == SkulkinRaidStatus.DEFEAT;
    }

    public void setDefeat() {
        this.status = SkulkinRaidStatus.DEFEAT;
    }

    public boolean isStopped() {
        return this.status == SkulkinRaidStatus.STOPPED;
    }

    public void setStopped() {
        this.status = SkulkinRaidStatus.STOPPED;
        this.active = false;
        this.raidEvent.removeAllPlayers();
    }

    public boolean isOver() {
        return this.isVictory() || this.isDefeat();
    }

    public BlockPos getCenter() {
        return this.center;
    }

    public void setCenter(BlockPos center) {
        this.center = center;
    }

    public Vec3 getEscapePos() {
        return this.escapePos;
    }

    public void setEscapePos(Vec3 escapePos) {
        this.escapePos = escapePos;
    }

    public abstract boolean isValidRaidItem(ItemStack stack);

    public abstract boolean isInValidRaidSearchArea(SkulkinRaider raider);

    public abstract boolean canExtractRaidItem(SkulkinRaider raider);

    public abstract @Nullable ItemStack extractRaidItem(SkulkinRaider raider);

    protected void setDirty() {
        SkulkinRaidsHolder.of(this.level).minejago$getSkulkinRaids().setDirty();
    }

    public void updateBossbar() {
        this.raidEvent.setProgress(Mth.clamp(this.getHealthOfLivingRaiders() / this.totalHealth, 0.0F, 1.0F));
    }

    public float getHealthOfLivingRaiders() {
        float total = 0.0F;

        for (Set<SkulkinRaider> set : this.raidersByWave.values()) {
            for (SkulkinRaider raider : set) {
                total += raider.getHealth();
            }
        }

        return total;
    }

    public void tick() {
        if (!this.isStopped()) {
            if (this.isOngoing()) {
                boolean wasActive = this.active;
                this.active = this.level.hasChunkAt(this.center);
                if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
                    this.setStopped();
                    return;
                }

                if (wasActive != this.active) {
                    this.raidEvent.setVisible(this.active);
                }

                if (!this.active) {
                    return;
                }

                if (!(this.level.getDayTime() == TIME_MIDNIGHT)) {
                    this.level.setDayTime(TIME_MIDNIGHT);
                }

                if (!(this.getType().findValidRaidCenter(this.level, center) == null)) {
                    this.moveRaidCenterToNearbyValidTarget();
                }

                this.ticksActive++;
                if (this.ticksActive >= RAID_TIMEOUT_TICKS) {
                    this.setStopped();
                    return;
                }

                int alive = this.getTotalRaidersAlive();
                if (alive == 0 && this.hasMoreWaves()) {
                    if (this.raidCooldownTicks <= 0) {
                        if (this.groupsSpawned > 0) {
                            this.raidCooldownTicks = DEFAULT_PRE_RAID_TICKS;
                            this.raidEvent.setName(name);
                            if (this.getEscapePos() != null) {
                                this.setEscapePos(null);
                            }
                            return;
                        }
                    } else {
                        boolean hasSpawnPos = this.waveSpawnPos.isPresent();
                        boolean findSpawnPos = !hasSpawnPos && this.raidCooldownTicks % 5 == 0;
                        if (hasSpawnPos && !this.level.isPositionEntityTicking(this.waveSpawnPos.get())) {
                            findSpawnPos = true;
                        }

                        if (findSpawnPos) {
                            int j = 0;
                            if (this.raidCooldownTicks < (SharedConstants.TICKS_PER_SECOND * 2)) {
                                j = 2;
                            } else if (this.raidCooldownTicks < (SharedConstants.TICKS_PER_SECOND * 5)) {
                                j = 1;
                            }

                            this.waveSpawnPos = this.findValidSpawnPos(j);
                        }

                        if (this.raidCooldownTicks == DEFAULT_PRE_RAID_TICKS || this.raidCooldownTicks % SharedConstants.TICKS_PER_SECOND == 0) {
                            this.updatePlayers();
                        }

                        this.raidCooldownTicks--;
                        this.raidEvent.setProgress(Mth.clamp((float) (DEFAULT_PRE_RAID_TICKS - this.raidCooldownTicks) / DEFAULT_PRE_RAID_TICKS, 0.0F, 1.0F));
                    }
                }

                if (this.ticksActive % SharedConstants.TICKS_PER_SECOND == 0L) {
                    this.updatePlayers();
                    this.updateRaiders();
                    if (alive > 0) {
                        if (alive <= LOW_MOB_THRESHOLD) {
                            this.raidEvent.setName(name.copy().append(" - ").append(Component.translatable(SKULKIN_REMAINING, alive)));
                        } else {
                            this.raidEvent.setName(name);
                        }
                    } else {
                        this.raidEvent.setName(name);
                    }
                }

                boolean playedSound = false;
                int spawnAttempts = 0;

                while (this.shouldSpawnGroup()) {
                    BlockPos blockPos = this.waveSpawnPos.isPresent() ? this.waveSpawnPos.get() : this.findRandomSpawnPos(spawnAttempts, 20).orElse(null);
                    if (blockPos != null) {
                        this.setStarted();
                        this.spawnGroup(blockPos);
                        if (!playedSound) {
                            this.playSound(blockPos);
                            playedSound = true;
                        }
                    } else {
                        spawnAttempts++;
                    }

                    if (spawnAttempts > NUM_SPAWN_ATTEMPTS) {
                        SkulkinRaidsHolder.of(this.level).minejago$getSkulkinRaids().removeRaidedArea(this.getCenter());
                        this.setStopped();
                        break;
                    }
                }

                if (this.isStarted() && !this.hasMoreWaves() && alive == 0) {
                    if (this.postRaidTicks < POST_RAID_TICK_LIMIT) {
                        this.postRaidTicks++;
                    } else {
                        this.setVictory();
                    }
                }

                this.setDirty();
            } else if (this.isOver()) {
                this.celebrationTicks++;
                if (this.celebrationTicks >= MAX_CELEBRATION_TICKS) {
                    this.setStopped();
                    return;
                }

                if (this.celebrationTicks % SharedConstants.TICKS_PER_SECOND == 0) {
                    this.updatePlayers();
                    this.raidEvent.setVisible(true);
                    if (this.isVictory()) {
                        this.raidEvent.setProgress(0.0F);
                        this.raidEvent.setName(name.copy().append(" - ").append(VICTORY_COMPONENT));
                    } else {
                        this.raidEvent.setName(name.copy().append(" - ").append(DEFEAT_COMPONENT));
                    }
                }
            }
        }
    }

    protected void moveRaidCenterToNearbyValidTarget() {
        Stream<SectionPos> stream = SectionPos.cube(SectionPos.of(this.center), SECTION_RADIUS_FOR_FINDING_NEW_CENTER);
        stream.map(pos -> this.getType().findValidRaidCenter(this.level, pos.center()))
                .filter(Objects::nonNull)
                .min(Comparator.comparingDouble(pos -> pos.distSqr(this.center)))
                .ifPresent(this::setCenter);
    }

    public int getTotalRaidersAlive() {
        return this.raidersByWave.values().stream().mapToInt(Set::size).sum();
    }

    public boolean hasMoreWaves() {
        return !this.isFinalWave();
    }

    public boolean isFinalWave() {
        return this.getGroupsSpawned() == this.numGroups;
    }

    protected Optional<BlockPos> findValidSpawnPos(int offsetMultiplier) {
        return this.findRandomSpawnPos(offsetMultiplier, 8);
    }

    protected Optional<BlockPos> findRandomSpawnPos(int offsetMultiplier, int maxTry) {
        int fixedOffsetMultiplier = 2 - offsetMultiplier;
        BlockPos.MutableBlockPos spawnPos = new BlockPos.MutableBlockPos();
        SpawnPlacementType spawnPlacementType = SpawnPlacements.getPlacementType(EntityType.RAVAGER);

        for (int i = 0; i < maxTry; i++) {
            float f = this.getRandom().nextFloat() * (float) (Math.PI * 2);
            int x = this.center.getX() + Mth.floor(Mth.cos(f) * CENTER_SEARCH_RADIUS * (float) fixedOffsetMultiplier) + this.level.random.nextInt(5);
            int z = this.center.getZ() + Mth.floor(Mth.sin(f) * CENTER_SEARCH_RADIUS * (float) fixedOffsetMultiplier) + this.level.random.nextInt(5);
            int y = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
            spawnPos.set(x, y, z);
            if (this.getType().findValidRaidCenter(getLevel(), spawnPos) == null || offsetMultiplier >= 2) {
                int offset = 10;
                if (this.level
                        .hasChunksAt(
                                spawnPos.getX() - offset,
                                spawnPos.getZ() - offset,
                                spawnPos.getX() + offset,
                                spawnPos.getZ() + offset)
                        && this.level.isPositionEntityTicking(spawnPos)
                        && (spawnPlacementType.isSpawnPositionOk(this.level, spawnPos, EntityType.RAVAGER)
                                || this.level.getBlockState(spawnPos.below()).is(Blocks.SNOW)
                                        && this.level.getBlockState(spawnPos).isAir())) {
                    return Optional.of(spawnPos);
                }
            }
        }

        return Optional.empty();
    }

    protected void updatePlayers() {
        Set<ServerPlayer> eventPlayers = Sets.newHashSet(this.raidEvent.getPlayers());
        List<ServerPlayer> validPlayers = this.level.getPlayers(serverPlayer -> serverPlayer.isAlive() && SkulkinRaidsHolder.of(level).minejago$getSkulkinRaids().getSkulkinRaidAt(serverPlayer.blockPosition()) == this);

        for (ServerPlayer serverPlayer : validPlayers) {
            if (!eventPlayers.contains(serverPlayer)) {
                this.raidEvent.addPlayer(serverPlayer);
            }
        }

        for (ServerPlayer serverPlayer : eventPlayers) {
            if (!validPlayers.contains(serverPlayer)) {
                this.raidEvent.removePlayer(serverPlayer);
            }
        }
    }

    protected void updateRaiders() {
        Iterator<Set<SkulkinRaider>> waveRaiders = this.raidersByWave.values().iterator();
        Set<SkulkinRaider> lostRaiders = Sets.newHashSet();

        while (waveRaiders.hasNext()) {
            Set<SkulkinRaider> raiders = waveRaiders.next();

            for (SkulkinRaider raider : raiders) {
                BlockPos blockPos = raider.blockPosition();
                if (raider.isRemoved() || raider.level().dimension() != this.level.dimension() || this.center.distSqr(blockPos) >= RAID_REMOVAL_THRESHOLD_SQR) {
                    lostRaiders.add(raider);
                } else if (raider.tickCount > MAX_CELEBRATION_TICKS) {
                    if (this.level.getEntity(raider.getUUID()) == null) {
                        lostRaiders.add(raider);
                    }

                    if (this.getType().findValidRaidCenter(this.level, blockPos) == null && raider.getNoActionTime() > MAX_NO_ACTION_TIME) {
                        raider.setTicksOutsideRaid(raider.getTicksOutsideRaid() + 1);
                    }

                    if (raider.getTicksOutsideRaid() >= OUTSIDE_RAID_BOUNDS_TIMEOUT) {
                        lostRaiders.add(raider);
                    }
                }
            }
        }

        for (SkulkinRaider raider : lostRaiders) {
            this.removeFromRaid(raider, true);
        }
    }

    public void removeFromRaid(SkulkinRaider raider, boolean wanderedOutOfRaid) {
        Set<SkulkinRaider> set = this.raidersByWave.get(raider.getWave());
        if (set != null) {
            boolean removed = set.remove(raider);
            if (removed) {
                if (wanderedOutOfRaid) {
                    this.totalHealth = this.totalHealth - raider.getHealth();
                }

                raider.setCurrentRaid(null);
                this.updateBossbar();
                this.setDirty();
            }
        }
    }

    protected boolean shouldSpawnGroup() {
        return this.raidCooldownTicks == 0 && (this.groupsSpawned < this.numGroups) && this.getTotalRaidersAlive() == 0;
    }

    protected void spawnGroup(BlockPos pos) {
        int groupNum = this.groupsSpawned + 1;
        this.totalHealth = 0.0F;
        DifficultyInstance difficultyInstance = this.level.getCurrentDifficultyAt(pos);

        int spawnCount = this.getDefaultNumSpawns(groupNum) + this.getPotentialBonusSpawns(this.getRandom(), difficultyInstance);

        if (this.groupsSpawned == this.numGroups - 1)
            this.spawnBosses(groupNum, pos);

        for (int i = 0; i < spawnCount; i++) {
            SkulkinRaider raider = MinejagoEntityTypes.SKULKIN.get().create(this.level);
            if (raider == null) {
                break;
            }

            this.joinRaid(groupNum, raider, pos, false);

            if (this.getRandom().nextInt(10) + difficultyInstance.getDifficulty().getId() > 5) {
                this.spawnVehicle(raider, pos);
            }
        }

        this.waveSpawnPos = Optional.empty();
        this.groupsSpawned++;
        this.updateBossbar();
        this.setDirty();
    }

    protected int getDefaultNumSpawns(int wave) {
        return switch (wave) {
            case 1, 4, 5, 6 -> 4;
            case 2, 3 -> 3;
            case 7 -> 2;
            default -> 0;
        };
    }

    protected int getPotentialBonusSpawns(RandomSource random, DifficultyInstance difficultyInstance) {
        Difficulty difficulty = difficultyInstance.getDifficulty();
        boolean easy = difficulty == Difficulty.EASY;
        boolean normal = difficulty == Difficulty.NORMAL;
        int i;
        if (easy) {
            i = random.nextInt(2);
        } else if (normal) {
            i = 1;
        } else {
            i = 2;
        }

        return i > 0 ? random.nextInt(i + 1) : 0;
    }

    protected void spawnBosses(int groupNum, BlockPos pos) {
        Samukai samukai = MinejagoEntityTypes.SAMUKAI.get().create(this.level);
        if (samukai != null)
            this.joinRaid(groupNum, samukai, pos, false);

        Nuckal nuckal = MinejagoEntityTypes.NUCKAL.get().create(this.level);
        if (nuckal != null)
            this.joinRaid(groupNum, nuckal, pos, false);

        Kruncha kruncha = MinejagoEntityTypes.KRUNCHA.get().create(this.level);
        if (kruncha != null)
            this.joinRaid(groupNum, kruncha, pos, false);

        if (MinejagoServerConfig.get().enableTech.get()) {
            if (samukai != null) {
                SkullTruck truck = MinejagoEntityTypes.SKULL_TRUCK.get().create(level);
                if (truck == null)
                    return;
                truck.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                EventHooks.finalizeMobSpawn(truck, this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
                this.level.addFreshEntityWithPassengers(truck);

                if (nuckal != null) nuckal.startRiding(truck);
                if (kruncha != null) kruncha.startRiding(truck);
            }
        } else {
            List<SkulkinRaider> raiders = new ArrayList<>();
            if (samukai != null) raiders.add(samukai);
            if (nuckal != null) raiders.add(nuckal);
            if (kruncha != null) raiders.add(kruncha);

            for (SkulkinRaider raider : raiders) {
                Mob horse = MinejagoEntityTypes.SKULKIN_HORSE.get().create(level);
                if (horse == null)
                    break;
                horse.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                EventHooks.finalizeMobSpawn(horse, this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
                this.level.addFreshEntityWithPassengers(horse);
                raider.startRiding(horse);
            }
        }
    }

    public void joinRaid(int wave, SkulkinRaider raider, @Nullable BlockPos pos, boolean isRecruited) {
        boolean added = this.addWaveMob(wave, raider);
        if (added) {
            raider.setCurrentRaid(this);
            raider.setWave(wave);
            raider.setTicksOutsideRaid(0);
            if (!isRecruited && pos != null) {
                raider.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                EventHooks.finalizeMobSpawn(raider, this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
                this.level.addFreshEntityWithPassengers(raider);
            }
        }
    }

    public boolean addWaveMob(int wave, SkulkinRaider raider) {
        return this.addWaveMob(wave, raider, true);
    }

    public boolean addWaveMob(int wave, SkulkinRaider raider, boolean isRecruited) {
        this.raidersByWave.computeIfAbsent(wave, i -> Sets.newHashSet());
        Set<SkulkinRaider> waveRaiders = this.raidersByWave.get(wave);
        SkulkinRaider existing = null;

        for (SkulkinRaider r : waveRaiders) {
            if (r.getUUID().equals(raider.getUUID())) {
                existing = r;
                break;
            }
        }

        if (existing != null) {
            waveRaiders.remove(existing);
        }

        waveRaiders.add(raider);

        if (isRecruited) {
            this.totalHealth = this.totalHealth + raider.getHealth();
        }

        this.updateBossbar();
        this.setDirty();
        return true;
    }

    protected void spawnVehicle(SkulkinRaider raider, BlockPos pos) {
        Mob ride;
        if (MinejagoServerConfig.get().enableTech.get())
            ride = MinejagoEntityTypes.SKULL_MOTORBIKE.get().create(this.level);
        else
            ride = MinejagoEntityTypes.SKULKIN_HORSE.get().create(this.level);
        if (ride == null) {
            return;
        }
        ride.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
        EventHooks.finalizeMobSpawn(ride, this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
        this.level.addFreshEntityWithPassengers(ride);
        raider.startRiding(ride);
    }

    protected void playSound(BlockPos pos) {
        float f = 13.0F;
        int i = 64;
        Collection<ServerPlayer> collection = this.raidEvent.getPlayers();
        long j = this.getRandom().nextLong();

        for (ServerPlayer serverplayer : this.level.players()) {
            Vec3 vec3 = serverplayer.position();
            Vec3 vec31 = Vec3.atCenterOf(pos);
            double d0 = Math.sqrt((vec31.x - vec3.x) * (vec31.x - vec3.x) + (vec31.z - vec3.z) * (vec31.z - vec3.z));
            double d1 = vec3.x + f / d0 * (vec31.x - vec3.x);
            double d2 = vec3.z + f / d0 * (vec31.z - vec3.z);
            if (d0 <= i || collection.contains(serverplayer)) {
                serverplayer.connection
                        .send(new ClientboundSoundPacket(MinejagoSoundEvents.SKULKIN_RAID_HORN, SoundSource.NEUTRAL, d1, serverplayer.getY(), d2, i, 1.0F, j));
            }
        }
    }

    public CompoundTag save(CompoundTag compound) {
        ResourceLocation key = MinejagoBuiltInRegistries.SKULKIN_RAID_TYPES.getKey(this.getType());
        if (key == null) throw new IllegalStateException("SkulkinRaidType " + this.getType() + " is not registered");
        compound.putString("Type", key.toString());
        compound.putInt("Id", this.id);
        compound.putInt("NumGroups", this.numGroups);
        compound.putBoolean("Started", this.started);
        compound.putBoolean("Active", this.active);
        compound.putLong("TicksActive", this.ticksActive);
        compound.putFloat("TotalHealth", this.totalHealth);
        compound.putInt("GroupsSpawned", this.groupsSpawned);
        compound.putInt("PreRaidTicks", this.raidCooldownTicks);
        compound.putInt("PostRaidTicks", this.postRaidTicks);
        compound.putString("Status", this.status.getName());
        compound.putInt("CenterX", this.center.getX());
        compound.putInt("CenterY", this.center.getY());
        compound.putInt("CenterZ", this.center.getZ());
        if (this.escapePos != null) {
            compound.putDouble("EscapeX", this.escapePos.x);
            compound.putDouble("EscapeY", this.escapePos.y);
            compound.putDouble("EscapeZ", this.escapePos.z);
        }
        return compound;
    }

    protected abstract SkulkinRaidType getType();

    enum SkulkinRaidStatus {
        ONGOING,
        VICTORY,
        DEFEAT,
        STOPPED;

        static SkulkinRaidStatus getByName(String name) {
            for (SkulkinRaidStatus status : values()) {
                if (name.equalsIgnoreCase(status.name())) {
                    return status;
                }
            }

            return ONGOING;
        }

        public String getName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}
