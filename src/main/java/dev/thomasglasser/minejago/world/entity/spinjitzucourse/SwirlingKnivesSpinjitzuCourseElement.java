package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.constant.DefaultAnimations;

public class SwirlingKnivesSpinjitzuCourseElement extends PlatformedSpinjitzuCourseElement<SwirlingKnivesSpinjitzuCourseElement> {
    public SwirlingKnivesSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);

        SpinningSpinjitzuCourseElementPart<SwirlingKnivesSpinjitzuCourseElement> knives1 = new SpinningSpinjitzuCourseElementPart<>(this, "knives_1", 1.25f, 1.25f, 0, 1.5f, 1.125f);
        SpinningSpinjitzuCourseElementPart<SwirlingKnivesSpinjitzuCourseElement> knives2 = new SpinningSpinjitzuCourseElementPart<>(this, "knives_2", 1.25f, 1.25f, 0, 1.5f, -1.125f);
        defineParts(bottom, top, knives1, knives2);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return MinejagoSoundEvents.SWIRLING_KNIVES_ACTIVE.get();
    }

    @Override
    public void checkPartCollision(AbstractSpinjitzuCourseElementPart<SwirlingKnivesSpinjitzuCourseElement> part, Entity entity) {
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
