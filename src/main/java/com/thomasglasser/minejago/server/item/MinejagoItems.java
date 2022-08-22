package com.thomasglasser.minejago.server.item;

import com.thomasglasser.minejago.MinejagoMod;
import com.thomasglasser.minejago.server.item.property.MinejagoTiers;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MinejagoMod.MODID);

    public static final RegistryObject<Item> BONE_KNIFE = ITEMS.register("bone_knife", () -> new BoneKnifeItem(MinejagoTiers.BONE_TIER, 3, -2, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> BAMBOO_STAFF = ITEMS.register("bamboo_staff", () -> new BambooStaffItem(Tiers.WOOD, 2, -1, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> SCYTHE_OF_QUAKES = ITEMS.register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties().defaultDurability(0).tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.EPIC).fireResistant().setNoRepair().stacksTo(1)));
}
