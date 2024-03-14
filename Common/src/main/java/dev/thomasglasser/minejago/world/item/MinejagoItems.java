package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.world.item.CustomEmptyMapItem;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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
    public static final List<ResourceLocation> SHERDS = new ArrayList<>();
    public static final String MOD_NEEDED = "error.mod_needed";

    public static final RegistryObject<BambooStaffItem> BAMBOO_STAFF = register("bamboo_staff", () -> new BambooStaffItem(Tiers.WOOD, 2, -1, new Item.Properties()), List.of(CreativeModeTabs.COMBAT));
    public static final RegistryObject<BoneKnifeItem> BONE_KNIFE = register("bone_knife", () -> new BoneKnifeItem(MinejagoTiers.BONE, 3, -2, new Item.Properties()), List.of(CreativeModeTabs.COMBAT));
    public static final RegistryObject<SpearItem> IRON_SPEAR = register("iron_spear", () -> new SpearItem(Tiers.IRON, 4, -2.8F, 1f, (new Item.Properties())), List.of(CreativeModeTabs.COMBAT));
    public static final RegistryObject<ScytheOfQuakesItem> SCYTHE_OF_QUAKES = register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties().defaultDurability(0).rarity(Rarity.EPIC).fireResistant().stacksTo(1)), List.of(CreativeModeTabs.COMBAT, CreativeModeTabs.TOOLS_AND_UTILITIES));
    public static final RegistryObject<ShurikenItem> IRON_SHURIKEN = register("iron_shuriken", () -> new ShurikenItem(Tiers.IRON, 1, 1F, (new Item.Properties())), List.of(CreativeModeTabs.COMBAT));
    public static final RegistryObject<SwordItem> IRON_KATANA = register("iron_katana", () -> new SwordItem(Tiers.IRON, 1, -1.4F, (new Item.Properties())), List.of(CreativeModeTabs.COMBAT));
    public static final RegistryObject<TeacupItem> TEACUP = register("teacup", () -> new TeacupItem(new Item.Properties()), List.of(CreativeModeTabs.INGREDIENTS));
    public static final RegistryObject<FilledTeacupItem> FILLED_TEACUP = register("filled_teacup", () -> new FilledTeacupItem(new Item.Properties().stacksTo(1)), List.of());
    public static final RegistryObject<BannerPatternItem> FOUR_WEAPONS_BANNER_PATTERN = register("four_weapons_banner_pattern", () -> new BannerPatternItem(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)), List.of(CreativeModeTabs.INGREDIENTS));
    public static final RegistryObject<IronScytheItem> IRON_SCYTHE = register("iron_scythe", () -> new IronScytheItem(Tiers.IRON, 8, -3.5F, BlockTags.MINEABLE_WITH_HOE, new Item.Properties()), List.of(CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.COMBAT));
    public static final RegistryObject<NunchucksItem> WOODEN_NUNCHUCKS = register("wooden_nunchucks", () -> new NunchucksItem(new Item.Properties().stacksTo(1)), List.of(CreativeModeTabs.COMBAT));
    public static final RegistryObject<ScrollItem> SCROLL = register("scroll", () -> new ScrollItem(new Item.Properties()), List.of(CreativeModeTabs.INGREDIENTS));
    public static final RegistryObject<WritableScrollItem> WRITABLE_SCROLL = register("writable_scroll", () -> new WritableScrollItem(new Item.Properties().stacksTo(1)), List.of(CreativeModeTabs.TOOLS_AND_UTILITIES));
    public static final RegistryObject<WrittenScrollItem> WRITTEN_SCROLL = register("written_scroll", () -> new WrittenScrollItem(new Item.Properties().stacksTo(1)), List.of());
    public static final RegistryObject<CustomEmptyMapItem> EMPTY_GOLDEN_WEAPONS_MAP = register("empty_golden_weapons_map", () -> new CustomEmptyMapItem(MinejagoItemUtils::getFourWeaponsMap, new Item.Properties()), List.of(MinejagoCreativeModeTabs.MINEJAGO.getResourceKey()));

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
    public static final RegistryObject<SpawnEggItem> WU_SPAWN_EGG = registerSpawnEgg("wu_spawn_egg", MinejagoEntityTypes.WU::get, 16645363, 14689295);
    public static final RegistryObject<SpawnEggItem> KAI_SPAWN_EGG = registerSpawnEgg("kai_spawn_egg", MinejagoEntityTypes.KAI::get, 9507597, 5185296);
    public static final RegistryObject<SpawnEggItem> NYA_SPAWN_EGG = registerSpawnEgg("nya_spawn_egg", MinejagoEntityTypes.NYA::get, 9507597, 3223857);
    public static final RegistryObject<SpawnEggItem> COLE_SPAWN_EGG = registerSpawnEgg("cole_spawn_egg", MinejagoEntityTypes.COLE::get, 1647949, 2697513);
    public static final RegistryObject<SpawnEggItem> JAY_SPAWN_EGG = registerSpawnEgg("jay_spawn_egg", MinejagoEntityTypes.JAY::get, 10057, 4854026);
    public static final RegistryObject<SpawnEggItem> ZANE_SPAWN_EGG = registerSpawnEgg("zane_spawn_egg", MinejagoEntityTypes.ZANE::get, 2697513, 16769956);
    public static final RegistryObject<SpawnEggItem> SKULKIN_SPAWN_EGG = registerSpawnEgg("skulkin_spawn_egg", MinejagoEntityTypes.SKULKIN::get, 12698049, 11348013);
    public static final RegistryObject<SpawnEggItem> KRUNCHA_SPAWN_EGG = registerSpawnEgg("kruncha_spawn_egg", MinejagoEntityTypes.KRUNCHA::get, 12698049, 4802889);
    public static final RegistryObject<SpawnEggItem> NUCKAL_SPAWN_EGG = registerSpawnEgg("nuckal_spawn_egg", MinejagoEntityTypes.NUCKAL::get, 12698049, 6974058);
    public static final RegistryObject<SpawnEggItem> SKULKIN_HORSE_SPAWN_EGG = registerSpawnEgg("skulkin_horse_spawn_egg", MinejagoEntityTypes.SKULKIN_HORSE::get, 0xfffffd, 0xad282d);
    public static final RegistryObject<SpawnEggItem> EARTH_DRAGON_SPAWN_EGG = registerSpawnEgg("earth_dragon_spawn_egg", MinejagoEntityTypes.EARTH_DRAGON::get, 0x412017, 0xa08d71);
    public static final RegistryObject<SpawnEggItem> SAMUKAI_SPAWN_EGG = registerSpawnEgg("samukai_spawn_egg", MinejagoEntityTypes.SAMUKAI::get, 0xdbd7bd, 0xb90e04);
    public static final RegistryObject<SpawnEggItem> SKULL_TRUCK_SPAWN_EGG = registerSpawnEgg("skull_truck_spawn_egg", MinejagoEntityTypes.SKULL_TRUCK::get, 0xcbc6a5, 0x832696);
    public static final RegistryObject<SpawnEggItem> SKULL_MOTORBIKE_SPAWN_EGG = registerSpawnEgg("skull_motorbike_spawn_egg", MinejagoEntityTypes.SKULL_MOTORBIKE::get, 0xd4cfae, 0x9143ff);

    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> itemSupplier, List<ResourceKey<CreativeModeTab>> tabs)
    {
        return ItemUtils.register(ITEMS, name, itemSupplier, tabs);
    }

    private static RegistryObject<Item> registerSherd(String name)
    {
        SHERDS.add(Minejago.modLoc(name));
        return ItemUtils.registerSherd(ITEMS, name);
    }

    public static void init() {}

    private static Map<DyeColor, RegistryObject<Item>> teapots()
    {
        Map<DyeColor, RegistryObject<Item>> map = new HashMap<>();
        for (DyeColor color : DyeColor.values())
        {
            map.put(color, register(color.getName() + "_teapot", () -> new BlockItem(MinejagoBlocks.TEAPOTS.get(color).get(), new Item.Properties().stacksTo(1)), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS)));
        }
        return map;
    }
    
    private static RegistryObject<SmithingTemplateItem> registerSmithingTemplate(ResourceKey<TrimPattern> pattern)
    {
        return ItemUtils.registerSmithingTemplate(ITEMS, pattern);
    }
    
    private static RegistryObject<SpawnEggItem> registerSpawnEgg(String name, Supplier<EntityType<? extends Mob>> entityType, int primaryColor, int secondaryColor)
    {
        return ItemUtils.registerSpawnEgg(ITEMS, name, entityType, primaryColor, secondaryColor);
    }
}
