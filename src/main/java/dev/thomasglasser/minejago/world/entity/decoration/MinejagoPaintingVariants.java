package dev.thomasglasser.minejago.world.entity.decoration;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.function.Supplier;

public class MinejagoPaintingVariants
{
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(BuiltInRegistries.PAINTING_VARIANT, Minejago.MOD_ID);
    
    public static final DeferredHolder<PaintingVariant, PaintingVariant> FOUR_WEAPONS = register("four_weapons", () -> new PaintingVariant(32, 16));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> A_MORNING_BREW = register("a_morning_brew", () -> new PaintingVariant(32, 32));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> AMBUSHED = register("ambushed", () -> new PaintingVariant(32, 16));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> BEFORE_THE_STORM = register("before_the_storm", () -> new PaintingVariant(32, 16));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> CREATION = register("creation", () -> new PaintingVariant(64, 48));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> EARTH = register("earth", () -> new PaintingVariant(16, 16));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> FIRE = register("fire", () -> new PaintingVariant(16, 16));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> FRUIT_COLORED_NINJA = register("fruit_colored_ninja", () -> new PaintingVariant(64, 48));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> ICE = register("ice", () -> new PaintingVariant(16, 16));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> LIGHTNING = register("lightning", () -> new PaintingVariant(16, 16));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> NEEDS_HAIR_GEL = register("needs_hair_gel", () -> new PaintingVariant(16, 32));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> THE_FOURTH_MOUNTAIN = register("the_fourth_mountain", () -> new PaintingVariant(32, 32));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> NOT_FOR_FURNITURE = register("not_for_furniture", () -> new PaintingVariant(16, 32));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> IT_TAKES_A_VILLAGE = register("it_takes_a_village", () -> new PaintingVariant(128, 64));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> IT_TAKES_A_VILLAGE_WRECKED = register("it_takes_a_village_wrecked", () -> new PaintingVariant(128, 64));

    private static DeferredHolder<PaintingVariant, PaintingVariant> register(String name, Supplier<PaintingVariant> variant)
    {
        return PAINTING_VARIANTS.register(name, variant);
    }
    
    public static void init() {}
}
