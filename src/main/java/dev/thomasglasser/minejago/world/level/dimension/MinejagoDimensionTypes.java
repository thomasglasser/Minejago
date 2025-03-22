package dev.thomasglasser.minejago.world.level.dimension;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import java.util.OptionalLong;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.DimensionType;

public class MinejagoDimensionTypes {
    public static final ResourceKey<DimensionType> UNDERWORLD = create("underworld");

    private static ResourceKey<DimensionType> create(String name) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, Minejago.modLoc(name));
    }

    public static void bootstrap(BootstrapContext<DimensionType> context) {
        context.register(UNDERWORLD, new DimensionType(
                OptionalLong.of(18000L),
                false,
                false,
                false,
                false,
                1.0,
                false,
                true,
                -32,
                256,
                128,
                MinejagoBlockTags.INFINIBURN_UNDERWORLD,
                UNDERWORLD.location(),
                0.1F,
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));
    }
}
