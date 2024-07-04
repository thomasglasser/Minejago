package dev.thomasglasser.minejago.client.model;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.DragonHeadBlock;
import dev.thomasglasser.minejago.world.level.block.entity.DragonHeadBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DragonHeadModel extends GeoModel<DragonHeadBlockEntity> {
    private String block;
    private String dragon;

    @Override
    public ResourceLocation getModelResource(DragonHeadBlockEntity animatable) {
        if (block == null) block = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        return Minejago.modLoc("geo/block/" + block + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DragonHeadBlockEntity animatable) {
        if (dragon == null && animatable.getBlockState().getBlock() instanceof DragonHeadBlock dragonHeadBlock) {
            dragon = BuiltInRegistries.ENTITY_TYPE.getKey(dragonHeadBlock.getEntityType()).getPath();
        }
        return Minejago.modLoc("textures/entity/dragon/" + dragon + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(DragonHeadBlockEntity animatable) {
        if (block == null) block = BuiltInRegistries.BLOCK.getKey(animatable.getBlockState().getBlock()).getPath();
        return Minejago.modLoc("animations/block/" + block + ".animation.json");
    }
}
