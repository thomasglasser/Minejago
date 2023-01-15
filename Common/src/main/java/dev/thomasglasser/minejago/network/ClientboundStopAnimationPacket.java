package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class ClientboundStopAnimationPacket {
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_stop_animation");

    private final UUID uuid;

    public ClientboundStopAnimationPacket(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public static FriendlyByteBuf toBytes(UUID uuid)
    {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

        buf.writeUUID(uuid);

        return buf;
    }

    // ON CLIENT
    public void handle() {
        MinejagoClientUtils.stopAnimation(MinejagoClientUtils.getClientPlayerByUUID(uuid));
    }
}
