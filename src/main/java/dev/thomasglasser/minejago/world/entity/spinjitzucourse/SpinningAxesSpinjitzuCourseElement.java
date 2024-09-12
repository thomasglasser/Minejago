package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SpinningAxesSpinjitzuCourseElement extends PlatformedSpinjitzuCourseElement<SpinningAxesSpinjitzuCourseElement> {
    public SpinningAxesSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        SpinningAxesSpinjitzuCourseElementPart axe1 = new SpinningAxesSpinjitzuCourseElementPart(this, "axe1", 0.5f, 0.5f, 0, 1.75f, 1.125f, true);
        SpinningAxesSpinjitzuCourseElementPart axe2 = new SpinningAxesSpinjitzuCourseElementPart(this, "axe2", 0.5f, 0.5f, 0, 1.75f, -1.125f, false);
        SpinningAxesSpinjitzuCourseElementPart axe3 = new SpinningAxesSpinjitzuCourseElementPart(this, "axe3", 0.5f, 0.5f, 1.125f, 1.75f, 0, false);
        SpinningAxesSpinjitzuCourseElementPart axe4 = new SpinningAxesSpinjitzuCourseElementPart(this, "axe4", 0.5f, 0.5f, -1.125f, 1.75f, 0, true);
        defineParts(bottom, top, axe1, axe2, axe3, axe4);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    public void checkPartCollision(AbstractSpinjitzuCourseElementPart<SpinningAxesSpinjitzuCourseElement> part, Entity entity) {
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
            float speedMultiplier = MinejagoServerConfig.get().courseSpeed.get().floatValue();
            float yOffset = Mth.sin(speedMultiplier * getParent().tickCount) * maxAngle;
            if (reverse) {
                yOffset = -yOffset;
            }
            moveTo(getX(), getY() - yOffset, getZ());
        }
    }
}
