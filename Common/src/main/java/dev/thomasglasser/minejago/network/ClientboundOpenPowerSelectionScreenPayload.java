package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.screens.inventory.PowerSelectionScreen;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record ClientboundOpenPowerSelectionScreenPayload(List<ResourceKey<Power>> powers, Optional<Integer> wuId) implements ExtendedPacketPayload
{
    public static final Type<ClientboundOpenPowerSelectionScreenPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_open_power_selection_screen"));
    public static final StreamCodec<FriendlyByteBuf, ClientboundOpenPowerSelectionScreenPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, ResourceKey.streamCodec(MinejagoRegistries.POWER)), ClientboundOpenPowerSelectionScreenPayload::powers,
            ByteBufCodecs.optional(ByteBufCodecs.INT), ClientboundOpenPowerSelectionScreenPayload::wuId,
            ClientboundOpenPowerSelectionScreenPayload::new
    );

    // ON CLIENT
    public void handle(Player player) {
        ClientUtils.setScreen(new PowerSelectionScreen(Component.translatable("gui.power_selection.title"), powers, wuId.isPresent() && ClientUtils.getEntityById(wuId.get()) instanceof Wu wu ? wu : null));
    }

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
