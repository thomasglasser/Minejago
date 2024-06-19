package dev.thomasglasser.minejago.core.particles;

import dev.thomasglasser.minejago.network.ClientboundSpawnParticlePayload;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.tslat.effectslib.api.particle.ParticleBuilder;
import org.joml.Vector3f;

public class MinejagoParticleUtils {
    public static void renderNormalSpinjitzu(Entity entity, Vector3f color1, Vector3f color2, double height, boolean toc) {
        float scale = toc && color1 == SpinjitzuParticleOptions.ELEMENT_GOLD ? 2.2f : 2f;
        float up = 0.0f;
        for (int i = 0; i < height; i++) {
            ParticleOptions particle = new SpinjitzuParticleOptions(color1, scale);
            ParticleBuilder.forPosition(particle, entity.getX(), entity.getY() + up, entity.getZ())
                    .ignoreDistanceAndLimits()
                    .power(new Vec3(0, 1, 0))
                    .sendToAllPlayersTrackingEntity((ServerLevel) entity.level(), entity);
            scale *= toc ? 1.1f : 1.18f;
            up += 0.2f;
        }
        scale = toc && color1 == SpinjitzuParticleOptions.ELEMENT_GOLD ? 2.2f : 2f;
        up = 0.1f;
        for (int i = 0; i < height; i++) {
            ParticleOptions particle = new SpinjitzuParticleOptions(color2, scale);
            ParticleBuilder.forPosition(particle, entity.getX(), entity.getY() + up, entity.getZ())
                    .ignoreDistanceAndLimits()
                    .power(new Vec3(0, 1, 0))
                    .sendToAllPlayersTrackingEntity((ServerLevel) entity.level(), entity);
            scale *= toc ? 1.1f : 1.18f;
            up += 0.2f;
        }
    }

    public static void renderNormalSpinjitzuBorder(ParticleOptions particle, Entity entity, double height, boolean toc) {
        for (int i = 0; i < height / 4; i++) {
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSpawnParticlePayload(particle, entity.getX(), entity.getY(), entity.getZ(), 0.5, 0.5, 0.5), entity.getServer());
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSpawnParticlePayload(particle, entity.getX(), entity.getY(), entity.getZ(), -0.5, 0.5, -0.5), entity.getServer());
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSpawnParticlePayload(particle, entity.getX(), entity.getY(), entity.getZ(), 0.5, 0.5, -0.5), entity.getServer());
            TommyLibServices.NETWORK.sendToAllClients(new ClientboundSpawnParticlePayload(particle, entity.getX(), entity.getY(), entity.getZ(), -0.5, 0.5, 0.5), entity.getServer());
        }
    }

    public static void addClientParticle(ParticleOptions particle, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        Minecraft.getInstance().level.addParticle(particle, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
