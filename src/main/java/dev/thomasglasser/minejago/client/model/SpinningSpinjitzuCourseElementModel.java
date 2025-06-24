package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SpinningSpinjitzuCourseElementModel<T extends AbstractSpinjitzuCourseElement<T>> extends DefaultedEntityGeoModel<T> {
    private final String spinningBone;

    public SpinningSpinjitzuCourseElementModel(ResourceLocation entityLoc, String spinningBone) {
        super(entityLoc.withPrefix("spinjitzu_course/"));
        this.spinningBone = spinningBone;
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animatable.isActive() && animatable.isFullyActive()) {
            this.getBone(spinningBone).ifPresent(bone -> {
                bone.setRotX(0);
                bone.setRotY((animatable.tickCount + animationState.getPartialTick()) * MinejagoServerConfig.get().courseSpeed.get().floatValue());
                bone.setRotZ(0);
            });
        } else {
            this.getBone(spinningBone).ifPresent(bone -> {
                bone.setRotX(0);
                bone.setRotY(0);
                bone.setRotZ(0);
            });
        }
    }
}
