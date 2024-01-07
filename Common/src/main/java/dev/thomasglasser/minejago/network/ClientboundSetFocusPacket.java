package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
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
		FriendlyByteBuf buf = MinejagoPacketUtils.create();

		buf.writeInt(focusData.getFocusLevel());
		buf.writeFloat(focusData.getSaturationLevel());

		return buf;
	}

	// ON CLIENT
	public void handle(@Nullable Player player)
	{
		((FocusDataHolder)MinejagoClientUtils.getMainClientPlayer()).getFocusData().setFocusLevel(focusLevel);
		((FocusDataHolder)MinejagoClientUtils.getMainClientPlayer()).getFocusData().setSaturation(saturationLevel);
	}

	@Override
	public Direction direction()
	{
		return Direction.SERVER_TO_CLIENT;
	}
}
