package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DragonRenderer<T extends Dragon> extends GeoEntityRenderer<T> {
    private static final String STRAP = "strap";
    private static final String SADDLE = "saddle";
    private static final String CHESTS = "chests";

    public DragonRenderer(EntityRendererProvider.Context renderManager, ResourceLocation entityLoc) {
        super(renderManager, new DefaultedEntityGeoModel<>(entityLoc.withPrefix("dragon/"), true));
    }

    @Override
    public void render(EntityRenderState entityRenderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        setModelProperties(animatable);
        super.render(entityRenderState, poseStack, bufferSource, packedLight);
    }

    protected void setModelProperties(T entity) {
        if (!model.getAnimationProcessor().getRegisteredBones().isEmpty()) {
            model.getBone(STRAP).orElseThrow().setHidden(!entity.isSaddled() && !entity.hasChest());
            model.getBone(SADDLE).orElseThrow().setHidden(!entity.isSaddled());
            model.getBone(CHESTS).orElseThrow().setHidden(!entity.hasChest());
        }
    }
}
