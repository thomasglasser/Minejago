package dev.thomasglasser.minejago.world.item.brewing;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Supplier;

public class MinejagoPotions
{
    public static final Supplier<Potion> ACACIA_TEA = register("acacia_tea", MinejagoMobEffects.ACACIA_TEA);
    public static final Supplier<Potion> OAK_TEA = register("oak_tea", MinejagoMobEffects.OAK_TEA);
    public static final Supplier<Potion> CHERRY_TEA = register("cherry_tea", MinejagoMobEffects.CHERRY_TEA);
    public static final Supplier<Potion> SPRUCE_TEA = register("spruce_tea", MinejagoMobEffects.SPRUCE_TEA);
    public static final Supplier<Potion> MANGROVE_TEA = register("mangrove_tea", MinejagoMobEffects.MANGROVE_TEA);
    public static final Supplier<Potion> JUNGLE_TEA = register("jungle_tea", MinejagoMobEffects.JUNGLE_TEA);
    public static final Supplier<Potion> DARK_OAK_TEA = register("dark_oak_tea", MinejagoMobEffects.DARK_OAK_TEA);
    public static final Supplier<Potion> BIRCH_TEA = register("birch_tea", MinejagoMobEffects.BIRCH_TEA);
    public static final Supplier<Potion> MILK = register("milk", MinejagoMobEffects.CURE);
    public static final Supplier<Potion> FOCUS_TEA = register("focus_tea", 4, MinejagoMobEffects.HYPERFOCUS);

    private static Supplier<Potion> register(String name, Supplier<MobEffect> effect)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.POTION, name, () -> new Potion(new MobEffectInstance(effect.get())));
    }

    private static Supplier<Potion> register(String name, int duration, Supplier<MobEffect> effect)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.POTION, name, () -> new Potion(new MobEffectInstance(effect.get(), duration)));
    }

    public static void init() {}
}
