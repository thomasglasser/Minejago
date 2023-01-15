package dev.thomasglasser.minejago.world.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.example.SBLSkeleton;

public class MeleeSkeleton extends SBLSkeleton
{
    public MeleeSkeleton(EntityType<? extends MeleeSkeleton> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public BrainActivityGroup<SBLSkeleton> getCoreTasks() {
        return super.getCoreTasks().behaviours(
                new SetWalkTargetToAttackTarget<>().startCondition((entity) -> !(entity.isHolding(stack -> stack.getItem() instanceof BowItem)))
        );
    }
}
