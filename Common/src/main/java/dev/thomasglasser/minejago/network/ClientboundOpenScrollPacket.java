package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ClientboundOpenScrollPacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_open_scroll");

    private InteractionHand hand;

    public ClientboundOpenScrollPacket(FriendlyByteBuf buf) {
        hand = buf.readEnum(InteractionHand.class);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(hand);
    }

    public static FriendlyByteBuf write(InteractionHand hand) {
        FriendlyByteBuf buf = PacketUtils.create();

        buf.writeEnum(hand);

        return buf;
    }

    // ON CLIENT
    public void handle(@Nullable Player player)
    {
        ItemStack itemStack = ClientUtils.getMainClientPlayer().getItemInHand(hand);
        if (itemStack.is(MinejagoItems.WRITTEN_SCROLL.get())) {
            ClientUtils.setScreen(new ScrollViewScreen(new BookViewScreen.WrittenBookAccess(itemStack)));
        }
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
