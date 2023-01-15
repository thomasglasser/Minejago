package dev.thomasglasser.minejago.world.level.storage;

import dev._100media.capabilitysyncer.core.LivingEntityCapability;
import dev._100media.capabilitysyncer.core.PlayerCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.simple.SimpleChannel;

public class SpinjitzuCapability extends LivingEntityCapability {
    private boolean active = false;
    private boolean unlocked = false;

    public SpinjitzuCapability(LivingEntity entity)
    {
        super(entity);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        this.updateTracking();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag nbt = new CompoundTag();

        nbt.putBoolean("Active", this.active);
        nbt.putBoolean("Unlocked", this.unlocked);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        if (nbt.contains("Active")) {
            this.active = nbt.getBoolean("Active");
        }
        if (nbt.contains("Unlocked")) {
            this.unlocked = nbt.getBoolean("Unlocked");
        }
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        // Make sure to register this update packet to your network channel!
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY_RL, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        // Return the network channel here
        return MinejagoMainChannel.getChannel();
    }
}
