package dev.thomasglasser.minejago.data.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * Example implementation of datagen for powers
 */
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
                new Power(modLoc("magic"), ChatFormatting.DARK_PURPLE, SpinjitzuParticleOptions.ELEMENT_YELLOW, SpinjitzuParticleOptions.TEAM_LIGHT_PURPLE, ParticleTypes.DRIPPING_DRIPSTONE_LAVA, false)
        );
    }
}
