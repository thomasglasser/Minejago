package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.ShadowSource;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForgeMod;

public record ServerboundStartShadowFormPayload() implements ExtendedPacketPayload {
    public static final ServerboundStartShadowFormPayload INSTANCE = new ServerboundStartShadowFormPayload();

    public static final Type<ServerboundStartShadowFormPayload> TYPE = new Type<>(Minejago.modLoc("serverbound_start_shadow_form"));
    public static final StreamCodec<ByteBuf, ServerboundStartShadowFormPayload> CODEC = StreamCodec.unit(INSTANCE);

    // ON SERVER
    public void handle(Player player) {
        if (player instanceof ServerPlayer serverPlayer && serverPlayer.getData(MinejagoAttachmentTypes.FOCUS).getFocusLevel() >= FocusConstants.SHADOW_FORM_LEVEL) {
            ShadowSource shadowSource = new ShadowSource(serverPlayer);
            player.setData(MinejagoAttachmentTypes.SHADOW_SOURCE, Optional.of(shadowSource.getUUID()));
            AttributeInstance flight = serverPlayer.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (flight != null && !flight.hasModifier(MinejagoEntityEvents.SHADOW_FLIGHT_MODIFIER)) {
                flight.addTransientModifier(new AttributeModifier(MinejagoEntityEvents.SHADOW_FLIGHT_MODIFIER, 1, AttributeModifier.Operation.ADD_VALUE));
            }
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSyncShadowSourcePayload(serverPlayer.getId(), Optional.of(shadowSource.getUUID())), serverPlayer.getServer());
            player.level().addFreshEntity(shadowSource);
            CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(shadowSource);
            persistentData.putString(MinejagoEntityEvents.KEY_START_POS, shadowSource.blockPosition().toString());
            TommyLibServices.ENTITY.setPersistentData(shadowSource, persistentData, false);
            // TODO: Start sound
//            serverPlayer.level().playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_START.get(), SoundSource.PLAYERS);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
