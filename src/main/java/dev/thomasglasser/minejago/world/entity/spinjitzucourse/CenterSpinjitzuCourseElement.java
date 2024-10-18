package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CenterSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<CenterSpinjitzuCourseElement> {
    public static final Vec3 VISIT_BOX = new Vec3(4.375, 2.6875, 4.375);
    public static final EntityDimensions IDLE_DIMENSIONS = EntityDimensions.scalable(1.25f, 2);
    public static final EntityDimensions ACTIVE_DIMENSIONS = EntityDimensions.scalable(1.25f, 6);

    public CenterSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level, VISIT_BOX);
        SpinningSpinjitzuCourseElementPart<CenterSpinjitzuCourseElement> bag = new SpinningSpinjitzuCourseElementPart<>(this, "bag", 0.5f, 1.75f, 0, 0.9375f, 1.9375f);
        SpinningSpinjitzuCourseElementPart<CenterSpinjitzuCourseElement> spikes = new SpinningSpinjitzuCourseElementPart<>(this, "spikes", 0.5f, 1f, 0, 1.6875f, -1.9375f);
        defineParts(bag, spikes);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.CENTER_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return MinejagoSoundEvents.CENTER_ACTIVE.get();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (isActive())
            return ACTIVE_DIMENSIONS;
        else
            return IDLE_DIMENSIONS;
    }

    @Override
    public boolean shouldIncludeInTracking() {
        return false;
    }
}
