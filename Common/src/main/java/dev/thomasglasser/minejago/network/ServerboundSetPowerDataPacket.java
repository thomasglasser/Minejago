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

public class ServerboundSetPowerDataPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_set_power_data_packet");

    private final ResourceKey<Power> power;
    private final boolean markGiven;

    public ServerboundSetPowerDataPacket(FriendlyByteBuf buf) {
        power = buf.readResourceKey(MinejagoRegistries.POWER);
        markGiven = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceKey(power);
        buf.writeBoolean(markGiven);
    }

    public static FriendlyByteBuf toBytes(ResourceKey<Power> key, boolean markGiven) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeResourceKey(key);
        buf.writeBoolean(markGiven);

        return buf;
    }

    // ON SERVER
    public void handle(ServerPlayer serverPlayer)
    {
        ResourceKey<Power> key = Services.DATA.getPowerData(serverPlayer).power();
        if (key != MinejagoPowers.NONE && markGiven && MinejagoPowersConfig.DRAIN_POOL.get()) Wu.getPowersToGive().add(key);
        Services.DATA.setPowerData(new PowerData(power, markGiven), serverPlayer);
    }
}
