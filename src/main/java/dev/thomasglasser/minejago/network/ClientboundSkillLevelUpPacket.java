package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public record ClientboundSkillLevelUpPacket(ResourceLocation key) implements ExtendedPacketPayload {
    public static final Type<ClientboundSkillLevelUpPacket> TYPE = new Type<>(Minejago.modLoc("skill_level_up"));
    public static final StreamCodec<ByteBuf, ClientboundSkillLevelUpPacket> CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, ClientboundSkillLevelUpPacket::key,
            ClientboundSkillLevelUpPacket::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        // TODO: Level up sound
        player.playSound(SoundEvents.PLAYER_LEVELUP);
        player.displayClientMessage(Component.literal("+1 ").append(Component.translatable(key.toLanguageKey("skill"))).withStyle(ChatFormatting.GREEN), true);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
