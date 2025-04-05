package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.AbstractSkulkinRaid;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SeekAndTakeNearbyBlockRaidItems<T extends SkulkinRaider> extends AbstractSeekAndTakeNearbyRaidItems<T> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(WALK_TARGET_REQUIREMENT);

    private final List<BlockPos> visited = Lists.newArrayList();

    public SeekAndTakeNearbyBlockRaidItems<T> speedModifier(float speedMod) {
        return (SeekAndTakeNearbyBlockRaidItems<T>) super.speedModifier(speedMod);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        return super.checkExtraStartConditions(level, entity) && entity.getCurrentRaid() != null && entity.getCurrentRaid().isInValidRaidSearchArea(entity);
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        return entity.getCurrentRaid() != null && entity.getCurrentRaid().isInValidRaidSearchArea(entity);
    }

    @Override
    protected void tick(ServerLevel level, T entity, long gameTime) {
        super.tick(level, entity, gameTime);
        AbstractSkulkinRaid raid = entity.getCurrentRaid();
        if (!BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET) && raid != null) {
            Vec3 center = Vec3.atBottomCenterOf(raid.getCenter());
            BlockPos pos = entity.blockPosition();
            if (visited.contains(pos)) {
                Vec3 toCheck;
                do {
                    toCheck = DefaultRandomPos.getPosTowards(entity, 12, 4, center, (float) (Math.PI / 2));
                } while (toCheck == null || visited.contains(new BlockPos((int) toCheck.x, (int) toCheck.y, (int) toCheck.z)));
                BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(toCheck, speedModifier, 1));
                entity.stopRiding();
            } else {
                if (raid.canExtractRaidItem(entity)) {
                    ItemStack stack = raid.extractRaidItem(entity);
                    if (stack != null) {
                        entity.setItemSlot(EquipmentSlot.OFFHAND, stack);
                        entity.setDropChance(EquipmentSlot.OFFHAND, 2.0F);
                        Vec3 escapePos = null;
                        while (escapePos == null) {
                            escapePos = DefaultRandomPos.getPosAway(entity, 64, 32, center);
                        }
                        raid.setEscapePos(escapePos);
                    }
                } else {
                    visited.add(pos);
                }
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
