package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;

public class SeekAndTakeNearbyItems<T extends SkulkinRaider> extends AbstractSeekAndTake<T> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(WALK_TARGET_REQUIREMENT, Pair.of(SBLMemoryTypes.NEARBY_ITEMS.get(), MemoryStatus.VALUE_PRESENT));

    protected Predicate<ItemStack> predicate = stack -> true;

    @Override
    public SeekAndTakeNearbyItems<T> speedModifier(float speedMod) {
        return (SeekAndTakeNearbyItems<T>) super.speedModifier(speedMod);
    }

    public SeekAndTakeNearbyItems<T> setPredicate(Predicate<ItemStack> predicate) {
        this.predicate = predicate;

        return this;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        if (!super.checkExtraStartConditions(level, entity) || !entity.canPickUpLoot())
            return false;
        List<ItemEntity> items = BrainUtils.getMemory(entity, SBLMemoryTypes.NEARBY_ITEMS.get());
        return items != null && !items.isEmpty();
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        List<ItemEntity> items = BrainUtils.getMemory(entity, SBLMemoryTypes.NEARBY_ITEMS.get());
        return items != null && !items.isEmpty();
    }

    @Override
    protected void tick(ServerLevel level, T entity, long gameTime) {
        super.tick(level, entity, gameTime);
        if (!BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET)) {
            if (predicate.test(entity.getOffhandItem())) {
                entity.setDropChance(EquipmentSlot.OFFHAND, 2.0F);
                Vec3 escapePos = null;
                while (escapePos == null) {
                    escapePos = DefaultRandomPos.getPosAway(entity, 64, 32, Vec3.atBottomCenterOf(entity.getCurrentRaid().getCenter()));
                }
                entity.getCurrentRaid().setLoss(escapePos);
            } else {
                BrainUtils.getMemory(entity, SBLMemoryTypes.NEARBY_ITEMS.get()).stream().filter(iE -> predicate.test(iE.getItem())).findFirst().ifPresent(item -> {
                    BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(item.position(), speedModifier, 1));
                    entity.stopRiding();
                });
            }
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
