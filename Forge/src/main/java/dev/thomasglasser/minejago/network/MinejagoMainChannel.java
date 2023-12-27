package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.simple.SimpleChannel;

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
        // Server bound
        INSTANCE.messageBuilder(ServerboundStartSpinjitzuPacket.class, id(), PlayNetworkDirection.PLAY_TO_SERVER)
                .decoder((buf) -> new ServerboundStartSpinjitzuPacket())
                .encoder((packet, buf) -> {})
                .consumerMainThread((packet, context) -> packet.handle(context.getSender()))
                .add();
        INSTANCE.messageBuilder(ServerboundChangeVipDataPacket.class, id(), PlayNetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundChangeVipDataPacket::new)
                .encoder(ServerboundChangeVipDataPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle(context.getSender()))
                .add();
        INSTANCE.messageBuilder(ServerboundSetPowerDataPacket.class, id(), PlayNetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundSetPowerDataPacket::new)
                .encoder(ServerboundSetPowerDataPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle(context.getSender()))
                .add();
        INSTANCE.messageBuilder(ServerboundStopSpinjitzuPacket.class, id(), PlayNetworkDirection.PLAY_TO_SERVER)
                .decoder((buf) -> new ServerboundStopSpinjitzuPacket())
                .encoder((packet, buf) -> {})
                .consumerMainThread((packet, context) -> packet.handle(context.getSender()))
                .add();
        INSTANCE.messageBuilder(ServerboundFlyVehiclePacket.class, id(), PlayNetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundFlyVehiclePacket::new)
                .encoder(ServerboundFlyVehiclePacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle(context.getSender()))
                .add();
        INSTANCE.messageBuilder(ServerboundStartMeditationPacket.class, id(), PlayNetworkDirection.PLAY_TO_SERVER)
                .decoder((buf) -> new ServerboundStartMeditationPacket())
                .encoder((packet, buf) -> {})
                .consumerMainThread((packet, context) -> packet.handle(context.getSender()))
                .add();
        INSTANCE.messageBuilder(ServerboundStopMeditationPacket.class, id(), PlayNetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundStopMeditationPacket::new)
                .encoder(ServerboundStopMeditationPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle(context.getSender()))
                .add();

        // Client bound
        INSTANCE.messageBuilder(ClientboundStartSpinjitzuPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStartSpinjitzuPacket::new)
                .encoder(ClientboundStartSpinjitzuPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundSpawnParticlePacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundSpawnParticlePacket::new)
                .encoder(ClientboundSpawnParticlePacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundStopAnimationPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStopAnimationPacket::new)
                .encoder(ClientboundStopAnimationPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundChangeVipDataPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundChangeVipDataPacket::new)
                .encoder(ClientboundChangeVipDataPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundRefreshVipDataPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder((context) -> null)
                .encoder((packet, context) -> {})
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundStartScytheAnimationPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStartScytheAnimationPacket::new)
                .encoder(ClientboundStartScytheAnimationPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundOpenPowerSelectionScreenPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundOpenPowerSelectionScreenPacket::new)
                .encoder(ClientboundOpenPowerSelectionScreenPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundOpenScrollPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundOpenScrollPacket::new)
                .encoder(ClientboundOpenScrollPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundSetFocusPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundSetFocusPacket::new)
                .encoder(ClientboundSetFocusPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundStartMeditationPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStartMeditationPacket::new)
                .encoder(ClientboundStartMeditationPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundStartMegaMeditationPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStartMegaMeditationPacket::new)
                .encoder(ClientboundStartMegaMeditationPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundStopSpinjitzuPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStopSpinjitzuPacket::new)
                .encoder(ClientboundStopSpinjitzuPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        INSTANCE.messageBuilder(ClientboundStopMeditationPacket.class, id(), PlayNetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStopMeditationPacket::new)
                .encoder(ClientboundStopMeditationPacket::toBytes)
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
}