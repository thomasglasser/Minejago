package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.KrunchaModel;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.tommylib.api.client.renderer.entity.layers.HumanoidSelectiveArmorLayer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.ResourceLocation;

public class KrunchaRenderer extends HumanoidMobRenderer<Kruncha, SkeletonRenderState, KrunchaModel<SkeletonRenderState>> {
    public static final ResourceLocation TEXTURE_LOCATION = Minejago.modLoc("textures/entity/skulkin/kruncha.png");

    public KrunchaRenderer(EntityRendererProvider.Context context) {
        super(context, new KrunchaModel<>(context.bakeLayer(KrunchaModel.LAYER_LOCATION)), 0.5f);
        HumanoidSelectiveArmorLayer<SkeletonRenderState, KrunchaModel<SkeletonRenderState>, KrunchaModel<SkeletonRenderState>> armorLayer = new HumanoidSelectiveArmorLayer<>(this, new KrunchaModel<>(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)), new KrunchaModel<>(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)), context.getEquipmentRenderer());
        armorLayer.renderHead = false;
        addLayer(armorLayer);
    }

    @Override
    public SkeletonRenderState createRenderState() {
        return new SkeletonRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonRenderState p_368654_) {
        return TEXTURE_LOCATION;
    }

    public void extractRenderState(Kruncha p_360621_, SkeletonRenderState p_364836_, float p_362389_) {
        super.extractRenderState(p_360621_, p_364836_, p_362389_);
        p_364836_.isAggressive = p_360621_.isAggressive();
        p_364836_.isShaking = p_360621_.isShaking();
    }

    @Override
    protected boolean isShaking(SkeletonRenderState p_364410_) {
        return p_364410_.isShaking;
    }
}
