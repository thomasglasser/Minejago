package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BouncingPoleSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<BouncingPoleSpinjitzuCourseElement> {
    public static final Vec3 VISIT_BOX = new Vec3(0.625, 3.0625, 0.625);
    public static final int MAX_BOUNCE_TICKS = 10;
    public static final int HALF_BOUNCE_TICKS = MAX_BOUNCE_TICKS / 2;

    private float bounceTicks;

    public BouncingPoleSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level, VISIT_BOX);
        BouncingPoleSpinjitzuCoursePart pole = new BouncingPoleSpinjitzuCoursePart(this, "pole", 0.625f, 1.51f, 0, 0, 0);
        defineParts(pole);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    public void tick() {
        if (this.bounceTicks > 0)
            this.bounceTicks--;
        else
            this.bounceTicks = MAX_BOUNCE_TICKS;
        super.tick();
    }

    public float getBounceTicks() {
        return bounceTicks;
    }

    @Override
    public void checkPartCollision(SpinjitzuCourseElementPart<BouncingPoleSpinjitzuCourseElement> part, Entity entity) {
        bounceUp(entity);
    }

    private static class BouncingPoleSpinjitzuCoursePart extends SpinjitzuCourseElementPart<BouncingPoleSpinjitzuCourseElement> {
        public BouncingPoleSpinjitzuCoursePart(BouncingPoleSpinjitzuCourseElement parent, String name, float width, float height, float offsetX, float offsetY, float offsetZ) {
            super(parent, name, width, height, offsetX, offsetY, offsetZ);
        }

        @Override
        public void calculatePosition() {
            float bounceTicks = getParent().getBounceTicks();
            float yOffset = bounceTicks < HALF_BOUNCE_TICKS ? bounceTicks : MAX_BOUNCE_TICKS - bounceTicks;
            moveTo(getParent().getX(), getParent().getY() + (yOffset / 16f), getParent().getZ());
        }
    }
}
