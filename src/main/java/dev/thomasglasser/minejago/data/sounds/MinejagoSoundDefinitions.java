package dev.thomasglasser.minejago.data.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class MinejagoSoundDefinitions extends SoundDefinitionsProvider
{
    public MinejagoSoundDefinitions(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, Minejago.MODID, helper);
    }

    @Override
    public void registerSounds() {
        SoundDefinition definition = SoundDefinition.definition()
                .with(SoundDefinition.Sound.sound(MinejagoSoundEvents.FOUR_WEAPONS_LEGEND_SCROLL.getId(), SoundDefinition.SoundType.SOUND));
        add(MinejagoSoundEvents.FOUR_WEAPONS_LEGEND_SCROLL.get(), definition);
    }
}
