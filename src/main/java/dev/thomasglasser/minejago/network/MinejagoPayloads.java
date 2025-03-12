package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.network.NeoForgeNetworkUtils;
import dev.thomasglasser.tommylib.api.network.PayloadInfo;
import java.util.List;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class MinejagoPayloads {
    public static List<PayloadInfo<?>> PAYLOADS = List.of(
            // Clientbound
            new PayloadInfo<>(ClientboundChangeVipDataPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundChangeVipDataPayload.CODEC),
            new PayloadInfo<>(ClientboundOpenPowerSelectionScreenPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundOpenPowerSelectionScreenPayload.CODEC),
            new PayloadInfo<>(ClientboundOpenScrollPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundOpenScrollPayload.CODEC),
            new PayloadInfo<>(ClientboundRefreshVipDataPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundRefreshVipDataPayload.CODEC),
            new PayloadInfo<>(ClientboundSetFocusPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSetFocusPayload.CODEC),
            new PayloadInfo<>(ClientboundSpawnParticlePayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSpawnParticlePayload.CODEC),
            new PayloadInfo<>(ClientboundStartMeditationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartMeditationPayload.CODEC),
            new PayloadInfo<>(ClientboundStartMegaMeditationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartMegaMeditationPayload.CODEC),
            new PayloadInfo<>(ClientboundStartScytheAnimationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartScytheAnimationPayload.CODEC),
            new PayloadInfo<>(ClientboundStartSpinjitzuPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartSpinjitzuPayload.CODEC),
            new PayloadInfo<>(ClientboundStopAnimationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStopAnimationPayload.CODEC),
            new PayloadInfo<>(ClientboundStopMeditationPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStopMeditationPayload.CODEC),
            new PayloadInfo<>(ClientboundStopSpinjitzuPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStopSpinjitzuPayload.CODEC),
            new PayloadInfo<>(ClientboundOpenDragonInventoryScreenPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundOpenDragonInventoryScreenPayload.CODEC),
            new PayloadInfo<>(ClientboundSyncPowerDataPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSyncPowerDataPayload.CODEC),
            new PayloadInfo<>(ClientboundSyncSpinjitzuDataPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSyncSpinjitzuDataPayload.CODEC),
            new PayloadInfo<>(ClientboundAddSkulkinRaidPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundAddSkulkinRaidPayload.CODEC),
            new PayloadInfo<>(ClientboundSyncSkillDataSetPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSyncSkillDataSetPayload.CODEC),
            new PayloadInfo<>(ClientboundSkillLevelUpPacket.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSkillLevelUpPacket.CODEC),
            new PayloadInfo<>(ClientboundSyncShadowSourcePayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundSyncShadowSourcePayload.CODEC),
            new PayloadInfo<>(ClientboundStartShadowFormPayload.TYPE, ExtendedPacketPayload.Direction.SERVER_TO_CLIENT, ClientboundStartShadowFormPayload.CODEC),

            // Serverbound
            new PayloadInfo<>(ServerboundChangeVipDataPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundChangeVipDataPayload.CODEC),
            new PayloadInfo<>(ServerboundFlyVehiclePayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundFlyVehiclePayload.CODEC),
            new PayloadInfo<>(ServerboundSetPowerDataPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundSetPowerDataPayload.CODEC),
            new PayloadInfo<>(ServerboundStartMeditationPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStartMeditationPayload.CODEC),
            new PayloadInfo<>(ServerboundStartSpinjitzuPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStartSpinjitzuPayload.CODEC),
            new PayloadInfo<>(ServerboundStopMeditationPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStopMeditationPayload.CODEC),
            new PayloadInfo<>(ServerboundStopSpinjitzuPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStopSpinjitzuPayload.CODEC),
            new PayloadInfo<>(ServerboundStartShadowFormPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStartShadowFormPayload.CODEC),
            new PayloadInfo<>(ServerboundStopShadowFormPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundStopShadowFormPayload.CODEC),
            new PayloadInfo<>(ServerboundSwitchDimensionPayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundSwitchDimensionPayload.CODEC),
            new PayloadInfo<>(ServerboundAdjustShadowScalePayload.TYPE, ExtendedPacketPayload.Direction.CLIENT_TO_SERVER, ServerboundAdjustShadowScalePayload.CODEC));

    public static void onRegisterPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Minejago.MOD_ID);
        MinejagoPayloads.PAYLOADS.forEach((info) -> NeoForgeNetworkUtils.register(registrar, info));
    }
}
