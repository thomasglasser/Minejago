package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class MinejagoSoundTypes {
    public static final DeferredSoundType ENCHANTED_HANGING_SIGN = new DeferredSoundType(
            1.0F,
            1.0F,
            MinejagoSoundEvents.ENCHANTED_WOOD_HANGING_SIGN_BREAK,
            MinejagoSoundEvents.ENCHANTED_WOOD_HANGING_SIGN_STEP,
            MinejagoSoundEvents.ENCHANTED_WOOD_HANGING_SIGN_PLACE,
            MinejagoSoundEvents.ENCHANTED_WOOD_HANGING_SIGN_HIT,
            MinejagoSoundEvents.ENCHANTED_WOOD_HANGING_SIGN_FALL);
    public static final DeferredSoundType ENCHANTED_WOOD = new DeferredSoundType(
            1.0F,
            1.0F,
            MinejagoSoundEvents.ENCHANTED_WOOD_BREAK,
            MinejagoSoundEvents.ENCHANTED_WOOD_STEP,
            MinejagoSoundEvents.ENCHANTED_WOOD_PLACE,
            MinejagoSoundEvents.ENCHANTED_WOOD_HIT,
            MinejagoSoundEvents.ENCHANTED_WOOD_FALL);
}
