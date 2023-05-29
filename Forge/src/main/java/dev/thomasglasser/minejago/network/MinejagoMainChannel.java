package dev.thomasglasser.minejago.network;

import dev._100media.capabilitysyncer.network.SimpleEntityCapabilityStatusPacket;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.storage.PowerCapabilityAttacher;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuCapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class MinejagoMainChannel
{

    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Minejago.MOD_ID, "main_channel"))
            .networkProtocolVersion(() -> "1.0")
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .simpleChannel();
    private static int packetID = 0;

    private static int id() { return packetID++;}

    public static void register()
    {
        SimpleEntityCapabilityStatusPacket.register(INSTANCE, id());
        SimpleEntityCapabilityStatusPacket.registerRetriever(PowerCapabilityAttacher.POWER_CAPABILITY_RL, PowerCapabilityAttacher::getPowerCapabilityUnwrap);
        SimpleEntityCapabilityStatusPacket.registerRetriever(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY_RL, SpinjitzuCapabilityAttacher::getSpinjitzuCapabilityUnwrap);

        // Server bound
        INSTANCE.messageBuilder(ServerboundStartSpinjitzuPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder((buf) -> new ServerboundStartSpinjitzuPacket())
                .encoder((packet, buf) -> {})
                .consumerMainThread((packet, context) -> packet.handle(context.get().getSender()))
                .add();
        INSTANCE.messageBuilder(ServerboundChangeVipDataPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundChangeVipDataPacket::new)
                .encoder(ServerboundChangeVipDataPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle(context.get().getSender()))
                .add();
        INSTANCE.messageBuilder(ServerboundSetPowerDataPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundSetPowerDataPacket::new)
                .encoder(ServerboundSetPowerDataPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle(context.get().getSender()))
                .add();

        // Client bound
        INSTANCE.messageBuilder(ClientboundStartSpinjitzuPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStartSpinjitzuPacket::new)
                .encoder(ClientboundStartSpinjitzuPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundSpawnParticlePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundSpawnParticlePacket::new)
                .encoder(ClientboundSpawnParticlePacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundStopAnimationPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStopAnimationPacket::new)
                .encoder(ClientboundStopAnimationPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundChangeVipDataPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundChangeVipDataPacket::new)
                .encoder(ClientboundChangeVipDataPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundRefreshVipDataPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder((context) -> null)
                .encoder((packet, context) -> {})
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundStartScytheAnimationPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStartScytheAnimationPacket::new)
                .encoder(ClientboundStartScytheAnimationPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundFailSpinjitzuPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundFailSpinjitzuPacket::new)
                .encoder(ClientboundFailSpinjitzuPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundOpenPowerSelectionScreenPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundOpenPowerSelectionScreenPacket::new)
                .encoder(ClientboundOpenPowerSelectionScreenPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundOpenScrollPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundOpenScrollPacket::new)
                .encoder(ClientboundOpenScrollPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
    }

    public static <MSG> void sendToServer(MSG msg)
    {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToClient(MSG msg, ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static <MSG> void sendToAllClients(MSG msg)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static SimpleChannel getChannel() {
        return INSTANCE;
    }
}
