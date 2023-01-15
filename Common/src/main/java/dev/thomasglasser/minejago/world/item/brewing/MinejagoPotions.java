package dev.thomasglasser.minejago.world.item.brewing;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class MinejagoPotions
{
    public static final RegistrationProvider<Potion> POTIONS = RegistrationProvider.get(Registries.POTION, Minejago.MOD_ID);

    public static final RegistryObject<Potion> REGULAR_TEA = POTIONS.register("regular_tea", Potion::new);
    public static final RegistryObject<Potion> MILK = POTIONS.register("milk", () -> new Potion(new MobEffectInstance(MinejagoMobEffects.CURE.get())));

    public static void init() {}
}
