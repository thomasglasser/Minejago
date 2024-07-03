package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.tommylib.api.network.ExtendedPacketPayload;
import dev.thomasglasser.tommylib.api.network.NetworkUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

public record ClientboundSpawnParticlePayload(ParticleOptions particleOptions, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) implements ExtendedPacketPayload {

    public static final Type<ClientboundSpawnParticlePayload> TYPE = new Type<>(Minejago.modLoc("clientbound_spawn_particle"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSpawnParticlePayload> CODEC = NetworkUtils.composite(
            ParticleTypes.STREAM_CODEC, ClientboundSpawnParticlePayload::particleOptions,
            ByteBufCodecs.DOUBLE, ClientboundSpawnParticlePayload::x,
            ByteBufCodecs.DOUBLE, ClientboundSpawnParticlePayload::y,
            ByteBufCodecs.DOUBLE, ClientboundSpawnParticlePayload::z,
            ByteBufCodecs.DOUBLE, ClientboundSpawnParticlePayload::xSpeed,
            ByteBufCodecs.DOUBLE, ClientboundSpawnParticlePayload::ySpeed,
            ByteBufCodecs.DOUBLE, ClientboundSpawnParticlePayload::zSpeed,
            ClientboundSpawnParticlePayload::new);

    // ON CLIENT
    public void handle(Player player) {
        MinejagoParticleUtils.addClientParticle(particleOptions, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
