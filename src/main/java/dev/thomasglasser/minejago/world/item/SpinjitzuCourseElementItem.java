package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class SpinjitzuCourseElementItem extends Item {
    private Supplier<EntityType<? extends AbstractSpinjitzuCourseElement<?>>> entityType;

    public SpinjitzuCourseElementItem(Supplier<EntityType<? extends AbstractSpinjitzuCourseElement<?>>> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            AbstractSpinjitzuCourseElement<?> entity = entityType.get().create(context.getLevel());
            if (entity != null) {
                BlockPos pos = context.getClickedPos();
                if (context.getLevel().getBlockState(pos).canBeReplaced()) {
                    context.getLevel().removeBlock(pos, false);
                    pos = pos.below();
                }
                if (context.getPlayer() != null)
                    entity.teleportTo((ServerLevel) context.getLevel(), pos.above().getBottomCenter().x, pos.above().getBottomCenter().y, pos.above().getBottomCenter().z, Set.of(), context.getPlayer().getDirection().getOpposite().toYRot(), 0.0F);
                else
                    entity.moveTo(pos.above().getBottomCenter());
                context.getLevel().addFreshEntity(entity);
                if (!(context.getPlayer() != null && context.getPlayer().getAbilities().instabuild))
                    context.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.FAIL;
        }
    }
}
