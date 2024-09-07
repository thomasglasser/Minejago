package dev.thomasglasser.minejago.world.level.block.state.properties;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoSoundTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.WoodType;

public class MinejagoWoodTypes {
    public static final WoodType ENCHANTED = WoodType.register(new WoodType(Minejago.modLoc("enchanted").toString(),
            MinejagoBlockSetTypes.ENCHANTED,
            MinejagoSoundTypes.ENCHANTED_WOOD,
            MinejagoSoundTypes.ENCHANTED_HANGING_SIGN,
            // TODO: Custom sounds
            SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE,
            SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN));
}
