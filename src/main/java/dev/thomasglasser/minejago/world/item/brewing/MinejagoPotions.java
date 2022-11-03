package dev.thomasglasser.minejago.world.item.brewing;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoPotions
{
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Minejago.MOD_ID);

    public static final RegistryObject<Potion> REGULAR_TEA = POTIONS.register("regular_tea", Potion::new);
    public static final RegistryObject<Potion> MILK = POTIONS.register("milk", () -> new Potion(new MobEffectInstance(MinejagoMobEffects.CURE.get())));
}
