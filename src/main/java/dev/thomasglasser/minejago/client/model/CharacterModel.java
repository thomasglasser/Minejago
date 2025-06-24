package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.character.Character;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CharacterModel<T extends Character> extends DefaultedEntityGeoModel<T> {
    public static final ResourceLocation ANIMATIONS = Minejago.modLoc("animations/entity/character/character.animation.json");

    private static final ResourceLocation MODEL = Minejago.modLoc("layered_biped");
    private static final ResourceLocation SLIM_MODEL = Minejago.modLoc("layered_biped_slim");

    private final ResourceLocation textureLocation;

    public CharacterModel(ResourceLocation entityId, boolean slim) {
        super(slim ? SLIM_MODEL : MODEL, true);
        this.textureLocation = entityId.withPrefix("textures/entity/character/").withSuffix(".png");
    }

    public CharacterModel(ResourceLocation entityId) {
        this(entityId, false);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return textureLocation;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ANIMATIONS;
    }
}
