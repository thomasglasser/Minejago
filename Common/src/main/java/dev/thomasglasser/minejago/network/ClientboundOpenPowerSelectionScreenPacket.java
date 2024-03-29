package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.screens.inventory.PowerSelectionScreen;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.List;

public class ClientboundOpenPowerSelectionScreenPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_open_power_selection_screen");

    private final List<ResourceKey<Power>> powers;
    @Nullable
    private final Integer wuId;

    public ClientboundOpenPowerSelectionScreenPacket(FriendlyByteBuf buf) {
        powers = buf.readList(friendlyByteBuf -> friendlyByteBuf.readResourceKey(MinejagoRegistries.POWER));
        wuId = buf.readNullable(FriendlyByteBuf::readInt);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeCollection(powers, FriendlyByteBuf::writeResourceKey);
        buf.writeNullable(wuId, FriendlyByteBuf::writeInt);
    }

    public static FriendlyByteBuf write(List<ResourceKey<Power>> powers, @Nullable Integer wuId) {
        FriendlyByteBuf buf = PacketUtils.create();

        buf.writeCollection(powers, FriendlyByteBuf::writeResourceKey);
        buf.writeNullable(wuId, FriendlyByteBuf::writeInt);

        return buf;
    }

    public static FriendlyByteBuf write(List<ResourceKey<Power>> powers) {
        FriendlyByteBuf buf = PacketUtils.create();

        buf.writeCollection(powers, FriendlyByteBuf::writeResourceKey);
        buf.writeNullable(null, FriendlyByteBuf::writeInt);

        return buf;
    }

    // ON CLIENT
    public void handle(@org.jetbrains.annotations.Nullable Player player) {
        ClientUtils.setScreen(new PowerSelectionScreen(Component.translatable("gui.power_selection.title"), powers, wuId != null && ClientUtils.getEntityById(wuId) instanceof Wu wu ? wu : null));
    }

    @Override
    public Direction direction()
    {
        return Direction.SERVER_TO_CLIENT;
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
