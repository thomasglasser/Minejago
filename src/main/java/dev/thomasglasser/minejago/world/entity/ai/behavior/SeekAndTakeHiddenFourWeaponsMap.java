package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.level.MinejagoLevelUtils;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SeekAndTakeHiddenFourWeaponsMap<T extends SkulkinRaider> extends AbstractSeekAndTake<T> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(WALK_TARGET_REQUIREMENT);

    private final List<BlockPos> visited = Lists.newArrayList();

    public SeekAndTakeHiddenFourWeaponsMap<T> speedModifier(float speedMod) {
        return (SeekAndTakeHiddenFourWeaponsMap<T>) super.speedModifier(speedMod);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        return super.checkExtraStartConditions(level, entity) && MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(entity, 16);
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        return MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(entity, 16);
    }

    @Override
    protected void tick(ServerLevel level, T entity, long gameTime) {
        super.tick(level, entity, gameTime);
        if (!BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET)) {
            if (visited.contains(entity.blockPosition())) {
                Vec3 toCheck;
                do {
                    toCheck = DefaultRandomPos.getPosTowards(entity, 15, 8, Vec3.atBottomCenterOf(MinejagoLevelUtils.getGoldenWeaponsMapHolderNearby(entity, 16).blockPosition()), (float) (Math.PI / 2));
                } while (toCheck == null || visited.contains(new BlockPos((int) toCheck.x, (int) toCheck.y, (int) toCheck.z)));
                BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(toCheck, speedModifier, 1));
                entity.stopRiding();
            } else {
                Painting fw = MinejagoLevelUtils.getGoldenWeaponsMapHolderNearby(entity, 1);
                if (fw != null) {
                    CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(fw);
                    persistentData.putBoolean("MapTaken", true);
                    TommyLibServices.ENTITY.setPersistentData(fw, persistentData, true);
                    entity.setItemSlot(EquipmentSlot.OFFHAND, MinejagoItemUtils.createFourWeaponsMaps(level, entity));
                    entity.setDropChance(EquipmentSlot.OFFHAND, 2.0F);
                    Vec3 escapePos = null;
                    while (escapePos == null) {
                        escapePos = DefaultRandomPos.getPosAway(entity, 64, 32, Vec3.atBottomCenterOf(entity.getCurrentRaid().getCenter()));
                    }
                    entity.getCurrentRaid().setLoss(escapePos);
                } else {
                    visited.add(entity.blockPosition());
                }
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
