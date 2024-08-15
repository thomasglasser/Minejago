package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.constant.DefaultAnimations;

public class SwirlingKnivesSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<SwirlingKnivesSpinjitzuCourseElement> {
    private final PlatformSpinjitzuCoursePart bottom;
    private final PlatformSpinjitzuCoursePart top;
    private final SpinningSpinjitzuCourseElementPart<SwirlingKnivesSpinjitzuCourseElement> knives1;
    private final SpinningSpinjitzuCourseElementPart<SwirlingKnivesSpinjitzuCourseElement> knives2;

    public SwirlingKnivesSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        bottom = new PlatformSpinjitzuCoursePart(this, false);
        top = new PlatformSpinjitzuCoursePart(this, true);
        knives1 = new SpinningSpinjitzuCourseElementPart<>(this, "knives_1", 1.25f, 1.25f, 0, 1.5f, 1.125f);
        knives2 = new SpinningSpinjitzuCourseElementPart<>(this, "knives_2", 1.25f, 1.25f, 0, 1.5f, -1.125f);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    protected List<SpinjitzuCourseElementPart<SwirlingKnivesSpinjitzuCourseElement>> getSubEntities() {
        return List.of(knives1, knives2, bottom, top);
    }

    @Override
    public void checkPartCollision(SpinjitzuCourseElementPart<SwirlingKnivesSpinjitzuCourseElement> part, Entity entity) {
        super.checkPartCollision(part, entity);
        entity.hurt(damageSources().trident(part, this), 2);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (isActive())
                return state.setAndContinue(DefaultAnimations.LIVING);
            else
                return state.setAndContinue(DefaultAnimations.IDLE);
        })
                .triggerableAnim(DEPLOY_KEY, DefaultAnimations.DEPLOY)
                .triggerableAnim(REST_KEY, DefaultAnimations.REST));
    }
}
