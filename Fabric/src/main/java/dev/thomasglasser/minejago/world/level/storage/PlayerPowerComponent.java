package dev.thomasglasser.minejago.world.level.storage;

import dev.onyxstudios.cca.api.v3.component.ComponentProvider;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.entity.PlayerComponent;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class PlayerPowerComponent implements PowerComponent, PlayerComponent, AutoSyncedComponent {
    private ResourceKey<Power> power;

    public PlayerPowerComponent()
    {
        power = MinejagoPowers.NONE;
    }

    public PlayerPowerComponent(ResourceKey<Power> basePower)
    {
        power = basePower;
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
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putString("Power", power.location().toString());
    }

    @Override
    public boolean shouldCopyForRespawn(boolean lossless, boolean keepInventory, boolean sameCharacter) {
        return true;
    }
}
