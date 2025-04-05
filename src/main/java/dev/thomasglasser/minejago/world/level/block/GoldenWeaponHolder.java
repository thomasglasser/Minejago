package dev.thomasglasser.minejago.world.level.block;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface GoldenWeaponHolder {
    boolean hasGoldenWeapon();

    @Nullable
    ItemStack extractGoldenWeapon();
}
