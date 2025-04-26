package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.GoldenWeaponHolder;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import dev.thomasglasser.minejago.world.level.MinejagoLevels;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

public class GoldenWeaponSkulkinRaid extends AbstractSkulkinRaid {
    public static final Component NAME_COMPONENT = Component.translatable("event.minejago.golden_weapon_skulkin_raid");

    public GoldenWeaponSkulkinRaid(ServerLevel level, int id, BlockPos center) {
        super(level, id, center, NAME_COMPONENT);
    }

    public GoldenWeaponSkulkinRaid(ServerLevel level, CompoundTag compound) {
        super(level, compound, NAME_COMPONENT);
    }

    public static @Nullable BlockPos findValidRaidCenter(ServerLevel level, BlockPos pos) {
        if (!StolenItemsHolder.of(level.getServer().getLevel(MinejagoLevels.UNDERWORLD)).minejago$getStolenItems().hasEverStolen(stack -> stack.has(MinejagoDataComponents.GOLDEN_WEAPONS_MAP)))
            return null;
        // Accounts for bounds of structure
        List<GoldenWeaponHolder> holders = level.getEntitiesOfClass(GoldenWeaponHolder.class, AABB.ofSize(pos.getCenter(), VALID_RAID_RADIUS + 38 * 2, VALID_RAID_RADIUS + 20 * 2, VALID_RAID_RADIUS + 38 * 2), GoldenWeaponHolder::hasGoldenWeapon);
        return holders.isEmpty() ? null : holders.getFirst().blockPosition();
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
        List<GoldenWeaponHolder> holders = raider.level().getEntitiesOfClass(GoldenWeaponHolder.class, AABB.ofSize(getCenter().getCenter(), 1, 1, 1), GoldenWeaponHolder::hasGoldenWeapon);
        return holders.isEmpty() ? null : holders.getFirst().extractGoldenWeapon();
    }

    @Override
    protected void spawnBosses(int groupNum, BlockPos pos) {
        Samukai samukai = MinejagoEntityTypes.SAMUKAI.get().create(this.getLevel());
        if (samukai != null) {
            this.joinRaid(groupNum, samukai, pos, false);
            List<ItemStack> maps = StolenItemsHolder.of(this.getLevel().getServer().getLevel(MinejagoLevels.UNDERWORLD)).minejago$getStolenItems().removeCurrentlyStolenMatching(stack -> stack.has(MinejagoDataComponents.GOLDEN_WEAPONS_MAP));
            if (!maps.isEmpty()) {
                ItemStack mapsHolder;
                if (maps.size() == 1) {
                    mapsHolder = maps.getFirst();
                } else {
                    mapsHolder = Items.BUNDLE.getDefaultInstance();
                    mapsHolder.set(DataComponents.BUNDLE_CONTENTS, new BundleContents(maps));
                }
                samukai.setItemInHand(InteractionHand.OFF_HAND, mapsHolder);
                samukai.setDropChance(EquipmentSlot.OFFHAND, 2.0F);
            }
        }

        Nuckal nuckal = MinejagoEntityTypes.NUCKAL.get().create(this.getLevel());
        if (nuckal != null)
            this.joinRaid(groupNum, nuckal, pos, false);

        Kruncha kruncha = MinejagoEntityTypes.KRUNCHA.get().create(this.getLevel());
        if (kruncha != null)
            this.joinRaid(groupNum, kruncha, pos, false);

        if (MinejagoServerConfig.get().enableTech.get()) {
            if (samukai != null) {
                SkullTruck truck = MinejagoEntityTypes.SKULL_TRUCK.get().create(this.getLevel());
                if (truck == null)
                    return;
                truck.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                EventHooks.finalizeMobSpawn(truck, this.getLevel(), this.getLevel().getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
                this.getLevel().addFreshEntityWithPassengers(truck);

                samukai.startRiding(truck);
                if (nuckal != null) nuckal.startRiding(truck);
                if (kruncha != null) kruncha.startRiding(truck);
            }
        } else {
            List<SkulkinRaider> raiders = new ArrayList<>();
            if (samukai != null) raiders.add(samukai);
            if (nuckal != null) raiders.add(nuckal);
            if (kruncha != null) raiders.add(kruncha);

            for (SkulkinRaider raider : raiders) {
                Mob horse = MinejagoEntityTypes.SKULKIN_HORSE.get().create(this.getLevel());
                if (horse == null)
                    break;
                horse.setPos((double) pos.getX() + 0.5, (double) pos.getY() + 1.0, (double) pos.getZ() + 0.5);
                EventHooks.finalizeMobSpawn(horse, this.getLevel(), this.getLevel().getCurrentDifficultyAt(pos), MobSpawnType.EVENT, null);
                this.getLevel().addFreshEntityWithPassengers(horse);
                raider.startRiding(horse);
            }
        }
    }

    @Override
    protected SkulkinRaidType getType() {
        return SkulkinRaidTypes.GOLDEN_WEAPONS.get();
    }
}
