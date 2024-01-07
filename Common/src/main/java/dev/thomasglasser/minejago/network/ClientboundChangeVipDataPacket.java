package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.entity.layers.SnapshotTesterCosmeticOptions;
import dev.thomasglasser.minejago.client.renderer.entity.layers.VipData;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ClientboundChangeVipDataPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_change_vip_data");

    private final UUID uuid;
    private final VipData vipData;

    public ClientboundChangeVipDataPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        vipData = new VipData(
                buf.readEnum(SnapshotTesterCosmeticOptions.class),
                buf.readBoolean(),
                buf.readBoolean(),
                buf.readBoolean()
        );
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeUUID(uuid);
        buffer.writeEnum(vipData.choice());
        buffer.writeBoolean(vipData.displaySnapshot());
        buffer.writeBoolean(vipData.displayDev());
        buffer.writeBoolean(vipData.displayOgDev());
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }

    public static FriendlyByteBuf write(UUID uuid, VipData vipData) {
        return ServerboundChangeVipDataPacket.write(uuid, vipData);
    }

    // ON CLIENT
    @Override
    public void handle(@Nullable Player player)
    {
        MinejagoClientUtils.setVipData(MinejagoClientUtils.getClientPlayerByUUID(uuid), vipData);
    }

    @Override
    public Direction direction()
    {
        return Direction.SERVER_TO_CLIENT;
    }
}
