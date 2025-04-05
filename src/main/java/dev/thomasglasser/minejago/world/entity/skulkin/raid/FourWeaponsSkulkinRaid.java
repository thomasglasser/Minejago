package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.level.MinejagoLevelUtils;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FourWeaponsSkulkinRaid extends AbstractSkulkinRaid {
    public static final Component NAME_COMPONENT = Component.translatable("event.minejago.four_weapons_skulkin_raid");

    public FourWeaponsSkulkinRaid(ServerLevel level, int id, BlockPos center) {
        super(level, id, center, NAME_COMPONENT);
    }

    public FourWeaponsSkulkinRaid(ServerLevel level, CompoundTag compound) {
        super(level, compound, NAME_COMPONENT);
    }

    @Override
    public boolean isValidRaidItem(ItemStack stack) {
        return stack.has(MinejagoDataComponents.GOLDEN_WEAPONS_MAP.get());
    }

    @Override
    public boolean isInValidRaidSearchArea(SkulkinRaider raider) {
        return getCenter().closerThan(raider.blockPosition(), CENTER_RADIUS_BUFFER);
    }

    @Override
    public boolean canExtractRaidItem(SkulkinRaider raider) {
        return getCenter().closerThan(raider.blockPosition().above(3), 2);
    }

    @Override
    public @Nullable ItemStack extractRaidItem(SkulkinRaider raider) {
        Painting fw = MinejagoLevelUtils.getGoldenWeaponsMapHolderNearby(raider, 1);
        if (fw != null) {
            CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(fw);
            persistentData.putBoolean("MapTaken", true);
            TommyLibServices.ENTITY.setPersistentData(fw, persistentData, true);
            return MinejagoItemUtils.createFourWeaponsMaps(this.getLevel(), raider.blockPosition());
        }
        return null;
    }

    @Override
    protected Type getType() {
        return Type.FOUR_WEAPONS;
    }
}
