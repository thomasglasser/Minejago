package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ClientboundRefreshVipDataPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_refresh_vip_data");

    // ON CLIENT
    public void handle(@Nullable Player player) {
        MinejagoClientUtils.refreshVip();
    }

    @Override
    public Direction direction()
    {
        return Direction.SERVER_TO_CLIENT;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {}

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
