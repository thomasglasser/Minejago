package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
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
        GeoBone structure = this.getBone(spinningBone).orElse(null);
        if (animatable.isActive() && animatable.isFullyActive()) {
            if (structure != null) {
                structure.setRotX(0);
                structure.setRotY((animatable.tickCount + animationState.getPartialTick()) * 0.5f);
                structure.setRotZ(0);
            }
        } else {
            if (structure != null) {
                structure.setRotX(0);
                structure.setRotY(0);
                structure.setRotZ(0);
            }
        }
    }
}
