package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.SkulkinHorse;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.resources.ResourceLocation;

public class SkulkinHorseRenderer extends AbstractHorseRenderer<SkulkinHorse, HorseRenderState, HorseModel> {
    public static final ResourceLocation TEXTURE = Minejago.modLoc("textures/entity/skulkin/skulkin_horse.png");

    public SkulkinHorseRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel(context.bakeLayer(ModelLayers.SKELETON_HORSE)), new HorseModel(context.bakeLayer(ModelLayers.SKELETON_HORSE)), 0.75F);
        this.addLayer(new HorseArmorLayer(this, context.getModelSet(), context.getEquipmentRenderer()));
    }

    @Override
    public HorseRenderState createRenderState() {
        return new HorseRenderState();
    }

    @Override
    public void extractRenderState(SkulkinHorse horse, HorseRenderState renderState, float p_361007_) {
        super.extractRenderState(horse, renderState, p_361007_);
        renderState.bodyArmorItem = horse.getBodyArmorItem().copy();
    }

    @Override
    public ResourceLocation getTextureLocation(HorseRenderState p_368654_) {
        return TEXTURE;
    }
}
