package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.CenterSpinjitzuCourseElement;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CenterSpinjitzuCourseElementModel extends DefaultedEntityGeoModel<CenterSpinjitzuCourseElement> {
    public CenterSpinjitzuCourseElementModel() {
        super(MinejagoEntityTypes.CENTER_SPINJITZU_COURSE_ELEMENT.getId().withPrefix("spinjitzu_course/"));
    }

    @Override
    public void setCustomAnimations(CenterSpinjitzuCourseElement animatable, long instanceId, AnimationState<CenterSpinjitzuCourseElement> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animatable.isActive() && animatable.playActiveAnimation()) {
            GeoBone spinning = this.getBone("spinning").orElse(null);
            if (spinning != null) {
                spinning.setRotX(0);
                spinning.setRotY(animatable.tickCount * 0.5f);
                spinning.setRotZ(0);
            }
        } else {
            GeoBone spinning = this.getBone("spinning").orElse(null);
            if (spinning != null) {
                spinning.setRotX(0);
                spinning.setRotY(0);
                spinning.setRotZ(0);
            }
        }
    }
}
