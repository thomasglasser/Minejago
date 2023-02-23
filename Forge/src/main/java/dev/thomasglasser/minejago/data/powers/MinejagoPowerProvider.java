package dev.thomasglasser.minejago.data.powers;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.PackOutput;

public class MinejagoPowerProvider extends PowerProvider
{

    public MinejagoPowerProvider(PackOutput pOutput) {
        super(Minejago.MOD_ID, pOutput);
    }

    @Override
    protected void generate() {
        add(
                new Power(modLoc("magic"), SpinjitzuParticleOptions.ELEMENT_TAN, SpinjitzuParticleOptions.TEAM_DARK_PURPLE, ParticleTypes.SCULK_CHARGE_POP, false)
        );
    }
}
