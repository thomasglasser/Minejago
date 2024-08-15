package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class RockingPoleSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<RockingPoleSpinjitzuCourseElement> {
    private final SpinjitzuCourseElementPart<RockingPoleSpinjitzuCourseElement> pole;

    public RockingPoleSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.pole = new RockingPoleSpinjitzuCoursePart(this, "pole", 0.625f, 1.3125f, 0, 0, 0);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    protected List<SpinjitzuCourseElementPart<RockingPoleSpinjitzuCourseElement>> getSubEntities() {
        return List.of(this.pole);
    }

    @Override
    public void checkPartCollision(SpinjitzuCourseElementPart<RockingPoleSpinjitzuCourseElement> part, Entity entity) {
        bounceUp(entity);
    }

    private static class RockingPoleSpinjitzuCoursePart extends SpinjitzuCourseElementPart<RockingPoleSpinjitzuCourseElement> {
        public RockingPoleSpinjitzuCoursePart(RockingPoleSpinjitzuCourseElement parent, String name, float width, float height, float offsetX, float offsetY, float offsetZ) {
            super(parent, name, width, height, offsetX, offsetY, offsetZ);
        }

        @Override
        public void calculatePosition() {
            float maxAngle = 1f;
            float speedMultiplier = 0.5f;
            float zOffset = Mth.sin(speedMultiplier * getParent().tickCount) * maxAngle;
            float yOffset = (zOffset < 0 ? zOffset : -zOffset) + 0.3f;
            moveTo(getParent().getX(), getParent().getY() + yOffset, getParent().getZ() - zOffset);
        }
    }
}
