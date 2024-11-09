package dev.thomasglasser.minejago.world.level.block.state.properties;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.level.block.MinejagoSoundTypes;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class MinejagoBlockSetTypes {
    public static final BlockSetType ENCHANTED = new BlockSetType("enchanted",
            true,
            true,
            true,
            BlockSetType.PressurePlateSensitivity.EVERYTHING,
            MinejagoSoundTypes.ENCHANTED_WOOD,
            MinejagoSoundEvents.ENCHANTED_WOOD_DOOR_CLOSE.get(),
            MinejagoSoundEvents.ENCHANTED_WOOD_DOOR_OPEN.get(),
            MinejagoSoundEvents.ENCHANTED_WOOD_TRAPDOOR_CLOSE.get(),
            MinejagoSoundEvents.ENCHANTED_WOOD_TRAPDOOR_OPEN.get(),
            MinejagoSoundEvents.ENCHANTED_WOOD_PRESSURE_PLATE_CLICK_OFF.get(),
            MinejagoSoundEvents.ENCHANTED_WOOD_PRESSURE_PLATE_CLICK_ON.get(),
            MinejagoSoundEvents.ENCHANTED_WOOD_BUTTON_CLICK_OFF.get(),
            MinejagoSoundEvents.ENCHANTED_WOOD_BUTTON_CLICK_ON.get());
}
