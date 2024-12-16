package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
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
                entity.moveTo(pos.above().getBottomCenter());
                if (context.getPlayer() != null)
                    entity.setYRotSynced(context.getPlayer().getDirection().getOpposite().toYRot());
                context.getLevel().addFreshEntity(entity);
                if (!(context.getPlayer() != null && context.getPlayer().getAbilities().instabuild))
                    context.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.FAIL;
        }
    }
}
