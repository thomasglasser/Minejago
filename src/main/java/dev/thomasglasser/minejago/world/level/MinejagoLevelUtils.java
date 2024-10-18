package dev.thomasglasser.minejago.world.level;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public final class MinejagoLevelUtils {
    private MinejagoLevelUtils() {}

    public static boolean isGoldenWeaponsMapHolderNearby(LivingEntity entity, int distance) {
        return !entity.level().getEntitiesOfClass(Painting.class, entity.getBoundingBox().inflate(distance, distance + 3, distance), painting -> painting.getVariant().is(Minejago.modLoc("four_weapons")) && !TommyLibServices.ENTITY.getPersistentData(painting).getBoolean("MapTaken")).isEmpty();
    }

    public static boolean isGoldenWeaponsMapHolderNearby(Level level, BlockPos pos, int distance) {
        return !level.getEntitiesOfClass(Painting.class, AABB.ofSize(pos.getCenter(), distance, distance + 3, distance), painting -> painting.getVariant().is(Minejago.modLoc("four_weapons")) && !TommyLibServices.ENTITY.getPersistentData(painting).getBoolean("MapTaken")).isEmpty();
    }

    public static Painting getGoldenWeaponsMapHolderNearby(LivingEntity entity, int distance) {
        List<Painting> paintings = entity.level().getEntitiesOfClass(Painting.class, entity.getBoundingBox().inflate(distance, distance + 3, distance), painting -> painting.getVariant().is(Minejago.modLoc("four_weapons")) && !TommyLibServices.ENTITY.getPersistentData(painting).getBoolean("MapTaken"));
        return !paintings.isEmpty() ? paintings.get(0) : null;
    }

    public static Painting getGoldenWeaponsMapHolderNearby(Level level, BlockPos pos, int distance) {
        List<Painting> paintings = level.getEntitiesOfClass(Painting.class, AABB.ofSize(pos.getCenter(), distance, distance + 3, distance), painting -> painting.getVariant().is(Minejago.modLoc("four_weapons")) && !TommyLibServices.ENTITY.getPersistentData(painting).getBoolean("MapTaken"));
        return !paintings.isEmpty() ? paintings.get(0) : null;
    }

    public static boolean isBlockInRange(Block block, Level level, BlockPos centerPos, int range) {
        int startX = centerPos.getX() - range;
        int startY = centerPos.getY() - range;
        int startZ = centerPos.getZ() - range;
        int endX = centerPos.getX() + range;
        int endY = centerPos.getY() + range;
        int endZ = centerPos.getZ() + range;

        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
