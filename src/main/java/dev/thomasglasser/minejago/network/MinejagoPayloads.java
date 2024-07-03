package dev.thomasglasser.minejago.network;

import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.network.PayloadInfo;
import java.util.ArrayList;
import java.util.List;

public class MinejagoPayloads {
    public static List<PayloadInfo<?>> PAYLOADS = new ArrayList<>();

    public static void init() {
        // Clientbound
        PAYLOADS.add(new PayloadInfo<>(ClientboundChangeVipDataPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundChangeVipDataPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundOpenPowerSelectionScreenPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundOpenPowerSelectionScreenPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundOpenScrollPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundOpenScrollPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundRefreshVipDataPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundRefreshVipDataPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundSetFocusPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSetFocusPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundSpawnParticlePayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSpawnParticlePayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundStartMeditationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartMeditationPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundStartMegaMeditationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartMegaMeditationPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundStartScytheAnimationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartScytheAnimationPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundStartSpinjitzuPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartSpinjitzuPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundStopAnimationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStopAnimationPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundStopMeditationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStopMeditationPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ClientboundStopSpinjitzuPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStopSpinjitzuPayload.CODEC));

        // Serverbound
        PAYLOADS.add(new PayloadInfo<>(ServerboundChangeVipDataPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundChangeVipDataPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ServerboundFlyVehiclePayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundFlyVehiclePayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ServerboundSetPowerDataPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundSetPowerDataPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ServerboundStartMeditationPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStartMeditationPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ServerboundStartSpinjitzuPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStartSpinjitzuPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ServerboundStopMeditationPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStopMeditationPayload.CODEC));
        PAYLOADS.add(new PayloadInfo<>(ServerboundStopSpinjitzuPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStopSpinjitzuPayload.CODEC));
    }
}
