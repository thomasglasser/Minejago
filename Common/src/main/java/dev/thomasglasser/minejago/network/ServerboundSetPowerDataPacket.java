package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowersConfig;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.tslat.smartbrainlib.util.BrainUtils;

import javax.annotation.Nullable;

public class ServerboundSetPowerDataPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_set_power_data_packet");

    private final ResourceKey<Power> power;
    private final boolean markGiven;
    @Nullable
    private final Integer wuId;

    public ServerboundSetPowerDataPacket(FriendlyByteBuf buf) {
        power = buf.readResourceKey(MinejagoRegistries.POWER);
        markGiven = buf.readBoolean();
        wuId = buf.readNullable(FriendlyByteBuf::readInt);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(power);
        buf.writeBoolean(markGiven);
        buf.writeNullable(wuId, FriendlyByteBuf::writeInt);
    }

    public static FriendlyByteBuf toBytes(ResourceKey<Power> key, boolean markGiven, @Nullable Integer wuId) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeResourceKey(key);
        buf.writeBoolean(markGiven);
        buf.writeNullable(wuId, FriendlyByteBuf::writeInt);

        return buf;
    }

    public static FriendlyByteBuf toBytes(ResourceKey<Power> key, boolean markGiven) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeResourceKey(key);
        buf.writeBoolean(markGiven);
        buf.writeNullable(null, FriendlyByteBuf::writeInt);

        return buf;
    }

    // ON SERVER
    public void handle(ServerPlayer serverPlayer)
    {
        Wu wu = null;
        if (wuId != null && serverPlayer.level().getEntity(wuId) instanceof Wu) wu = (Wu) serverPlayer.level().getEntity(wuId);
        ResourceKey<Power> oldPower = Services.DATA.getPowerData(serverPlayer).power();
        if (Services.DATA.getPowerData(serverPlayer).given() && oldPower != MinejagoPowers.NONE && MinejagoPowersConfig.DRAIN_POOL.get() && wu != null) wu.addPowersToGive(oldPower);
        if (power != MinejagoPowers.NONE && wu != null) wu.removePowersToGive(power);
        if (power == MinejagoPowers.NONE)
        {
            Services.DATA.setPowerData(new PowerData(power, true), serverPlayer);
            serverPlayer.displayClientMessage(Component.translatable(Wu.NO_POWER_GIVEN_KEY), true);
        }
        else if (wu != null)
        {
            BrainUtils.setMemory(wu, MemoryModuleType.INTERACTION_TARGET, serverPlayer);
            BrainUtils.setMemory(wu, MinejagoMemoryModuleTypes.SELECTED_POWER.get(), power);
        }
        else {
            Services.DATA.setPowerData(new PowerData(power, true), serverPlayer);
        }
    }
}
