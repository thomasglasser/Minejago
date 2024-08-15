package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningAxesSpinjitzuCourseElement;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;

public class SpinningAxesSpinjitzuCourseElementModel extends SpinningSpinjitzuCourseElementModel<SpinningAxesSpinjitzuCourseElement> {
    public SpinningAxesSpinjitzuCourseElementModel() {
        super(MinejagoEntityTypes.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT.getId(), "structure");
    }

    @Override
    public void setCustomAnimations(SpinningAxesSpinjitzuCourseElement animatable, long instanceId, AnimationState<SpinningAxesSpinjitzuCourseElement> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animatable.isActive() && animatable.isFullyActive()) {
            GeoBone axe1 = this.getBone("axe1").orElse(null);
            GeoBone axe2 = this.getBone("axe2").orElse(null);
            GeoBone axe3 = this.getBone("axe3").orElse(null);
            GeoBone axe4 = this.getBone("axe4").orElse(null);
            float maxAngle = 1f;
            float speedMultiplier = 0.5f;
            float angleZ = Mth.sin(speedMultiplier * (animatable.tickCount + animationState.getPartialTick())) * maxAngle;
            if (axe1 != null) {
                axe1.setRotZ(angleZ);
            }
            if (axe2 != null) {
                axe2.setRotZ(angleZ);
            }
            if (axe3 != null) {
                axe3.setRotX(angleZ);
            }
            if (axe4 != null) {
                axe4.setRotX(angleZ);
            }
        } else {
            GeoBone axe1 = this.getBone("axe1").orElse(null);
            GeoBone axe2 = this.getBone("axe2").orElse(null);
            GeoBone axe3 = this.getBone("axe3").orElse(null);
            GeoBone axe4 = this.getBone("axe4").orElse(null);
            if (axe1 != null) {
                axe1.setRotZ(0);
            }
            if (axe2 != null) {
                axe2.setRotZ(0);
            }
            if (axe3 != null) {
                axe3.setRotX(0);
            }
            if (axe4 != null) {
                axe4.setRotX(0);
            }
        }
    }
}
