package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class SkulkinRenderer extends SkeletonRenderer {
    public static final ResourceLocation TEXTURE_LOCATION = Minejago.modLoc("textures/entity/skulkin/skulkin.png");

    public SkulkinRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeleton pEntity) {
        return TEXTURE_LOCATION;
    }
}
