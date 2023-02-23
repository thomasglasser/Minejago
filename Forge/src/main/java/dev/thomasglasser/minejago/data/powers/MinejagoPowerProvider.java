package dev.thomasglasser.minejago.data.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.BiConsumer;

public class MinejagoPowerProvider extends PowerProvider
{
    /**
     * @param output             {@linkplain PackOutput} provided by the {@link DataGenerator}.
     * @param existingFileHelper
     */
    public MinejagoPowerProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper, Minejago.MOD_ID);
    }

    @Override
    protected void generate() {
        add(
                new Power(Minejago.modLoc("none")),
                new Power(Minejago.modLoc("fire"), SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, MinejagoParticleTypes.SPARKS, true),
                new Power(Minejago.modLoc("earth"), SpinjitzuParticleOptions.ELEMENT_BROWN, SpinjitzuParticleOptions.ELEMENT_TAN, MinejagoParticleTypes.ROCKS, true),
                new Power(Minejago.modLoc("lightning"), SpinjitzuParticleOptions.ELEMENT_BLUE, SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, MinejagoParticleTypes.BOLTS, true),
                new Power(Minejago.modLoc("ice"), SpinjitzuParticleOptions.ELEMENT_LIGHT_BLUE, SpinjitzuParticleOptions.ELEMENT_WHITE, MinejagoParticleTypes.SNOWS, true)
        );
    }
}
