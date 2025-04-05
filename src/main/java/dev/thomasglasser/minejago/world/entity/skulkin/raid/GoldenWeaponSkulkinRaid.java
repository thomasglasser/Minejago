package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.level.block.GoldenWeaponHolder;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class GoldenWeaponSkulkinRaid extends AbstractSkulkinRaid {
    public static final Component NAME_COMPONENT = Component.translatable("event.minejago.golden_weapon_skulkin_raid");

    public GoldenWeaponSkulkinRaid(ServerLevel level, int id, BlockPos center) {
        super(level, id, center, NAME_COMPONENT);
    }

    public GoldenWeaponSkulkinRaid(ServerLevel level, CompoundTag compound) {
        super(level, compound, NAME_COMPONENT);
    }

    public static @Nullable BlockPos findValidRaidCenter(ServerLevel level, BlockPos pos) {
        if (!SkulkinRaidsHolder.of(level).minejago$getSkulkinRaids().isMapTaken())
            return null;
        BlockPos center = null;
        double minDist = Double.MAX_VALUE;
        AABB toCheck = AABB.ofSize(pos.getCenter(), VALID_RAID_RADIUS, VALID_RAID_RADIUS, VALID_RAID_RADIUS);
        ChunkPos min = new ChunkPos(new BlockPos((int) toCheck.minX, (int) toCheck.minY, (int) toCheck.minZ));
        ChunkPos max = new ChunkPos(new BlockPos((int) toCheck.maxX, (int) toCheck.maxY, (int) toCheck.maxZ));
        for (int x = min.x; x <= max.x; x++) {
            for (int z = min.z; z <= max.z; z++) {
                for (Map.Entry<BlockPos, BlockEntity> entry : level.getChunk(x, z).getBlockEntities().entrySet()) {
                    BlockPos blockPos = entry.getKey();
                    BlockEntity blockEntity = entry.getValue();
                    if (blockEntity instanceof GoldenWeaponHolder goldenWeaponHolder && goldenWeaponHolder.hasGoldenWeapon()) {
                        double dist = pos.distSqr(blockPos);
                        if (dist < minDist) {
                            center = blockPos;
                            minDist = dist;
                        }
                    }
                }
            }
        }
        return center;
    }

    @Override
    public boolean isValidRaidItem(ItemStack stack) {
        return stack.is(MinejagoItemTags.GOLDEN_WEAPONS);
    }

    @Override
    public boolean isInValidRaidSearchArea(SkulkinRaider raider) {
        return getCenter().closerThan(raider.blockPosition(), CENTER_RADIUS_BUFFER);
    }

    @Override
    public boolean canExtractRaidItem(SkulkinRaider raider) {
        return getCenter().closerThan(raider.blockPosition(), 4);
    }

    @Override
    public @Nullable ItemStack extractRaidItem(SkulkinRaider raider) {
        return getLevel().getBlockEntity(getCenter()) instanceof GoldenWeaponHolder goldenWeaponHolder ? goldenWeaponHolder.extractGoldenWeapon() : null;
    }

    @Override
    protected SkulkinRaidType getType() {
        return SkulkinRaidTypes.GOLDEN_WEAPONS.get();
    }
}
