package dev.thomasglasser.minejago.world.level;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.Structure;
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

    private static final Map<Level, Map<ChunkPos, List<ResourceKey<Structure>>>> STRUCTURE_CACHE = new HashMap<>();

    public static boolean isStructureInRange(TagKey<Structure> structureTag, ServerLevel level, BlockPos centerPos, int range) {
        int startX = centerPos.getX() - range;
        int startZ = centerPos.getZ() - range;
        int endX = centerPos.getX() + range;
        int endZ = centerPos.getZ() + range;

        Registry<Structure> registry = level.registryAccess().lookupOrThrow(Registries.STRUCTURE);

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                ChunkPos pos = new ChunkPos(new BlockPos(x, 0, z));
                Map<ChunkPos, List<ResourceKey<Structure>>> structureCache = STRUCTURE_CACHE.computeIfAbsent(level, k -> new HashMap<>());
                if (!structureCache.containsKey(pos)) {
                    Set<Structure> structures = level.structureManager().getAllStructuresAt(pos.getWorldPosition()).keySet();
                    List<ResourceKey<Structure>> structureKeys = new ArrayList<>();
                    for (Structure structure : structures) {
                        Optional<ResourceKey<Structure>> key = registry.getResourceKey(structure);
                        key.ifPresent(structureKeys::add);
                    }
                    structureCache.put(pos, structureKeys);
                    STRUCTURE_CACHE.put(level, structureCache);
                }
                List<ResourceKey<Structure>> structures = structureCache.get(pos);
                for (Holder<Structure> structure : registry.getTagOrEmpty(structureTag)) {
                    if (structures.contains(structure.getKey())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
