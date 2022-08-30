package dev.thomasglasser.minejago.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
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
        this.lifetime = 15;
        this.setSpriteFromAge(pSprites);
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

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        Vec3 vec3 = pRenderInfo.getPosition();
        float f = (float)(Mth.lerp((double)pPartialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp((double)pPartialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp((double)pPartialTicks, this.zo, this.z) - vec3.z());

        Quaternion quaternion = new Quaternion((float) 0.23429132, (float) -0.66716385, (float) -0.66716385, (float) -0.23429132);

        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        vector3f1.transform(quaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = this.getQuadSize(pPartialTicks);
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        float f7 = this.getU0();
        float f8 = this.getU1();
        float f5 = this.getV0();
        float f6 = this.getV1();
        int j = this.getLightColor(pPartialTicks);
        pBuffer.vertex((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).uv(f8, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).uv(f8, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).uv(f7, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();

        renderUnder(pBuffer, pRenderInfo, pPartialTicks);
    }

        public void renderUnder(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks)
        {
            Vec3 vec3 = pRenderInfo.getPosition();
            float f = (float)(Mth.lerp((double)pPartialTicks, this.xo, this.x) - vec3.x());
            float f1 = (float)(Mth.lerp((double)pPartialTicks, this.yo, this.y) - vec3.y());
            float f2 = (float)(Mth.lerp((double)pPartialTicks, this.zo, this.z) - vec3.z());

            Quaternion quaternion = new Quaternion((float) 0.243873, (float) -0.66372126, (float) 0.66372126, (float) 0.243873);

            Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
            vector3f1.transform(quaternion);
            Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
            float f4 = this.getQuadSize(pPartialTicks);

            for(int i = 0; i < 4; ++i) {
                Vector3f vector3f = avector3f[i];
                vector3f.transform(quaternion);
                vector3f.mul(f4);
                vector3f.add(f, f1, f2);
            }

            float f7 = this.getU0();
            float f8 = this.getU1();
            float f5 = this.getV0();
            float f6 = this.getV1();
            int j = this.getLightColor(pPartialTicks);
            pBuffer.vertex((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).uv(f8, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            pBuffer.vertex((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).uv(f8, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            pBuffer.vertex((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
            pBuffer.vertex((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).uv(f7, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
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

    public static void renderSpinjitzu(Entity entity, Vector3f color1, Vector3f color2, double height, boolean toc)
    {
        float scale = toc && color1 == SpinjitzuParticleOptions.GOLD ? 2.2f : 2f;
        float up = 0.0f;
        for (int i = 0; i < height; i++) {
            entity.getLevel().addParticle(new SpinjitzuParticleOptions(color1, scale), true, entity.getX(), entity.getY() + up, entity.getZ(), 0, 1, 0);
            scale *= toc ? 1.1f : 1.15f;
            up += 0.2f;
        }
        scale = toc && color1 == SpinjitzuParticleOptions.GOLD ? 2.2f : 2f;
        up = 0.1f;
        for (int i = 0; i < height; i++) {
            entity.getLevel().addParticle(new SpinjitzuParticleOptions(color2, scale), true, entity.getX(), entity.getY() + up, entity.getZ(), 0, 1, 0);
            scale *= toc ? 1.1f : 1.15f;
            up += 0.2f;
        }
    }
}
