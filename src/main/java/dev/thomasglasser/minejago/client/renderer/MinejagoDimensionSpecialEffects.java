package dev.thomasglasser.minejago.client.renderer;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

public class MinejagoDimensionSpecialEffects {
    public static final DimensionSpecialEffects UNDERWORLD = new DimensionSpecialEffects(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, false) {
        @Override
        public Vec3 getBrightnessDependentFogColor(Vec3 vec3, float v) {
            return vec3;
        }

        @Override
        public boolean isFoggyAt(int i, int i1) {
            return false;
        }
    };
}
