package dev.thomasglasser.minejago.world.entity.element;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class MinejagoElements {
    public static final ResourceKey<Element> NONE = create("none");
    public static final ResourceKey<Element> ICE = create("ice");
    public static final ResourceKey<Element> EARTH = create("earth");
    public static final ResourceKey<Element> FIRE = create("fire");
    public static final ResourceKey<Element> LIGHTNING = create("lightning");
    public static final ResourceKey<Element> CREATION = create("creation");

    private static ResourceKey<Element> create(String id) {
        return ResourceKey.create(MinejagoRegistries.ELEMENT, Minejago.modLoc(id));
    }

    public static void bootstrap(BootstrapContext<Element> context) {
        context.register(NONE, Element.builder(NONE)
                .defaultTagline()
                .defaultDisplay()
                .build());

        context.register(ICE, Element.builder(ICE)
                .color("#EDECE4")
                .defaultTagline()
                .defaultDisplay()
                .hasSets()
                .hasSpecialSets()
                .borderParticle(MinejagoParticleTypes.SNOWS)
                .build());

        context.register(EARTH, Element.builder(EARTH)
                .color("#5A4441")
                .defaultTagline()
                .defaultDisplay()
                .hasSets()
                .hasSpecialSets()
                .borderParticle(MinejagoParticleTypes.ROCKS)
                .build());

        context.register(FIRE, Element.builder(FIRE)
                .color("#B90E04")
                .defaultTagline()
                .defaultDisplay()
                .hasSets()
                .hasSpecialSets()
                .borderParticle(MinejagoParticleTypes.SPARKS)
                .build());

        context.register(LIGHTNING, Element.builder(LIGHTNING)
                .color("#4668D5")
                .defaultTagline()
                .defaultDisplay()
                .hasSets()
                .hasSpecialSets()
                .borderParticle(MinejagoParticleTypes.BOLTS)
                .build());

        context.register(CREATION, Element.builder(CREATION)
                .color("#F8D66F")
                .borderParticle(MinejagoParticleTypes.SPARKLES)
                .isSpecial()
                .build());
    }
}
