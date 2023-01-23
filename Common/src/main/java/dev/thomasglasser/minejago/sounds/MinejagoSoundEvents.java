package dev.thomasglasser.minejago.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class MinejagoSoundEvents
{
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, Minejago.MOD_ID);

    public static final RegistryObject<SoundEvent> TEAPOT_WHISTLE = SOUND_EVENTS.register("teapot_whistle", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("teapot_whistle")));

    public static void init() {}
}
