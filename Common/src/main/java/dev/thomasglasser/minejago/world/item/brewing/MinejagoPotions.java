package dev.thomasglasser.minejago.world.item.brewing;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class MinejagoPotions
{
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Minejago.MOD_ID);

    public static final DeferredHolder<Potion, Potion> ACACIA_TEA = register("acacia_tea", MinejagoMobEffects.ACACIA_TEA);
    public static final DeferredHolder<Potion, Potion> OAK_TEA = register("oak_tea", MinejagoMobEffects.OAK_TEA);
    public static final DeferredHolder<Potion, Potion> CHERRY_TEA = register("cherry_tea", MinejagoMobEffects.CHERRY_TEA);
    public static final DeferredHolder<Potion, Potion> SPRUCE_TEA = register("spruce_tea", MinejagoMobEffects.SPRUCE_TEA);
    public static final DeferredHolder<Potion, Potion> MANGROVE_TEA = register("mangrove_tea", MinejagoMobEffects.MANGROVE_TEA);
    public static final DeferredHolder<Potion, Potion> JUNGLE_TEA = register("jungle_tea", MinejagoMobEffects.JUNGLE_TEA);
    public static final DeferredHolder<Potion, Potion> DARK_OAK_TEA = register("dark_oak_tea", MinejagoMobEffects.DARK_OAK_TEA);
    public static final DeferredHolder<Potion, Potion> BIRCH_TEA = register("birch_tea", MinejagoMobEffects.BIRCH_TEA);
    public static final DeferredHolder<Potion, Potion> AZALEA_TEA = register("azalea_tea", MinejagoMobEffects.AZALEA_TEA);
    public static final DeferredHolder<Potion, Potion> FLOWERING_AZALEA_TEA = register("flowering_azalea_tea", MinejagoMobEffects.FLOWERING_AZALEA_TEA);
    public static final DeferredHolder<Potion, Potion> MILK = register("milk", MinejagoMobEffects.CURE);
    public static final DeferredHolder<Potion, Potion> FOCUS_TEA = register("focus_tea", 4, MinejagoMobEffects.HYPERFOCUS);

    private static DeferredHolder<Potion, Potion> register(String name, DeferredHolder<MobEffect, MobEffect> effect)
    {
        return POTIONS.register(name, () -> new Potion(new MobEffectInstance(effect)));
    }

    private static DeferredHolder<Potion, Potion> register(String name, int duration, DeferredHolder<MobEffect, MobEffect> effect)
    {
        return POTIONS.register(name, () -> new Potion(new MobEffectInstance(effect, duration)));
    }

    public static void init() {}
}
