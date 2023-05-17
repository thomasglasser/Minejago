package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.entity.npc.Wu;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowersConfig;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

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
        PowerData powerData = Services.DATA.getPowerData(serverPlayer);
        ResourceKey<Power> key = powerData.power();
        if (powerData.given() && markGiven && MinejagoPowersConfig.DRAIN_POOL.get() && wuId != null && serverPlayer.level.getEntity(wuId) instanceof Wu wu) wu.addPowersToGive(key);
        Services.DATA.setPowerData(new PowerData(power, markGiven), serverPlayer);
        if (MinejagoPowersConfig.DRAIN_POOL.get() && wuId != null && serverPlayer.level.getEntity(wuId) instanceof Wu wu) wu.removePowersToGive(power);
    }
}
