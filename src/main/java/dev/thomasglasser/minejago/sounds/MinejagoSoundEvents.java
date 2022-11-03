package dev.thomasglasser.minejago.sounds;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoSoundEvents
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Minejago.MOD_ID);

    public static final RegistryObject<SoundEvent> TEAPOT_WHISTLE = SOUND_EVENTS.register("teapot_whistle", () -> new SoundEvent(new ResourceLocation(Minejago.MOD_ID, "teapot_whistle")));
}
