package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SpinningPoleSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<SpinningPoleSpinjitzuCourseElement> {
    private static final Vec3 VISIT_BOX = new Vec3(2.25, 2.125, 2.25);
    private static final Vec3 LAUNCH_BOX = new Vec3(2.25, 1.6875, 2.25);

    public SpinningPoleSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level, VISIT_BOX);
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
            List<LivingEntity> inside = level().getEntities(this, AABB.ofSize(position(), LAUNCH_BOX.x(), LAUNCH_BOX.y(), LAUNCH_BOX.z()), entity -> entity instanceof LivingEntity).stream().map(entity -> (LivingEntity) entity).toList();
            for (LivingEntity entity : inside) {
                entity.knockback(5.0F, entity.getX() - this.getX(), entity.getZ() - this.getZ());
            }
        }
    }
}
