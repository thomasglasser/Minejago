package dev.thomasglasser.minejago.world.entity.decoration;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class MinejagoPaintingVariants {
    public static final ResourceKey<PaintingVariant> A_MORNING_BREW = create("a_morning_brew");
    public static final ResourceKey<PaintingVariant> AMBUSHED = create("ambushed");
    public static final ResourceKey<PaintingVariant> BEFORE_THE_STORM = create("before_the_storm");
    public static final ResourceKey<PaintingVariant> CREATION = create("creation");
    public static final ResourceKey<PaintingVariant> EARTH = create("earth");
    public static final ResourceKey<PaintingVariant> FIRE = create("fire");
    public static final ResourceKey<PaintingVariant> FOUR_WEAPONS = create("four_weapons");
    public static final ResourceKey<PaintingVariant> FRUIT_COLORED_NINJA = create("fruit_colored_ninja");
    public static final ResourceKey<PaintingVariant> ICE = create("ice");
    public static final ResourceKey<PaintingVariant> LIGHTNING = create("lightning");
    public static final ResourceKey<PaintingVariant> NEEDS_HAIR_GEL = create("needs_hair_gel");
    public static final ResourceKey<PaintingVariant> THE_FOURTH_MOUNTAIN = create("the_fourth_mountain");
    public static final ResourceKey<PaintingVariant> NOT_FOR_FURNITURE = create("not_for_furniture");
    public static final ResourceKey<PaintingVariant> IT_TAKES_A_VILLAGE = create("it_takes_a_village");
    public static final ResourceKey<PaintingVariant> IT_TAKES_A_VILLAGE_WRECKED = create("it_takes_a_village_wrecked");

    public static void boostrap(BootstrapContext<PaintingVariant> context) {
        register(context, A_MORNING_BREW, 2, 2);
        register(context, AMBUSHED, 2, 1);
        register(context, BEFORE_THE_STORM, 2, 1);
        register(context, CREATION, 4, 3);
        register(context, EARTH, 1, 1);
        register(context, FIRE, 1, 1);
        register(context, FOUR_WEAPONS, 2, 1);
        register(context, FRUIT_COLORED_NINJA, 4, 3);
        register(context, ICE, 1, 1);
        register(context, LIGHTNING, 1, 1);
        register(context, NEEDS_HAIR_GEL, 1, 2);
        register(context, THE_FOURTH_MOUNTAIN, 2, 2);
        register(context, NOT_FOR_FURNITURE, 1, 2);
        register(context, IT_TAKES_A_VILLAGE, 8, 4);
        register(context, IT_TAKES_A_VILLAGE_WRECKED, 8, 4);
    }

    private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int width, int height) {
        context.register(key, new PaintingVariant(width, height, key.location()));
    }

    private static ResourceKey<PaintingVariant> create(String pName) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, Minejago.modLoc(pName));
    }
}
