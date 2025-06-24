package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.SpinningAxesSpinjitzuCourseElement;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;

public class SpinningAxesSpinjitzuCourseElementModel extends SpinningSpinjitzuCourseElementModel<SpinningAxesSpinjitzuCourseElement> {
    private static final String STRUCTURE = "structure";
    private static final String AXE_1 = "axe1";
    private static final String AXE_2 = "axe2";
    private static final String AXE_3 = "axe3";
    private static final String AXE_4 = "axe4";

    public SpinningAxesSpinjitzuCourseElementModel() {
        super(MinejagoEntityTypes.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT.getId(), STRUCTURE);
    }

    @Override
    public void setCustomAnimations(SpinningAxesSpinjitzuCourseElement animatable, long instanceId, AnimationState<SpinningAxesSpinjitzuCourseElement> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animatable.isActive() && animatable.isFullyActive()) {
            float angleZ = Mth.sin(MinejagoServerConfig.get().courseSpeed.get().floatValue() * (animatable.tickCount + animationState.getPartialTick()));
            this.getBone(AXE_1).ifPresent(axe1 -> axe1.setRotZ(angleZ));
            this.getBone(AXE_2).ifPresent(axe2 -> axe2.setRotZ(angleZ));
            this.getBone(AXE_3).ifPresent(axe3 -> axe3.setRotX(angleZ));
            this.getBone(AXE_4).ifPresent(axe4 -> axe4.setRotX(angleZ));
        } else {
            this.getBone(AXE_1).ifPresent(axe1 -> axe1.setRotZ(0));
            this.getBone(AXE_2).ifPresent(axe2 -> axe2.setRotZ(0));
            this.getBone(AXE_3).ifPresent(axe3 -> axe3.setRotX(0));
            this.getBone(AXE_4).ifPresent(axe4 -> axe4.setRotX(0));
        }
    }
}
