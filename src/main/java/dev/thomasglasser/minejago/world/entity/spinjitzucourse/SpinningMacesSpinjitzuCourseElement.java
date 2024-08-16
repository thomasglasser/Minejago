package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class SpinningMacesSpinjitzuCourseElement extends PlatformedSpinjitzuCourseElement<SpinningMacesSpinjitzuCourseElement> {
    private final SpinningSpinjitzuCourseElementPart<SpinningMacesSpinjitzuCourseElement> mace1;
    private final SpinningSpinjitzuCourseElementPart<SpinningMacesSpinjitzuCourseElement> mace2;

    public SpinningMacesSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.mace1 = new SpinningSpinjitzuCourseElementPart<>(this, "mace1", 0.625f, 0.5625f, 0, 1.6875f, 1.25f);
        this.mace2 = new SpinningSpinjitzuCourseElementPart<>(this, "mace2", 0.625f, 0.5625f, 0, 1.6875f, -1.25f);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    protected List<SpinjitzuCourseElementPart<SpinningMacesSpinjitzuCourseElement>> getSubEntities() {
        return List.of(top, bottom, mace1, mace2);
    }
}
