package dev.thomasglasser.minejago.platform;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.ItemHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

public class FabricItemHelper implements ItemHelper
{

    @Override
    public Attribute getAttackRangeAttribute() {
        if (Minejago.Dependencies.REACH_ENTITY_ATTRIBUTES.isInstalled())
            return ReachEntityAttributes.ATTACK_RANGE;
        return null;
    }

    @Override
    public Supplier<SpawnEggItem> makeSpawnEgg(Supplier<EntityType<? extends Mob>> entityType, int bg, int fg, Item.Properties properties) {
        return () -> new SpawnEggItem(entityType.get(), bg, fg, properties);
    }

    @Override
    public void renderItem(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, String model) {
        renderItem(itemStack, displayContext, false, poseStack, buffer, combinedLight, combinedOverlay, Minejago.MOD_ID, model);
    }

    @Override
    public void renderItem(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, String modid, String model) {
        Minecraft.getInstance().getItemRenderer().render(itemStack, displayContext, false, poseStack, buffer, combinedLight, combinedOverlay, Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(new ResourceLocation(modid, model), "inventory")));
    }

    @SafeVarargs
    @Override
    public final CreativeModeTab newTab(Component title, Supplier<ItemStack> icon, boolean search, CreativeModeTab.DisplayItemsGenerator displayItems, ResourceKey<CreativeModeTab>... tabsBefore) {
        CreativeModeTab.Builder builder = FabricItemGroup.builder().title(title).icon(icon).displayItems(displayItems);
        return builder.build();
    }
}
