package dev.thomasglasser.minejago.platform.services;

import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public interface DataHelper
{
    PowerData getPowerData(LivingEntity entity);

    default void setPowerData(PowerData data, LivingEntity entity)
    {
        if (entity instanceof ServerPlayer serverPlayer)
            MinejagoCriteriaTriggers.GOT_POWER.get().trigger(serverPlayer, data.power());
    }

    SpinjitzuData getSpinjitzuData(LivingEntity entity);
    void setSpinjitzuData(SpinjitzuData data, LivingEntity entity);
}
