package dev.thomasglasser.minejago.platform;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.IItemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
        return ReachEntityAttributes.ATTACK_RANGE;
    }

    @Override
    public Supplier<SpawnEggItem> makeSpawnEgg(Supplier<EntityType<? extends Mob>> entityType, int bg, int fg, Item.Properties properties) {
        return () -> new SpawnEggItem(entityType.get(), bg, fg, properties);
    }

    @Override
    public void renderItem(ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, String model) {
        Minecraft.getInstance().getItemRenderer().render(itemStack, transformType, false, poseStack, buffer, combinedLight, combinedOverlay, Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(Minejago.modLoc(model), "inventory")));
    }
}
