package dev.thomasglasser.minejago.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.platform.services.IItemHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class FabricItemHelper implements IItemHelper
{

    @Override
    public Attribute getAttackRangeAttribute() {
        return null; // TODO: Update when 1.19.3 version is released
    }

    @Override
    public Supplier<SpawnEggItem> makeSpawnEgg(Supplier<EntityType<? extends Mob>> entityType, int bg, int fg, Item.Properties properties) {
        return () -> new SpawnEggItem(entityType.get(), bg, fg, properties);
    }

    @Override
    public void forgeRenderItem(ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, ResourceLocation model) {}
}
