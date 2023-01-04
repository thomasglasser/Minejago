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

public class MinejagoMainChannel
{

    private static SimpleChannel INSTANCE;
    private static int packetID = 0;

    private static int id() { return packetID++;}

    public static void register()
    {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Minejago.MOD_ID, "main_channel"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        SimpleEntityCapabilityStatusPacket.register(net, id());
        SimpleEntityCapabilityStatusPacket.registerRetriever(PowerCapabilityAttacher.POWER_CAPABILITY_RL, PowerCapabilityAttacher::getPowerCapabilityUnwrap);
        SimpleEntityCapabilityStatusPacket.registerRetriever(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY_RL, SpinjitzuCapabilityAttacher::getSpinjitzuCapabilityUnwrap);

        // Server bound
        net.messageBuilder(ServerboundStartSpinjitzuPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder((buf) -> null)
                .encoder((packet, buf) -> {})
                .consumerMainThread((packet, context) -> packet.handle(context.get().getSender()))
                .add();
        net.messageBuilder(ServerboundChangeVipDataPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundChangeVipDataPacket::new)
                .encoder(ServerboundChangeVipDataPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle(context.get().getSender()))
                .add();

        // Client bound
        net.messageBuilder(ClientboundStartSpinjitzuPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStartSpinjitzuPacket::new)
                .encoder(ClientboundStartSpinjitzuPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        net.messageBuilder(ClientboundSpawnParticlePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundSpawnParticlePacket::new)
                .encoder(ClientboundSpawnParticlePacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        net.messageBuilder(ClientboundStopAnimationPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStopAnimationPacket::new)
                .encoder(ClientboundStopAnimationPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        net.messageBuilder(ClientboundChangeVipDataPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundChangeVipDataPacket::new)
                .encoder(ClientboundChangeVipDataPacket::toBytes)
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        net.messageBuilder(ClientboundRefreshVipDataPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder((context) -> null)
                .encoder((packet, context) -> {})
                .consumerMainThread((packet, context) -> packet.handle())
                .add();
        net.messageBuilder(ClientboundStartScytheAnimationPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundStartScytheAnimationPacket::new)
                .encoder(ClientboundStartScytheAnimationPacket::toBytes)
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

    public static <MSG> void sendToAllClients(MSG msg, ServerPlayer player)
    {
        List<ServerPlayer> players = player.getLevel().getPlayers((player1 -> true));

        for (ServerPlayer player1 : players)
        {
            sendToClient(msg, player1);
        }
    }

    public static SimpleChannel getChannel() {
        return INSTANCE;
    }
}
