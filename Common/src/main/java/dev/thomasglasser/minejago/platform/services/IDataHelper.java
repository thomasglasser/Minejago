package dev.thomasglasser.minejago.platform.services;

import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public interface IDataHelper
{
    PowerData getPowerData(LivingEntity entity);

    default void setPowerData(PowerData data, LivingEntity entity)
    {
        if (entity instanceof ServerPlayer serverPlayer)
            MinejagoCriteriaTriggers.GET_POWER.trigger(serverPlayer, data.power(), entity.level().registryAccess().registryOrThrow(MinejagoRegistries.POWER));
    }

    SpinjitzuData getSpinjitzuData(LivingEntity entity);
    default void setSpinjitzuData(SpinjitzuData data, LivingEntity entity)
    {
        if (data.active() && entity instanceof ServerPlayer serverPlayer)
            MinejagoCriteriaTriggers.DO_SPINJITZU.trigger(serverPlayer);
    }
}
