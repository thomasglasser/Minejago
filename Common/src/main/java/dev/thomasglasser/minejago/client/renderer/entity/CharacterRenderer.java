package dev.thomasglasser.minejago.client.renderer.entity;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.Character;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class CharacterRenderer<T extends Character> extends HumanoidMobRenderer<T, PlayerModel<T>> {
    public CharacterRenderer(EntityRendererProvider.Context context, boolean slim) {
        super(context, new PlayerModel<>(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), slim), 0.5f);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR))));
    }

    public CharacterRenderer(EntityRendererProvider.Context context) {
        this(context, false);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(T entity)
    {
        return Minejago.modLoc("textures/entity/" + BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).getPath() + ".png");
    }
}