package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;

public record ClientboundOpenPowerSelectionScreenPayload(List<ResourceKey<Power>> powers, Optional<Integer> wuId) implements ExtendedPacketPayload {
    public static final Type<ClientboundOpenPowerSelectionScreenPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_open_power_selection_screen"));
    public static final StreamCodec<FriendlyByteBuf, ClientboundOpenPowerSelectionScreenPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, ResourceKey.streamCodec(MinejagoRegistries.POWER)), ClientboundOpenPowerSelectionScreenPayload::powers,
            ByteBufCodecs.optional(ByteBufCodecs.INT), ClientboundOpenPowerSelectionScreenPayload::wuId,
            ClientboundOpenPowerSelectionScreenPayload::new);

    // ON CLIENT
    public void handle(Player player) {
        MinejagoClientUtils.openPowerSelectionScreen(powers, wuId);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
