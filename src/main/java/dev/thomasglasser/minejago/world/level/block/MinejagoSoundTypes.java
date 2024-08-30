package dev.thomasglasser.minejago.world.level.block;

import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class MinejagoSoundTypes {
    public static final DeferredSoundType ENCHANTED_HANGING_SIGN = new DeferredSoundType(
            1.0F,
            1.0F,
            // TODO: Custom sounds
            () -> SoundEvents.BAMBOO_WOOD_HANGING_SIGN_BREAK,
            () -> SoundEvents.BAMBOO_WOOD_HANGING_SIGN_STEP,
            () -> SoundEvents.BAMBOO_WOOD_HANGING_SIGN_PLACE,
            () -> SoundEvents.BAMBOO_WOOD_HANGING_SIGN_HIT,
            () -> SoundEvents.BAMBOO_WOOD_HANGING_SIGN_FALL);
    public static final DeferredSoundType ENCHANTED_WOOD = new DeferredSoundType(
            1.0F,
            1.0F,
            // TODO: Custom sounds
            () -> SoundEvents.BAMBOO_WOOD_BREAK,
            () -> SoundEvents.BAMBOO_WOOD_STEP,
            () -> SoundEvents.BAMBOO_WOOD_PLACE,
            () -> SoundEvents.BAMBOO_WOOD_HIT,
            () -> SoundEvents.BAMBOO_WOOD_FALL);
}
