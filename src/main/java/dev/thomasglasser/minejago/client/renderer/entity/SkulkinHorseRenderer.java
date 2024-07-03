package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.SkulkinHorse;
import dev.thomasglasser.tommylib.api.client.renderer.entity.layers.AbstractHorseArmorLayer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SkulkinHorseRenderer extends AbstractHorseRenderer<SkulkinHorse, HorseModel<SkulkinHorse>> {
    public static final ResourceLocation TEXTURE_LOCATION = Minejago.modLoc("textures/entity/skulkin/skulkin_horse.png");

    public SkulkinHorseRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel<>(context.bakeLayer(ModelLayers.SKELETON_HORSE)), 1.0F);
        this.addLayer(new AbstractHorseArmorLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(SkulkinHorse entity) {
        return TEXTURE_LOCATION;
    }
}
