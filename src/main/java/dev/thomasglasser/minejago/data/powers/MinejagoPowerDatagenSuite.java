package dev.thomasglasser.minejago.data.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class MinejagoPowerDatagenSuite extends PowerDatagenSuite {
    public MinejagoPowerDatagenSuite(GatherDataEvent event, LanguageProvider languageProvider) {
        super(event, Minejago.MOD_ID, languageProvider::add);
    }

    @Override
    public void generate() {
        // TODO: Better descriptions, lore, taglines
        makePowerSuite(MinejagoPowers.NONE, builder -> builder
                .defaultTagline()
                .defaultDisplay(),
                false,
                config -> {
                    config.tagline("The average Joe.");
                    config.desc("""
                            TODO: Description
                            """);
                    config.lore("TODO: Lore");
                });
        makePowerSuite(MinejagoPowers.ICE, builder -> builder
                .color("#EDECE4")
                .defaultTagline()
                .hasSets()
                .defaultDisplay(),
                MinejagoParticleTypes.SNOWS, "snow", 4, true, config -> {
                    config.tagline("Cool as ice.");
                    config.desc("""
                            Like ice itself, the Master of Ice is cold and calculating.
                            They keep their cool in the heat of battle,
                            and can keep their team in check.

                            TODO:Description
                            """);
                    config.lore("TODO: Lore");
                });
        makePowerSuite(MinejagoPowers.EARTH, builder -> builder
                .color("#5A4441")
                .defaultTagline()
                .hasSets()
                .defaultDisplay(),
                MinejagoParticleTypes.ROCKS, "rock", 4, true, config -> {
                    config.tagline("Solid as rock.");
                    config.desc("""
                            TODO: Description
                            """);
                    config.lore("TODO: Lore");
                });
        makePowerSuite(MinejagoPowers.FIRE, builder -> builder
                .color("#B90E04")
                .defaultTagline()
                .hasSets()
                .defaultDisplay(),
                MinejagoParticleTypes.SPARKS, "spark", 4, true, config -> {
                    config.tagline("It burns bright in you.");
                    config.desc("""
                            TODO: Description
                            """);
                    config.lore("TODO: Lore");
                });
        makePowerSuite(MinejagoPowers.LIGHTNING, builder -> builder
                .color("#4668D5")
                .defaultTagline()
                .hasSets()
                .defaultDisplay(),
                MinejagoParticleTypes.BOLTS, "bolt", 4, true, config -> {
                    config.tagline("Ignite the storm within.");
                    config.desc("""
                            TODO: Description
                            """);
                    config.lore("TODO: Lore");
                });
        makePowerSuite(MinejagoPowers.CREATION, builder -> builder
                .color("#F8D66F")
                .isSpecial(),
                MinejagoParticleTypes.SPARKLES, "sparkle", 4, false, config -> {});
    }
}
