package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.resources.ResourceLocation;

public class SkulkinRenderer extends SkeletonRenderer {
    public static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/skulkin/skulkin.png");

    public SkulkinRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonRenderState p_365297_) {
        return TEXTURE;
    }
}
