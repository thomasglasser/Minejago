package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundSkillLevelUpPacket(Skill key) implements ExtendedPacketPayload {
    public static final Type<ClientboundSkillLevelUpPacket> TYPE = new Type<>(Minejago.modLoc("skill_level_up"));
    public static final StreamCodec<ByteBuf, ClientboundSkillLevelUpPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.map(Skill::valueOf, Skill::name), ClientboundSkillLevelUpPacket::key,
            ClientboundSkillLevelUpPacket::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        player.playSound(MinejagoSoundEvents.PLAYER_SKILL_LEVELUP.get());
        player.displayClientMessage(Component.literal("+1 ").append(Component.translatable(key.toLanguageKey())).withStyle(ChatFormatting.GREEN), true);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
