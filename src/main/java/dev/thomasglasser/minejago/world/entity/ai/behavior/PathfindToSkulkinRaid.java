package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaids;
import dev.thomasglasser.minejago.world.level.MinejagoLevelUtils;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

public class PathfindToSkulkinRaid<T extends SkulkinRaider> extends ExtendedBehaviour<T> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));

    private int recruitmentTick;

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        return !BrainUtils.hasMemory(entity, MemoryModuleType.ATTACK_TARGET)
                && !entity.isVehicle()
                && entity.hasActiveRaid()
                && !entity.getCurrentRaid().isOver()
                && !MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(entity, 16);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, T entity, long gameTime) {
        return entity.hasActiveRaid()
                && !entity.getCurrentRaid().isOver()
                && entity.level() instanceof ServerLevel
                && !MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(entity, 8);
    }

    @Override
    protected void tick(T entity) {
        if (entity.hasActiveRaid()) {
            SkulkinRaid raid = entity.getCurrentRaid();
            if (entity.tickCount > this.recruitmentTick) {
                this.recruitmentTick = entity.tickCount + 20;
                this.recruitNearby(raid, entity);
            }

            if (!BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET)) {
                Vec3 vec3 = DefaultRandomPos.getPosTowards(entity, 15, 4, Vec3.atBottomCenterOf(raid.getCenter()), (float) (Math.PI / 2));
                if (vec3 != null) {
                    BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(vec3, 1.0f, 8));
                }
            }
        }
    }

    private void recruitNearby(SkulkinRaid raid, T entity) {
        if (raid.isActive()) {
            Set<SkulkinRaider> set = Sets.newHashSet();
            List<SkulkinRaider> list = entity
                    .level()
                    .getEntitiesOfClass(SkulkinRaider.class, entity.getBoundingBox().inflate(16.0), raiderx -> !raiderx.hasActiveRaid() && SkulkinRaids.canJoinSkulkinRaid(raiderx, raid));
            set.addAll(list);

            for (SkulkinRaider raider : set) {
                raid.joinRaid(raid.getGroupsSpawned(), raider, null, true);
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
