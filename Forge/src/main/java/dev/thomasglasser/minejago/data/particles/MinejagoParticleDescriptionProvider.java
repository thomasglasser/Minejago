package dev.thomasglasser.minejago.data.particles;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;

public class MinejagoParticleDescriptionProvider extends ParticleDescriptionProvider {
    public MinejagoParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(MinejagoParticleTypes.VAPORS.get(),
                Minejago.modLoc("vapor_0"),
                Minejago.modLoc("vapor_1"),
                Minejago.modLoc("vapor_2"),
                Minejago.modLoc("vapor_3")
        );
        spriteSet(MinejagoParticleTypes.SPINJITZU.get(),
                Minejago.modLoc("spinjitzu")
        );
    }
}
