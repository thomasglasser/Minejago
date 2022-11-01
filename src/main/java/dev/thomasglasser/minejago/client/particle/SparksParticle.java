package dev.thomasglasser.minejago.client.particle;

import com.mojang.math.Vector3f;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SparksParticle extends TextureSheetParticle {

    public SparksParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        this.lifetime = 10 + ((int)(Math.random() * 6));

    }

    public SparksParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.lifetime = 10 + ((int)(Math.random() * 6));
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void setSpriteFromAge(SpriteSet pSprite) {
        if (!this.removed)
            pickSprite(pSprite);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            SparksParticle sparks = new SparksParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            sparks.pickSprite(this.sprites);
            return sparks;
        }
    }

    public static void renderPlayerSpinjitzuBorder(Entity entity, double height, boolean toc)
    {
        for (int i = 0; i < height / 4; i++) {
            entity.getLevel().addParticle(MinejagoParticleTypes.SPARKS.get(), true, entity.getX(), entity.getY(), entity.getZ(), 0.5, 0.5, 0.5);
            entity.getLevel().addParticle(MinejagoParticleTypes.SPARKS.get(), true, entity.getX(), entity.getY(), entity.getZ(), -0.5, 0.5, -0.5);
            entity.getLevel().addParticle(MinejagoParticleTypes.SPARKS.get(), true, entity.getX(), entity.getY(), entity.getZ(), 0.5, 0.5, -0.5);
            entity.getLevel().addParticle(MinejagoParticleTypes.SPARKS.get(), true, entity.getX(), entity.getY(), entity.getZ(), -0.5, 0.5, 0.5);
        }
    }
}
