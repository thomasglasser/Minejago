package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.shadow.ShadowClone;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ServerboundSummonClonePayload() implements ExtendedPacketPayload {
    public static final ServerboundSummonClonePayload INSTANCE = new ServerboundSummonClonePayload();
    public static final Type<ServerboundSummonClonePayload> TYPE = new Type<>(Minejago.modLoc("serverbound_summon_clone"));
    public static final StreamCodec<ByteBuf, ServerboundSummonClonePayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON SERVER
    @Override
    public void handle(Player player) {
        ShadowClone clone = new ShadowClone(player);
        List<UUID> clones = new ArrayList<>(player.getData(MinejagoAttachmentTypes.SHADOW_CLONES));
        clones.add(clone.getUUID());
        player.setData(MinejagoAttachmentTypes.SHADOW_CLONES, clones);
        player.level().addFreshEntity(clone);
        player.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_SHADOW_FORM_SUPER_ABILITY);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
