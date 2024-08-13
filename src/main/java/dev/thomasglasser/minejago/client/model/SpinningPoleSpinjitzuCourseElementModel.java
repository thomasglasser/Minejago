package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningPoleSpinjitzuCourseElement;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SpinningPoleSpinjitzuCourseElementModel extends DefaultedEntityGeoModel<SpinningPoleSpinjitzuCourseElement> {
    public SpinningPoleSpinjitzuCourseElementModel() {
        super(MinejagoEntityTypes.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT.getId().withPrefix("spinjitzu_course/"));
    }

    @Override
    public void setCustomAnimations(SpinningPoleSpinjitzuCourseElement animatable, long instanceId, AnimationState<SpinningPoleSpinjitzuCourseElement> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animatable.isActive() && animatable.playActiveAnimation()) {
            this.getBone("pole").ifPresent(pole -> pole.setRotY((animatable.tickCount + animationState.getPartialTick()) / 1.5F));
        } else {
            this.getBone("pole").ifPresent(pole -> pole.setRotY(0));
        }
    }
}
