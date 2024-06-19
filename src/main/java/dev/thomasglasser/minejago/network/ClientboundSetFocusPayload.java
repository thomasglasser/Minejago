package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundSetFocusPayload(int focusLevel, float saturationLevel) implements ExtendedPacketPayload
{
	public static final Type<ClientboundSetFocusPayload> TYPE = new Type<>(Minejago.modLoc("clientbound_set_focus"));
	public static final StreamCodec<ByteBuf, ClientboundSetFocusPayload> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, ClientboundSetFocusPayload::focusLevel,
			ByteBufCodecs.FLOAT, ClientboundSetFocusPayload::saturationLevel,
			ClientboundSetFocusPayload::new
	);

	// ON CLIENT
	public void handle(Player player)
	{
		FocusData focusData = player.getData(MinejagoAttachmentTypes.FOCUS);
		focusData.setFocusLevel(focusLevel);
		focusData.setSaturation(saturationLevel);
	}

	@Override
	public Type<? extends CustomPacketPayload> type()
	{
		return TYPE;
	}
}
