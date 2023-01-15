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
        add(MinejagoSoundEvents.TEAPOT_WHISTLE.get(), SoundDefinition.definition().with(SoundDefinition.Sound.sound(new ResourceLocation(Minejago.MOD_ID, "teapot_whistle"), SoundDefinition.SoundType.SOUND)).subtitle(MinejagoSoundEvents.TEAPOT_WHISTLE.get().getLocation().toLanguageKey() + ".subtitle"));
    }
}
