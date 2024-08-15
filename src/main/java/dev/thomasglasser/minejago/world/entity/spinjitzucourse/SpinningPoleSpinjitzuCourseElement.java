package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SpinningPoleSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<SpinningPoleSpinjitzuCourseElement> {
    public SpinningPoleSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    protected List<SpinjitzuCourseElementPart<SpinningPoleSpinjitzuCourseElement>> getSubEntities() {
        return List.of();
    }

    @Override
    public void tick() {
        super.tick();
        if (isFullyActive()) {
            List<LivingEntity> inside = level().getEntities(this, getBoundingBox().inflate(0.875, 0, 0.875), entity -> entity instanceof LivingEntity).stream().map(entity -> (LivingEntity) entity).toList();
            for (LivingEntity entity : inside) {
                entity.knockback(5.0F, entity.getX() - this.getX(), entity.getZ() - this.getZ());
            }
        }
    }
}
