package dev.thomasglasser.minejago.client.dynamiclights.entity;

import com.mojang.serialization.MapCodec;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import dev.thomasglasser.minejago.world.entity.GoldenWeaponHolder;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Range;

public record GoldenWeaponHolderLuminance() implements EntityLuminance {
    public static final GoldenWeaponHolderLuminance INSTANCE = new GoldenWeaponHolderLuminance();
    public static final MapCodec<GoldenWeaponHolderLuminance> CODEC = MapCodec.unit(INSTANCE);

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
