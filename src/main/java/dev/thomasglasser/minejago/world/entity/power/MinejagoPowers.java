package dev.thomasglasser.minejago.world.entity.power;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class MinejagoPowers {
    public static final ResourceKey<Power> NONE = create("none");
    public static final ResourceKey<Power> ICE = create("ice");
    public static final ResourceKey<Power> EARTH = create("earth");
    public static final ResourceKey<Power> FIRE = create("fire");
    public static final ResourceKey<Power> LIGHTNING = create("lightning");
    public static final ResourceKey<Power> CREATION = create("creation");

    private static ResourceKey<Power> create(String id) {
        return ResourceKey.create(MinejagoRegistries.POWER, Minejago.modLoc(id));
    }

    public static void bootstrap(BootstrapContext<Power> context) {
        context.register(NONE, Power.builder(NONE)
                .defaultTagline()
                .defaultDisplay()
                .build());

        context.register(ICE, Power.builder(ICE)
                .color("#EDECE4")
                .defaultTagline()
                .defaultDisplay()
                .hasSets()
                .hasSpecialSets()
                .borderParticle(MinejagoParticleTypes.SNOWS)
                .build());

        context.register(EARTH, Power.builder(EARTH)
                .color("#5A4441")
                .defaultTagline()
                .defaultDisplay()
                .hasSets()
                .hasSpecialSets()
                .borderParticle(MinejagoParticleTypes.ROCKS)
                .build());

        context.register(FIRE, Power.builder(FIRE)
                .color("#B90E04")
                .defaultTagline()
                .defaultDisplay()
                .hasSets()
                .hasSpecialSets()
                .borderParticle(MinejagoParticleTypes.SPARKS)
                .build());

        context.register(LIGHTNING, Power.builder(LIGHTNING)
                .color("#4668D5")
                .defaultTagline()
                .defaultDisplay()
                .hasSets()
                .hasSpecialSets()
                .borderParticle(MinejagoParticleTypes.BOLTS)
                .build());

        context.register(CREATION, Power.builder(CREATION)
                .color("#F8D66F")
                .borderParticle(MinejagoParticleTypes.SPARKLES)
                .isSpecial()
                .build());
    }
}
