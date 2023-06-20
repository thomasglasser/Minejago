package dev.thomasglasser.minejago.world.level.storage;

import dev._100media.capabilitysyncer.core.LivingEntityCapability;
import dev._100media.capabilitysyncer.network.EntityCapabilityStatusPacket;
import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;

public class PowerCapability extends LivingEntityCapability {
    @NotNull
    private ResourceKey<Power> power;
    private boolean given;

    public PowerCapability(LivingEntity entity)
    {
        this(entity, MinejagoPowers.NONE, false);
    }

    public PowerCapability(LivingEntity entity, ResourceKey<Power> power, boolean given)
    {
        super(entity);
        this.power = power;
        this.given = given;
    }

    public @NotNull ResourceKey<Power> getPower() {
        return power;
    }

    public void setPower(ResourceKey<Power> power) {
        this.power = power;
        this.updateTracking();
    }

    @Override
    public CompoundTag serializeNBT(boolean savingToDisk) {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("Power", power.location().toString());
        nbt.putBoolean("Given", given);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt, boolean readingFromDisk) {
        if (nbt.contains("Power")) {
            this.power = ResourceKey.create(MinejagoRegistries.POWER, ResourceLocation.of(nbt.getString("Power"), ':'));
        }
        if (nbt.contains("Given"))
            given = nbt.getBoolean("Given");
    }

    @Override
    public EntityCapabilityStatusPacket createUpdatePacket() {
        // Make sure to register this update packet to your network channel!
        return new SimpleEntityCapabilityStatusPacket(this.entity.getId(), PowerCapabilityAttacher.POWER_CAPABILITY_RL, this);
    }

    @Override
    public SimpleChannel getNetworkChannel() {
        // Return the network channel here
        return MinejagoMainChannel.getChannel();
    }

    public void setGiven(boolean given) {
        this.given = given;
    }

    public boolean isGiven() {
        return given;
    }
}
