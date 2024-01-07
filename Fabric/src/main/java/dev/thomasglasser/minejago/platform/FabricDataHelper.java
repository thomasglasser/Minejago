package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.DataHelper;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.level.storage.*;
import net.minecraft.world.entity.LivingEntity;

public class FabricDataHelper implements DataHelper {
    @Override
    public PowerData getPowerData(LivingEntity entity) {
        if (MinejagoFabricEntityComponents.POWER.isProvidedBy(entity))
            return MinejagoFabricEntityComponents.POWER.get(entity).getPowerData();
        return null;
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        if (MinejagoFabricEntityComponents.POWER.isProvidedBy(entity))
            MinejagoFabricEntityComponents.POWER.get(entity).setPowerData(data);
        DataHelper.super.setPowerData(data, entity);
    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        if (MinejagoFabricEntityComponents.SPINJITZU.isProvidedBy(entity))
            return MinejagoFabricEntityComponents.SPINJITZU.get(entity).getSpinjitzuData();
        return null;
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {
        if (MinejagoFabricEntityComponents.SPINJITZU.isProvidedBy(entity))
            MinejagoFabricEntityComponents.SPINJITZU.get(entity).setSpinjitzuData(data);
    }

    @Override
    public FocusData getFocusData(LivingEntity entity) {
        if (MinejagoFabricEntityComponents.FOCUS.isProvidedBy(entity))
            return MinejagoFabricEntityComponents.FOCUS.get(entity).getFocusData();
        return null;
    }

    @Override
    public void setFocusData(FocusData data, LivingEntity entity) {
        if (MinejagoFabricEntityComponents.FOCUS.isProvidedBy(entity))
            MinejagoFabricEntityComponents.FOCUS.get(entity).setFocusData(data);
    }
}
