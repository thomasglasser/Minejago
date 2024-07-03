package dev.thomasglasser.minejago.data.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.tommylib.api.data.sounds.ExtendedSoundDefinitionsProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MinejagoSoundDefinitions extends ExtendedSoundDefinitionsProvider {
    public MinejagoSoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, Minejago.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(MinejagoSoundEvents.TEAPOT_WHISTLE);

        add(MinejagoSoundEvents.SPINJITZU_START);
        add(MinejagoSoundEvents.SPINJITZU_STOP);
        add(MinejagoSoundEvents.SPINJITZU_ACTIVE);

        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH);
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION);
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL);
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE);

        add(MinejagoSoundEvents.BONE_KNIFE_THROW);
        add(MinejagoSoundEvents.BONE_KNIFE_IMPACT);

        add(MinejagoSoundEvents.BAMBOO_STAFF_THROW);
        add(MinejagoSoundEvents.BAMBOO_STAFF_IMPACT);

        add(MinejagoSoundEvents.SHURIKEN_THROW);
        add(MinejagoSoundEvents.SHURIKEN_IMPACT);

        add(MinejagoSoundEvents.SPEAR_THROW);
        add(MinejagoSoundEvents.SPEAR_IMPACT);

        add(MinejagoSoundEvents.EARTH_DRAGON_AMBIENT, 4);
        add(MinejagoSoundEvents.EARTH_DRAGON_AWAKEN);
        add(MinejagoSoundEvents.EARTH_DRAGON_DEATH);
        add(MinejagoSoundEvents.EARTH_DRAGON_FLAP, 2);
        add(MinejagoSoundEvents.EARTH_DRAGON_HURT);
        add(MinejagoSoundEvents.EARTH_DRAGON_ROAR);
        add(MinejagoSoundEvents.EARTH_DRAGON_STEP);
    }
}
