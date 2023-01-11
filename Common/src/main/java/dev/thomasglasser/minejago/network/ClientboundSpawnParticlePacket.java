package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.minejago.util.MinejagoPacketUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ClientboundSpawnParticlePacket {
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

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(BuiltInRegistries.PARTICLE_TYPE.getKey(this.particle.getType()));
        this.particle.writeToNetwork(buf);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeDouble(this.xSpeed);
        buf.writeDouble(this.ySpeed);
        buf.writeDouble(this.zSpeed);
    }

    public static FriendlyByteBuf toBytes(ParticleOptions particle, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        FriendlyByteBuf buf = MinejagoPacketUtils.create();

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
    public void handle() {
        MinejagoParticleUtils.addClientParticle(particle, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
