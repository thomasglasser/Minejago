package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IDataHelper;
import dev.thomasglasser.minejago.world.level.storage.MinejagoFabricEntityComponents;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuComponent;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.world.entity.LivingEntity;

public class FabricDataHelper implements IDataHelper {
    @Override
    public PowerData getPowerData(LivingEntity entity) {
        return new PowerData(MinejagoFabricEntityComponents.POWER.get(entity).getPower());
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        MinejagoFabricEntityComponents.POWER.get(entity).setPower(data.power());
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
