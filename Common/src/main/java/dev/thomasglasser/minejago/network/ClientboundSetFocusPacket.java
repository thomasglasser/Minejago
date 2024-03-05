package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ClientboundSetFocusPacket implements CustomPacket
{
	public static final ResourceLocation ID = Minejago.modLoc("clientbound_set_focus");

	private final int focusLevel;
	private final float saturationLevel;

	public ClientboundSetFocusPacket(FriendlyByteBuf buf) {
		focusLevel = buf.readInt();
		saturationLevel = buf.readFloat();
	}

	public void write(FriendlyByteBuf buf) {
		buf.writeInt(focusLevel);
		buf.writeFloat(saturationLevel);
	}

	@Override
	public ResourceLocation id()
	{
		return null;
	}

	public static FriendlyByteBuf write(FocusData focusData) {
		FriendlyByteBuf buf = PacketUtils.create();

		buf.writeInt(focusData.getFocusLevel());
		buf.writeFloat(focusData.getSaturationLevel());

		return buf;
	}

	// ON CLIENT
	public void handle(@Nullable Player player)
	{
		FocusData focusData = Services.DATA.getFocusData(ClientUtils.getMainClientPlayer());
		focusData.setFocusLevel(focusLevel);
		focusData.setSaturation(saturationLevel);
	}

	@Override
	public Direction direction()
	{
		return Direction.SERVER_TO_CLIENT;
	}
}
