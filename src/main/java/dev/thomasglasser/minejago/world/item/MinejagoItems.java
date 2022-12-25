package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.data.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MinejagoItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Minejago.MOD_ID);
    private static final HashMap<CreativeModeTab, ArrayList<ResourceLocation>> ITEM_TABS = new HashMap<>();

    public static final RegistryObject<Item> BAMBOO_STAFF = register("bamboo_staff", () -> new BambooStaffItem(Tiers.WOOD, 2, -1, new Item.Properties()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> BONE_KNIFE = register("bone_knife", () -> new BoneKnifeItem(MinejagoTiers.BONE_TIER, 3, -2, new Item.Properties()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> IRON_SPEAR = register("iron_spear", () -> new SpearItem(Tiers.IRON, 4, -2.8F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> SCYTHE_OF_QUAKES = register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties().defaultDurability(0).rarity(Rarity.EPIC).fireResistant().setNoRepair().stacksTo(1)), CreativeModeTabs.COMBAT, CreativeModeTabs.TOOLS_AND_UTILITIES);
    public static final RegistryObject<Item> IRON_SHURIKEN = register("iron_shuriken", () -> new ShurikenItem(Tiers.IRON, 1, 1F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> IRON_KATANA = register("iron_katana", () -> new SwordItem(Tiers.IRON, 1, -1.4F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> TEACUP = register("teacup", () -> new TeacupItem(new Item.Properties()), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<Item> FILLED_TEACUP = register("filled_teacup", () -> new FilledTeacupItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FOUR_WEAPONS_BANNER_PATTERN = register("four_weapons_banner_pattern", () -> new BannerPatternItem(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<Item> SKELETAL_CHESTPLATE = register("skeletal_chestplate", () -> new SkeletalChestplateItem(MinejagoArmorMaterials.SKELETAL, EquipmentSlot.CHEST, new Item.Properties().stacksTo(1).setNoRepair()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> BLACK_GI_HELMET = register("black_gi_helmet", () -> new BlackGiItem(MinejagoArmorMaterials.BLACK_GI, EquipmentSlot.HEAD, new Item.Properties().stacksTo(1).setNoRepair()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> BLACK_GI_CHESTPLATE = register("black_gi_chestplate", () -> new BlackGiItem(MinejagoArmorMaterials.BLACK_GI, EquipmentSlot.CHEST, new Item.Properties().stacksTo(1).setNoRepair()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> BLACK_GI_LEGGINGS = register("black_gi_leggings", () -> new BlackGiItem(MinejagoArmorMaterials.BLACK_GI, EquipmentSlot.LEGS, new Item.Properties().stacksTo(1).setNoRepair()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> BLACK_GI_BOOTS = register("black_gi_boots", () -> new BlackGiItem(MinejagoArmorMaterials.BLACK_GI, EquipmentSlot.FEET, new Item.Properties().stacksTo(1).setNoRepair()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> IRON_SCYTHE = register("iron_scythe", () -> new IronScytheItem(Tiers.IRON, 8, -3.5F, BlockTags.REPLACEABLE_PLANTS, new Item.Properties()), CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> WOODEN_NUNCHUCKS = register("wooden_nunchucks", () -> new WoodenNunchucksItem(new Item.Properties().stacksTo(1)), CreativeModeTabs.COMBAT);

    // SPAWN EGGS
    public static final RegistryObject<Item> WU_SPAWN_EGG = register("wu_spawn_egg", () -> new ForgeSpawnEggItem(MinejagoEntityTypes.WU, 16645363, 15517489, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> KAI_SPAWN_EGG = register("kai_spawn_egg", () -> new ForgeSpawnEggItem(MinejagoEntityTypes.KAI, 14689295, 6698273, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> NYA_SPAWN_EGG = register("nya_spawn_egg", () -> new ForgeSpawnEggItem(MinejagoEntityTypes.NYA, 14689295, 1184274, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);

    // BLOCK ITEMS
    public static final RegistryObject<Item> TEAPOT = register("teapot", () -> new BlockItem(MinejagoBlocks.TEAPOT.get(), new Item.Properties().stacksTo(1)), CreativeModeTabs.FUNCTIONAL_BLOCKS);

    private static RegistryObject<Item> register(String name, Supplier<? extends Item> supplier, CreativeModeTab... tabs)
    {
        for (CreativeModeTab tab: tabs) {
            ArrayList<ResourceLocation> list = ITEM_TABS.computeIfAbsent(tab, empty -> new ArrayList<>());
            list.add(new ResourceLocation(Minejago.MOD_ID, name));
        }
        return ITEMS.register(name, supplier);
    }

    public static Map<CreativeModeTab, ArrayList<ResourceLocation>> getItemTabs() {
        return ITEM_TABS;
    }
}
