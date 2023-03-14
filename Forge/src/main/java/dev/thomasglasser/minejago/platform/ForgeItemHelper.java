package dev.thomasglasser.minejago.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.IItemHelper;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ForgeItemHelper implements IItemHelper {
    @Override
    public Attribute getAttackRangeAttribute() {
        return ForgeMod.ATTACK_RANGE.get();
    }

    @Override
    public Supplier<SpawnEggItem> makeSpawnEgg(Supplier<EntityType<? extends Mob>> entityType, int bg, int fg, Item.Properties properties) {
        return () -> new ForgeSpawnEggItem(entityType, bg, fg, properties);
    }

    @Override
    public void renderItem(ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, String model) {
        renderItem(itemStack, transformType, false, poseStack, buffer, combinedLight, combinedOverlay, Minejago.MOD_ID, model);
    }

    @Override
    public void renderItem(ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, String modid, String model) {
        Minecraft.getInstance().getItemRenderer().render(itemStack, transformType, false, poseStack, buffer, combinedLight, combinedOverlay, Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(modid, "item/" + model)));
    }

    public final List<List<Object>> TABS = new ArrayList<>();

    @Override
    public CreativeModeTab newTab(ResourceLocation rl, Component title, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems) {
        int i = TABS.size();
        TABS.add(i, new ArrayList<>());
        TABS.get(i).add(0, rl);
        TABS.get(i).add(1, title);
        TABS.get(i).add(2, icon);
        TABS.get(i).add(3, displayItems);
        return null;
    }
}
