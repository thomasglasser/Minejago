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

        add(MinejagoSoundEvents.DRAGON_BUTTON_OPEN);
        add(MinejagoSoundEvents.DRAGON_BUTTON_CLICK);
        add(MinejagoSoundEvents.DRAGON_BUTTON_CLOSE);

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

        add(MinejagoSoundEvents.EARTH_DRAGON_AMBIENT, 4);
        add(MinejagoSoundEvents.EARTH_DRAGON_AWAKEN);
        add(MinejagoSoundEvents.EARTH_DRAGON_DEATH);
        add(MinejagoSoundEvents.EARTH_DRAGON_FLAP, 2);
        add(MinejagoSoundEvents.EARTH_DRAGON_HURT);
        add(MinejagoSoundEvents.EARTH_DRAGON_ROAR);
        add(MinejagoSoundEvents.EARTH_DRAGON_STEP);
        add(MinejagoSoundEvents.EARTH_DRAGON_BOND_UP);
        add(MinejagoSoundEvents.EARTH_DRAGON_BOND_DOWN);
        add(MinejagoSoundEvents.EARTH_DRAGON_TAME);

        add(MinejagoSoundEvents.SKULL_TRUCK_AMBIENT_ACTIVE);
        add(MinejagoSoundEvents.SKULL_TRUCK_AMBIENT_IDLE);
        add(MinejagoSoundEvents.SKULL_TRUCK_DEATH);
        add(MinejagoSoundEvents.SKULL_TRUCK_HURT, 3);
        add(MinejagoSoundEvents.SKULL_TRUCK_IGNITION);

        add(MinejagoSoundEvents.SKULL_MOTORBIKE_AMBIENT_ACTIVE);
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_AMBIENT_IDLE);
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_DEATH);
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_HURT, 3);
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_IGNITION);

        add(MinejagoSoundEvents.SPINJITZU_COURSE_RISE);
        add(MinejagoSoundEvents.SPINJITZU_COURSE_FALL);
        add(MinejagoSoundEvents.BOUNCING_POLE_ACTIVE);
        add(MinejagoSoundEvents.CENTER_ACTIVE);
        add(MinejagoSoundEvents.ROCKING_POLE_ACTIVE);
        add(MinejagoSoundEvents.SPINNING_AXES_ACTIVE);
        add(MinejagoSoundEvents.SPINNING_DUMMIES_ACTIVE);
        add(MinejagoSoundEvents.SPINNING_DUMMIES_HIT);
        add(MinejagoSoundEvents.SPINNING_MACES_ACTIVE);
        add(MinejagoSoundEvents.SPINNING_POLE_ACTIVE);
        add(MinejagoSoundEvents.SWIRLING_KNIVES_ACTIVE);

        add(MinejagoSoundEvents.SKULKIN_RAID_HORN);

        addMusic(MinejagoSoundEvents.MUSIC_MONASTERY_OF_SPINJITZU);
        addMusic(MinejagoSoundEvents.MUSIC_SKULKIN_RAID);
    }
}
