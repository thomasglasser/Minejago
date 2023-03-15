package dev.thomasglasser.minejago.platform;

import dev.thomasglasser.minejago.platform.services.IDataHelper;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.level.storage.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;

public class ForgeDataHelper implements IDataHelper
{

    @Override
    public PowerData getPowerData(LivingEntity entity) {
        ResourceKey<Power> key = entity.getCapability(PowerCapabilityAttacher.POWER_CAPABILITY).orElse(new PowerCapability(entity)).getPower();
        Power power = MinejagoPowers.POWERS.get(entity.level.registryAccess()).get(key);
        return new PowerData(power != null ? key : MinejagoPowers.NONE);
    }

    @Override
    public void setPowerData(PowerData data, LivingEntity entity) {
        entity.getCapability(PowerCapabilityAttacher.POWER_CAPABILITY).ifPresent(cap -> cap.setPower(data.power()));
    }

    @Override
    public SpinjitzuData getSpinjitzuData(LivingEntity entity) {
        SpinjitzuCapability capability = entity.getCapability(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY).orElse(new SpinjitzuCapability(entity));
        return new SpinjitzuData(capability.isUnlocked(), capability.isActive());
    }

    @Override
    public void setSpinjitzuData(SpinjitzuData data, LivingEntity entity) {
        entity.getCapability(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY).ifPresent(cap ->
        {
            cap.setActive(data.active());
            cap.setUnlocked(data.unlocked());
        });
    }
}
