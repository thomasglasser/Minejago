package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.RockingPoleSpinjitzuCourseElement;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class RockingPoleSpinjitzuCourseElementModel extends DefaultedEntityGeoModel<RockingPoleSpinjitzuCourseElement> {
    public RockingPoleSpinjitzuCourseElementModel() {
        super(MinejagoEntityTypes.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT.getId().withPrefix("spinjitzu_course/"));
    }

    @Override
    public void setCustomAnimations(RockingPoleSpinjitzuCourseElement animatable, long instanceId, AnimationState<RockingPoleSpinjitzuCourseElement> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animatable.isFullyActive()) {
            GeoBone pole = this.getBone("pole").orElse(null);
            if (pole != null) {
                float maxAngle = 1f;
                float speedMultiplier = MinejagoServerConfig.get().courseSpeed.get().floatValue();
                float angleX = Mth.sin(speedMultiplier * (animatable.tickCount + animationState.getPartialTick())) * maxAngle;
                pole.setRotX(angleX);
            }
        } else {
            this.getBone("pole").ifPresent(pole -> pole.setRotX(animatable.getXRot()));
        }
    }
}
