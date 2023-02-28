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
        add(MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("teapot_whistle"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.TEAPOT_WHISTLE.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_START.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("spinjitzu_start"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SPINJITZU_START.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_STOP.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("spinjitzu_stop"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SPINJITZU_STOP.get().getLocation().toLanguageKey("sound") + ".subtitle"));
        add(MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(Minejago.modLoc("spinjitzu_active"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.SPINJITZU_ACTIVE.get().getLocation().toLanguageKey("sound") + ".subtitle"));
    }
}
