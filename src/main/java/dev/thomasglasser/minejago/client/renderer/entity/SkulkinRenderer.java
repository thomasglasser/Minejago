package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;

public class SkulkinRenderer extends SkeletonRenderer<Skulkin> {
    public static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/skulkin/skulkin.png");

    public SkulkinRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Skulkin entity) {
        return TEXTURE;
    }
}
