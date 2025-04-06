package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import dev.thomasglasser.minejago.world.entity.skulkin.Samukai;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import dev.thomasglasser.minejago.world.level.MinejagoLevels;
import dev.thomasglasser.minejago.world.level.block.GoldenWeaponHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
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
        BlockPos center = null;
        double minDist = Double.MAX_VALUE;
        AABB toCheck = AABB.ofSize(pos.getCenter(), VALID_RAID_RADIUS, VALID_RAID_RADIUS, VALID_RAID_RADIUS);
        ChunkPos min = new ChunkPos(new BlockPos((int) toCheck.minX, (int) toCheck.minY, (int) toCheck.minZ));
        ChunkPos max = new ChunkPos(new BlockPos((int) toCheck.maxX, (int) toCheck.maxY, (int) toCheck.maxZ));
        for (int x = min.x; x <= max.x; x++) {
            for (int z = min.z; z <= max.z; z++) {
                for (Map.Entry<BlockPos, BlockEntity> entry : level.getChunk(x, z).getBlockEntities().entrySet()) {
                    BlockPos blockPos = entry.getKey();
                    BlockEntity blockEntity = entry.getValue();
                    if (blockEntity instanceof GoldenWeaponHolder goldenWeaponHolder && goldenWeaponHolder.hasGoldenWeapon()) {
                        double dist = pos.distSqr(blockPos);
                        if (dist < minDist) {
                            center = blockPos;
                            minDist = dist;
                        }
                    }
                }
            }
        }
        return center;
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
