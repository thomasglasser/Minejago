package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.client.model.CharacterModel;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WuRenderer<T extends Wu> extends GeoEntityRenderer<T> {
    public WuRenderer(EntityRendererProvider.Context renderManager, ResourceLocation entityId) {
        super(renderManager, new CharacterModel<>(entityId) {
            private ResourceLocation animationLocation;

            @Override
            public ResourceLocation getAnimationResource(T animatable) {
                if (animationLocation == null) {
                    animationLocation = entityId.withPrefix("animations/entity/character/").withSuffix(".animation.json");
                }
                return animationLocation;
            }

            @Override
            public ResourceLocation[] getAnimationResourceFallbacks(T animatable) {
                return new ResourceLocation[] { CharacterModel.ANIMATIONS };
            }
        });
        // TODO: Add hat and beard
    }
}
