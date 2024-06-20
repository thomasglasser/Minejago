package dev.thomasglasser.minejago.data.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class MinejagoPowerDatagenSuite extends PowerDatagenSuite
{
    public MinejagoPowerDatagenSuite(GatherDataEvent event, LanguageProvider languageProvider) {
        super(event, Minejago.MOD_ID, languageProvider::add);
    }

    @Override
    public void generate() {
        makePowerSuite(MinejagoPowers.NONE);
        makePowerSuite(MinejagoPowers.ICE, builder -> builder
                        .color("#EDECE4")
                        .defaultTagline()
                // TODO: Implement colors
//                        .mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE)
//                        .altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_WHITE)
                        .hasSets()
                        .defaultDisplay(),
                MinejagoParticleTypes.SNOWS, "snow", 4, true, config -> {});
        makePowerSuite(MinejagoPowers.EARTH, builder -> builder
                        .color("#5A4441")
                        .defaultTagline()
                        // TODO: Implement colors
//                        .mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_BROWN)
//                        .altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_TAN)
                        .hasSets()
                        .defaultDisplay(),
                MinejagoParticleTypes.ROCKS, "rock", 4, true, config ->
                        config.tagline("Solid as rock."));
        makePowerSuite(MinejagoPowers.FIRE, builder -> builder
                        .color("#B90E04")
                        .defaultTagline()
                        // TODO: Implement colors
//                        .mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_ORANGE)
//                        .altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_YELLOW)
                        .hasSets()
                        .defaultDisplay(),
                MinejagoParticleTypes.SPARKS, "spark", 4, true, config ->
                        config.tagline("It burns bright in you."));
        makePowerSuite(MinejagoPowers.LIGHTNING, builder -> builder
                        .color("#4668D5")
                        .defaultTagline()
                        // TODO: Implement colors
//                        .mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_BLUE)
//                        .altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE)
                        .hasSets()
                        .defaultDisplay(),
                MinejagoParticleTypes.BOLTS, "bolt", 4, true, config -> {});
        makePowerSuite(MinejagoPowers.CREATION, builder -> builder
                        .color("#F8D66F")
                        // TODO: Implement colors
//                        .mainSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_DARK_GOLD)
//                        .altSpinjitzuColor(SpinjitzuParticleOptions.ELEMENT_GOLD)
                        .defaultDisplay()
                        .isSpecial(),
                MinejagoParticleTypes.SPARKLES, "sparkle", 4, false, config -> {});
    }
}
