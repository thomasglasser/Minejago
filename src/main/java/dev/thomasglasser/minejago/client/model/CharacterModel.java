package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.character.Character;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CharacterModel<T extends Character> extends DefaultedEntityGeoModel<T> {
    public static final ResourceLocation ANIMATIONS = Minejago.modLoc("animations/entity/character/character.animation.json");

    private final ResourceLocation slimModel;
    private final boolean slim;

    private ResourceLocation texture;

    public CharacterModel(boolean slim) {
        super(Minejago.modLoc("layered_biped"), true);
        this.slimModel = buildFormattedModelPath(Minejago.modLoc("layered_biped_slim"));
        this.slim = slim;
    }

    public CharacterModel() {
        this(false);
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return slim ? slimModel : super.getModelResource(animatable);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if (texture == null) {
            texture = Minejago.modLoc("textures/entity/character/" + BuiltInRegistries.ENTITY_TYPE.getKey(animatable.getType()).getPath() + ".png");
        }
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATIONS;
    }
}
