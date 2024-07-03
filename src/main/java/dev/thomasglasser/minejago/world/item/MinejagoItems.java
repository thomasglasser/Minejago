package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.item.CustomEmptyMapItem;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.armortrim.TrimPattern;

public class MinejagoItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Minejago.MOD_ID);
    public static final List<String> SHERDS = new ArrayList<>();
    public static final String MOD_NEEDED = "error.mod_needed";

    public static final DeferredItem<BambooStaffItem> BAMBOO_STAFF = register("bamboo_staff", () -> new BambooStaffItem(Tiers.WOOD, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.WOOD, 2, -1))), List.of(CreativeModeTabs.COMBAT));
    public static final DeferredItem<BoneKnifeItem> BONE_KNIFE = register("bone_knife", () -> new BoneKnifeItem(MinejagoTiers.BONE, new Item.Properties().attributes(SwordItem.createAttributes(MinejagoTiers.BONE, 3, -2))), List.of(CreativeModeTabs.COMBAT));
    public static final DeferredItem<SpearItem> IRON_SPEAR = register("iron_spear", () -> new SpearItem(Tiers.IRON, (new Item.Properties().attributes(SpearItem.createAttributes(Tiers.IRON, 4, -2.8F, 1f)))), List.of(CreativeModeTabs.COMBAT));
    public static final DeferredItem<ScytheOfQuakesItem> SCYTHE_OF_QUAKES = register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties()), List.of(CreativeModeTabs.COMBAT, CreativeModeTabs.TOOLS_AND_UTILITIES));
    public static final DeferredItem<ShurikenItem> IRON_SHURIKEN = register("iron_shuriken", () -> new ShurikenItem(Tiers.IRON, (new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 1, 1F)))), List.of(CreativeModeTabs.COMBAT));
    public static final DeferredItem<SwordItem> IRON_KATANA = register("iron_katana", () -> new SwordItem(Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 1, -1.4F))), List.of(CreativeModeTabs.COMBAT));
    public static final DeferredItem<TeacupItem> TEACUP = register("teacup", () -> new TeacupItem(new Item.Properties()), List.of(CreativeModeTabs.INGREDIENTS));
    public static final DeferredItem<FilledTeacupItem> FILLED_TEACUP = register("filled_teacup", () -> new FilledTeacupItem(new Item.Properties().stacksTo(1)), List.of());
    public static final DeferredItem<BannerPatternItem> FOUR_WEAPONS_BANNER_PATTERN = register("four_weapons_banner_pattern", () -> new BannerPatternItem(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)), List.of(CreativeModeTabs.INGREDIENTS));
    public static final DeferredItem<ScytheItem> IRON_SCYTHE = register("iron_scythe", () -> new ScytheItem(Tiers.IRON, new Item.Properties().attributes(DiggerItem.createAttributes(Tiers.IRON, 8, -3.5F))), List.of(CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.COMBAT));
    public static final DeferredItem<NunchucksItem> WOODEN_NUNCHUCKS = register("wooden_nunchucks", () -> new NunchucksItem(new Item.Properties().stacksTo(1)), List.of(CreativeModeTabs.COMBAT));
    public static final DeferredItem<ScrollItem> SCROLL = register("scroll", () -> new ScrollItem(new Item.Properties()), List.of(CreativeModeTabs.INGREDIENTS));
    public static final DeferredItem<WritableScrollItem> WRITABLE_SCROLL = register("writable_scroll", () -> new WritableScrollItem(new Item.Properties().stacksTo(1)), List.of(CreativeModeTabs.TOOLS_AND_UTILITIES));
    public static final DeferredItem<WrittenScrollItem> WRITTEN_SCROLL = register("written_scroll", () -> new WrittenScrollItem(new Item.Properties().stacksTo(1)), List.of());
    public static final DeferredItem<CustomEmptyMapItem> EMPTY_GOLDEN_WEAPONS_MAP = register("empty_golden_weapons_map", () -> new CustomEmptyMapItem(MinejagoItemUtils::createGoldenWeaponsMap, new Item.Properties()), List.of(MinejagoCreativeModeTabs.MINEJAGO.getKey()));

    // POTTERY SHERDS
    public static final DeferredItem<Item> POTTERY_SHERD_ICE_CUBE = registerSherd("ice_cube");
    public static final DeferredItem<Item> POTTERY_SHERD_THUNDER = registerSherd("thunder");
    public static final DeferredItem<Item> POTTERY_SHERD_PEAKS = registerSherd("peaks");
    public static final DeferredItem<Item> POTTERY_SHERD_MASTER = registerSherd("master");
    public static final DeferredItem<Item> POTTERY_SHERD_YIN_YANG = registerSherd("yin_yang");
    public static final DeferredItem<Item> POTTERY_SHERD_DRAGONS_HEAD = registerSherd("dragons_head");
    public static final DeferredItem<Item> POTTERY_SHERD_DRAGONS_TAIL = registerSherd("dragons_tail");

    // SMITHING TEMPLATES
    public static final DeferredItem<SmithingTemplateItem> FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.FOUR_WEAPONS);

    // SPAWN EGGS
    public static final DeferredItem<SpawnEggItem> WU_SPAWN_EGG = registerSpawnEgg("wu_spawn_egg", MinejagoEntityTypes.WU::get, 16645363, 14689295);
    public static final DeferredItem<SpawnEggItem> KAI_SPAWN_EGG = registerSpawnEgg("kai_spawn_egg", MinejagoEntityTypes.KAI::get, 9507597, 5185296);
    public static final DeferredItem<SpawnEggItem> NYA_SPAWN_EGG = registerSpawnEgg("nya_spawn_egg", MinejagoEntityTypes.NYA::get, 9507597, 3223857);
    public static final DeferredItem<SpawnEggItem> COLE_SPAWN_EGG = registerSpawnEgg("cole_spawn_egg", MinejagoEntityTypes.COLE::get, 1647949, 2697513);
    public static final DeferredItem<SpawnEggItem> JAY_SPAWN_EGG = registerSpawnEgg("jay_spawn_egg", MinejagoEntityTypes.JAY::get, 10057, 4854026);
    public static final DeferredItem<SpawnEggItem> ZANE_SPAWN_EGG = registerSpawnEgg("zane_spawn_egg", MinejagoEntityTypes.ZANE::get, 2697513, 16769956);
    public static final DeferredItem<SpawnEggItem> SKULKIN_SPAWN_EGG = registerSpawnEgg("skulkin_spawn_egg", MinejagoEntityTypes.SKULKIN::get, 12698049, 11348013);
    public static final DeferredItem<SpawnEggItem> KRUNCHA_SPAWN_EGG = registerSpawnEgg("kruncha_spawn_egg", MinejagoEntityTypes.KRUNCHA::get, 12698049, 4802889);
    public static final DeferredItem<SpawnEggItem> NUCKAL_SPAWN_EGG = registerSpawnEgg("nuckal_spawn_egg", MinejagoEntityTypes.NUCKAL::get, 12698049, 6974058);
    public static final DeferredItem<SpawnEggItem> SKULKIN_HORSE_SPAWN_EGG = registerSpawnEgg("skulkin_horse_spawn_egg", MinejagoEntityTypes.SKULKIN_HORSE::get, 0xfffffd, 0xad282d);
    public static final DeferredItem<SpawnEggItem> EARTH_DRAGON_SPAWN_EGG = registerSpawnEgg("earth_dragon_spawn_egg", MinejagoEntityTypes.EARTH_DRAGON::get, 0x412017, 0xa08d71);
    public static final DeferredItem<SpawnEggItem> SAMUKAI_SPAWN_EGG = registerSpawnEgg("samukai_spawn_egg", MinejagoEntityTypes.SAMUKAI::get, 0xdbd7bd, 0xb90e04);
    public static final DeferredItem<SpawnEggItem> SKULL_TRUCK_SPAWN_EGG = registerSpawnEgg("skull_truck_spawn_egg", MinejagoEntityTypes.SKULL_TRUCK::get, 0xcbc6a5, 0x832696);
    public static final DeferredItem<SpawnEggItem> SKULL_MOTORBIKE_SPAWN_EGG = registerSpawnEgg("skull_motorbike_spawn_egg", MinejagoEntityTypes.SKULL_MOTORBIKE::get, 0xd4cfae, 0x9143ff);

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> itemSupplier, List<ResourceKey<CreativeModeTab>> tabs) {
        return ItemUtils.register(ITEMS, name, itemSupplier, tabs);
    }

    private static DeferredItem<Item> registerSherd(String name) {
        SHERDS.add(name);
        return ItemUtils.registerSherd(ITEMS, name);
    }

    public static void init() {}

    private static Map<DyeColor, DeferredItem<Item>> teapots() {
        Map<DyeColor, DeferredItem<Item>> map = new HashMap<>();
        for (DyeColor color : DyeColor.values()) {
            map.put(color, register(color.getName() + "_teapot", () -> new BlockItem(MinejagoBlocks.TEAPOTS.get(color).get(), new Item.Properties().stacksTo(1)), List.of(CreativeModeTabs.FUNCTIONAL_BLOCKS)));
        }
        return map;
    }

    private static DeferredItem<SmithingTemplateItem> registerSmithingTemplate(ResourceKey<TrimPattern> pattern) {
        return ItemUtils.registerSmithingTemplate(ITEMS, pattern);
    }

    private static DeferredItem<SpawnEggItem> registerSpawnEgg(String name, Supplier<EntityType<? extends Mob>> entityType, int primaryColor, int secondaryColor) {
        return ItemUtils.registerSpawnEgg(ITEMS, name, entityType, primaryColor, secondaryColor);
    }
}
