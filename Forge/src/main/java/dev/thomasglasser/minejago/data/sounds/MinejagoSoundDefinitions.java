package dev.thomasglasser.minejago.data.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class MinejagoSoundDefinitions extends SoundDefinitionsProvider {
    public MinejagoSoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, Minejago.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundDefinition.definition().with(sound(Minejago.modLoc("teapot_whistle"))).subtitle(MinejagoSoundEvents.TEAPOT_WHISTLE.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_START.get(), definition().with(sound(Minejago.modLoc("spinjitzu_start"))).subtitle(MinejagoSoundEvents.SPINJITZU_START.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_STOP.get(), definition().with(sound(Minejago.modLoc("spinjitzu_stop"))).subtitle(MinejagoSoundEvents.SPINJITZU_STOP.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), definition().with(sound(Minejago.modLoc("spinjitzu_active"))).subtitle(MinejagoSoundEvents.SPINJITZU_ACTIVE.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.get(), definition().with(sound(Minejago.modLoc("scythe_of_quakes_path"))).subtitle(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.get(), definition().with(sound(Minejago.modLoc("scythe_of_quakes_cascade"))).subtitle(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.get(), definition().with(sound(Minejago.modLoc("scythe_of_quakes_fail"))).subtitle(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.get(), definition().with(sound(Minejago.modLoc("scythe_of_quakes_explosion"))).subtitle(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.get().getLocation().toLanguageKey("sound") + ".subtitle"));
    }
}
