package com.thomasglasser.minejago.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public class SpinjitzuParticleOptions implements ParticleOptions {
    public static final float MIN_SCALE = 0.01F;
    public static final float MAX_SCALE = 10.0F;

    public static final Vector3f BLUE = new Vector3f(142, 76, 3);
    public static final Vector3f BROWN = new Vector3f(157, 193, 240);
    public static final Vector3f DARK_GOLD = new Vector3f(103, 126, 242);
    public static final Vector3f LIGHT_BLUE = new Vector3f(78, 5, 2);
    public static final Vector3f ORANGE = new Vector3f(37, 144, 211);
    public static final Vector3f TAN = new Vector3f(25, 76, 138);
    public static final Vector3f WHITE = new Vector3f(7, 2, 3);
    public static final Vector3f YELLOW = new Vector3f(1, 21, 134);

    protected final Vector3f color;
    protected final float scale;

    public SpinjitzuParticleOptions(Vector3f pColor, float pScale) {
        this.color = pColor;
        this.scale = Mth.clamp(pScale, MIN_SCALE, MAX_SCALE);
    }

    public static Vector3f readVector3f(StringReader pStringInput) throws CommandSyntaxException {
        pStringInput.expect(' ');
        float f = pStringInput.readFloat();
        pStringInput.expect(' ');
        float f1 = pStringInput.readFloat();
        pStringInput.expect(' ');
        float f2 = pStringInput.readFloat();
        return new Vector3f(f, f1, f2);
    }

    public static Vector3f readVector3f(FriendlyByteBuf pBuffer) {
        return new Vector3f(pBuffer.readFloat(), pBuffer.readFloat(), pBuffer.readFloat());
    }

    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeFloat(this.color.x());
        pBuffer.writeFloat(this.color.y());
        pBuffer.writeFloat(this.color.z());
        pBuffer.writeFloat(this.scale);
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.color.x(), this.color.y(), this.color.z(), this.scale);
    }

    public Vector3f getColor() {
        return this.color;
    }

    public float getScale() {
        return this.scale;
    }

    public static final Codec<SpinjitzuParticleOptions> CODEC = RecordCodecBuilder.create((p_175793_) -> {
        return p_175793_.group(Vector3f.CODEC.fieldOf("color").forGetter(SpinjitzuParticleOptions::getColor), Codec.FLOAT.fieldOf("scale").forGetter(SpinjitzuParticleOptions::getScale)).apply(p_175793_, SpinjitzuParticleOptions::new);
    });
    public static final ParticleOptions.Deserializer<SpinjitzuParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<SpinjitzuParticleOptions>() {
        public SpinjitzuParticleOptions fromCommand(ParticleType<SpinjitzuParticleOptions> p_123689_, StringReader p_123690_) throws CommandSyntaxException {
            Vector3f vector3f = readVector3f(p_123690_);
            p_123690_.expect(' ');
            float f = p_123690_.readFloat();
            return new SpinjitzuParticleOptions(vector3f, f);
        }

        public SpinjitzuParticleOptions fromNetwork(ParticleType<SpinjitzuParticleOptions> p_123692_, FriendlyByteBuf p_123693_) {
            return new SpinjitzuParticleOptions(readVector3f(p_123693_), p_123693_.readFloat());
        }
    };

    public ParticleType<SpinjitzuParticleOptions> getType() {
        return MinejagoParticleTypes.SPINJITZU.get();
    }
}