package dev.thomasglasser.minejago.world.level.storage;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class LivingEntityPowerComponent implements PowerComponent {
    private ResourceKey<Power> power;
    private boolean given;

    public LivingEntityPowerComponent()
    {
        this(MinejagoPowers.NONE, false);
    }

    public LivingEntityPowerComponent(ResourceKey<Power> basePower, boolean given)
    {
        power = basePower;
        this.given = given;
    }

    @Override
    public ResourceKey<Power> getPower() {
        return power;
    }

    @Override
    public void setPower(ResourceKey<Power> newPower) {
        power = newPower;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        if (tag.contains("Power")) {
            power = ResourceKey.create(MinejagoRegistries.POWER, ResourceLocation.of(tag.getString("Power"), ':'));
        }
        if (tag.contains("Given"))
            given = tag.getBoolean("Given");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putString("Power", power.location().toString());
        tag.putBoolean("Given", given);
    }

    public boolean isGiven() {
        return given;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }
}
