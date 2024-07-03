package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.NuckalModel;
import dev.thomasglasser.minejago.world.entity.skulkin.Nuckal;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class NuckalRenderer extends HumanoidMobRenderer<Nuckal, NuckalModel<Nuckal>> {
    public static final ResourceLocation TEXTURE_LOCATION = Minejago.modLoc("textures/entity/skulkin/nuckal.png");

    public NuckalRenderer(EntityRendererProvider.Context context) {
        super(context, new NuckalModel<>(context.bakeLayer(NuckalModel.LAYER_LOCATION)), 0.5f);
        addLayer(new HumanoidArmorLayer<>(this, new NuckalModel<>(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)), new NuckalModel<>(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)), context.getModelManager()));
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(Nuckal pEntity) {
        return TEXTURE_LOCATION;
    }

    protected boolean isShaking(Nuckal pEntity) {
        return pEntity.isShaking();
    }
}
