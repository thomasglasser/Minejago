package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SpinningMacesSpinjitzuCourseElement extends PlatformedSpinjitzuCourseElement<SpinningMacesSpinjitzuCourseElement> {
    public SpinningMacesSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        SpinningSpinjitzuCourseElementPart<SpinningMacesSpinjitzuCourseElement> mace1 = new SpinningSpinjitzuCourseElementPart<>(this, "mace1", 0.625f, 0.5625f, 0, 1.6875f, 1.25f);
        SpinningSpinjitzuCourseElementPart<SpinningMacesSpinjitzuCourseElement> mace2 = new SpinningSpinjitzuCourseElementPart<>(this, "mace2", 0.625f, 0.5625f, 0, 1.6875f, -1.25f);
        defineParts(top, bottom, mace1, mace2);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT.get();
    }
}
