package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RockingPoleSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<RockingPoleSpinjitzuCourseElement> {
    public static final Vec3 VISIT_BOX = new Vec3(0.625, 2, 1.875);

    public RockingPoleSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level, VISIT_BOX);
        AbstractSpinjitzuCourseElementPart<RockingPoleSpinjitzuCourseElement> pole = new RockingPoleSpinjitzuCoursePart(this, "pole", 0.625f, 1.3125f, 0, 0, 0);
        defineParts(pole);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    public void checkPartCollision(AbstractSpinjitzuCourseElementPart<RockingPoleSpinjitzuCourseElement> part, Entity entity) {
        bounceUp(entity);
    }

    private static class RockingPoleSpinjitzuCoursePart extends AbstractSpinjitzuCourseElementPart<RockingPoleSpinjitzuCourseElement> {
        public RockingPoleSpinjitzuCoursePart(RockingPoleSpinjitzuCourseElement parent, String name, float width, float height, float offsetX, float offsetY, float offsetZ) {
            super(parent, name, width, height, offsetX, offsetY, offsetZ);
        }

        @Override
        public void calculatePosition() {
            float maxAngle = 1f;
            float speedMultiplier = MinejagoServerConfig.get().courseSpeed.get().floatValue();
            float poleOffset = Mth.sin(speedMultiplier * getParent().tickCount) * maxAngle;
            float yOffset = (poleOffset < 0 ? poleOffset : -poleOffset) + 0.3f;
            float xOffset = 0;
            float zOffset = 0;
            switch (getParent().getDirection().getClockWise()) {
                case DOWN, UP, NORTH -> zOffset = -poleOffset;
                case WEST -> xOffset = -poleOffset;
                case SOUTH -> zOffset = poleOffset;
                case EAST -> xOffset = poleOffset;
            }
            moveTo(getParent().getX() + xOffset, getParent().getY() + yOffset, getParent().getZ() + zOffset);
        }
    }
}
