package dev.thomasglasser.minejago.data.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

import java.util.ArrayList;

public class MinejagoSoundDefinitions extends SoundDefinitionsProvider {
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

    private SoundDefinition define(String subtitle, SoundDefinition.Sound... sounds)
    {
        return SoundDefinition.definition().with(sounds).subtitle("subtitles." + subtitle);
    }

    private void add(RegistryObject<SoundEvent> sound)
    {
        add(sound, define(sound.get().getLocation().getPath(), sound(sound.get().getLocation())));
    }

    private void add(RegistryObject<SoundEvent> sound, int variants)
    {
        add(sound, defineVariants(sound.get().getLocation().getPath(), sound.get().getLocation(), variants));
    }

    private SoundDefinition defineVariants(String subtitle, ResourceLocation sound, int variants)
    {
        ArrayList<SoundDefinition.Sound> sounds = new ArrayList<>();
        for (int i = 1; i < variants + 1; i++)
        {
            sounds.add(sound(new ResourceLocation(sound.getNamespace(), sound.getPath() + i)));
        }
        return define(subtitle, sounds.toArray(new SoundDefinition.Sound[] {}));
    }

    public static SoundDefinition.Sound sound(ResourceLocation location)
    {
        if (location.getPath().contains("."))
        {
            return sound(new ResourceLocation(location.getNamespace(), location.getPath().replace('.', '/')));
        }
        return SoundDefinitionsProvider.sound(location);
    }
}
