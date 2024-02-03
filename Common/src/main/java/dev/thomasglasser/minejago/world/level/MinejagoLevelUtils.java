package dev.thomasglasser.minejago.world.level;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.world.entity.DataHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public final class MinejagoLevelUtils
{
    private MinejagoLevelUtils() {}

    public static boolean isGoldenWeaponsMapHolderNearby(LivingEntity entity, int distance)
    {
        return !entity.level().getEntitiesOfClass(Painting.class, entity.getBoundingBox().inflate(distance, distance + 3, distance), painting -> painting.getVariant().is(Minejago.modLoc( "four_weapons")) && !((DataHolder)painting).getPersistentData().getBoolean("MapTaken")).isEmpty();
    }

    public static boolean isGoldenWeaponsMapHolderNearby(Level level, BlockPos pos, int distance)
    {
        return !level.getEntitiesOfClass(Painting.class, AABB.ofSize(pos.getCenter(), distance, distance + 3, distance), painting -> painting.getVariant().is(Minejago.modLoc( "four_weapons")) && !((DataHolder)painting).getPersistentData().getBoolean("MapTaken")).isEmpty();
    }

    public static Painting getGoldenWeaponsMapHolderNearby(LivingEntity entity, int distance)
    {
        List<Painting> paintings = entity.level().getEntitiesOfClass(Painting.class, entity.getBoundingBox().inflate(distance, distance + 3, distance), painting -> painting.getVariant().is(Minejago.modLoc( "four_weapons")) && !((DataHolder)painting).getPersistentData().getBoolean("MapTaken"));
        return !paintings.isEmpty() ? paintings.get(0) : null;
    }

    public static Painting getGoldenWeaponsMapHolderNearby(Level level, BlockPos pos, int distance)
    {
        List<Painting> paintings = level.getEntitiesOfClass(Painting.class, AABB.ofSize(pos.getCenter(), distance, distance + 3, distance), painting -> painting.getVariant().is(Minejago.modLoc( "four_weapons")) && !((DataHolder)painting).getPersistentData().getBoolean("MapTaken"));
        return !paintings.isEmpty() ? paintings.get(0) : null;
    }
}
