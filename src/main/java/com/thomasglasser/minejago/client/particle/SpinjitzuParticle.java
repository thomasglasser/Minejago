package com.thomasglasser.minejago.client.particle;

import com.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpinjitzuParticle<T extends SpinjitzuParticleOptions> extends TextureSheetParticle {
    private final SpriteSet sprites;

    public SpinjitzuParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, T pOptions, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0.96F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = pSprites;
        this.xd *= (double) 0.1F;
        this.yd *= (double) 0.1F;
        this.zd *= (double) 0.1F;
        this.rCol = pOptions.getColor().x();
        this.gCol = pOptions.getColor().y();
        this.bCol = pOptions.getColor().z();
        this.quadSize *= 0.75F * pOptions.getScale();
        int i = (int) (8.0D / (this.random.nextDouble() * 0.8D + 0.2D));
        this.lifetime = (int) Math.max((float) i * pOptions.getScale(), 1.0F);
        this.setSpriteFromAge(pSprites);
    }

    protected float randomizeColor(float pCoordMultiplier, float pMultiplier) {
        return (this.random.nextFloat() * 0.2F + 0.8F) * pCoordMultiplier * pMultiplier;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public float getQuadSize(float pScaleFactor) {
        return this.quadSize * Mth.clamp(((float) this.age + pScaleFactor) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SpinjitzuParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SpinjitzuParticleOptions pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new SpinjitzuParticle<>(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pType, this.sprites);
        }
    }
}
