package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.armortrim.TrimPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MinejagoItems
{
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Minejago.MOD_ID);
    private static final HashMap<ResourceKey<CreativeModeTab>, ArrayList<ResourceLocation>> ITEM_TABS = new HashMap<>();
    public static final List<ResourceLocation> SHERDS = new ArrayList<>();
    public static final String MOD_NEEDED = "error.mod_needed";

    public static final RegistryObject<BambooStaffItem> BAMBOO_STAFF = register("bamboo_staff", () -> new BambooStaffItem(Tiers.WOOD, 2, -1, new Item.Properties()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<BoneKnifeItem> BONE_KNIFE = register("bone_knife", () -> new BoneKnifeItem(MinejagoTiers.BONE, 3, -2, new Item.Properties()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<SpearItem> IRON_SPEAR = register("iron_spear", () -> new SpearItem(Tiers.IRON, 4, -2.8F, 1f, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<ScytheOfQuakesItem> SCYTHE_OF_QUAKES = register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties().defaultDurability(0).rarity(Rarity.EPIC).fireResistant().stacksTo(1)), CreativeModeTabs.COMBAT, CreativeModeTabs.TOOLS_AND_UTILITIES);
    public static final RegistryObject<ShurikenItem> IRON_SHURIKEN = register("iron_shuriken", () -> new ShurikenItem(Tiers.IRON, 1, 1F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<SwordItem> IRON_KATANA = register("iron_katana", () -> new SwordItem(Tiers.IRON, 1, -1.4F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<TeacupItem> TEACUP = register("teacup", () -> new TeacupItem(new Item.Properties()), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<FilledTeacupItem> FILLED_TEACUP = register("filled_teacup", () -> new FilledTeacupItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<BannerPatternItem> FOUR_WEAPONS_BANNER_PATTERN = register("four_weapons_banner_pattern", () -> new BannerPatternItem(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<IronScytheItem> IRON_SCYTHE = register("iron_scythe", () -> new IronScytheItem(Tiers.IRON, 8, -3.5F, BlockTags.MINEABLE_WITH_HOE, new Item.Properties()), CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.COMBAT);
    public static final RegistryObject<NunchucksItem> WOODEN_NUNCHUCKS = register("wooden_nunchucks", () -> new NunchucksItem(new Item.Properties().stacksTo(1)), CreativeModeTabs.COMBAT);
    public static final RegistryObject<ScrollItem> SCROLL = register("scroll", () -> new ScrollItem(new Item.Properties()), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<WritableScrollItem> WRITABLE_SCROLL = register("writable_scroll", () -> new WritableScrollItem(new Item.Properties().stacksTo(1)), CreativeModeTabs.TOOLS_AND_UTILITIES);
    public static final RegistryObject<WrittenScrollItem> WRITTEN_SCROLL = register("written_scroll", () -> new WrittenScrollItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<CustomEmptyMapItem> EMPTY_GOLDEN_WEAPONS_MAP = register("empty_golden_weapons_map", () -> new CustomEmptyMapItem(CustomEmptyMapItem::getFourWeaponsMap, new Item.Properties()), MinejagoCreativeModeTabs.MINEJAGO.getResourceKey());

    // POTTERY SHERDS
    public static final RegistryObject<Item> POTTERY_SHERD_ICE_CUBE = registerSherd("pottery_sherd_ice_cube");
    public static final RegistryObject<Item> POTTERY_SHERD_THUNDER = registerSherd("pottery_sherd_thunder");
    public static final RegistryObject<Item> POTTERY_SHERD_PEAKS = registerSherd("pottery_sherd_peaks");
    public static final RegistryObject<Item> POTTERY_SHERD_MASTER = registerSherd("pottery_sherd_master");
    public static final RegistryObject<Item> POTTERY_SHERD_YIN_YANG = registerSherd("pottery_sherd_yin_yang");
    public static final RegistryObject<Item> POTTERY_SHERD_DRAGONS_HEAD = registerSherd("pottery_sherd_dragons_head");
    public static final RegistryObject<Item> POTTERY_SHERD_DRAGONS_TAIL = registerSherd("pottery_sherd_dragons_tail");

    // SMITHING TEMPLATES
    public static final RegistryObject<SmithingTemplateItem> FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.FOUR_WEAPONS);

    // SPAWN EGGS
    public static final RegistryObject<SpawnEggItem> WU_SPAWN_EGG = register("wu_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.WU::get, 16645363, 14689295, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> KAI_SPAWN_EGG = register("kai_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.KAI::get, 9507597, 5185296, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> NYA_SPAWN_EGG = register("nya_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.NYA::get, 9507597, 3223857, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> COLE_SPAWN_EGG = register("cole_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.COLE::get, 1647949, 2697513, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> JAY_SPAWN_EGG = register("jay_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.JAY::get, 10057, 4854026, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> ZANE_SPAWN_EGG = register("zane_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.ZANE::get, 2697513, 16769956, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> SKULKIN_SPAWN_EGG = register("skulkin_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.SKULKIN::get, 12698049, 11348013, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> KRUNCHA_SPAWN_EGG = register("kruncha_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.KRUNCHA::get, 12698049, 4802889, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> NUCKAL_SPAWN_EGG = register("nuckal_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.NUCKAL::get, 12698049, 6974058, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> SKULKIN_HORSE_SPAWN_EGG = register("skulkin_horse_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.SKULKIN_HORSE::get, 0xfffffd, 0xad282d, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> EARTH_DRAGON_SPAWN_EGG = register("earth_dragon_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.EARTH_DRAGON::get, 0x412017, 0xa08d71, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> SAMUKAI_SPAWN_EGG = register("samukai_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.SAMUKAI::get, 0xdbd7bd, 0xb90e04, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> SKULL_TRUCK_SPAWN_EGG = register("skull_truck_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.SKULL_TRUCK::get, 0xcbc6a5, 0x832696, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<SpawnEggItem> SKULL_MOTORBIKE_SPAWN_EGG = register("skull_motorbike_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.SKULL_MOTORBIKE::get, 0xd4cfae, 0x9143ff, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);

    @SafeVarargs
    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item, ResourceKey<CreativeModeTab>... tabs)
    {
        for (ResourceKey<CreativeModeTab> tab: tabs) {
            ArrayList<ResourceLocation> list = ITEM_TABS.computeIfAbsent(tab, empty -> new ArrayList<>());
            list.add(new ResourceLocation(Minejago.MOD_ID, name));
        }
        return ITEMS.register(name, item);
    }

    private static RegistryObject<Item> registerSherd(String name)
    {
        RegistryObject<Item> sherd = register(name, () -> new Item(new Item.Properties()), CreativeModeTabs.INGREDIENTS);

        SHERDS.add(Minejago.modLoc(name));

        return sherd;
    }

    private static RegistryObject<SmithingTemplateItem> registerSmithingTemplate(ResourceKey<TrimPattern> key)
    {
        return register(key.location().getPath() + "_armor_trim_smithing_template", () -> (SmithingTemplateItem.createArmorTrimTemplate(key)), CreativeModeTabs.INGREDIENTS);
    }

    public static Map<ResourceKey<CreativeModeTab>, ArrayList<ResourceLocation>> getItemTabs() {
        return ITEM_TABS;
    }

    public static void init() {}

    private static Map<DyeColor, RegistryObject<Item>> teapots()
    {
        Map<DyeColor, RegistryObject<Item>> map = new HashMap<>();
        for (DyeColor color : DyeColor.values())
        {
            map.put(color, register(color.getName() + "_teapot", () -> new BlockItem(MinejagoBlocks.TEAPOTS.get(color).get(), new Item.Properties().stacksTo(1)), CreativeModeTabs.FUNCTIONAL_BLOCKS));
        }
        return map;
    }
}
