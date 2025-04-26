package dev.thomasglasser.minejago.client.dynamiclights.entity;

import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import dev.thomasglasser.minejago.world.entity.GoldenWeaponHolder;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Range;

public class GoldenWeaponHolderLuminance implements EntityLuminance {
    public static final GoldenWeaponHolderLuminance INSTANCE = new GoldenWeaponHolderLuminance();

    private GoldenWeaponHolderLuminance() {}

    @Override
    public Type type() {
        return MinejagoEntityLuminanceTypes.GOLDEN_WEAPON_HOLDER;
    }

    @Override
    public @Range(from = 0L, to = 15L) int getLuminance(ItemLightSourceManager itemLightSourceManager, Entity entity) {
        if (entity instanceof GoldenWeaponHolder goldenWeaponHolder && goldenWeaponHolder.hasGoldenWeapon()) {
            return itemLightSourceManager.getLuminance(goldenWeaponHolder.getGoldenWeapon());
        }
        return 0;
    }
}
