package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IDataHelper;
import dev.thomasglasser.minejago.world.level.storage.*;
import net.minecraft.world.entity.LivingEntity;

public class FabricDataHelper implements IDataHelper {
    @Override
    public PowerData getPowerData(LivingEntity entity) {
        PlayerPowerComponent component = (PlayerPowerComponent) MinejagoFabricEntityComponents.POWER.get(entity);
        return new PowerData(component.getPower(), component.isGiven());
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        MinejagoFabricEntityComponents.POWER.get(entity).setPower(data.power());
        MinejagoFabricEntityComponents.POWER.get(entity).setGiven(data.given());
        MinejagoFabricEntityComponents.POWER.sync(entity);
    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        SpinjitzuComponent component = MinejagoFabricEntityComponents.SPINJITZU.get(entity);
        return new SpinjitzuData(component.isUnlocked(), component.isActive());
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {
        SpinjitzuComponent component = MinejagoFabricEntityComponents.SPINJITZU.get(entity);
        component.setActive(data.active());
        component.setUnlocked(data.unlocked());
    }
}
