package dev.thomasglasser.minejago.core.particles;

import dev.thomasglasser.minejago.network.ClientboundSpawnParticlePacket;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerPlayer;
import org.joml.Vector3f;

public class MinejagoParticleUtils {
    public static void renderPlayerSpinjitzu(ServerPlayer player, Vector3f color1, Vector3f color2, double height, boolean toc) {
        float scale = toc && color1 == SpinjitzuParticleOptions.ELEMENT_GOLD ? 2.2f : 2f;
        float up = 0.0f;
        for (int i = 0; i < height; i++) {
            ParticleOptions particle = new SpinjitzuParticleOptions(color1, scale);
            Services.NETWORK.sendToAllClients(new ClientboundSpawnParticlePacket(particle, player.getX(), player.getY() + up, player.getZ(), 0, 1, 0), player);
            scale *= toc ? 1.1f : 1.2f;
            up += 0.2f;
        }
        scale = toc && color1 == SpinjitzuParticleOptions.ELEMENT_GOLD ? 2.2f : 2f;
        up = 0.1f;
        for (int i = 0; i < height; i++) {
            ParticleOptions particle = new SpinjitzuParticleOptions(color2, scale);
            Services.NETWORK.sendToAllClients(new ClientboundSpawnParticlePacket(particle, player.getX(), player.getY() + up, player.getZ(), 0, 1, 0), player);
            scale *= toc ? 1.1f : 1.2f;
            up += 0.2f;
        }
    }

    public static void renderPlayerSpinjitzuBorder(ParticleOptions particle, ServerPlayer player, double height, boolean toc) {
        for (int i = 0; i < height / 4; i++) {
            Services.NETWORK.sendToAllClients(new ClientboundSpawnParticlePacket(particle, player.getX(), player.getY(), player.getZ(), 0.5, 0.5, 0.5), player);
            Services.NETWORK.sendToAllClients(new ClientboundSpawnParticlePacket(particle, player.getX(), player.getY(), player.getZ(), -0.5, 0.5, -0.5), player);
            Services.NETWORK.sendToAllClients(new ClientboundSpawnParticlePacket(particle, player.getX(), player.getY(), player.getZ(), 0.5, 0.5, -0.5), player);
            Services.NETWORK.sendToAllClients(new ClientboundSpawnParticlePacket(particle, player.getX(), player.getY(), player.getZ(), -0.5, 0.5, 0.5), player);
        }
    }

    public static void addClientParticle(ParticleOptions particle, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        Minecraft.getInstance().level.addParticle(particle, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
