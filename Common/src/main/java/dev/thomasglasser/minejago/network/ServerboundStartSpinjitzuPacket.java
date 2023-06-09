package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ServerboundStartSpinjitzuPacket {
    public static final ResourceLocation ID = Minejago.modLoc("serverbound_start_spinjitzu");

    // ON SERVER
    public void handle(ServerPlayer serverPlayer) {
        Services.DATA.setSpinjitzuData(new SpinjitzuData(true, true), serverPlayer);
        Services.NETWORK.sendToAllClients(ClientboundStartSpinjitzuPacket.class, ClientboundStartSpinjitzuPacket.toBytes(serverPlayer.getUUID()), serverPlayer.getServer());
        AttributeInstance speed = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && !speed.hasModifier(SpinjitzuData.SPEED_MODIFIER)) speed.addTransientModifier(SpinjitzuData.SPEED_MODIFIER);
        AttributeInstance kb = serverPlayer.getAttribute(Attributes.ATTACK_KNOCKBACK);
        if (kb != null && !kb.hasModifier(SpinjitzuData.KNOCKBACK_MODIFIER)) kb.addTransientModifier(SpinjitzuData.KNOCKBACK_MODIFIER);
        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), MinejagoSoundEvents.SPINJITZU_START.get(), SoundSource.PLAYERS);
    }
}
