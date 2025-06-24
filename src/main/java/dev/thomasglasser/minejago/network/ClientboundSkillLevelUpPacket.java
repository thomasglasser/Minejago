package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.util.TommyLibExtraStreamCodecs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundSkillLevelUpPacket(Skill skill) implements ExtendedPacketPayload {
    public static final Type<ClientboundSkillLevelUpPacket> TYPE = new Type<>(Minejago.modLoc("skill_level_up"));
    public static final StreamCodec<FriendlyByteBuf, ClientboundSkillLevelUpPacket> CODEC = StreamCodec.composite(
            TommyLibExtraStreamCodecs.forEnum(Skill.class), ClientboundSkillLevelUpPacket::skill,
            ClientboundSkillLevelUpPacket::new);

    // ON CLIENT
    @Override
    public void handle(Player player) {
        player.playSound(MinejagoSoundEvents.PLAYER_SKILL_LEVELUP.get());
        player.displayClientMessage(Component.literal("+1 ").append(skill.displayName()).withStyle(ChatFormatting.GREEN), true);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
