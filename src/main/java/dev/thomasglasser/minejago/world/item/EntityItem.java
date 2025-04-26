package dev.thomasglasser.minejago.world.item;

import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class EntityItem<T extends Entity> extends Item {
    private Supplier<EntityType<T>> entityType;

    public EntityItem(Supplier<EntityType<T>> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            T entity = createEntity((ServerLevel) context.getLevel());
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

    protected T createEntity(ServerLevel level) {
        return entityType.get().create(level);
    }
}
