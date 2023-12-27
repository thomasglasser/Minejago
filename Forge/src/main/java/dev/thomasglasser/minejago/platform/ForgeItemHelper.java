package dev.thomasglasser.minejago.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.ItemHelper;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.function.Supplier;

public class ForgeItemHelper implements ItemHelper {
    @Override
    public Attribute getAttackRangeAttribute() {
        return NeoForgeMod.ENTITY_REACH.value();
    }

    @Override
    public Supplier<SpawnEggItem> makeSpawnEgg(Supplier<EntityType<? extends Mob>> entityType, int bg, int fg, Item.Properties properties) {
        return () -> new DeferredSpawnEggItem(entityType, bg, fg, properties);
    }

    @Override
    public void renderItem(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, String model) {
        renderItem(itemStack, displayContext, false, poseStack, buffer, combinedLight, combinedOverlay, Minejago.MOD_ID, model);
    }

    @Override
    public void renderItem(ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, String modid, String model) {
        MinejagoClientUtils.getMinecraft().getItemRenderer().render(itemStack, displayContext, false, poseStack, buffer, combinedLight, combinedOverlay, Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(modid, "item/" + model)));
    }

    @SafeVarargs
    @Override
    public final CreativeModeTab newTab(Component title, Supplier<ItemStack> icon, boolean search, CreativeModeTab.DisplayItemsGenerator displayItems, ResourceKey<CreativeModeTab>... tabsBefore) {
        CreativeModeTab.Builder builder = CreativeModeTab.builder().title(title).icon(icon).displayItems(displayItems).withTabsBefore(tabsBefore);
        if (search) builder.withSearchBar();
        return builder.build();
    }
}
