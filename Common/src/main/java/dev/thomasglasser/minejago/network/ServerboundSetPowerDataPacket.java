package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundSetPowerDataPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_set_power_data_packet");

    private final ResourceKey<Power> power;

    public ServerboundSetPowerDataPacket(FriendlyByteBuf buf) {
        power = buf.readResourceKey(MinejagoRegistries.POWER);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(power);
    }

    public static FriendlyByteBuf toBytes(ResourceKey<Power> key) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeResourceKey(key);

        return buf;
    }

    // ON SERVER
    public void handle(ServerPlayer serverPlayer)
    {
        Services.DATA.setPowerData(new PowerData(power), serverPlayer);
    }
}
