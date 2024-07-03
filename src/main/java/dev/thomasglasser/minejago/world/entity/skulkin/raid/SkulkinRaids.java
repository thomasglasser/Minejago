package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import com.google.common.collect.Maps;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.tags.MinejagoDimensionTypeTags;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Nullable;

public class SkulkinRaids extends SavedData {
    private static final String FILE_ID = "skulkin_raids";
    private final Map<Integer, SkulkinRaid> raidMap = Maps.newHashMap();
    private int nextAvailableID;
    private int tick;
    private boolean mapTaken;

    public static SavedData.Factory<SkulkinRaids> factory(ServerLevel level) {
        return new SavedData.Factory<>(SkulkinRaids::new, (compoundTag, provider) -> SkulkinRaids.load(level, compoundTag), DataFixTypes.SAVED_DATA_RAIDS);
    }

    public SkulkinRaids() {
        this.nextAvailableID = 1;
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
            if (!MinejagoServerConfig.enableSkulkinRaids) {
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

    public static boolean canJoinSkulkinRaid(MeleeCompatibleSkeletonRaider raider, SkulkinRaid raid) {
        if (raider != null && raid != null && raid.getLevel() != null) {
            return raider.isAlive() && raider.canJoinSkulkinRaid() && raider.getNoActionTime() <= 2400 && raider.level().dimensionType() == raid.getLevel().dimensionType();
        } else {
            return false;
        }
    }

    @Nullable
    public SkulkinRaid createOrExtendSkulkinRaid(ServerPlayer serverPlayer) {
        if (serverPlayer.isSpectator()) {
            return null;
        } else if (!MinejagoServerConfig.enableSkulkinRaids) {
            return null;
        } else {
            if (Holder.Reference.createIntrusive(serverPlayer.level().registryAccess().registryOrThrow(Registries.DIMENSION_TYPE).holderOwner(), serverPlayer.level().dimensionType()).is(MinejagoDimensionTypeTags.HAS_SKULKIN_RAIDS)) {
                return null;
            } else {
                SkulkinRaid raid = this.getOrCreateSkulkinRaid(serverPlayer.serverLevel(), serverPlayer.blockPosition());
                if (!raid.isStarted()) {
                    if (!this.raidMap.containsKey(raid.getId())) {
                        this.raidMap.put(raid.getId(), raid);
                        raid.setDifficultyLevel(raid.random.nextInt(raid.getMaxSkulkinsCurseLevel() + 1));
                    }
                }
                this.setDirty();
                return raid;
            }
        }
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
        ListTag listTag = tag.getList("SkulkinRaids", 10);

        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            SkulkinRaid raid = new SkulkinRaid(level, compoundTag);
            raids.raidMap.put(raid.getId(), raid);
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
}
