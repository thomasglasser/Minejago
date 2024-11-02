package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.character.Character;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoRenderer;

public class WuModel<T extends Character> extends CharacterModel<T> {
    private static final ResourceLocation ANIMATIONS = Minejago.modLoc("animations/entity/character/wu.animation.json");

    private final ResourceLocation MODEL;

    public WuModel() {
        super();
        MODEL = buildFormattedModelPath(Minejago.modLoc("wu"));
    }

    @Override
    public ResourceLocation getModelResource(T animatable, GeoRenderer<T> renderer) {
        return MODEL;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATIONS;
    }

    @Override
    public ResourceLocation[] getAnimationResourceFallbacks(T animatable, GeoRenderer<T> renderer) {
        return new ResourceLocation[] { CharacterModel.ANIMATIONS };
    }
}
