package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ClientboundSetFocusPacket
{
	public static final ResourceLocation ID = Minejago.modLoc("clientbound_set_focus");

	private final int focusLevel;
	private final float saturationLevel;

	public ClientboundSetFocusPacket(FriendlyByteBuf buf) {
		focusLevel = buf.readInt();
		saturationLevel = buf.readFloat();
	}

	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(focusLevel);
		buf.writeFloat(saturationLevel);
	}

	public static FriendlyByteBuf toBytes(FocusData focusData) {
		FriendlyByteBuf buf = MinejagoPacketUtils.create();

		buf.writeInt(focusData.getFocusLevel());
		buf.writeFloat(focusData.getSaturationLevel());

		return buf;
	}

	// ON CLIENT
	public void handle()
	{
		((FocusDataHolder)MinejagoClientUtils.getMainClientPlayer()).getFocusData().setFocusLevel(focusLevel);
		((FocusDataHolder)MinejagoClientUtils.getMainClientPlayer()).getFocusData().setSaturation(saturationLevel);
	}
}
