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
    private final Map<Integer, SkulkinRaid> raidMap = Maps.newHashMap();
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

    public SkulkinRaid get(int id) {
        return this.raidMap.get(id);
    }

    public void tick() {
        ++this.tick;
        Iterator<SkulkinRaid> iterator = this.raidMap.values().iterator();

        while (iterator.hasNext()) {
            SkulkinRaid raid = iterator.next();
            if (!MinejagoServerConfig.get().enableSkulkinRaids.get()) {
                raid.stop();
            }

            if (raid.isStopped()) {
                iterator.remove();
                this.setDirty();
            } else {
                raid.tick();
            }
        }

        if (this.tick % 200 == 0) {
            this.setDirty();
        }
    }

    public static boolean canJoinSkulkinRaid(SkulkinRaider raider, SkulkinRaid raid) {
        if (raider != null && raid != null && raid.getLevel() != null) {
            return raider.isAlive() && raider.getNoActionTime() <= 2400 && raider.level().dimensionType() == raid.getLevel().dimensionType();
        } else {
            return false;
        }
    }

    @Nullable
    public SkulkinRaid createOrExtendSkulkinRaid(ServerPlayer serverPlayer) {
        if (serverPlayer.isSpectator()) {
            return null;
        } else if (!MinejagoServerConfig.get().enableSkulkinRaids.get()) {
            return null;
        } else if (!isRaidedChunk(serverPlayer.blockPosition())) {
            SkulkinRaid raid = this.getOrCreateSkulkinRaid(serverPlayer.serverLevel(), serverPlayer.blockPosition());
            if (!raid.isStarted()) {
                if (!this.raidMap.containsKey(raid.getId())) {
                    this.raidMap.put(raid.getId(), raid);
                    raidedAreas.add(AABB.ofSize(serverPlayer.blockPosition().getCenter(), SkulkinRaid.VALID_RAID_RADIUS, SkulkinRaid.VALID_RAID_RADIUS, SkulkinRaid.VALID_RAID_RADIUS));
                }
            }
            this.setDirty();
            return raid;
        }
        return null;
    }

    public boolean hasAnyRaidsActive() {
        return raidMap.values().stream().anyMatch(SkulkinRaid::isActive);
    }

    public void removeRaidedArea(BlockPos pos) {
        raidedAreas.removeIf(area -> area.contains(pos.getX(), pos.getY(), pos.getZ()));
    }

    private SkulkinRaid getOrCreateSkulkinRaid(ServerLevel serverLevel, BlockPos pos) {
        SkulkinRaid raid = ((SkulkinRaidsHolder) serverLevel).getSkulkinRaidAt(pos);
        return raid != null ? raid : new SkulkinRaid(this.getUniqueId(), serverLevel, pos);
    }

    public static SkulkinRaids load(ServerLevel level, CompoundTag tag) {
        SkulkinRaids raids = new SkulkinRaids();
        raids.nextAvailableID = tag.getInt("NextAvailableID");
        raids.tick = tag.getInt("Tick");
        raids.mapTaken = tag.getBoolean("MapTaken");

        ListTag raidsTag = tag.getList("SkulkinRaids", 10);
        for (int i = 0; i < raidsTag.size(); ++i) {
            CompoundTag compoundTag = raidsTag.getCompound(i);
            SkulkinRaid raid = new SkulkinRaid(level, compoundTag);
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
        for (SkulkinRaid raid : this.raidMap.values()) {
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
    public SkulkinRaid getNearbySkulkinRaid(BlockPos pos, int distance) {
        SkulkinRaid raid = null;
        double d = distance;

        for (SkulkinRaid raid2 : this.raidMap.values()) {
            double e = raid2.getCenter().distSqr(pos);
            if (raid2.isActive() && e < d) {
                raid = raid2;
                d = e;
            }
        }

        return raid;
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
