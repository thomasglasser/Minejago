package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ServerboundChangeVipDataPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_change_vip_data");

    private final UUID uuid;
    private final VipData vipData;

    public ServerboundChangeVipDataPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        vipData = new VipData(
                buf.readEnum(SnapshotTesterCosmeticOptions.class),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean()
        );
    }

    public void write(FriendlyByteBuf buffer) {
        buffer.writeUUID(uuid);
        buffer.writeEnum(vipData.choice());
        buffer.writeBoolean(vipData.displaySnapshot());
        buffer.writeBoolean(vipData.displayDev());
        buffer.writeBoolean(vipData.displayOgDev());
    }

    public static FriendlyByteBuf write(UUID uuid, VipData vipData) {
        FriendlyByteBuf buffer = PacketUtils.create();

        buffer.writeUUID(uuid);
        buffer.writeEnum(vipData.choice());
        buffer.writeBoolean(vipData.displaySnapshot());
        buffer.writeBoolean(vipData.displayDev());
        buffer.writeBoolean(vipData.displayOgDev());

        return buffer;
    }

    public void handle(@Nullable Player player) {
        TommyLibServices.NETWORK.sendToAllClients(ClientboundChangeVipDataPacket.ID, ClientboundChangeVipDataPacket::new, ClientboundChangeVipDataPacket.write(uuid, vipData), player.getServer());
    }

    @Override
    public Direction direction()
    {
        return Direction.CLIENT_TO_SERVER;
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
