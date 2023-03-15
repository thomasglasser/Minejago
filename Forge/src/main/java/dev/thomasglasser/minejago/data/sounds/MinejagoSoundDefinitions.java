package dev.thomasglasser.minejago.data.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class MinejagoSoundDefinitions extends SoundDefinitionsProvider {
    public MinejagoSoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, Minejago.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(MinejagoSoundEvents.TEAPOT_WHISTLE.get(), define(MinejagoSoundEvents.TEAPOT_WHISTLE.getId().getPath(), sound(MinejagoSoundEvents.TEAPOT_WHISTLE.get().getLocation())));
        add(MinejagoSoundEvents.SPINJITZU_START.get(), define(MinejagoSoundEvents.SPINJITZU_START.getId().getPath(), sound(MinejagoSoundEvents.SPINJITZU_START.get().getLocation())));
        add(MinejagoSoundEvents.SPINJITZU_STOP.get(), define(MinejagoSoundEvents.SPINJITZU_STOP.getId().getPath(), sound(MinejagoSoundEvents.SPINJITZU_STOP.get().getLocation())));
        add(MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), define(MinejagoSoundEvents.SPINJITZU_ACTIVE.getId().getPath(), sound(MinejagoSoundEvents.SPINJITZU_ACTIVE.get().getLocation())));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.get(), define(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.getId().getPath(), sound(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.get().getLocation())));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.get(), define(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.getId().getPath(), sound(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.get().getLocation())));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.get(), define(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.getId().getPath(), sound(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.get().getLocation())));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.get(), define(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.getId().getPath(), sound(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.get().getLocation())));
        add(MinejagoSoundEvents.BONE_KNIFE_THROW.get(), define(MinejagoSoundEvents.BONE_KNIFE_THROW.getId().getPath(), sound(MinejagoSoundEvents.BONE_KNIFE_THROW.get().getLocation())));
        add(MinejagoSoundEvents.BONE_KNIFE_IMPACT.get(), define(MinejagoSoundEvents.BONE_KNIFE_IMPACT.getId().getPath(), sound(MinejagoSoundEvents.BONE_KNIFE_IMPACT.get().getLocation())));
        add(MinejagoSoundEvents.BAMBOO_STAFF_THROW.get(), define(MinejagoSoundEvents.BAMBOO_STAFF_THROW.getId().getPath(), sound(MinejagoSoundEvents.BAMBOO_STAFF_THROW.get().getLocation())));
        add(MinejagoSoundEvents.BAMBOO_STAFF_IMPACT.get(), define(MinejagoSoundEvents.BAMBOO_STAFF_IMPACT.getId().getPath(), sound(MinejagoSoundEvents.BAMBOO_STAFF_IMPACT.get().getLocation())));
        add(MinejagoSoundEvents.SHURIKEN_THROW.get(), define(MinejagoSoundEvents.SHURIKEN_THROW.getId().getPath(), sound(MinejagoSoundEvents.SHURIKEN_THROW.get().getLocation())));
        add(MinejagoSoundEvents.SHURIKEN_IMPACT.get(), define(MinejagoSoundEvents.SHURIKEN_IMPACT.getId().getPath(), sound(MinejagoSoundEvents.SHURIKEN_IMPACT.get().getLocation())));
        add(MinejagoSoundEvents.SPEAR_THROW.get(), define(MinejagoSoundEvents.SPEAR_THROW.getId().getPath(), sound(MinejagoSoundEvents.SPEAR_THROW.get().getLocation())));
        add(MinejagoSoundEvents.SPEAR_IMPACT.get(), define(MinejagoSoundEvents.SPEAR_IMPACT.getId().getPath(), sound(MinejagoSoundEvents.SPEAR_IMPACT.get().getLocation())));
    }

    private SoundDefinition define(String subtitle, SoundDefinition.Sound... sounds)
    {
        return SoundDefinition.definition().with(sounds).subtitle("subtitles." + subtitle);
    }

    protected static SoundDefinition.Sound sound(ResourceLocation location)
    {
        if (location.getPath().contains("."))
        {
            return sound(new ResourceLocation(location.getNamespace(), location.getPath().replace('.', '/')));
        }
        return SoundDefinitionsProvider.sound(location);
    }
}
