package dev.thomasglasser.minejago.world.entity.decoration;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class MinejagoPaintingVariants
{
    public static final RegistrationProvider<PaintingVariant> PAINTING_VARIANTS = RegistrationProvider.get(Registries.PAINTING_VARIANT, Minejago.MOD_ID);

    public static final RegistryObject<PaintingVariant> FOUR_WEAPONS = PAINTING_VARIANTS.register("four_weapons", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> A_MORNING_BREW = PAINTING_VARIANTS.register("a_morning_brew", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> AMBUSHED = PAINTING_VARIANTS.register("ambushed", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> BEFORE_THE_STORM = PAINTING_VARIANTS.register("before_the_storm", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> CREATION = PAINTING_VARIANTS.register("creation", () -> new PaintingVariant(64, 48));
    public static final RegistryObject<PaintingVariant> EARTH = PAINTING_VARIANTS.register("earth", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> FIRE = PAINTING_VARIANTS.register("fire", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> FRUIT_COLORED_NINJA = PAINTING_VARIANTS.register("fruit_colored_ninja", () -> new PaintingVariant(64, 48));
    public static final RegistryObject<PaintingVariant> ICE = PAINTING_VARIANTS.register("ice", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> LIGHTNING = PAINTING_VARIANTS.register("lightning", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> NEEDS_HAIR_GEL = PAINTING_VARIANTS.register("needs_hair_gel", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> THE_FOURTH_MOUNTAIN = PAINTING_VARIANTS.register("the_fourth_mountain", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> NOT_FOR_FURNITURE = PAINTING_VARIANTS.register("not_for_furniture", () -> new PaintingVariant(16, 32));

    public static void init() {}
}
