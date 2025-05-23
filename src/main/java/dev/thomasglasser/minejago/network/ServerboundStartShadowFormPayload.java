package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.shadow.ShadowSource;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.level.storage.ShadowSourceData;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
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
            new ShadowSourceData(shadowSource.getUUID(), shadowSource.level().dimension()).save(serverPlayer, true);
            player.level().addFreshEntity(shadowSource);
            serverPlayer.serverLevel().getChunkSource().addRegionTicket(TicketType.PLAYER, shadowSource.chunkPosition(), 3, shadowSource.chunkPosition());
//            CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(shadowSource);
//            persistentData.putString(MinejagoEntityEvents.KEY_START_POS, shadowSource.blockPosition().toString());
//            TommyLibServices.ENTITY.mergePersistentData(shadowSource, persistentData, false);
            AttributeInstance flight = serverPlayer.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (flight != null && !flight.hasModifier(ShadowSourceData.FLIGHT_MODIFIER)) {
                flight.addTransientModifier(new AttributeModifier(ShadowSourceData.FLIGHT_MODIFIER, 1, AttributeModifier.Operation.ADD_VALUE));
            }
            serverPlayer.teleportTo(serverPlayer.getX(), serverPlayer.getY() + 2, serverPlayer.getZ());
            serverPlayer.setOnGround(false);
            serverPlayer.getAbilities().flying = true;
            serverPlayer.onUpdateAbilities();
            TommyLibServices.NETWORK.sendToAllClients(ClientboundStartShadowFormPayload.INSTANCE, serverPlayer.getServer());
            // TODO: Start sound
//            serverPlayer.level().playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_START.get(), SoundSource.PLAYERS);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
