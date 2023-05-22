package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.character.Character;
import net.minecraft.resources.ResourceLocation;

public class WuModel<T extends Character> extends CharacterModel<T>
{
    @Override
    public ResourceLocation getModelResource(T animatable) {
        return buildFormattedModelPath(Minejago.modLoc("wu"));
    }
}
