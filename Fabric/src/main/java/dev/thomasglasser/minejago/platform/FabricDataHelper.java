package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IDataHelper;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.level.storage.MinejagoFabricEntityComponents;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuComponent;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;

public class FabricDataHelper implements IDataHelper {
    @Override
    public PowerData getPowerData(LivingEntity entity) {
        ResourceKey<Power> key = MinejagoFabricEntityComponents.POWER.get(entity).getPower();
        Power power = MinejagoPowers.POWERS.get(entity.level.registryAccess()).get(key);
        return new PowerData(power != null ? key : MinejagoPowers.NONE);
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
