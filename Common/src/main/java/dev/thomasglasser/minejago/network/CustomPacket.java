package dev.thomasglasser.minejago.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public interface CustomPacket extends CustomPacketPayload
{
	void handle(@Nullable Player player);

	Direction direction();

	enum Direction
	{
		SERVER_TO_CLIENT,
		CLIENT_TO_SERVER
	}
}
