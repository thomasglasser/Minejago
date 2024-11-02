package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.NuckalModel;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.ResourceLocation;

public class NuckalRenderer extends HumanoidMobRenderer<Nuckal, SkeletonRenderState, NuckalModel<SkeletonRenderState>> {
    public static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/skulkin/nuckal.png");

    public NuckalRenderer(EntityRendererProvider.Context context) {
        super(context, new NuckalModel<>(context.bakeLayer(NuckalModel.LAYER_LOCATION)), 0.5f);
        addLayer(new HumanoidArmorLayer<>(this, new NuckalModel<>(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)), new NuckalModel<>(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)), context.getEquipmentRenderer()));
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }

    public void extractRenderState(Nuckal nuckal, SkeletonRenderState renderState, float p_362389_) {
        super.extractRenderState(nuckal, renderState, p_362389_);
        renderState.isAggressive = nuckal.isAggressive();
        renderState.isShaking = nuckal.isShaking();
    }

    protected boolean isShaking(SkeletonRenderState p_364410_) {
        return p_364410_.isShaking;
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonRenderState p_368654_) {
        return TEXTURE;
    }
}
