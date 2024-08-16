package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SpinningAxesSpinjitzuCourseElement extends PlatformedSpinjitzuCourseElement<SpinningAxesSpinjitzuCourseElement> {
    private final SpinningAxesSpinjitzuCourseElementPart axe1;
    private final SpinningAxesSpinjitzuCourseElementPart axe2;
    private final SpinningAxesSpinjitzuCourseElementPart axe3;
    private final SpinningAxesSpinjitzuCourseElementPart axe4;

    public SpinningAxesSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        axe1 = new SpinningAxesSpinjitzuCourseElementPart(this, "axe1", 0.5f, 0.5f, 0, 1.75f, 1.125f, true);
        axe2 = new SpinningAxesSpinjitzuCourseElementPart(this, "axe2", 0.5f, 0.5f, 0, 1.75f, -1.125f, false);
        axe3 = new SpinningAxesSpinjitzuCourseElementPart(this, "axe3", 0.5f, 0.5f, 1.125f, 1.75f, 0, false);
        axe4 = new SpinningAxesSpinjitzuCourseElementPart(this, "axe4", 0.5f, 0.5f, -1.125f, 1.75f, 0, true);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    protected List<SpinjitzuCourseElementPart<SpinningAxesSpinjitzuCourseElement>> getSubEntities() {
        return List.of(bottom, top, axe1, axe2, axe3, axe4);
    }

    @Override
    public void checkPartCollision(SpinjitzuCourseElementPart<SpinningAxesSpinjitzuCourseElement> part, Entity entity) {
        entity.hurt(damageSources().trident(part, this), 4.0f);
    }

    protected static class SpinningAxesSpinjitzuCourseElementPart extends SpinningSpinjitzuCourseElementPart<SpinningAxesSpinjitzuCourseElement> {
        private final boolean reverse;

        public SpinningAxesSpinjitzuCourseElementPart(SpinningAxesSpinjitzuCourseElement parent, String name, float width, float height, float offsetX, float offsetY, float offsetZ, boolean reverse) {
            super(parent, name, width, height, offsetX, offsetY, offsetZ);
            this.reverse = reverse;
        }

        @Override
        public void calculatePosition() {
            super.calculatePosition();
            float maxAngle = 1f;
            float speedMultiplier = 0.5f;
            float yOffset = Mth.sin(speedMultiplier * getParent().tickCount) * maxAngle;
            if (reverse) {
                yOffset = -yOffset;
            }
            moveTo(getX(), getY() - yOffset, getZ());
        }
    }
}
