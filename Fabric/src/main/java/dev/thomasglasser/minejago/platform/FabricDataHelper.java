package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IDataHelper;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.world.entity.LivingEntity;

// TODO: Implement w Cardinal Components
public class FabricDataHelper implements IDataHelper {
    @Override
    public PowerData getPowerData(LivingEntity entity) {
        return null;
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {

    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        return null;
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {

    }
}
