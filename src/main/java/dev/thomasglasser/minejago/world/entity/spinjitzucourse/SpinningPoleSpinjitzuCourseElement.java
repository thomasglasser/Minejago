package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SpinningPoleSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<SpinningPoleSpinjitzuCourseElement> {
    public static final EntityDimensions IDLE_DIMENSIONS = EntityDimensions.scalable(0.625f, 0.375f);
    public static final EntityDimensions ACTIVE_DIMENSIONS = EntityDimensions.scalable(0.625f, 2.0625f);

    public SpinningPoleSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (isActive())
            return ACTIVE_DIMENSIONS;
        return IDLE_DIMENSIONS;
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    protected List<SpinjitzuCourseElementPart<SpinningPoleSpinjitzuCourseElement>> getSubEntities() {
        return List.of();
    }
}
