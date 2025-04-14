package dev.thomasglasser.minejago.core.particles;

import dev.thomasglasser.minejago.network.ClientboundSpawnParticlePayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;

public class MinejagoParticleUtils {
    public static void renderSpinjitzuBorder(ParticleOptions particle, Entity entity, double height, boolean toc) {
        for (int i = 0; i < height / 4; i++) {
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSpawnParticlePayload(particle, entity.getX(), entity.getY(), entity.getZ(), 0.5, 0.5, 0.5), entity.getServer());
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSpawnParticlePayload(particle, entity.getX(), entity.getY(), entity.getZ(), -0.5, 0.5, -0.5), entity.getServer());
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSpawnParticlePayload(particle, entity.getX(), entity.getY(), entity.getZ(), 0.5, 0.5, -0.5), entity.getServer());
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSpawnParticlePayload(particle, entity.getX(), entity.getY(), entity.getZ(), -0.5, 0.5, 0.5), entity.getServer());
        }
    }

    public static void addClientParticle(ParticleOptions particle, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        Minecraft.getInstance().level.addParticle(particle, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
