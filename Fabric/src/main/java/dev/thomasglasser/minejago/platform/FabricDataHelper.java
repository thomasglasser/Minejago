package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.DataHelper;
import dev.thomasglasser.minejago.world.level.storage.*;
import net.minecraft.world.entity.LivingEntity;

public class FabricDataHelper implements DataHelper {
    @Override
    public PowerData getPowerData(LivingEntity entity) {
        if (MinejagoFabricEntityComponents.POWER.isProvidedBy(entity))
        {
            PowerComponent component = MinejagoFabricEntityComponents.POWER.get(entity);
            return new PowerData(component.getPower(), component.isGiven());
        }
        return null;
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        if (MinejagoFabricEntityComponents.POWER.isProvidedBy(entity))
        {
            MinejagoFabricEntityComponents.POWER.get(entity).setPower(data.power());
            MinejagoFabricEntityComponents.POWER.get(entity).setGiven(data.given());
            MinejagoFabricEntityComponents.POWER.sync(entity);
        }
        DataHelper.super.setPowerData(data, entity);
    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        if (MinejagoFabricEntityComponents.SPINJITZU.isProvidedBy(entity))
        {
            SpinjitzuComponent component = MinejagoFabricEntityComponents.SPINJITZU.get(entity);
            return new SpinjitzuData(component.isUnlocked(), component.isActive());
        }
        return null;
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {
        if (MinejagoFabricEntityComponents.SPINJITZU.isProvidedBy(entity))
        {
            SpinjitzuComponent component = MinejagoFabricEntityComponents.SPINJITZU.get(entity);
            component.setActive(data.active());
            component.setUnlocked(data.unlocked());
            MinejagoFabricEntityComponents.SPINJITZU.sync(entity);
        }
        DataHelper.super.setSpinjitzuData(data, entity);
    }
}
