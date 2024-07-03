package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.KrunchaModel;
import dev.thomasglasser.minejago.world.entity.skulkin.Kruncha;
import dev.thomasglasser.tommylib.api.client.renderer.entity.layers.HumanoidSelectiveArmorLayer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KrunchaRenderer extends HumanoidMobRenderer<Kruncha, KrunchaModel<Kruncha>> {
    public static final ResourceLocation TEXTURE_LOCATION = Minejago.modLoc("textures/entity/skulkin/kruncha.png");

    public KrunchaRenderer(EntityRendererProvider.Context context) {
        super(context, new KrunchaModel<>(context.bakeLayer(KrunchaModel.LAYER_LOCATION)), 0.5f);
        HumanoidSelectiveArmorLayer<Kruncha, KrunchaModel<Kruncha>, KrunchaModel<Kruncha>> armorLayer = new HumanoidSelectiveArmorLayer<>(this, new KrunchaModel<>(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)), new KrunchaModel<>(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)), context.getModelManager());
        armorLayer.renderHead = false;
        addLayer(armorLayer);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(Kruncha pEntity) {
        return TEXTURE_LOCATION;
    }

    protected boolean isShaking(Kruncha pEntity) {
        return pEntity.isShaking();
    }
}
