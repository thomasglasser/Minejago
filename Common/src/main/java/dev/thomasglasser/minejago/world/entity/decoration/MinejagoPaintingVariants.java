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

    public static void init() {}
}
