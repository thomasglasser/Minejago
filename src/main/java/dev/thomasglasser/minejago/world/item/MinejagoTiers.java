package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.Arrays;

public class MinejagoTiers
{
    public static final ForgeTier BONE_TIER = new ForgeTier(2, 150, 10.0f, 1.5f, 10, BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Tags.Items.BONES));

    public static void register()
    {
        TierSortingRegistry.registerTier(BONE_TIER, new ResourceLocation(Minejago.MOD_ID, "bone_tier"), Arrays.asList(net.minecraft.world.item.Tiers.WOOD, net.minecraft.world.item.Tiers.GOLD, net.minecraft.world.item.Tiers.STONE), Arrays.asList(net.minecraft.world.item.Tiers.IRON, net.minecraft.world.item.Tiers.DIAMOND, net.minecraft.world.item.Tiers.NETHERITE));
    }
}
