package dev.thomasglasser.minejago.world.level.block.state.properties;

import dev.thomasglasser.minejago.world.level.block.MinejagoSoundTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class MinejagoBlockSetTypes {
    public static final BlockSetType ENCHANTED = new BlockSetType("enchanted",
            true,
            true,
            true,
            BlockSetType.PressurePlateSensitivity.EVERYTHING,
            MinejagoSoundTypes.ENCHANTED_WOOD,
            // TODO: Custom sounds
            SoundEvents.BAMBOO_WOOD_DOOR_CLOSE,
            SoundEvents.BAMBOO_WOOD_DOOR_OPEN,
            SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE,
            SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN,
            SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF,
            SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON,
            SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF,
            SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON);
}
