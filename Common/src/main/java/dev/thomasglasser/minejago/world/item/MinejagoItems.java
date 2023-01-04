package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MinejagoItems
{
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Minejago.MOD_ID);
    private static final HashMap<CreativeModeTab, ArrayList<ResourceLocation>> ITEM_TABS = new HashMap<>();

    public static final RegistryObject<Item> BAMBOO_STAFF = register("bamboo_staff", () -> new BambooStaffItem(Tiers.WOOD, 2, -1, new Item.Properties()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> BONE_KNIFE = register("bone_knife", () -> new BoneKnifeItem(MinejagoTiers.BONE, 3, -2, new Item.Properties()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> IRON_SPEAR = register("iron_spear", () -> new SpearItem(Tiers.IRON, 4, -2.8F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> SCYTHE_OF_QUAKES = register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties().defaultDurability(0).rarity(Rarity.EPIC).fireResistant().stacksTo(1)), CreativeModeTabs.COMBAT, CreativeModeTabs.TOOLS_AND_UTILITIES);
    public static final RegistryObject<Item> IRON_SHURIKEN = register("iron_shuriken", () -> new ShurikenItem(Tiers.IRON, 1, 1F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> IRON_KATANA = register("iron_katana", () -> new SwordItem(Tiers.IRON, 1, -1.4F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> TEACUP = register("teacup", () -> new TeacupItem(new Item.Properties()), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<Item> FILLED_TEACUP = register("filled_teacup", () -> new FilledTeacupItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FOUR_WEAPONS_BANNER_PATTERN = register("four_weapons_banner_pattern", () -> new BannerPatternItem(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<Item> IRON_SCYTHE = register("iron_scythe", () -> new IronScytheItem(Tiers.IRON, 8, -3.5F, BlockTags.REPLACEABLE_PLANTS, new Item.Properties()), CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> WOODEN_NUNCHUCKS = register("wooden_nunchucks", () -> new WoodenNunchucksItem(new Item.Properties().stacksTo(1)), CreativeModeTabs.COMBAT);

    // SPAWN EGGS
    public static final RegistryObject<Item> WU_SPAWN_EGG = register("wu_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.WU, 16645363, 14689295, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> KAI_SPAWN_EGG = register("kai_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.KAI, 9507597, 5185296, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> NYA_SPAWN_EGG = register("nya_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.NYA, 9507597, 3223857, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> COLE_SPAWN_EGG = register("cole_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.COLE, 1647949, 2697513, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> JAY_SPAWN_EGG = register("jay_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.JAY, 10057, 4854026, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> ZANE_SPAWN_EGG = register("zane_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.ZANE, 2697513, 16769956, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> UNDERWORLD_SKELETON_SPAWN_EGG = register("underworld_skeleton_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.UNDERWORLD_SKELETON, 12698049, 11348013, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> KRUNCHA_SPAWN_EGG = register("kruncha_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.KRUNCHA, 12698049, 4802889, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> NUCKAL_SPAWN_EGG = register("nuckal_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.NUCKAL, 12698049, 6974058, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);

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

    public static void init() {}
}
