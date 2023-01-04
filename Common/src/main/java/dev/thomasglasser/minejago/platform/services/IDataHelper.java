package dev.thomasglasser.minejago.platform.services;

import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.world.entity.LivingEntity;

public interface IDataHelper
{
    PowerData getPowerData(LivingEntity entity);
    void setPowerData(PowerData data, LivingEntity entity);
    SpinjitzuData getSpinjitzuData(LivingEntity entity);
    void setSpinjitzuData(SpinjitzuData data, LivingEntity entity);


}
