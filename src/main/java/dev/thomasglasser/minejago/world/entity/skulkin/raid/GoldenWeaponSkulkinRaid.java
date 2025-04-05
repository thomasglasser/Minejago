package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.level.block.GoldenWeaponHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GoldenWeaponSkulkinRaid extends AbstractSkulkinRaid {
    public static final Component NAME_COMPONENT = Component.translatable("event.minejago.golden_weapon_skulkin_raid");

    public GoldenWeaponSkulkinRaid(ServerLevel level, int id, BlockPos center) {
        super(level, id, center, NAME_COMPONENT);
    }

    public GoldenWeaponSkulkinRaid(ServerLevel level, CompoundTag compound) {
        super(level, compound, NAME_COMPONENT);
    }

    @Override
    public boolean isValidRaidItem(ItemStack stack) {
        return stack.is(MinejagoItemTags.GOLDEN_WEAPONS);
    }

    @Override
    public boolean isInValidRaidSearchArea(SkulkinRaider raider) {
        return getCenter().closerThan(raider.blockPosition(), CENTER_RADIUS_BUFFER);
    }

    @Override
    public boolean canExtractRaidItem(SkulkinRaider raider) {
        return getCenter().closerThan(raider.blockPosition(), 4);
    }

    @Override
    public @Nullable ItemStack extractRaidItem(SkulkinRaider raider) {
        return getLevel().getBlockEntity(getCenter()) instanceof GoldenWeaponHolder goldenWeaponHolder ? goldenWeaponHolder.extractGoldenWeapon() : null;
    }

    @Override
    protected Type getType() {
        return Type.GOLDEN_WEAPON;
    }
}
