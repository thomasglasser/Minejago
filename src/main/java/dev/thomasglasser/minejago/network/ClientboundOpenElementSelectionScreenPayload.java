package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundOpenElementSelectionScreenPayload(ArrayList<Holder<Element>> elements, Optional<Integer> wuId) implements ExtendedPacketPayload {
    public static final Type<ClientboundOpenElementSelectionScreenPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_open_element_selection_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundOpenElementSelectionScreenPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.holder(MinejagoRegistries.ELEMENT, Element.STREAM_CODEC)), ClientboundOpenElementSelectionScreenPayload::elements,
            ByteBufCodecs.optional(ByteBufCodecs.INT), ClientboundOpenElementSelectionScreenPayload::wuId,
            ClientboundOpenElementSelectionScreenPayload::new);

    // ON CLIENT
    public void handle(Player player) {
        MinejagoClientUtils.openElementSelectionScreen(elements, wuId);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
