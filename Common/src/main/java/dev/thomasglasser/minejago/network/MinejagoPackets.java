package dev.thomasglasser.minejago.network;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MinejagoPackets
{
	public static Map<Function<FriendlyByteBuf, ? extends CustomPacket>, Pair<ResourceLocation, CustomPacket.Direction>> PACKETS = new HashMap<>();

	public static void init()
	{
		// Clientbound
		PACKETS.put(ClientboundChangeVipDataPacket::new, Pair.of(ClientboundChangeVipDataPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundOpenPowerSelectionScreenPacket::new, Pair.of(ClientboundOpenPowerSelectionScreenPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundOpenScrollPacket::new, Pair.of(ClientboundOpenScrollPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(buf -> new ClientboundRefreshVipDataPacket(), Pair.of(ClientboundRefreshVipDataPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundSetFocusPacket::new, Pair.of(ClientboundSetFocusPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundSpawnParticlePacket::new, Pair.of(ClientboundSpawnParticlePacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStartMeditationPacket::new, Pair.of(ClientboundStartMeditationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStartMegaMeditationPacket::new, Pair.of(ClientboundStartMegaMeditationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStartScytheAnimationPacket::new, Pair.of(ClientboundStartScytheAnimationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStartSpinjitzuPacket::new, Pair.of(ClientboundStartSpinjitzuPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStopAnimationPacket::new, Pair.of(ClientboundStopAnimationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStopMeditationPacket::new, Pair.of(ClientboundStopMeditationPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));
		PACKETS.put(ClientboundStopSpinjitzuPacket::new, Pair.of(ClientboundStopSpinjitzuPacket.ID, CustomPacket.Direction.SERVER_TO_CLIENT));

		// Serverbound
		PACKETS.put(ServerboundChangeVipDataPacket::new, Pair.of(ServerboundChangeVipDataPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundFlyVehiclePacket::new, Pair.of(ServerboundFlyVehiclePacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundSetPowerDataPacket::new, Pair.of(ServerboundSetPowerDataPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(buf -> new ServerboundStartMeditationPacket(), Pair.of(ServerboundStartMeditationPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(buf -> new ServerboundStartSpinjitzuPacket(), Pair.of(ServerboundStartSpinjitzuPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(ServerboundStopMeditationPacket::new, Pair.of(ServerboundStopMeditationPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
		PACKETS.put(buf -> new ServerboundStopSpinjitzuPacket(), Pair.of(ServerboundStopSpinjitzuPacket.ID, CustomPacket.Direction.CLIENT_TO_SERVER));
	}
}
