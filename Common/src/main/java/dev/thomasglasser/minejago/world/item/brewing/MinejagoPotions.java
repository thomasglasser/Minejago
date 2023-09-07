package dev.thomasglasser.minejago.world.item.brewing;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class MinejagoPotions
{
    public static final RegistrationProvider<Potion> POTIONS = RegistrationProvider.get(Registries.POTION, Minejago.MOD_ID);

    public static final RegistryObject<Potion> ACACIA_TEA = register("acacia_tea", MinejagoMobEffects.ACACIA_TEA);
    public static final RegistryObject<Potion> OAK_TEA = register("oak_tea", MinejagoMobEffects.OAK_TEA);
    public static final RegistryObject<Potion> CHERRY_TEA = register("cherry_tea", MinejagoMobEffects.CHERRY_TEA);
    public static final RegistryObject<Potion> SPRUCE_TEA = register("spruce_tea", MinejagoMobEffects.SPRUCE_TEA);
    public static final RegistryObject<Potion> MANGROVE_TEA = register("mangrove_tea", MinejagoMobEffects.MANGROVE_TEA);
    public static final RegistryObject<Potion> JUNGLE_TEA = register("jungle_tea", MinejagoMobEffects.JUNGLE_TEA);
    public static final RegistryObject<Potion> DARK_OAK_TEA = register("dark_oak_tea", MinejagoMobEffects.DARK_OAK_TEA);
    public static final RegistryObject<Potion> BIRCH_TEA = register("birch_tea", MinejagoMobEffects.BIRCH_TEA);
    public static final RegistryObject<Potion> MILK = register("milk", MinejagoMobEffects.CURE);

    @SafeVarargs
    private static RegistryObject<Potion> register(String name, Supplier<MobEffect>... effects)
    {
        return POTIONS.register(name, () -> new Potion(Arrays.stream(effects).map(mobEffectSupplier -> new MobEffectInstance(mobEffectSupplier.get())).toList().toArray(new MobEffectInstance[] {})));
    }

    public static void init() {}
}
