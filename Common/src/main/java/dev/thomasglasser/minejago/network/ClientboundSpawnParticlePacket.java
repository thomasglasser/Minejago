package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.tommylib.api.network.CustomPacket;
import dev.thomasglasser.tommylib.api.network.PacketUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ClientboundSpawnParticlePacket implements CustomPacket
{
    public static final ResourceLocation ID = Minejago.modLoc("clientbound_spawn_particle");

    private final ParticleOptions particle;
    private final double x;
    private final double y;
    private final double z;
    private final double xSpeed;
    private final double ySpeed;
    private final double zSpeed;

    public ClientboundSpawnParticlePacket(FriendlyByteBuf buf) {
        ParticleType type = BuiltInRegistries.PARTICLE_TYPE.get(buf.readResourceLocation());
        this.particle = type.getDeserializer().fromNetwork(type, buf);
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.xSpeed = buf.readDouble();
        this.ySpeed = buf.readDouble();
        this.zSpeed = buf.readDouble();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(BuiltInRegistries.PARTICLE_TYPE.getKey(this.particle.getType()));
        this.particle.writeToNetwork(buf);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeDouble(this.xSpeed);
        buf.writeDouble(this.ySpeed);
        buf.writeDouble(this.zSpeed);
    }

    public static FriendlyByteBuf write(ParticleOptions particle, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        FriendlyByteBuf buf = PacketUtils.create();

        buf.writeResourceLocation(BuiltInRegistries.PARTICLE_TYPE.getKey(particle.getType()));
        particle.writeToNetwork(buf);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(xSpeed);
        buf.writeDouble(ySpeed);
        buf.writeDouble(zSpeed);

        return buf;
    }

    // ON CLIENT
    public void handle(@Nullable Player player) {
        MinejagoParticleUtils.addClientParticle(particle, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Override
    public Direction direction()
    {
        return Direction.SERVER_TO_CLIENT;
    }

    @Override
    public ResourceLocation id()
    {
        return ID;
    }
}
