package dev.thomasglasser.minejago.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;

import java.util.Locale;

public class SpinjitzuParticleOptions implements ParticleOptions {
    public static final float MIN_SCALE = 0.01F;

    // DEFAULT COLORS
    public static final Vector3f DEFAULT = new Vector3f(58, 58, 58);

    // ELEMENTAL COLORS
    public static final Vector3f ELEMENT_BLUE = new Vector3f(142, 76, 3);
    public static final Vector3f ELEMENT_BROWN = new Vector3f(157, 193, 240);
    public static final Vector3f ELEMENT_DARK_GOLD = new Vector3f(103, 126, 242);
    public static final Vector3f ELEMENT_LIGHT_BLUE = new Vector3f(78, 5, 2);
    public static final Vector3f ELEMENT_ORANGE = new Vector3f(37, 144, 211);
    public static final Vector3f ELEMENT_TAN = new Vector3f(25, 76, 138);
    public static final Vector3f ELEMENT_WHITE = new Vector3f(7, 2, 3);
    public static final Vector3f ELEMENT_YELLOW = new Vector3f(1, 21, 134);
    public static final Vector3f ELEMENT_GOLD = new Vector3f(); // TODO: Figure out TOC/Golden color

    // TEAM COLORS
    public static final Vector3f TEAM_BLACK = new Vector3f(0, 0, 0);
    public static final Vector3f TEAM_DARK_BLUE = new Vector3f(0, 0, 170);
    public static final Vector3f TEAM_DARK_GREEN = new Vector3f(0, 170, 0);
    public static final Vector3f TEAM_DARK_AQUA = new Vector3f(0, 170, 170);
    public static final Vector3f TEAM_DARK_RED = new Vector3f(170, 0, 0);
    public static final Vector3f TEAM_DARK_PURPLE = new Vector3f(170, 0, 170);
    public static final Vector3f TEAM_GOLD = new Vector3f(128, 170, 0);
    public static final Vector3f TEAM_GRAY = new Vector3f(85, 85, 85);
    public static final Vector3f TEAM_DARK_GRAY = new Vector3f(170, 170, 170);
    public static final Vector3f TEAM_BLUE = new Vector3f(255, 255, 85);
    public static final Vector3f TEAM_GREEN = new Vector3f(255, 85, 255);
    public static final Vector3f TEAM_AQUA = new Vector3f(255, 85, 85);
    public static final Vector3f TEAM_RED = new Vector3f(85, 255, 255);
    public static final Vector3f TEAM_LIGHT_PURPLE = new Vector3f(85, 255, 85);
    public static final Vector3f TEAM_YELLOW = new Vector3f(85, 85, 255);
    public static final Vector3f TEAM_WHITE = new Vector3f(1, 1, 1);

    protected final Vector3f color;
    protected final float scale;

    public SpinjitzuParticleOptions(Vector3f pColor, float pScale) {
        this.color = pColor;
        this.scale = Mth.clamp(pScale, MIN_SCALE, Float.MAX_VALUE);
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
        return p_175793_.group(ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(SpinjitzuParticleOptions::getColor), Codec.FLOAT.fieldOf("scale").forGetter(SpinjitzuParticleOptions::getScale)).apply(p_175793_, SpinjitzuParticleOptions::new);
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