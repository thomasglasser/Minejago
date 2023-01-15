package dev.thomasglasser.minejago.util;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

public class MinejagoPacketUtils
{
    public static FriendlyByteBuf empty() {
        return new FriendlyByteBuf(Unpooled.EMPTY_BUFFER);
    }

    public static FriendlyByteBuf create() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }
}
