package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

public abstract class AbstractSeekAndTake<T extends SkulkinRaider> extends ExtendedBehaviour<T> {
    protected static final Pair<MemoryModuleType<?>, MemoryStatus> WALK_TARGET_REQUIREMENT = Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED);

    protected float speedModifier = 1;

    public AbstractSeekAndTake<T> speedModifier(float speedMod) {
        this.speedModifier = speedMod;

        return this;
    }

    protected boolean isValidRaid(T entity) {
        return entity.hasActiveRaid() && !entity.getCurrentRaid().isOver();
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        return this.isValidRaid(entity) && (entity.getTarget() == null || entity.getTarget().isRemoved());
    }
}
