package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.character.Character;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CharacterModel<T extends Character> extends DefaultedEntityGeoModel<T> {
    private final boolean slim;

    public CharacterModel(boolean slim)
    {
        super(Minejago.modLoc("layered_biped"), true);
        this.slim = slim;
    }

    public CharacterModel()
    {
        this(false);
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return slim ? buildFormattedModelPath(Minejago.modLoc("layered_biped_slim")) : super.getModelResource(animatable);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return Minejago.modLoc("textures/entity/character/" + BuiltInRegistries.ENTITY_TYPE.getKey(animatable.getType()).getPath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return Minejago.modLoc("animations/entity/character.animation.json");
    }
}
