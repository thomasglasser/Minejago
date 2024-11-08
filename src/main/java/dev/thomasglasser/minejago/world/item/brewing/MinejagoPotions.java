package dev.thomasglasser.minejago.world.item.brewing;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.item.alchemy.EmptyColoredPotion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class MinejagoPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Minejago.MOD_ID);

    // Teas - Color is darkened light part of planks
    public static final DeferredHolder<Potion, Potion> ACACIA_TEA = register("acacia_tea", 0x4d3c21);
    public static final DeferredHolder<Potion, Potion> OAK_TEA = register("oak_tea", 0x4d3b21);
    public static final DeferredHolder<Potion, Potion> CHERRY_TEA = register("cherry_tea", 0x4d3230);
    public static final DeferredHolder<Potion, Potion> SPRUCE_TEA = register("spruce_tea", 0x4d341c);
    public static final DeferredHolder<Potion, Potion> MANGROVE_TEA = register("mangrove_tea", 0x4d1719);
    public static final DeferredHolder<Potion, Potion> JUNGLE_TEA = register("jungle_tea", 0x4d3323);
    public static final DeferredHolder<Potion, Potion> DARK_OAK_TEA = register("dark_oak_tea", 0x33200f);
    public static final DeferredHolder<Potion, Potion> BIRCH_TEA = register("birch_tea", 0x4d432f);
    public static final DeferredHolder<Potion, Potion> AZALEA_TEA = register("azalea_tea", 0x3f4a2b);
    public static final DeferredHolder<Potion, Potion> FLOWERING_AZALEA_TEA = register("flowering_azalea_tea", 0x592f62);

    public static final DeferredHolder<Potion, Potion> MILK = register("milk", MinejagoMobEffects.CURE);
    public static final DeferredHolder<Potion, Potion> FOCUS_TEA = register("focus_tea", 5, MinejagoMobEffects.HYPERFOCUS);

    private static DeferredHolder<Potion, Potion> register(String name, int color) {
        return POTIONS.register(name, () -> new EmptyColoredPotion(name, color));
    }

    private static DeferredHolder<Potion, Potion> register(String name, DeferredHolder<MobEffect, MobEffect> effect) {
        return POTIONS.register(name, () -> new Potion(name, new MobEffectInstance(effect)));
    }

    private static DeferredHolder<Potion, Potion> register(String name, int duration, DeferredHolder<MobEffect, MobEffect> effect) {
        return POTIONS.register(name, () -> new Potion(name, new MobEffectInstance(effect, duration)));
    }

    public static void init() {}
}
