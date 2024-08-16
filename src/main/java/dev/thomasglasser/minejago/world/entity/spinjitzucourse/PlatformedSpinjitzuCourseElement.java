package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class PlatformedSpinjitzuCourseElement<T extends PlatformedSpinjitzuCourseElement<T>> extends AbstractSpinjitzuCourseElement<T> {
    public static final Vec3 PLATFORMED_VISIT_BOX = new Vec3(3.625, 4.25, 3.625);

    protected final PlatformSpinjitzuCoursePart bottom;
    protected final PlatformSpinjitzuCoursePart top;

    public PlatformedSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level, PLATFORMED_VISIT_BOX);
        bottom = new PlatformSpinjitzuCoursePart((T) this, false);
        top = new PlatformSpinjitzuCoursePart((T) this, true);
    }

    protected class PlatformSpinjitzuCoursePart extends SpinjitzuCourseElementPart<T> {
        public PlatformSpinjitzuCoursePart(T parent, String name, float width, float height, double offsetX, double offsetY, double offsetZ) {
            super(parent, name, width, height, offsetX, offsetY, offsetZ);
            moveTo(getParent().getX() + offsetX, getParent().getY() + offsetY, getParent().getZ() + offsetZ);
        }

        public PlatformSpinjitzuCoursePart(T parent, boolean top) {
            this(parent, top ? "top" : "bottom", 3.625f, 0.3125f, 0, top ? 3.9375f : 0, 0);
        }

        @Override
        public boolean canBeCollidedWith() {
            return true;
        }

        @Override
        public void calculatePosition() {
            this.moveTo(getParent().getX() + offsetX, getParent().getY() + offsetY, getParent().getZ() + offsetZ);
        }
    }
}
