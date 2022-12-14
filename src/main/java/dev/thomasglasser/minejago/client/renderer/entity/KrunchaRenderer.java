package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.KrunchaModel;
import dev.thomasglasser.minejago.world.entity.Kruncha;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KrunchaRenderer extends HumanoidMobRenderer<Kruncha, KrunchaModel<Kruncha>>
{
    public KrunchaRenderer(EntityRendererProvider.Context context) {
        super(context, new KrunchaModel<>(context.bakeLayer(KrunchaModel.LAYER_LOCATION)), 0.5f);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(Kruncha pEntity) {
        return Minejago.modLoc("textures/entity/kruncha.png");
    }

    protected boolean isShaking(Kruncha pEntity) {
        return pEntity.isShaking();
    }
}
