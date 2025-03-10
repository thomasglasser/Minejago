package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.BouncingPoleSpinjitzuCourseElement;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class BouncingPoleSpinjitzuCourseElementModel extends DefaultedEntityGeoModel<BouncingPoleSpinjitzuCourseElement> {
    public BouncingPoleSpinjitzuCourseElementModel() {
        super(MinejagoEntityTypes.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT.getId().withPrefix("spinjitzu_course/"));
    }

    @Override
    public void setCustomAnimations(BouncingPoleSpinjitzuCourseElement animatable, long instanceId, AnimationState<BouncingPoleSpinjitzuCourseElement> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animatable.isFullyActive()) {
            GeoBone pole = this.getBone("pole").orElse(null);
            if (pole != null) {
                float bounceTicks = animatable.getBounceTicks();
                float yOffset = bounceTicks < BouncingPoleSpinjitzuCourseElement.HALF_BOUNCE_TICKS ? bounceTicks : BouncingPoleSpinjitzuCourseElement.MAX_BOUNCE_TICKS - bounceTicks;
                // Because of when tick is called, we have to render 1 pixel lower
                pole.setPosY(pole.getParent().getPosY() + yOffset - 1);
            }
        } else {
            this.getBone("pole").ifPresent(pole -> pole.setPosY(pole.getParent().getPosY()));
        }
    }
}
