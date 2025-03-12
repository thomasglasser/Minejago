package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.level.storage.ShadowSourceData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public record ServerboundAdjustShadowScalePayload(double amount) implements ExtendedPacketPayload {
    public static final Type<ServerboundAdjustShadowScalePayload> TYPE = new Type<>(Minejago.modLoc("serverbound_adjust_shadow_scale"));
    public static final StreamCodec<ByteBuf, ServerboundAdjustShadowScalePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, ServerboundAdjustShadowScalePayload::amount,
            ServerboundAdjustShadowScalePayload::new);

    // ON SERVER
    @Override
    public void handle(Player player) {
        AttributeInstance scale = player.getAttribute(Attributes.SCALE);
        if (scale != null) {
            AttributeModifier existing = scale.getModifier(ShadowSourceData.SCALE_MODIFIER);
            double newAmount = existing == null ? amount : existing.amount() + amount;
            scale.addOrUpdateTransientModifier(new AttributeModifier(ShadowSourceData.SCALE_MODIFIER, Math.max(-1, Math.min(newAmount, 15)), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            player.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_SHADOW_FORM_NORMAL_ABILITY);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
