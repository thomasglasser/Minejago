package dev.thomasglasser.minejago.platform.services;

import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.world.entity.LivingEntity;

public interface DataHelper
{
    PowerData getPowerData(LivingEntity entity);

    void setPowerData(PowerData data, LivingEntity entity);

    SpinjitzuData getSpinjitzuData(LivingEntity entity);
    void setSpinjitzuData(SpinjitzuData data, LivingEntity entity);

    FocusData getFocusData(LivingEntity entity);
    void setFocusData(FocusData data, LivingEntity entity);
}
