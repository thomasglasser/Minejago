package dev.thomasglasser.minejago.world.item.crafting.display;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public class MinejagoSlotDisplays {
    public static final DeferredRegister<SlotDisplay.Type<?>> SLOT_DISPLAYS = DeferredRegister.create(Registries.SLOT_DISPLAY, Minejago.MOD_ID);

    public static final DeferredHolder<SlotDisplay.Type<?>, SlotDisplay.Type<PotionSlotDisplay>> POTION = SLOT_DISPLAYS.register("potion", () -> PotionSlotDisplay.TYPE);

    public static void init() {}
}
