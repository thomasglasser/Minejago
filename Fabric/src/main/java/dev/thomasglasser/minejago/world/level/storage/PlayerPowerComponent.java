package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class PlayerPowerComponent implements PowerComponent, PlayerComponent {
    private Power power;

    public PlayerPowerComponent()
    {
        power = MinejagoPowers.NONE.get();
    }

    public PlayerPowerComponent(Power basePower)
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
        if (tag.contains("Power")) {
            Power power = MinejagoRegistries.POWER.get().get(ResourceLocation.of(tag.getString("Power"), ':'));
            this.power = power != null ? power : MinejagoPowers.NONE.get();
            System.out.println("read " + power);
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        System.out.println("write " + MinejagoRegistries.POWER.get().getKey(power).toString());
        tag.putString("Power", MinejagoRegistries.POWER.get().getKey(power).toString());
    }

    @Override
    public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
        return true;
    }
}
