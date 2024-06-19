package dev.thomasglasser.minejago.core.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public class SpinjitzuParticleOptions implements ParticleOptions
{
    public static final float MIN_SCALE = 0.01F;

    // DEFAULT COLOR
    public static final Vector3f DEFAULT = new Vector3f(58, 58, 58);

    // ELEMENTAL COLORS
    public static final Vector3f ELEMENT_BLUE = new Vector3f(142, 76, 3);
    public static final Vector3f ELEMENT_BROWN = new Vector3f(157, 193, 240);
    public static final Vector3f ELEMENT_DARK_GOLD = new Vector3f(8, 41, 144);
    public static final Vector3f ELEMENT_LIGHT_BLUE = new Vector3f(78, 5, 2);
    public static final Vector3f ELEMENT_ORANGE = new Vector3f(37, 144, 211);
    public static final Vector3f ELEMENT_TAN = new Vector3f(25, 76, 138);
    public static final Vector3f ELEMENT_WHITE = new Vector3f(7, 2, 3);
    public static final Vector3f ELEMENT_YELLOW = new Vector3f(1, 21, 134);
    public static final Vector3f ELEMENT_GOLD = new Vector3f(4, 25, 84);

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

    public static final MapCodec<SpinjitzuParticleOptions> CODEC = RecordCodecBuilder.mapCodec((p_175793_) -> p_175793_.group(
            ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(SpinjitzuParticleOptions::getColor),
            Codec.FLOAT.fieldOf("scale").forGetter(SpinjitzuParticleOptions::getScale)
    ).apply(p_175793_, SpinjitzuParticleOptions::new));
    public static final StreamCodec<FriendlyByteBuf, SpinjitzuParticleOptions> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, SpinjitzuParticleOptions::getColor,
            ByteBufCodecs.FLOAT, SpinjitzuParticleOptions::getScale,
            SpinjitzuParticleOptions::new
    );

    protected final Vector3f color;
    protected final float scale;

    public SpinjitzuParticleOptions(Vector3f pColor, float pScale) {
        this.color = pColor;
        this.scale = Math.clamp(pScale, MIN_SCALE, Float.MAX_VALUE);
    }

    public Vector3f getColor() {
        return this.color;
    }

    public float getScale()
    {
        return scale;
    }

    public ParticleType<SpinjitzuParticleOptions> getType() {
        return MinejagoParticleTypes.SPINJITZU.get();
    }
}