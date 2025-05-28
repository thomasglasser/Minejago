package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.network.codec.ExtraStreamCodecs;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record ClientboundOpenScrollPayload(InteractionHand hand) implements ExtendedPacketPayload {
    public static final Type<ClientboundOpenScrollPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_open_scroll"));
    public static final StreamCodec<FriendlyByteBuf, ClientboundOpenScrollPayload> CODEC = StreamCodec.composite(
            ExtraStreamCodecs.forEnum(InteractionHand.class), ClientboundOpenScrollPayload::hand,
            ClientboundOpenScrollPayload::new);

    // ON CLIENT
    public void handle(@Nullable Player player) {
        ItemStack itemStack = ClientUtils.getLocalPlayer().getItemInHand(hand);
        if (itemStack.is(MinejagoItems.WRITTEN_SCROLL.get())) {
            BookViewScreen.BookAccess bookAccess = BookViewScreen.BookAccess.fromItem(itemStack);
            if (bookAccess != null) {
                MinejagoClientUtils.openScrollScreen(bookAccess);
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
