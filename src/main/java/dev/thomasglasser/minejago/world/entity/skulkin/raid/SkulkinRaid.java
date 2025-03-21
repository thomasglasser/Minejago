package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import com.google.common.collect.Sets;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import dev.thomasglasser.minejago.network.ClientboundAddSkulkinRaidPayload;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import dev.thomasglasser.minejago.world.level.MinejagoLevelUtils;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
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
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SkulkinRaid {
    private static final int ALLOW_SPAWNING_WITHIN_PAINTING_SECONDS_THRESHOLD = 7;
    private static final int SECTION_RADIUS_FOR_FINDING_NEW_PAINTING = 2;
    private static final int PAINTING_SEARCH_RADIUS = 32;
    private static final int RAID_TIMEOUT_TICKS = 48000;
    private static final int NUM_SPAWN_ATTEMPTS = 20;
    public static final Component CURSED_BANNER_PATTERN_NAME = Component.translatable("block.minejago.cursed_banner").withStyle(ChatFormatting.GOLD);
    public static final String SKULKIN_REMAINING = "event.minejago.skulkin_raid.skulkin_remaining";
    public static final int PAINTING_RADIUS_BUFFER = 16;
    private static final int POST_RAID_TICK_LIMIT = 40;
    private static final int DEFAULT_PRE_RAID_TICKS = 300;
    public static final int MAX_NO_ACTION_TIME = 2400;
    public static final int MAX_CELEBRATION_TICKS = 600;
    private static final int OUTSIDE_RAID_BOUNDS_TIMEOUT = 30;
    public static final int TICKS_PER_DAY = 24000;
    private static final int LOW_MOB_THRESHOLD = 2;
    public static final Component RAID_NAME_COMPONENT = Component.translatable("event.minejago.skulkin_raid");
    public static final Component RAID_BAR_VICTORY_COMPONENT = Component.translatable("event.minejago.skulkin_raid.victory.full");
    public static final Component RAID_BAR_DEFEAT_COMPONENT = Component.translatable("event.minejago.skulkin_raid.defeat.full");
    public static final int VALID_RAID_RADIUS = 96;
    public static final int VALID_RAID_RADIUS_SQR = 9216;
    public static final int RAID_REMOVAL_THRESHOLD_SQR = 12544;
    private final Map<Integer, Set<SkulkinRaider>> raidersByWave = new HashMap<>();
    private long ticksActive;
    private BlockPos center;
    private final ServerLevel level;
    private boolean started;
    private final int id;
    private float totalHealth;
    private boolean active;
    private int groupsSpawned;
    private final ServerBossEvent raidEvent = new ServerBossEvent(RAID_NAME_COMPONENT, BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.NOTCHED_10);
    private int postRaidTicks;
    private int raidCooldownTicks;
    protected final RandomSource random = RandomSource.create();
    private final int numGroups;
    private SkulkinRaidStatus status;
    private int celebrationTicks;
    private Optional<BlockPos> waveSpawnPos = Optional.empty();
    private long time;
    private Vec3 escapePos;

    public SkulkinRaid(int id, ServerLevel level, BlockPos center) {
        this.id = id;
        this.level = level;
        this.active = true;
        this.raidCooldownTicks = DEFAULT_PRE_RAID_TICKS;
        this.raidEvent.setProgress(0.0F);
        this.center = center;
        this.numGroups = this.getNumGroups(level.getDifficulty());
        this.status = SkulkinRaidStatus.ONGOING;
        this.raidEvent.setPlayBossMusic(this.active);
        TommyLibServices.NETWORK.sendToAllClients(new ClientboundAddSkulkinRaidPayload(raidEvent.getId()), level.getServer());
    }

    public SkulkinRaid(ServerLevel level, CompoundTag compound) {
        this.level = level;
        this.id = compound.getInt("Id");
        this.started = compound.getBoolean("Started");
        this.active = compound.getBoolean("Active");
        this.ticksActive = compound.getLong("TicksActive");
        this.groupsSpawned = compound.getInt("GroupsSpawned");
        this.raidCooldownTicks = compound.getInt("PreRaidTicks");
        this.postRaidTicks = compound.getInt("PostRaidTicks");
        this.totalHealth = compound.getFloat("TotalHealth");
        this.center = new BlockPos(compound.getInt("CX"), compound.getInt("CY"), compound.getInt("CZ"));
        this.numGroups = compound.getInt("NumGroups");
        this.status = SkulkinRaidStatus.getByName(compound.getString("Status"));
        this.raidEvent.setPlayBossMusic(this.active);
        TommyLibServices.NETWORK.sendToAllClients(new ClientboundAddSkulkinRaidPayload(raidEvent.getId()), level.getServer());
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
        return this.status == SkulkinRaidStatus.STOPPED;
    }

    public boolean isVictory() {
        return this.status == SkulkinRaidStatus.VICTORY;
    }

    public boolean isLoss() {
        return this.status == SkulkinRaidStatus.LOSS;
    }

    public float getTotalHealth() {
        return this.totalHealth;
    }

    public Set<SkulkinRaider> getAllRaiders() {
        Set<SkulkinRaider> set = Sets.newHashSet();

        for (Set<SkulkinRaider> set1 : raidersByWave.values()) {
            set.addAll(set1);
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
            return serverPlayer.isAlive() && ((SkulkinRaidsHolder) this.level).getSkulkinRaidAt(blockPos) == this;
        };
    }

    private void updatePlayers() {
        Set<ServerPlayer> set = Sets.newHashSet(this.raidEvent.getPlayers());
        List<ServerPlayer> list = this.level.getPlayers(this.validPlayer());

        for (ServerPlayer serverPlayer : list) {
            if (!set.contains(serverPlayer)) {
                this.raidEvent.addPlayer(serverPlayer);
            }
        }

        for (ServerPlayer serverPlayer : set) {
            if (!list.contains(serverPlayer)) {
                this.raidEvent.removePlayer(serverPlayer);
            }
        }
    }

    public void stop() {
        this.active = false;
        this.raidEvent.removeAllPlayers();
        this.status = SkulkinRaidStatus.STOPPED;
        this.time = 0;
    }

    public void tick() {
        if (!this.isStopped()) {
            if (this.status == SkulkinRaidStatus.ONGOING) {
                boolean flag = this.active;
                this.active = this.level.hasChunkAt(this.center);
                if (this.level.getDifficulty() == Difficulty.PEACEFUL) {
                    this.stop();
                    return;
                }

                if (flag != this.active) {
                    this.raidEvent.setVisible(this.active);
                }

                if (!this.active) {
                    return;
                }

                if (!level.isNight()) {
                    this.level.setDayTime(18000);
                }

                if (!MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(this.level, this.center, VALID_RAID_RADIUS)) {
                    this.moveRaidCenterToNearbyPainting();
                }

                this.ticksActive++;
                if (this.ticksActive >= RAID_TIMEOUT_TICKS) {
                    this.stop();
                    return;
                }

                int i = this.getTotalRaidersAlive();
                if (i == 0 && this.hasMoreWaves()) {
                    if (this.raidCooldownTicks <= 0) {
                        if (this.raidCooldownTicks == 0 && this.groupsSpawned > 0) {
                            this.raidCooldownTicks = DEFAULT_PRE_RAID_TICKS;
                            this.raidEvent.setName(RAID_NAME_COMPONENT);
                            return;
                        }
                    } else {
                        boolean flag1 = this.waveSpawnPos.isPresent();
                        boolean flag2 = !flag1 && this.raidCooldownTicks % 5 == 0;
                        if (flag1 && !this.level.isPositionEntityTicking(this.waveSpawnPos.get())) {
                            flag2 = true;
                        }

                        if (flag2) {
                            this.waveSpawnPos = this.getValidSpawnPos();
                        }

                        if (this.raidCooldownTicks == DEFAULT_PRE_RAID_TICKS || this.raidCooldownTicks % 20 == 0) {
                            this.updatePlayers();
                        }

                        this.raidCooldownTicks--;
                        this.raidEvent.setProgress(Mth.clamp((float) (DEFAULT_PRE_RAID_TICKS - this.raidCooldownTicks) / DEFAULT_PRE_RAID_TICKS, 0.0F, 1.0F));
                    }
                }

                if (this.ticksActive % 20L == 0L) {
                    this.updatePlayers();
                    this.updateRaiders();
                    if (i > 0) {
                        if (i <= LOW_MOB_THRESHOLD) {
                            this.raidEvent
                                    .setName(RAID_NAME_COMPONENT.copy().append(" - ").append(Component.translatable(SKULKIN_REMAINING, i)));
                        } else {
                            this.raidEvent.setName(RAID_NAME_COMPONENT);
                        }
                    } else {
                        this.raidEvent.setName(RAID_NAME_COMPONENT);
                    }
                }

                boolean flag3 = false;
                int j = 0;

                while (this.shouldSpawnGroup()) {
                    BlockPos blockPos = this.waveSpawnPos.orElseGet(() -> this.findRandomSpawnPos(20));
                    if (blockPos != null) {
                        this.started = true;
                        raidEvent.getPlayers().forEach(serverPlayer -> MinejagoCriteriaTriggers.SKULKIN_RAID_STATUS_CHANGED.get().trigger(serverPlayer, SkulkinRaidTrigger.Status.STARTED));
                        this.spawnGroup(blockPos);
                        if (!flag3) {
                            this.playSound(blockPos);
                            flag3 = true;
                        }
                    } else {
                        j++;
                    }

                    if (j > NUM_SPAWN_ATTEMPTS) {
                        ((SkulkinRaidsHolder) this.level).minejago$getSkulkinRaids().removeRaidedArea(this.getCenter());
                        this.stop();
                        break;
                    }
                }

                if (this.isStarted() && !this.hasMoreWaves() && i == 0) {
                    if (this.postRaidTicks < POST_RAID_TICK_LIMIT) {
                        this.postRaidTicks++;
                    } else {
                        this.status = SkulkinRaidStatus.VICTORY;
                        raidEvent.getPlayers().forEach(serverPlayer -> MinejagoCriteriaTriggers.SKULKIN_RAID_STATUS_CHANGED.get().trigger(serverPlayer, SkulkinRaidTrigger.Status.WON));
                    }
                }

                this.setDirty();
            } else if (this.isOver()) {
                this.celebrationTicks++;
                if (this.celebrationTicks >= MAX_CELEBRATION_TICKS) {
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

    private void moveRaidCenterToNearbyPainting() {
        Stream<SectionPos> stream = SectionPos.cube(SectionPos.of(this.center), SECTION_RADIUS_FOR_FINDING_NEW_PAINTING);
        stream.filter(sectionPos -> MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(this.level, sectionPos.center(), VALID_RAID_RADIUS))
                .map(SectionPos::center)
                .min(Comparator.comparingDouble(p_37766_ -> p_37766_.distSqr(this.center)))
                .ifPresent(this::setCenter);
    }

    private Optional<BlockPos> getValidSpawnPos() {
        BlockPos blockpos = this.findRandomSpawnPos(8);
        return blockpos != null ? Optional.of(blockpos) : Optional.empty();
    }

    private boolean hasMoreWaves() {
        return !this.isFinalWave();
    }

    private boolean isFinalWave() {
        return this.getGroupsSpawned() == this.numGroups;
    }

    private void updateRaiders() {
        Iterator<Set<SkulkinRaider>> iterator = this.raidersByWave.values().iterator();
        Set<SkulkinRaider> set = Sets.newHashSet();

        while (iterator.hasNext()) {
            Set<SkulkinRaider> set1 = iterator.next();

            for (SkulkinRaider raider : set1) {
                BlockPos blockpos = raider.blockPosition();
                if (raider.isRemoved() || raider.level().dimension() != this.level.dimension() || this.center.distSqr(blockpos) >= RAID_REMOVAL_THRESHOLD_SQR) {
                    set.add(raider);
                } else if (raider.tickCount > MAX_CELEBRATION_TICKS) {
                    if (this.level.getEntity(raider.getUUID()) == null) {
                        set.add(raider);
                    }

                    if (!MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(this.level, blockpos, VALID_RAID_RADIUS) && raider.getNoActionTime() > MAX_NO_ACTION_TIME) {
                        raider.setTicksOutsideRaid(raider.getTicksOutsideRaid() + 1);
                    }

                    if (raider.getTicksOutsideRaid() >= OUTSIDE_RAID_BOUNDS_TIMEOUT) {
                        set.add(raider);
                    }
                }
            }
        }

        for (SkulkinRaider raider1 : set) {
            this.removeFromRaid(raider1, true);
        }
    }

    private void playSound(BlockPos pos) {
        float f = 13.0F;
        int i = 64;
        Collection<ServerPlayer> collection = this.raidEvent.getPlayers();
        long j = random.nextLong();

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

    private void spawnGroup(BlockPos pos) {
        int i = this.groupsSpawned + 1;
        this.totalHealth = 0.0F;
        DifficultyInstance difficultyInstance = this.level.getCurrentDifficultyAt(pos);

        int j = this.getDefaultNumSpawns(i)
                + this.getPotentialBonusSpawns(this.random, difficultyInstance);
        int k = 0;

        if (this.groupsSpawned == this.numGroups - 1)
            spawnBosses(i, pos);

        for (int l = 0; l < j; l++) {
            SkulkinRaider raider = MinejagoEntityTypes.SKULKIN.get().create(this.level);
            if (raider == null) {
                break;
            }

            this.joinRaid(i, raider, pos, false);

            if (random.nextInt(10) + difficultyInstance.getDifficulty().getId() > 5) {
                Mob ride;
                if (MinejagoServerConfig.get().enableTech.get())
                    ride = MinejagoEntityTypes.SKULL_MOTORBIKE.get().create(level);
                else
                    ride = MinejagoEntityTypes.SKULKIN_HORSE.get().create(level);
                if (ride == null)
                    break;
                ride.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                ride.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
                ride.setOnGround(true);
                this.level.addFreshEntityWithPassengers(ride);
                ride.moveTo(pos, 0.0F, 0.0F);
                raider.startRiding(ride);
            }
        }

        this.waveSpawnPos = Optional.empty();
        this.groupsSpawned++;
        this.updateBossbar();
        this.setDirty();
    }

    public void joinRaid(int wave, SkulkinRaider raider, @Nullable BlockPos pos, boolean isRecruited) {
        boolean flag = this.addWaveMob(wave, raider);
        if (flag) {
            raider.setCurrentRaid(this);
            raider.setWave(wave);
            raider.setTicksOutsideRaid(0);
            if (!isRecruited && pos != null) {
                raider.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                raider.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
                raider.setOnGround(true);
                this.level.addFreshEntityWithPassengers(raider);
            }
        }
    }

    public void updateBossbar() {
        this.raidEvent.setProgress(Mth.clamp(this.getHealthOfLivingRaiders() / this.totalHealth, 0.0F, 1.0F));
    }

    public float getHealthOfLivingRaiders() {
        float f = 0.0F;

        for (Set<SkulkinRaider> set : this.raidersByWave.values()) {
            for (SkulkinRaider raider : set) {
                f += raider.getHealth();
            }
        }

        return f;
    }

    private boolean shouldSpawnGroup() {
        return this.raidCooldownTicks == 0 && (this.groupsSpawned < this.numGroups) && this.getTotalRaidersAlive() == 0;
    }

    public int getTotalRaidersAlive() {
        return this.raidersByWave.values().stream().mapToInt(Set::size).sum();
    }

    public void removeFromRaid(SkulkinRaider raider, boolean wanderedOutOfRaid) {
        Set<SkulkinRaider> set = this.raidersByWave.get(raider.getWave());
        if (set != null) {
            boolean flag = set.remove(raider);
            if (flag) {
                if (wanderedOutOfRaid) {
                    this.totalHealth = this.totalHealth - raider.getHealth();
                }

                raider.setCurrentRaid(null);
                this.updateBossbar();
                this.setDirty();
            }
        }
    }

    private void setDirty() {
        this.level.getRaids().setDirty();
    }

    @Nullable
    private BlockPos findRandomSpawnPos(int p_37708_) {
        int i = this.raidCooldownTicks / 20;
        float f = 0.22F * (float) i - 0.24F;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        float f1 = this.level.random.nextFloat() * (float) (Math.PI * 2);

        for (int i1 = 0; i1 < p_37708_; i1++) {
            float f2 = f1 + (float) Math.PI * (float) i1 / 8.0F;
            int j = this.center.getX() + Mth.floor(Mth.cos(f2) * PAINTING_SEARCH_RADIUS * f) + this.level.random.nextInt(3) * Mth.floor(f);
            int l = this.center.getZ() + Mth.floor(Mth.sin(f2) * PAINTING_SEARCH_RADIUS * f) + this.level.random.nextInt(3) * Mth.floor(f);
            int k = this.level.getHeight(Heightmap.Types.WORLD_SURFACE, j, l);
            if (Mth.abs(k - this.center.getY()) <= VALID_RAID_RADIUS) {
                blockpos$mutableblockpos.set(j, k, l);
                if (!MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(this.level, blockpos$mutableblockpos, VALID_RAID_RADIUS) || i <= ALLOW_SPAWNING_WITHIN_PAINTING_SECONDS_THRESHOLD) {
                    int j1 = 10;
                    if (this.level
                            .hasChunksAt(
                                    blockpos$mutableblockpos.getX() - j1,
                                    blockpos$mutableblockpos.getZ() - j1,
                                    blockpos$mutableblockpos.getX() + j1,
                                    blockpos$mutableblockpos.getZ() + j1)
                            && this.level.isPositionEntityTicking(blockpos$mutableblockpos)
                            && (SpawnPlacements.getPlacementType(EntityType.RAVAGER).isSpawnPositionOk(this.level, blockpos$mutableblockpos, EntityType.RAVAGER)
                                    || this.level.getBlockState(blockpos$mutableblockpos.below()).is(Blocks.SNOW)
                                            && this.level.getBlockState(blockpos$mutableblockpos).isAir())) {
                        return blockpos$mutableblockpos;
                    }
                }
            }
        }

        return null;
    }

    private boolean addWaveMob(int wave, SkulkinRaider raider) {
        return this.addWaveMob(wave, raider, true);
    }

    public boolean addWaveMob(int wave, SkulkinRaider p_raider, boolean isRecruited) {
        this.raidersByWave.computeIfAbsent(wave, p_37746_ -> Sets.newHashSet());
        Set<SkulkinRaider> set = this.raidersByWave.get(wave);
        SkulkinRaider raider = null;

        for (SkulkinRaider raider1 : set) {
            if (raider1.getUUID().equals(p_raider.getUUID())) {
                raider = raider1;
                break;
            }
        }

        if (raider != null) {
            set.remove(raider);
            set.add(p_raider);
        }

        set.add(p_raider);
        if (isRecruited) {
            this.totalHealth = this.totalHealth + p_raider.getHealth();
        }

        this.updateBossbar();
        this.setDirty();
        return true;
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

    private int getDefaultNumSpawns(int wave) {
        return switch (wave) {
            case 1, 4, 5, 6 -> 4;
            case 2, 3 -> 3;
            case 7 -> 2;
            default -> 0;
        };
    }

    private int getPotentialBonusSpawns(RandomSource random, DifficultyInstance difficultyInstance) {
        Difficulty difficulty = difficultyInstance.getDifficulty();
        boolean flag = difficulty == Difficulty.EASY;
        boolean flag1 = difficulty == Difficulty.NORMAL;
        int i;
        if (flag) {
            i = random.nextInt(2);
        } else if (flag1) {
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
        compound.putInt("GroupsSpawned", this.groupsSpawned);
        compound.putInt("PreRaidTicks", this.raidCooldownTicks);
        compound.putInt("PostRaidTicks", this.postRaidTicks);
        compound.putFloat("TotalHealth", this.totalHealth);
        compound.putInt("NumGroups", this.numGroups);
        compound.putString("Status", this.status.getName());
        compound.putInt("CX", this.center.getX());
        compound.putInt("CY", this.center.getY());
        compound.putInt("CZ", this.center.getZ());
        return compound;
    }

    public int getNumGroups(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 3;
            case NORMAL -> 5;
            case HARD -> 7;
            default -> 0;
        };
    }

    private boolean spawnBosses(int i, BlockPos pos) {
        boolean bl = false;
        Samukai samukai = MinejagoEntityTypes.SAMUKAI.get().create(this.level);
        if (samukai == null) {
            return false;
        }
        this.joinRaid(i, samukai, pos, false);

        Nuckal nuckal = MinejagoEntityTypes.NUCKAL.get().create(this.level);
        if (nuckal != null)
            this.joinRaid(i, nuckal, pos, false);

        Kruncha kruncha = MinejagoEntityTypes.KRUNCHA.get().create(this.level);
        if (kruncha != null)
            this.joinRaid(i, kruncha, pos, false);

        if (MinejagoServerConfig.get().enableTech.get()) {
            List<SkulkinRaider> raiders = new ArrayList<>();
            if (nuckal != null) raiders.add(nuckal);
            if (kruncha != null) raiders.add(kruncha);

            SkullTruck truck = MinejagoEntityTypes.SKULL_TRUCK.get().create(level);
            if (truck == null)
                return false;
            truck.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
            truck.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
            truck.setOnGround(true);
            this.level.addFreshEntityWithPassengers(truck);
            truck.moveTo(pos, 0.0F, 0.0F);

            samukai.startRiding(truck);

            for (SkulkinRaider raider : raiders) {
                raider.startRiding(truck, true);
            }
        } else {
            List<SkulkinRaider> raiders = new ArrayList<>();
            raiders.add(samukai);
            if (nuckal != null) raiders.add(nuckal);
            if (kruncha != null) raiders.add(kruncha);

            for (SkulkinRaider raider : raiders) {
                Mob horse = MinejagoEntityTypes.SKULKIN_HORSE.get().create(level);
                if (horse == null)
                    break;
                horse.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                horse.finalizeSpawn(this.level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
                horse.setOnGround(true);
                this.level.addFreshEntityWithPassengers(horse);
                horse.moveTo(pos, 0.0F, 0.0F);
                raider.startRiding(horse);
            }
        }
        return bl;
    }

    public Vec3 getEscapePos() {
        return escapePos;
    }

    public void setEscapePos(Vec3 escapePos) {
        this.escapePos = escapePos;
    }

    public void setLoss(Vec3 escapePos) {
        this.status = SkulkinRaidStatus.LOSS;
        setEscapePos(escapePos);
    }

    enum SkulkinRaidStatus {
        ONGOING,
        VICTORY,
        LOSS,
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
