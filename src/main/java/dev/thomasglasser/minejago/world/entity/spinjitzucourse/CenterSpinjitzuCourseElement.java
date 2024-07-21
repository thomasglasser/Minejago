package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class CenterSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<CenterSpinjitzuCourseElement> {
    public static final EntityDimensions IDLE_DIMENSIONS = EntityDimensions.scalable(1.25f, 2);
    public static final EntityDimensions ACTIVE_DIMENSIONS = EntityDimensions.scalable(1.25f, 6);

    private final ArrayList<AbstractSpinjitzuCourseElement<?>> courseElements;
    private final SpinjitzuCourseElementPart<CenterSpinjitzuCourseElement> bag;
    private final SpinjitzuCourseElementPart<CenterSpinjitzuCourseElement> spikes;

    public CenterSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.courseElements = new ArrayList<>();
        this.bag = new CenterSpinjitzuCourseElementPart(this, "bag", 0.5f, 1.75f, 0, 0.9375, 1.9375);
        this.spikes = new CenterSpinjitzuCourseElementPart(this, "spikes", 0.5f, 1f, 0, 1.6875, -1.9375);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.CENTER_SPINJITZU_COURSE_ELEMENT.get();
    }

    public void addCourseElement(AbstractSpinjitzuCourseElement<?> courseElement) {
        this.courseElements.add(courseElement);
    }

    public ArrayList<AbstractSpinjitzuCourseElement<?>> getCourseElements() {
        return this.courseElements;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (isActive())
            return ACTIVE_DIMENSIONS;
        else
            return IDLE_DIMENSIONS;
    }

    @Override
    protected List<SpinjitzuCourseElementPart<CenterSpinjitzuCourseElement>> getSubEntities() {
        return List.of(this.bag, this.spikes);
    }

    private static class CenterSpinjitzuCourseElementPart extends SpinjitzuCourseElementPart<CenterSpinjitzuCourseElement> {
        public CenterSpinjitzuCourseElementPart(CenterSpinjitzuCourseElement parent, String name, float width, float height, double offsetX, double offsetY, double offsetZ) {
            super(parent, name, width, height, offsetX, offsetY, offsetZ);
        }

        @Override
        public void calculatePosition() {
            double angleRadians = getParent().tickCount * 0.5f;
            double cos = Math.cos(angleRadians);
            double sin = Math.sin(angleRadians);
            double rotatedX = offsetX * cos + offsetZ * sin;
            double rotatedZ = offsetZ * cos - offsetX * sin;
            double resultingX = rotatedX + getParent().getX();
            double resultingZ = rotatedZ + getParent().getZ();
            this.moveTo(resultingX, getParent().getY() + offsetY, resultingZ);
        }
    }
}
