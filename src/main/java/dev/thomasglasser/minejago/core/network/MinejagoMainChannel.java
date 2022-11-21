package dev.thomasglasser.minejago.core.network;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

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

        net.messageBuilder(MinejagoS2CPlayerAnimationPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MinejagoS2CPlayerAnimationPacket::new)
                .encoder(MinejagoS2CPlayerAnimationPacket::toBytes)
                .consumerMainThread(MinejagoS2CPlayerAnimationPacket::handle)
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
}
