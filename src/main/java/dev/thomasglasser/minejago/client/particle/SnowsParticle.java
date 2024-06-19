package dev.thomasglasser.minejago.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SnowsParticle extends TextureSheetParticle {

    public SnowsParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        this.lifetime = 10 + ((int)(Math.random() * 6));
    }

    public SnowsParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
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

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            SnowsParticle particle = new SnowsParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
            particle.pickSprite(this.sprites);
            return particle;
        }
    }
}
