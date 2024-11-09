package dev.thomasglasser.minejago.world.level.block.state.properties;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.level.block.MinejagoSoundTypes;
import net.minecraft.world.level.block.state.properties.WoodType;

public class MinejagoWoodTypes {
    private static WoodType enchanted;

    public static WoodType getEnchanted() {
        if (enchanted == null) {
            enchanted = WoodType.register(new WoodType(Minejago.modLoc("enchanted").toString(),
                    MinejagoBlockSetTypes.ENCHANTED,
                    MinejagoSoundTypes.ENCHANTED_WOOD,
                    MinejagoSoundTypes.ENCHANTED_HANGING_SIGN,
                    MinejagoSoundEvents.ENCHANTED_WOOD_FENCE_GATE_CLOSE.get(),
                    MinejagoSoundEvents.ENCHANTED_WOOD_FENCE_GATE_OPEN.get()));
        }

        return enchanted;
    }
}
