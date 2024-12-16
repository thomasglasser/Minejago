package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.SkulkinHorse;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SkulkinHorseRenderer extends AbstractHorseRenderer<SkulkinHorse, HorseModel<SkulkinHorse>> {
    public static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/skulkin/skulkin_horse.png");

    public SkulkinHorseRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel<>(context.bakeLayer(ModelLayers.SKELETON_HORSE)), 0.75F);
//        this.addLayer(new HorseArmorLayer(this, context.getModelSet(), context.getEquipmentRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(SkulkinHorse skulkinHorse) {
        return TEXTURE;
    }
}
