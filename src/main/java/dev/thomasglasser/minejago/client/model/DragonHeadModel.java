package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.DragonHeadBlock;
import dev.thomasglasser.minejago.world.level.block.entity.DragonHeadBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class DragonHeadModel extends GeoModel<DragonHeadBlockEntity> {
    private String block;
    private ResourceLocation model;
    private ResourceLocation animations;
    private ResourceLocation texture;

    @Override
    public ResourceLocation getModelResource(DragonHeadBlockEntity animatable, @Nullable GeoRenderer<DragonHeadBlockEntity> renderer) {
        if (block == null) block = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        if (model == null) model = Minejago.modLoc("geo/block/" + block + ".geo.json");
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(DragonHeadBlockEntity animatable, @Nullable GeoRenderer<DragonHeadBlockEntity> renderer) {
        if (texture == null && animatable.getBlockState().getBlock() instanceof DragonHeadBlock dragonHeadBlock) {
            texture = Minejago.modLoc("textures/entity/dragon/" + BuiltInRegistries.ENTITY_TYPE.getKey(dragonHeadBlock.getEntityType()).getPath() + ".png");
        }
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(DragonHeadBlockEntity animatable) {
        if (block == null) block = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        if (animations == null) animations = Minejago.modLoc("animations/block/" + block + ".animation.json");
        return animations;
    }
}
