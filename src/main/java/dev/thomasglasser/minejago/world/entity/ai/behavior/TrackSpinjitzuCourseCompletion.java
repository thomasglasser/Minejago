package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.entity.ai.poi.MinejagoPoiTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

public class TrackSpinjitzuCourseCompletion<T extends Wu> extends ExtendedBehaviour<T> {
    // TODO: Animation times
    protected static final int SIT_DOWN_DURATION = 20;
    protected static final int PAPER_DURATION = 20;
    protected static final int CUP_DURATION = 20;
    protected static final int TEA_DURATION = 20;
    protected static final int SUGAR_DURATION = 20;
    protected static final int LIFT_DURATION = 20;
    protected static final int INTERRUPTED_DURATION = 20;
    protected static final int PUT_DOWN_DURATION = 20;

    protected int maxTime;
    protected int timeLeft;

    public TrackSpinjitzuCourseCompletion() {
        super();
        runFor(wu -> Integer.MAX_VALUE);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        if (level.getPoiManager().findClosest(poi -> poi.is(MinejagoPoiTypes.TEAPOTS), entity.blockPosition(), 2, PoiManager.Occupancy.ANY).isPresent()) {
            return super.checkExtraStartConditions(level, entity);
        } else if (BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET) == null && !entity.getCourseData().isEmpty()) {
            Optional<BlockPos> potPos = level.getPoiManager().findClosest(poi -> poi.is(MinejagoPoiTypes.TEAPOTS), entity.blockPosition(), MinejagoServerConfig.INSTANCE.courseRadius.get(), PoiManager.Occupancy.ANY);
            if (potPos.isPresent())
                BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(potPos.get(), 1f, 0));
            else
                entity.getCourseData().clear();
        }
        return false;
    }

    @Override
    protected void start(T entity) {
        super.start(entity);
        // TODO: Signal begin (sit down and make noise?)
        this.maxTime = MinejagoServerConfig.INSTANCE.courseTimeLimit.get() * SharedConstants.TICKS_PER_SECOND;
        this.timeLeft = maxTime;
    }

    @Override
    protected void tick(T entity) {
        super.tick(entity);
        timeLeft--;
        int timePassed = maxTime - timeLeft;
        // Sit down, then paper, then cup, then tea, then sugar, then drink until time is almost up, then put down cup
        // TODO: Ensure interruption is handled correctly visually
        List<Player> currentPlayers = new ArrayList<>(entity.getCourseData().keySet());
        if (timePassed == 1) {
            // TODO: Sit down
            currentPlayers.forEach(player -> player.sendSystemMessage(Component.literal("Sitting down")));
        } else if (timePassed == SIT_DOWN_DURATION && !entity.wasInterrupted()) {
            // TODO: Paper
            currentPlayers.forEach(player -> player.sendSystemMessage(Component.literal("Placing paper")));
        } else if (entity.wasInterrupted() ? timePassed == SIT_DOWN_DURATION + PAPER_DURATION + INTERRUPTED_DURATION : timePassed == SIT_DOWN_DURATION + PAPER_DURATION) {
            if (entity.wasInterrupted())
                entity.setInterrupted(false);
            // TODO: Cup
            currentPlayers.forEach(player -> player.sendSystemMessage(Component.literal("Placing cup")));
        } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION + CUP_DURATION) {
            // TODO: Tea
            currentPlayers.forEach(player -> player.sendSystemMessage(Component.literal("Pouring tea")));
        } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION + CUP_DURATION + TEA_DURATION) {
            // TODO: Sugar
            currentPlayers.forEach(player -> player.sendSystemMessage(Component.literal("Adding sugar")));
        } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION + CUP_DURATION + TEA_DURATION + SUGAR_DURATION) {
            // TODO: Lift cup
            entity.setLifting(true);
            currentPlayers.forEach(player -> player.sendSystemMessage(Component.literal("Lifting cup")));
        } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION + CUP_DURATION + TEA_DURATION + SUGAR_DURATION + LIFT_DURATION) {
            // TODO: Drink
            entity.setLifting(false);
            currentPlayers.forEach(player -> player.sendSystemMessage(Component.literal("Drinking tea")));
        } else if (timeLeft == PUT_DOWN_DURATION) {
            // TODO: Put down
            currentPlayers.forEach(player -> player.sendSystemMessage(Component.literal("Putting down cup")));
        }

        // If cup swatted away, go back for another
        if (entity.isLifting() && entity.wasInterrupted()) {
            timeLeft += INTERRUPTED_DURATION + CUP_DURATION + TEA_DURATION + SUGAR_DURATION;
            entity.setLifting(false);
            // TODO: Play sad noise and interrupt animation
        }
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        return timeLeft > 0;
    }

    @Override
    protected void stop(T entity) {
        super.stop(entity);
        entity.setLifting(false);
        entity.setInterrupted(false);
        // TODO: Signal end (stand up and make noise?)
        // TODO: Make sad noise (particles?)
        Set<Player> currentPlayers = Set.copyOf(entity.getCourseData().keySet());
        currentPlayers.forEach((player) -> {
            player.sendSystemMessage(Component.literal("Time's up"));
            entity.stopTracking(player);
            entity.getEntitiesOnCooldown().computeIfAbsent((int) (entity.level().getGameTime() + SharedConstants.TICKS_PER_GAME_DAY), k -> new ArrayList<>()).add(player);
        });
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return List.of();
    }
}
