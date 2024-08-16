package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public record ServerboundStartSpinjitzuPayload() implements ExtendedPacketPayload {
    public static final ServerboundStartSpinjitzuPayload INSTANCE = new ServerboundStartSpinjitzuPayload();

    public static final Type<ServerboundStartSpinjitzuPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_start_spinjitzu"));
    public static final StreamCodec<ByteBuf, ServerboundStartSpinjitzuPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON SERVER
    public void handle(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            new SpinjitzuData(serverPlayer.getData(MinejagoAttachmentTypes.SPINJITZU).unlocked(), true).save(serverPlayer, true);
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundStartSpinjitzuPayload(serverPlayer.getUUID()), serverPlayer.getServer());
            AttributeInstance speed = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null && !speed.hasModifier(SpinjitzuData.SPEED_MODIFIER)) {
                speed.addTransientModifier(new AttributeModifier(SpinjitzuData.SPEED_MODIFIER, 1.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
            AttributeInstance kb = serverPlayer.getAttribute(Attributes.ATTACK_KNOCKBACK);
            if (kb != null && !kb.hasModifier(SpinjitzuData.KNOCKBACK_MODIFIER)) {
                kb.addTransientModifier(new AttributeModifier(SpinjitzuData.KNOCKBACK_MODIFIER, 1.5, AttributeModifier.Operation.ADD_VALUE));
            }
            serverPlayer.level().playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_START.get(), SoundSource.PLAYERS);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
