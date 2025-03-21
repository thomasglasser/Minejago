package dev.thomasglasser.minejago.data.particles;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class MinejagoParticleDescriptionProvider extends ParticleDescriptionProvider {
    public MinejagoParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(MinejagoParticleTypes.SPARKS.get(),
                Minejago.modLoc("spark_0"),
                Minejago.modLoc("spark_1"),
                Minejago.modLoc("spark_2"),
                Minejago.modLoc("spark_3"));
        spriteSet(MinejagoParticleTypes.SPARKLES.get(),
                Minejago.modLoc("sparkle_0"),
                Minejago.modLoc("sparkle_1"),
                Minejago.modLoc("sparkle_2"),
                Minejago.modLoc("sparkle_3"));
        spriteSet(MinejagoParticleTypes.SNOWS.get(),
                Minejago.modLoc("snow_0"),
                Minejago.modLoc("snow_1"),
                Minejago.modLoc("snow_2"),
                Minejago.modLoc("snow_3"));
        spriteSet(MinejagoParticleTypes.ROCKS.get(),
                Minejago.modLoc("rock_0"),
                Minejago.modLoc("rock_1"),
                Minejago.modLoc("rock_2"),
                Minejago.modLoc("rock_3"));
        spriteSet(MinejagoParticleTypes.BOLTS.get(),
                Minejago.modLoc("bolt_0"),
                Minejago.modLoc("bolt_1"),
                Minejago.modLoc("bolt_2"),
                Minejago.modLoc("bolt_3"));
        spriteSet(MinejagoParticleTypes.VAPORS.get(),
                Minejago.modLoc("vapor_0"),
                Minejago.modLoc("vapor_1"),
                Minejago.modLoc("vapor_2"),
                Minejago.modLoc("vapor_3"));
    }
}
