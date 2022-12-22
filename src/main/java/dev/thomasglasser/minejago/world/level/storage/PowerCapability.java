package dev.thomasglasser.minejago.world.level.storage;

import dev._100media.capabilitysyncer.core.PlayerCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;

public class PowerCapability extends PlayerCapability {
    @NotNull
    private Power power;

    public PowerCapability(Player player)
    {
        super(player);
        power = MinejagoPowers.NONE.get();
    }

    public @NotNull Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
        this.updateTracking();
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("Power", MinejagoRegistries.POWERS.get().getKey(power).toString());

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        if (nbt.contains("Power")) {
            Power power = MinejagoRegistries.POWERS.get().getValue(ResourceLocation.of(nbt.getString("Power"), ':'));
            this.power = power != null ? power : MinejagoPowers.NONE.get();
        }
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        // Make sure to register this update packet to your network channel!
        return new SimpleEntityCapabilityStatusPacket(this.player.getId(), PowerCapabilityAttacher.POWER_CAPABILITY_RL, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        // Return the network channel here
        return MinejagoMainChannel.getChannel();
    }
}
