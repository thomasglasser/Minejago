package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;

public class ClientboundSpawnParticlePacket {
    private ParticleOptions particle;
    private double x;
    private double y;
    private double z;
    private double xSpeed;
    private double ySpeed;
    private double zSpeed;

    public ClientboundSpawnParticlePacket(ParticleOptions particle, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        this.particle = particle;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

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

    // ON CLIENT
    public void handle() {
        MinejagoParticleUtils.addClientParticle(particle, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
