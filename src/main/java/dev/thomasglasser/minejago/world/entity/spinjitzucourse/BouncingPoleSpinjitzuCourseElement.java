package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BouncingPoleSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<BouncingPoleSpinjitzuCourseElement> {
    public static final int MAX_BOUNCE_TICKS = 10;
    public static final int HALF_BOUNCE_TICKS = MAX_BOUNCE_TICKS / 2;

    private final SpinjitzuCourseElementPart<BouncingPoleSpinjitzuCourseElement> pole;
    private float bounceTicks;

    public BouncingPoleSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.pole = new BouncingPoleSpinjitzuCoursePart(this, "pole", 0.625f, 1.51f, 0, 0, 0);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    protected List<SpinjitzuCourseElementPart<BouncingPoleSpinjitzuCourseElement>> getSubEntities() {
        return List.of(this.pole);
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
    public void checkPartCollisions(SpinjitzuCourseElementPart<BouncingPoleSpinjitzuCourseElement> part) {
        level().getEntities(this, part.getBoundingBox(), EntitySelector.NO_SPECTATORS).forEach(this::bounceUp);
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

        @Override
        public boolean canBeCollidedWith() {
            return true;
        }

        @Override
        public boolean isPickable() {
            return !this.isRemoved();
        }
    }

    private void bounceUp(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < 0.0) {
            entity.setDeltaMovement(vec3.x, 0.5, vec3.z);
        }
    }
}
