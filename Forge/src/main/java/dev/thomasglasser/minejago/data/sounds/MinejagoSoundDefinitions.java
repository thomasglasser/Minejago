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
        // TODO: New teapot whistle
//        add(MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("teapot_whistle"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.TEAPOT_WHISTLE.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_START.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("spinjitzu_start"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SPINJITZU_START.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_STOP.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("spinjitzu_stop"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SPINJITZU_STOP.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("spinjitzu_active"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SPINJITZU_ACTIVE.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("scythe_of_quakes_path"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("scythe_of_quakes_cascade"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("scythe_of_quakes_fail"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("scythe_of_quakes_explosion"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.get().getLocation().toLanguageKey("sound") + ".subtitle"));
    }
}
