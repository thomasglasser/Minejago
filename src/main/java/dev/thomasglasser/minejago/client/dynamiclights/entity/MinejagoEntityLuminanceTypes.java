package dev.thomasglasser.minejago.client.dynamiclights.entity;

import com.mojang.serialization.MapCodec;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;

public class MinejagoEntityLuminanceTypes {
    public static final EntityLuminance.Type SPINJITZU = register("spinjitzu", SpinjitzuLuminance.CODEC);
    public static final EntityLuminance.Type GOLDEN_WEAPON_HOLDER = registerSimple("golden_weapon_holder", GoldenWeaponHolderLuminance.INSTANCE);

    private static EntityLuminance.Type register(ResourceLocation id, MapCodec<? extends EntityLuminance> codec) {
        return EntityLuminance.Type.register(id, codec);
    }

    private static EntityLuminance.Type register(String name, MapCodec<? extends EntityLuminance> codec) {
        return register(Minejago.modLoc(name), codec);
    }

    private static EntityLuminance.Type registerSimple(String name, EntityLuminance singleton) {
        return register(Minejago.modLoc(name), MapCodec.unit(singleton));
    }

    public static void init() {}
}
