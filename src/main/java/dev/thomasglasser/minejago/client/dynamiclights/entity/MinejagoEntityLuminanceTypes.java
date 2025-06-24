package dev.thomasglasser.minejago.client.dynamiclights.entity;

import com.mojang.serialization.MapCodec;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.thomasglasser.minejago.Minejago;

public class MinejagoEntityLuminanceTypes {
    public static final EntityLuminance.Type GOLDEN_WEAPON_HOLDER = register("golden_weapon_holder", GoldenWeaponHolderLuminance.CODEC);
    public static final EntityLuminance.Type SPINJITZU = register("spinjitzu", SpinjitzuLuminance.CODEC);

    private static EntityLuminance.Type register(String name, MapCodec<? extends EntityLuminance> codec) {
        return EntityLuminance.Type.register(Minejago.modLoc(name), codec);
    }

    public static void init() {}
}
