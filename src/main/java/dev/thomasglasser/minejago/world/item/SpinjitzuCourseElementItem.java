package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import java.util.function.Supplier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.Vec3;

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
                Vec3 pos = context.getClickLocation();
                if (context.getLevel().getBlockState(context.getClickedPos()).canBeReplaced()) {
                    context.getLevel().removeBlock(context.getClickedPos(), false);
                    pos = pos.add(0, -0.5, 0);
                }
                entity.moveTo(pos);
                entity.setYRot(context.getPlayer().getYRot());
                context.getLevel().addFreshEntity(entity);
                if (!(context.getPlayer() != null && context.getPlayer().getAbilities().instabuild))
                    context.getItemInHand().shrink(1);
                return InteractionResult.CONSUME;
            }
            return InteractionResult.FAIL;
        }
    }
}
