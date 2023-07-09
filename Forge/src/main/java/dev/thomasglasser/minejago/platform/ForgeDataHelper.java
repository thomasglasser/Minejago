package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.DataHelper;
import dev.thomasglasser.minejago.world.level.storage.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;

public class ForgeDataHelper implements DataHelper
{

    @Override
    public PowerData getPowerData(LivingEntity entity) {
        LazyOptional<PowerCapability> capability = entity.getCapability(PowerCapabilityAttacher.POWER_CAPABILITY);

        if (capability.isPresent()) {
            PowerCapability pc = capability.orElse(new PowerCapability(entity));
            return new PowerData(pc.getPower(), pc.isGiven());
        }

        return new PowerData();
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        entity.getCapability(PowerCapabilityAttacher.POWER_CAPABILITY).ifPresent(cap ->
        {
            cap.setPower(data.power());
            cap.setGiven(data.given());
        });
        DataHelper.super.setPowerData(data, entity);
    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        LazyOptional<SpinjitzuCapability> capability = entity.getCapability(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY);

        if (capability.isPresent()) {
            SpinjitzuCapability sc = capability.orElse(new SpinjitzuCapability(entity));
            return new SpinjitzuData(sc.isUnlocked(), sc.isActive());
        }

        return new SpinjitzuData();
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {
        entity.getCapability(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY).ifPresent(cap ->
        {
            cap.setActive(data.active());
            cap.setUnlocked(data.unlocked());
        });
        DataHelper.super.setSpinjitzuData(data, entity);
    }
}
