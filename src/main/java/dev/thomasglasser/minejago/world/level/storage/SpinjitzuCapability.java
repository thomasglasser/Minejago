package dev.thomasglasser.minejago.world.level.storage;

import dev._100media.capabilitysyncer.core.PlayerCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;

public class SpinjitzuCapability extends PlayerCapability {
    private boolean active = false;

    public SpinjitzuCapability(Player player)
    {
        super(player);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active, boolean sync) {
        this.active = active;

        if (sync)
            this.updateTracking();
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag nbt = new CompoundTag();

        nbt.putBoolean("Active", this.active);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        if (nbt.contains("Active")) {
            this.active = nbt.getBoolean("Active");
        } else {
            this.active = false;
        }
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        // Make sure to register this update packet to your network channel!
        return new SimpleEntityCapabilityStatusPacket(this.player.getId(), SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY_RL, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        // Return the network channel here
        return MinejagoMainChannel.getChannel();
    }
}
