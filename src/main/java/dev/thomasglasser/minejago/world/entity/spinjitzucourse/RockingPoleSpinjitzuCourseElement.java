package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
    public void checkPartCollisions(SpinjitzuCourseElementPart<RockingPoleSpinjitzuCourseElement> part) {
        level().getEntities(this, part.getBoundingBox(), EntitySelector.NO_SPECTATORS).forEach(this::knockback);
    }

    private void knockback(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < 0.0) {
            entity.setDeltaMovement(vec3.x, 0.5, vec3.z);
        }
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

        @Override
        public boolean canBeCollidedWith() {
            return true;
        }

        @Override
        public boolean isPickable() {
            return !this.isRemoved();
        }
    }
}
