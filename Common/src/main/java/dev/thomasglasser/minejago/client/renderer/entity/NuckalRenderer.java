package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.model.NuckalModel;
import dev.thomasglasser.minejago.world.entity.Nuckal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NuckalRenderer extends HumanoidMobRenderer<Nuckal, NuckalModel<Nuckal>>
{
    public NuckalRenderer(EntityRendererProvider.Context context) {
        super(context, new NuckalModel<>(context.bakeLayer(NuckalModel.LAYER_LOCATION)), 0.5f);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(Nuckal pEntity) {
        return Minejago.modLoc("textures/entity/nuckal.png");
    }

    protected boolean isShaking(Nuckal pEntity) {
        return pEntity.isShaking();
    }
}
