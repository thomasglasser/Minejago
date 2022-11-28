package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.client.animation.definitions.SpinjitzuAnimation;
import dev.thomasglasser.minejago.world.level.storage.ActivatedSpinjitzuCapabilityAttacher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundActivateSpinjitzuPacket {

    public ServerboundActivateSpinjitzuPacket() {
    }

    public ServerboundActivateSpinjitzuPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ctx.get().getSender().getCapability(ActivatedSpinjitzuCapabilityAttacher.ACTIVATED_SPINJITZU_CAPABILITY).ifPresent(cap -> cap.setActive(true));
        });
        ctx.get().setPacketHandled(true);
    }
}
