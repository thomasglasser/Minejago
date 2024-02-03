package dev.thomasglasser.minejago.network;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class MinejagoPackets
{
	public static Map<Class<? extends CustomPacket>, Pair<ResourceLocation, CustomPacket.Direction>> PACKETS = new HashMap<>();

	public static void init()
	{
		// Clientbound
		PACKETS.put(ClientboundChangeVipDataPacket.class, Pair.of(ClientboundChangeVipDataPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundOpenPowerSelectionScreenPacket.class, Pair.of(ClientboundOpenPowerSelectionScreenPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundOpenScrollPacket.class, Pair.of(ClientboundOpenScrollPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundRefreshVipDataPacket.class, Pair.of(ClientboundRefreshVipDataPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundSetFocusPacket.class, Pair.of(ClientboundSetFocusPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundSpawnParticlePacket.class, Pair.of(ClientboundSpawnParticlePacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStartMeditationPacket.class, Pair.of(ClientboundStartMeditationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStartMegaMeditationPacket.class, Pair.of(ClientboundStartMegaMeditationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStartScytheAnimationPacket.class, Pair.of(ClientboundStartScytheAnimationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStartSpinjitzuPacket.class, Pair.of(ClientboundStartSpinjitzuPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStopAnimationPacket.class, Pair.of(ClientboundStopAnimationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStopMeditationPacket.class, Pair.of(ClientboundStopMeditationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStopSpinjitzuPacket.class, Pair.of(ClientboundStopSpinjitzuPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));

		// Serverbound
		PACKETS.put(ServerboundChangeVipDataPacket.class, Pair.of(ServerboundChangeVipDataPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundFlyVehiclePacket.class, Pair.of(ServerboundFlyVehiclePacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundSetPowerDataPacket.class, Pair.of(ServerboundSetPowerDataPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundStartMeditationPacket.class, Pair.of(ServerboundStartMeditationPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundStartSpinjitzuPacket.class, Pair.of(ServerboundStartSpinjitzuPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundStopMeditationPacket.class, Pair.of(ServerboundStopMeditationPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundStopSpinjitzuPacket.class, Pair.of(ServerboundStopSpinjitzuPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
	}
}
