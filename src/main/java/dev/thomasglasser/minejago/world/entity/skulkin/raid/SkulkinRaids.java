package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import com.google.common.collect.Maps;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SkulkinRaids extends SavedData {
    private static final String FILE_ID = "skulkin_raids";
    private final Map<Integer, AbstractSkulkinRaid> raidMap = Maps.newHashMap();
    private final List<AABB> raidedAreas = new ArrayList<>();
    private int nextAvailableID;
    private int tick;
    private boolean mapTaken;

    public static SavedData.Factory<SkulkinRaids> factory(ServerLevel level) {
        return new SavedData.Factory<>(SkulkinRaids::new, (compoundTag, provider) -> SkulkinRaids.load(level, compoundTag), DataFixTypes.SAVED_DATA_RAIDS);
    }

    public SkulkinRaids() {
        this.nextAvailableID = 0;
        this.setDirty();
    }

    public AbstractSkulkinRaid get(int id) {
        return this.raidMap.get(id);
    }

    public void tick() {
        this.tick++;
        Iterator<AbstractSkulkinRaid> iterator = this.raidMap.values().iterator();

        while (iterator.hasNext()) {
            AbstractSkulkinRaid raid = iterator.next();
            if (!MinejagoServerConfig.get().enableSkulkinRaids.get()) {
                raid.setStopped();
            }

            if (raid.isStopped()) {
                iterator.remove();
                this.setDirty();
            } else {
                raid.tick();
            }
        }
    }

    public static boolean canJoinSkulkinRaid(SkulkinRaider raider, AbstractSkulkinRaid raid) {
        if (raider != null && raid != null && raid.getLevel() != null) {
            return raider.isAlive() && raider.getNoActionTime() <= 2400 && raider.level().dimensionType() == raid.getLevel().dimensionType();
        } else {
            return false;
        }
    }

    @Nullable
    public AbstractSkulkinRaid createOrExtendSkulkinRaid(ServerPlayer serverPlayer, BlockPos center, AbstractSkulkinRaid.Type type) {
        if (serverPlayer.isSpectator()) {
            return null;
        } else if (!MinejagoServerConfig.get().enableSkulkinRaids.get()) {
            return null;
        } else if (!isRaidedChunk(center)) {
            AbstractSkulkinRaid raid = this.getOrCreateSkulkinRaid(serverPlayer.serverLevel(), center, type);
            if (!raid.isStarted()) {
                if (!this.raidMap.containsKey(raid.getId())) {
                    this.raidMap.put(raid.getId(), raid);
                    raidedAreas.add(AABB.ofSize(center.getCenter(), AbstractSkulkinRaid.VALID_RAID_RADIUS, AbstractSkulkinRaid.VALID_RAID_RADIUS, AbstractSkulkinRaid.VALID_RAID_RADIUS));
                }
            }
            this.setDirty();
            return raid;
        }
        return null;
    }

    public boolean hasAnyRaidsActive() {
        return raidMap.values().stream().anyMatch(AbstractSkulkinRaid::isActive);
    }

    public void removeRaidedArea(BlockPos pos) {
        raidedAreas.removeIf(area -> area.contains(pos.getX(), pos.getY(), pos.getZ()));
    }

    private AbstractSkulkinRaid getOrCreateSkulkinRaid(ServerLevel serverLevel, BlockPos center, AbstractSkulkinRaid.Type type) {
        AbstractSkulkinRaid raid = SkulkinRaidsHolder.of(serverLevel).getSkulkinRaidAt(center);
        return raid != null ? raid : type.create(serverLevel, this.getUniqueId(), center);
    }

    public static SkulkinRaids load(ServerLevel level, CompoundTag tag) {
        SkulkinRaids raids = new SkulkinRaids();
        raids.nextAvailableID = tag.getInt("NextAvailableID");
        raids.tick = tag.getInt("Tick");
        raids.mapTaken = tag.getBoolean("MapTaken");

        ListTag raidsTag = tag.getList("SkulkinRaids", 10);
        for (int i = 0; i < raidsTag.size(); ++i) {
            CompoundTag compoundTag = raidsTag.getCompound(i);
            AbstractSkulkinRaid.Type type = AbstractSkulkinRaid.Type.of(compoundTag.getString("Type"));
            AbstractSkulkinRaid raid = type.load(level, compoundTag);
            raids.raidMap.put(raid.getId(), raid);
        }

        ListTag raidedAreasTag = tag.getList("RaidedAreas", 10);
        for (int i = 0; i < raidedAreasTag.size(); ++i) {
            CompoundTag area = raidedAreasTag.getCompound(i);
            Vec3 min = new Vec3(area.getDouble("MinX"), area.getDouble("MinY"), area.getDouble("MinZ"));
            Vec3 max = new Vec3(area.getDouble("MaxX"), area.getDouble("MaxY"), area.getDouble("MaxZ"));
            raids.raidedAreas.add(new AABB(min, max));
        }

        return raids;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putInt("NextAvailableID", this.nextAvailableID);
        compoundTag.putInt("Tick", this.tick);
        compoundTag.putBoolean("MapTaken", mapTaken);

        ListTag listTag = new ListTag();
        for (AbstractSkulkinRaid raid : this.raidMap.values()) {
            CompoundTag compoundTag2 = new CompoundTag();
            raid.save(compoundTag2);
            listTag.add(compoundTag2);
        }
        compoundTag.put("SkulkinRaids", listTag);

        ListTag raidedAreaTag = new ListTag();
        for (AABB aabb : raidedAreas) {
            CompoundTag area = new CompoundTag();
            Vec3 min = aabb.getMinPosition();
            area.putDouble("MinX", min.x);
            area.putDouble("MinY", min.y);
            area.putDouble("MinZ", min.z);
            Vec3 max = aabb.getMaxPosition();
            area.putDouble("MaxX", max.x);
            area.putDouble("MaxY", max.y);
            area.putDouble("MaxZ", max.z);
            raidedAreaTag.add(area);
        }
        compoundTag.put("RaidedAreas", raidedAreaTag);

        return compoundTag;
    }

    public static String getFileId() {
        return FILE_ID;
    }

    private int getUniqueId() {
        return ++this.nextAvailableID;
    }

    @Nullable
    public AbstractSkulkinRaid getNearbySkulkinRaid(BlockPos pos, int distance) {
        AbstractSkulkinRaid nearby = null;
        double d = distance;

        for (AbstractSkulkinRaid raid : this.raidMap.values()) {
            double e = raid.getCenter().distSqr(pos);
            if (raid.isActive() && e < d) {
                nearby = raid;
                d = e;
            }
        }

        return nearby;
    }

    public void setMapTaken() {
        this.mapTaken = true;
    }

    public boolean isMapTaken() {
        return mapTaken;
    }

    public boolean isRaidedChunk(BlockPos pos) {
        return raidedAreas.stream().anyMatch(aabb -> aabb.contains(pos.getX(), pos.getY(), pos.getZ()));
    }
}
