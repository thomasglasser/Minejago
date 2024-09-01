package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundAddSkulkinRaidPayload(UUID uuid) implements ExtendedPacketPayload {
    public static final Type<ClientboundAddSkulkinRaidPayload> TYPE = new Type<>(Minejago.modLoc("add_skulkin_raid"));
    public static final StreamCodec<FriendlyByteBuf, ClientboundAddSkulkinRaidPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.map(UUID::fromString, UUID::toString), ClientboundAddSkulkinRaidPayload::uuid,
            ClientboundAddSkulkinRaidPayload::new);

    // On Client
    @Override
    public void handle(Player player) {
        MinejagoClientEvents.addSkulkinRaid(uuid);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
