package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.entity.ai.poi.MinejagoPoiTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.item.MinejagoItemUtils;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

public class TrackSpinjitzuCourseCompletion<T extends Wu> extends ExtendedBehaviour<T> {
    public static final int SIT_DOWN_DURATION = 15;
    public static final int PAPER_DURATION = 32;
    public static final int CUP_DURATION = 30;
    public static final int TEA_DURATION = 45;
    public static final int SUGAR_DURATION = 40;
    public static final int LIFT_DURATION = 35;
    public static final int INTERRUPTED_DURATION = 30;
    public static final int PUT_DOWN_DURATION = 20;

    private ItemStack originalStack = ItemStack.EMPTY;

    public TrackSpinjitzuCourseCompletion() {
        super();
        runFor(wu -> Integer.MAX_VALUE);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        if (level.getPoiManager().findClosest(poi -> poi.is(MinejagoPoiTypes.TEAPOTS), entity.blockPosition(), 2, PoiManager.Occupancy.ANY).isPresent()) {
            return super.checkExtraStartConditions(level, entity) && !entity.getCourseData().isEmpty();
        } else if (BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET) == null && !entity.getCourseData().isEmpty()) {
            Optional<BlockPos> potPos = level.getPoiManager().findClosest(poi -> poi.is(MinejagoPoiTypes.TEAPOTS), entity.blockPosition(), MinejagoServerConfig.get().courseRadius.get(), PoiManager.Occupancy.ANY);
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
        entity.playSound(SoundEvents.VILLAGER_YES, 1f, 0.9f);
        entity.setMaxTime(MinejagoServerConfig.get().courseTimeLimit.get() * SharedConstants.TICKS_PER_SECOND);
        entity.setTimeLeft(entity.getMaxTime());
        originalStack = entity.getMainHandItem();
        entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
    }

    @Override
    protected void tick(T entity) {
        super.tick(entity);
        if (!entity.tickInterrupted()) {
            int timeLeft = entity.tickTimeLeft();
            int timePassed = entity.getMaxTime() - timeLeft;
            // Sit down, then paper, then cup, then tea, then sugar, then drink until time is almost up, then put down cup
            if (timePassed == 1) {
                entity.setCurrentStage(Stage.SIT_DOWN);
            } else if (timePassed == SIT_DOWN_DURATION) {
                entity.setCurrentStage(Stage.PAPER);
                entity.setItemInHand(InteractionHand.MAIN_HAND, Items.PAPER.getDefaultInstance());
            } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION) {
                entity.setCurrentStage(Stage.PREPARE);
                entity.setItemInHand(InteractionHand.MAIN_HAND, MinejagoItems.TEACUP.toStack());
            } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION + CUP_DURATION) {
                entity.setItemInHand(InteractionHand.MAIN_HAND, MinejagoBlocks.TEAPOTS.get(DyeColor.CYAN).toStack());
            } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION + CUP_DURATION + TEA_DURATION) {
                entity.setItemInHand(InteractionHand.MAIN_HAND, Items.SUGAR.getDefaultInstance());
            } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION + CUP_DURATION + TEA_DURATION + SUGAR_DURATION) {
                entity.setLifting(true);
                entity.setItemInHand(InteractionHand.MAIN_HAND, MinejagoItemUtils.fillTeacup(MinejagoPotions.OAK_TEA));
            } else if (timePassed == SIT_DOWN_DURATION + PAPER_DURATION + CUP_DURATION + TEA_DURATION + SUGAR_DURATION + LIFT_DURATION) {
                entity.setCurrentStage(Stage.DRINK);
                entity.setLifting(false);
            } else if (timeLeft == PUT_DOWN_DURATION) {
                entity.setCurrentStage(Stage.PUT_DOWN);
                entity.setItemInHand(InteractionHand.MAIN_HAND, MinejagoItems.TEACUP.toStack());
            } else if (timeLeft <= 0) {
                stop(entity);
            }
        }
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        return entity.getMaxTime() > 0;
    }

    @Override
    protected void stop(T entity) {
        super.stop(entity);
        entity.setLifting(false);
        entity.setCurrentStage(Stage.STAND_UP);
        entity.setItemInHand(InteractionHand.MAIN_HAND, originalStack);
        // TODO: Stand up
        Set<Player> currentPlayers = Set.copyOf(entity.getCourseData().keySet());
        currentPlayers.forEach((player) -> {
            entity.level().playSound(null, player.blockPosition(), SoundEvents.VILLAGER_NO, entity.getSoundSource(), 1f, 0.9f);
            entity.stopTracking(player);
            entity.getEntitiesOnCooldown().computeIfAbsent((int) (entity.level().getGameTime() + SharedConstants.TICKS_PER_GAME_DAY), k -> new ArrayList<>()).add(player);
        });
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return List.of();
    }

    public enum Stage {
        SIT_DOWN,
        PAPER,
        PREPARE,
        DRINK,
        PUT_DOWN,
        STAND_UP
    }
}
