package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import java.util.function.Function;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SkulkinRenderer<T extends SkulkinRaider, M extends SkeletonModel<T>> extends HumanoidMobRenderer<T, M> {
    private final ResourceLocation textureLocation;

    public SkulkinRenderer(EntityRendererProvider.Context context, M model, Function<ModelPart, M> armorModel, ResourceLocation entityId) {
        super(context, model, 0.5f);
        this.textureLocation = entityId.withPrefix("textures/entity/skulkin/").withSuffix(".png");
        addLayer(new HumanoidArmorLayer<>(this, armorModel.apply(context.bakeLayer(ModelLayers.SKELETON_INNER_ARMOR)), armorModel.apply(context.bakeLayer(ModelLayers.SKELETON_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return textureLocation;
    }

    @Override
    protected boolean isShaking(T entity) {
        return entity.isShaking();
    }
}
