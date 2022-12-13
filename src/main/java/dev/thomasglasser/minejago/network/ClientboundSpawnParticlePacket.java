package dev.thomasglasser.minejago.network;

import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ClientboundSpawnParticlePacket {
    ParticleOptions particle;
    double x;
    double y;
    double z;
    double xSpeed;
    double ySpeed;
    double zSpeed;

    public ClientboundSpawnParticlePacket(ParticleOptions particle, double pPosX, double pPosY, double pPosZ, double xSpeed, double ySpeed, double zSpeed) {
        this.particle = particle;
        x = pPosX;
        y = pPosY;
        z = pPosZ;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public ClientboundSpawnParticlePacket(FriendlyByteBuf buf) {
        ParticleType<?> particletype = buf.readRegistryIdUnsafe(ForgeRegistries.PARTICLE_TYPES);
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.particle = this.readParticle(buf, particletype);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeRegistryIdUnsafe(ForgeRegistries.PARTICLE_TYPES, this.particle.getType());
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        this.particle.writeToNetwork(buf);
    }

    private <T extends ParticleOptions> T readParticle(FriendlyByteBuf pBuffer, ParticleType<T> pParticleType) {
        return pParticleType.getDeserializer().fromNetwork(pParticleType, pBuffer);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            MinejagoParticleUtils.addClientParticle(particle, x, y, z, 0, 1, 0);
        });
        ctx.get().setPacketHandled(true);
    }
}
