package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.AbstractSkulkinVehicle;
import dev.thomasglasser.minejago.world.entity.skulkin.SkulkinHorse;
import dev.thomasglasser.minejago.world.entity.skulkin.SkullTruck;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.AbstractSkulkinRaid;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.StolenItems;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.StolenItemsHolder;
import dev.thomasglasser.minejago.world.level.MinejagoLevels;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.item.ItemStack;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

public class FleeSkulkinRaidAndDespawn<T extends SkulkinRaider> extends ExtendedBehaviour<T> {
    public static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, T entity) {
        AbstractSkulkinRaid raid = entity.getCurrentRaid();
        return !BrainUtils.hasMemory(entity, MemoryModuleType.ATTACK_TARGET)
                && !entity.isVehicle()
                && raid != null
                && raid.getEscapePos() != null;
    }

    @Override
    protected boolean shouldKeepRunning(T entity) {
        return true;
    }

    @Override
    protected void tick(T entity) {
        AbstractSkulkinRaid raid = entity.getCurrentRaid();
        if (raid != null && raid.getEscapePos() != null) {
            if (entity.position().closerThan(raid.getEscapePos(), 5)) {
                // TODO: Place underworld portal blocks
                ItemStack mainHandItem = entity.getItemInHand(InteractionHand.MAIN_HAND);
                boolean mainHandValid = raid.isValidRaidItem(mainHandItem);
                ItemStack offHandItem = entity.getItemInHand(InteractionHand.OFF_HAND);
                boolean offHandValid = raid.isValidRaidItem(offHandItem);
                if (mainHandValid || offHandValid) {
                    StolenItems stolenItems = StolenItemsHolder.of(entity.getServer().getLevel(MinejagoLevels.UNDERWORLD)).minejago$getStolenItems();
                    if (mainHandValid)
                        stolenItems.add(mainHandItem);
                    if (offHandValid)
                        stolenItems.add(offHandItem);
                    raid.setDefeat();
                }
                if (entity.getVehicle() != null)
                    entity.getVehicle().remove(Entity.RemovalReason.CHANGED_DIMENSION);
                entity.remove(Entity.RemovalReason.CHANGED_DIMENSION);
                raid.updateBossbar();
                return;
            } else if (MinejagoServerConfig.get().enableTech.get()) {
                if (entity.getType().is(MinejagoEntityTypeTags.SKULL_TRUCK_RIDERS)) {
                    List<SkullTruck> trucks = entity.level().getEntitiesOfClass(SkullTruck.class, entity.getBoundingBox().inflate(8), truck -> truck.getPassengers().size() < 3);
                    if (!trucks.isEmpty()) entity.startRiding(trucks.getFirst());
                } else {
                    List<AbstractSkulkinVehicle> bikes = entity.level().getEntitiesOfClass(AbstractSkulkinVehicle.class, entity.getBoundingBox().inflate(8), bike -> bike.getType() == MinejagoEntityTypes.SKULL_MOTORBIKE.get() && bike.getControllingPassenger() == null);
                    if (!bikes.isEmpty()) entity.startRiding(bikes.getFirst());
                }
            } else {
                List<SkulkinHorse> horses = entity.level().getEntitiesOfClass(SkulkinHorse.class, entity.getBoundingBox().inflate(8), skulkinHorse -> !skulkinHorse.hasControllingPassenger());
                if (!horses.isEmpty()) entity.startRiding(horses.getFirst());
            }
            BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(raid.getEscapePos(), 1.4f, 4));
        }
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }
}
