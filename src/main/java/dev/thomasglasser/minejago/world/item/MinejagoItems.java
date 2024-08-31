package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import dev.thomasglasser.tommylib.api.client.renderer.BewlrProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.item.BaseModeledThrowableSwordItem;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import dev.thomasglasser.tommylib.api.world.item.ThrowableSwordItem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.block.entity.BannerPattern;

public class MinejagoItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Minejago.MOD_ID);
    public static final List<String> SHERDS = new ArrayList<>();

    public static final String MOD_NEEDED = "error.mod_needed";

    public static final DeferredItem<BaseModeledThrowableSwordItem> BAMBOO_STAFF = register("bamboo_staff", () -> new BaseModeledThrowableSwordItem(MinejagoEntityTypes.THROWN_BAMBOO_STAFF::value, MinejagoSoundEvents.BAMBOO_STAFF_THROW, MinejagoSoundEvents.BAMBOO_STAFF_IMPACT, Tiers.WOOD, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.WOOD, 2, -1))) {
        @Override
        public void createBewlrProvider(Consumer<BewlrProvider> consumer) {
            consumer.accept(new BewlrProvider() {
                @Override
                public BlockEntityWithoutLevelRenderer getBewlr() {
                    return MinejagoClientUtils.getBewlr();
                }
            });
        }
    }, List.of(CreativeModeTabs.COMBAT));
    public static final DeferredItem<ThrowableSwordItem> BONE_KNIFE = register("bone_knife", () -> new ThrowableSwordItem(MinejagoEntityTypes.THROWN_BONE_KNIFE::value, MinejagoSoundEvents.BONE_KNIFE_THROW, MinejagoSoundEvents.BONE_KNIFE_IMPACT, MinejagoTiers.BONE, new Item.Properties().attributes(SwordItem.createAttributes(MinejagoTiers.BONE, 3, -2))), List.of(CreativeModeTabs.COMBAT));
    public static final DeferredItem<ScytheOfQuakesItem> SCYTHE_OF_QUAKES = register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties()), List.of(CreativeModeTabs.COMBAT, CreativeModeTabs.TOOLS_AND_UTILITIES));
    public static final DeferredItem<TeacupItem> TEACUP = register("teacup", () -> new TeacupItem(new Item.Properties()), List.of(CreativeModeTabs.INGREDIENTS));
    public static final DeferredItem<FilledTeacupItem> FILLED_TEACUP = register("filled_teacup", () -> new FilledTeacupItem(new Item.Properties().stacksTo(1)), List.of());
    public static final DeferredItem<ScrollItem> SCROLL = register("scroll", () -> new ScrollItem(new Item.Properties()), List.of(CreativeModeTabs.INGREDIENTS));
    public static final DeferredItem<WritableScrollItem> WRITABLE_SCROLL = register("writable_scroll", () -> new WritableScrollItem(new Item.Properties().stacksTo(1)), List.of(CreativeModeTabs.TOOLS_AND_UTILITIES));
    public static final DeferredItem<WrittenScrollItem> WRITTEN_SCROLL = register("written_scroll", () -> new WrittenScrollItem(new Item.Properties().stacksTo(1)), List.of());

    // Banner Patterns
    public static final DeferredItem<BannerPatternItem> FOUR_WEAPONS_BANNER_PATTERN = registerBannerPattern("four_weapons", MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS);
    public static final DeferredItem<BannerPatternItem> NINJA_BANNER_PATTERN = registerBannerPattern("ninja", MinejagoBannerPatternTags.PATTERN_ITEM_NINJA);

    // Pottery Sherds
    public static final DeferredItem<Item> POTTERY_SHERD_ICE_CUBE = registerSherd("ice_cube");
    public static final DeferredItem<Item> POTTERY_SHERD_THUNDER = registerSherd("thunder");
    public static final DeferredItem<Item> POTTERY_SHERD_PEAKS = registerSherd("peaks");
    public static final DeferredItem<Item> POTTERY_SHERD_MASTER = registerSherd("master");
    public static final DeferredItem<Item> POTTERY_SHERD_YIN_YANG = registerSherd("yin_yang");
    public static final DeferredItem<Item> POTTERY_SHERD_DRAGONS_HEAD = registerSherd("dragons_head");
    public static final DeferredItem<Item> POTTERY_SHERD_DRAGONS_TAIL = registerSherd("dragons_tail");

    // Smithing Templates
    public static final DeferredItem<SmithingTemplateItem> FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.FOUR_WEAPONS);
    public static final DeferredItem<SmithingTemplateItem> TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.TERRAIN);
    public static final DeferredItem<SmithingTemplateItem> LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.LOTUS);

    // Spawn Eggs
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

    // Spinjitzu Course Elements
    public static final DeferredItem<SpinjitzuCourseElementItem> CENTER_SPINJITZU_COURSE_ELEMENT = register("center_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.CENTER_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()), List.of(CreativeModeTabs.REDSTONE_BLOCKS));
    public static final DeferredItem<SpinjitzuCourseElementItem> BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT = register("bouncing_pole_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()), List.of(CreativeModeTabs.REDSTONE_BLOCKS));
    public static final DeferredItem<SpinjitzuCourseElementItem> ROCKING_POLE_SPINJITZU_COURSE_ELEMENT = register("rocking_pole_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()), List.of(CreativeModeTabs.REDSTONE_BLOCKS));
    public static final DeferredItem<SpinjitzuCourseElementItem> SPINNING_POLE_SPINJITZU_COURSE_ELEMENT = register("spinning_pole_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()), List.of(CreativeModeTabs.REDSTONE_BLOCKS));
    public static final DeferredItem<SpinjitzuCourseElementItem> SPINNING_MACES_SPINJITZU_COURSE_ELEMENT = register("spinning_maces_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()), List.of(CreativeModeTabs.REDSTONE_BLOCKS));
    public static final DeferredItem<SpinjitzuCourseElementItem> SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT = register("spinning_dummies_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()), List.of(CreativeModeTabs.REDSTONE_BLOCKS));
    public static final DeferredItem<SpinjitzuCourseElementItem> SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT = register("swirling_knives_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()), List.of(CreativeModeTabs.REDSTONE_BLOCKS));
    public static final DeferredItem<SpinjitzuCourseElementItem> SPINNING_AXES_SPINJITZU_COURSE_ELEMENT = register("spinning_axes_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()), List.of(CreativeModeTabs.REDSTONE_BLOCKS));

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> itemSupplier, List<ResourceKey<CreativeModeTab>> tabs) {
        return ItemUtils.register(ITEMS, name, itemSupplier, tabs);
    }

    public static void init() {}

    private static DeferredItem<BannerPatternItem> registerBannerPattern(String name, TagKey<BannerPattern> patterns) {
        return register(name + "_banner_pattern", () -> new BannerPatternItem(patterns, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)), List.of(CreativeModeTabs.INGREDIENTS));
    }

    private static DeferredItem<Item> registerSherd(String name) {
        SHERDS.add(name);
        return ItemUtils.registerSherd(ITEMS, name);
    }

    private static DeferredItem<SmithingTemplateItem> registerSmithingTemplate(ResourceKey<TrimPattern> pattern) {
        return ItemUtils.registerSmithingTemplate(ITEMS, pattern);
    }

    private static DeferredItem<SpawnEggItem> registerSpawnEgg(String name, Supplier<EntityType<? extends Mob>> entityType, int primaryColor, int secondaryColor) {
        return ItemUtils.registerSpawnEgg(ITEMS, name, entityType, primaryColor, secondaryColor);
    }
}
