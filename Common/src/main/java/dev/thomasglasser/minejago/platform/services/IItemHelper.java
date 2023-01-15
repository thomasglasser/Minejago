package dev.thomasglasser.minejago.platform.services;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public interface IItemHelper
{
    Attribute getAttackRangeAttribute();

    Supplier<SpawnEggItem> makeSpawnEgg(Supplier<EntityType<? extends Mob>> entityType, int bg, int fg, Item.Properties properties);

    void renderItem(ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, String model);
}
