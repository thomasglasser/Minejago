package dev.thomasglasser.minejago.world.level.storage;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class EntityPowerComponent implements PowerComponent{
    private Power power;

    public EntityPowerComponent()
    {
        power = MinejagoPowers.NONE.get();
    }

    public EntityPowerComponent(Power basePower)
    {
        power = basePower;
    }

    @Override
    public Power getPower() {
        return power;
    }

    @Override
    public void setPower(Power newPower) {
        power = newPower;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        ResourceLocation location = new ResourceLocation(tag.getString("Power"));
        power = MinejagoRegistries.POWER.get().get(location);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putString("Power", MinejagoRegistries.POWER.get().getKey(power).toString());
    }
}
