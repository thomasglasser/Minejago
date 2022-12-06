package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.renderer.MinejagoBlockEntityWithoutLevelRenderer;
import dev.thomasglasser.minejago.data.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MinejagoItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Minejago.MOD_ID);

    public static final RegistryObject<Item> BAMBOO_STAFF = ITEMS.register("bamboo_staff", () -> new BambooStaffItem(Tiers.WOOD, 2, -1, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> BONE_KNIFE = ITEMS.register("bone_knife", () -> new BoneKnifeItem(MinejagoTiers.BONE_TIER, 3, -2, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear", () -> new SpearItem(Tiers.IRON, 4, -2.8F, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> SCYTHE_OF_QUAKES = ITEMS.register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties().defaultDurability(0).tab(CreativeModeTab.TAB_COMBAT).rarity(Rarity.EPIC).fireResistant().setNoRepair().stacksTo(1)));
    public static final RegistryObject<Item> IRON_SHURIKEN = ITEMS.register("iron_shuriken", () -> new ShurikenItem(Tiers.IRON, 1, 1F, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_KATANA = ITEMS.register("iron_katana", () -> new SwordItem(Tiers.IRON, 1, -1.4F, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> TEACUP = ITEMS.register("teacup", () -> new TeacupItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> FILLED_TEACUP = ITEMS.register("filled_teacup", () -> new FilledTeacupItem(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_BREWING)));
    public static final RegistryObject<Item> FOUR_WEAPONS_BANNER_PATTERN = ITEMS.register("four_weapons_banner_pattern", () -> new BannerPatternItem(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS, (new Item.Properties()).stacksTo(1).tab(CreativeModeTab.TAB_MISC).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SKELETAL_CHESTPLATE = ITEMS.register("skeletal_chestplate", () -> new SkeletalChestplateItem(MinejagoArmorMaterials.SKELETAL, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).setNoRepair()));
    public static final RegistryObject<Item> BLACK_GI_HELMET = ITEMS.register("black_gi_helmet", () -> new BlackGiItem(MinejagoArmorMaterials.BLACK_GI, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).setNoRepair()));
    public static final RegistryObject<Item> BLACK_GI_CHESTPLATE = ITEMS.register("black_gi_chestplate", () -> new BlackGiItem(MinejagoArmorMaterials.BLACK_GI, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).setNoRepair()));
    public static final RegistryObject<Item> BLACK_GI_LEGGINGS = ITEMS.register("black_gi_leggings", () -> new BlackGiItem(MinejagoArmorMaterials.BLACK_GI, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).setNoRepair()));
    public static final RegistryObject<Item> BLACK_GI_BOOTS = ITEMS.register("black_gi_boots", () -> new BlackGiItem(MinejagoArmorMaterials.BLACK_GI, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).stacksTo(1).setNoRepair()));
    public static final RegistryObject<Item> IRON_SCYTHE = ITEMS.register("iron_scythe", () -> new IronScytheItem(Tiers.IRON, 8, -3.5F, new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    // BLOCK ITEMS
    public static final RegistryObject<Item> TEAPOT = ITEMS.register("teapot", () -> new BlockItem(MinejagoBlocks.TEAPOT.get(), new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_BREWING)));
}
