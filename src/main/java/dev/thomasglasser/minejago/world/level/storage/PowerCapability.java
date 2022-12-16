package dev.thomasglasser.minejago.world.level.storage;

import dev._100media.capabilitysyncer.core.PlayerCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;

public class PowerCapability extends PlayerCapability {
    private Power power;

    public PowerCapability(Player player)
    {
        super(player);
        power = MinejagoPowers.NONE.get();
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
        this.updateTracking();
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag nbt = new CompoundTag();

        nbt.put("Power", power.save());

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        if (nbt.contains("Power")) {
            this.power = new Power(nbt.getCompound("Power"));
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
