package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.gui.screens.inventory.PowerSelectionScreen;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class ClientboundOpenPowerSelectionScreenPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_open_power_selection_screen");

    private final List<ResourceKey<Power>> powers;
    @Nullable
    private final Integer wuId;

    public ClientboundOpenPowerSelectionScreenPacket(FriendlyByteBuf buf) {
        powers = buf.readList(friendlyByteBuf -> friendlyByteBuf.readResourceKey(MinejagoRegistries.POWER));
        wuId = buf.readNullable(FriendlyByteBuf::readInt);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeCollection(powers, FriendlyByteBuf::writeResourceKey);
        buf.writeNullable(wuId, FriendlyByteBuf::writeInt);
    }

    public static FriendlyByteBuf toBytes(List<ResourceKey<Power>> powers, @Nullable Integer wuId) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeCollection(powers, FriendlyByteBuf::writeResourceKey);
        buf.writeNullable(wuId, FriendlyByteBuf::writeInt);

        return buf;
    }

    public static FriendlyByteBuf toBytes(List<ResourceKey<Power>> powers) {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeCollection(powers, FriendlyByteBuf::writeResourceKey);
        buf.writeNullable(null, FriendlyByteBuf::writeInt);

        return buf;
    }

    // ON CLIENT
    public void handle() {
        MinejagoClientUtils.setScreen(new PowerSelectionScreen(Component.translatable("gui.power_selection.title"), powers, wuId != null && MinejagoClientUtils.getEntityById(wuId) instanceof Wu wu ? wu : null));
    }
}
