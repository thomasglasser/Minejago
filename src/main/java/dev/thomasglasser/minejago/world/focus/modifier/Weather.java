package dev.thomasglasser.minejago.world.focus.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum Weather implements StringRepresentable {
    CLEAR,
    RAIN,
    THUNDER_RAIN,
    SNOW,
    THUNDER_SNOW;

    public static final Codec<Weather> CODEC = Codec.STRING.xmap(Weather::of, Weather::getSerializedName);

    public static Weather of(String s) {
        return valueOf(s.toUpperCase());
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}
