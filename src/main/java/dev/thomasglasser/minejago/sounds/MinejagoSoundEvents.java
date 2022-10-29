package dev.thomasglasser.minejago.sounds;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoSoundEvents
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Minejago.MODID);

    public static final RegistryObject<SoundEvent> FOUR_WEAPONS_LEGEND_SCROLL = SOUND_EVENTS.register("four_weapons_legend_scroll", () -> new SoundEvent(new ResourceLocation(Minejago.MODID, "four_weapons_legend_scroll")));
}
