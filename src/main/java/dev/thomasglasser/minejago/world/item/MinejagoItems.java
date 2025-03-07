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
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import dev.thomasglasser.tommylib.api.world.item.ModeledThrowableSwordItem;
import dev.thomasglasser.tommylib.api.world.item.ThrowableSwordItem;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.BannerPattern;

public class MinejagoItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Minejago.MOD_ID);

    public static final String MOD_NEEDED = "error.mod_needed";

    public static final DeferredItem<ModeledThrowableSwordItem> BAMBOO_STAFF = register("bamboo_staff", () -> new ModeledThrowableSwordItem(MinejagoEntityTypes.THROWN_BAMBOO_STAFF::value, MinejagoSoundEvents.BAMBOO_STAFF_THROW, MinejagoSoundEvents.BAMBOO_STAFF_IMPACT, MinejagoTiers.BONE, new Item.Properties()) {
        @Override
        public void createBewlrProvider(Consumer<BewlrProvider> consumer) {
            consumer.accept(new BewlrProvider() {
                @Override
                public BlockEntityWithoutLevelRenderer getBewlr() {
                    return MinejagoClientUtils.getBewlr();
                }
            });
        }
    });
    public static final DeferredItem<ThrowableSwordItem> BONE_KNIFE = register("bone_knife", () -> new ThrowableSwordItem(MinejagoEntityTypes.THROWN_BONE_KNIFE::value, MinejagoSoundEvents.BONE_KNIFE_THROW, MinejagoSoundEvents.BONE_KNIFE_IMPACT, MinejagoTiers.BONE, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SCROLL = register("scroll", () -> new Item(new Item.Properties()));
    public static final DeferredItem<WritableScrollItem> WRITABLE_SCROLL = register("writable_scroll", () -> new WritableScrollItem(new Item.Properties().stacksTo(1).component(DataComponents.WRITABLE_BOOK_CONTENT, WritableBookContent.EMPTY)));
    public static final DeferredItem<WrittenScrollItem> WRITTEN_SCROLL = register("written_scroll", () -> new WrittenScrollItem(new Item.Properties().stacksTo(16).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)));

    // Teacups
    private static final Function<ItemLike, TeacupItem> TEACUP_FUNCTION = (filled) -> new TeacupItem(new Item.Properties(), filled);
    private static final Function<ItemLike, FilledTeacupItem> FILLED_TEACUP_FUNCTION = (item) -> new FilledTeacupItem(new Item.Properties().stacksTo(1).component(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).component(DataComponents.FOOD, new FoodProperties.Builder().usingConvertsTo(item).alwaysEdible().build()));
    public static final DeferredItem<TeacupItem> TEACUP = register("teacup", () -> TEACUP_FUNCTION.apply(BuiltInRegistries.ITEM.get(Minejago.modLoc("filled_teacup"))));
    public static final SortedMap<DyeColor, DeferredItem<TeacupItem>> TEACUPS = teacups();
    public static final DeferredItem<TeacupItem> MINICUP = register("minicup", () -> TEACUP_FUNCTION.apply(BuiltInRegistries.ITEM.get(Minejago.modLoc("filled_minicup"))));
    public static final DeferredItem<FilledTeacupItem> FILLED_TEACUP = register("filled_teacup", () -> FILLED_TEACUP_FUNCTION.apply(TEACUP));
    public static final SortedMap<DyeColor, DeferredItem<FilledTeacupItem>> FILLED_TEACUPS = filledTeacups();
    public static final DeferredItem<FilledTeacupItem> FILLED_MINICUP = register("filled_minicup", () -> FILLED_TEACUP_FUNCTION.apply(MINICUP));

    //
    public static final DeferredItem<ScytheOfQuakesItem> SCYTHE_OF_QUAKES = register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties()));
    public static final DeferredItem<ShurikenOfIceItem> SHURIKEN_OF_ICE = register("shuriken_of_ice", () -> new ShurikenOfIceItem(new Item.Properties()));

    // Banner Patterns
    public static final DeferredItem<BannerPatternItem> FOUR_WEAPONS_BANNER_PATTERN = registerBannerPattern(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS);
    public static final DeferredItem<BannerPatternItem> NINJA_BANNER_PATTERN = registerBannerPattern(MinejagoBannerPatternTags.PATTERN_ITEM_NINJA);

    // Pottery Sherds
    public static final DeferredItem<Item> ICE_CUBE_POTTERY_SHERD = registerSherd("ice_cube");
    public static final DeferredItem<Item> THUNDER_POTTERY_SHERD = registerSherd("thunder");
    public static final DeferredItem<Item> PEAKS_POTTERY_SHERD = registerSherd("peaks");
    public static final DeferredItem<Item> MASTER_POTTERY_SHERD = registerSherd("master");
    public static final DeferredItem<Item> YIN_YANG_POTTERY_SHERD = registerSherd("yin_yang");
    public static final DeferredItem<Item> DRAGONS_HEAD_POTTERY_SHERD = registerSherd("dragons_head");
    public static final DeferredItem<Item> DRAGONS_TAIL_POTTERY_SHERD = registerSherd("dragons_tail");

    // Smithing Templates
    public static final DeferredItem<SmithingTemplateItem> FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.FOUR_WEAPONS);
    public static final DeferredItem<SmithingTemplateItem> TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.TERRAIN);
    public static final DeferredItem<SmithingTemplateItem> LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.LOTUS);

    // Spawn Eggs
    public static final DeferredItem<SpawnEggItem> COLE_SPAWN_EGG = registerSpawnEgg("cole_spawn_egg", MinejagoEntityTypes.COLE::get, 1647949, 2697513);
    public static final DeferredItem<SpawnEggItem> EARTH_DRAGON_SPAWN_EGG = registerSpawnEgg("earth_dragon_spawn_egg", MinejagoEntityTypes.EARTH_DRAGON::get, 0x412017, 0xa08d71);
    public static final DeferredItem<SpawnEggItem> JAY_SPAWN_EGG = registerSpawnEgg("jay_spawn_egg", MinejagoEntityTypes.JAY::get, 10057, 4854026);
    public static final DeferredItem<SpawnEggItem> KAI_SPAWN_EGG = registerSpawnEgg("kai_spawn_egg", MinejagoEntityTypes.KAI::get, 9507597, 5185296);
    public static final DeferredItem<SpawnEggItem> KRUNCHA_SPAWN_EGG = registerSpawnEgg("kruncha_spawn_egg", MinejagoEntityTypes.KRUNCHA::get, 12698049, 4802889);
    public static final DeferredItem<SpawnEggItem> NUCKAL_SPAWN_EGG = registerSpawnEgg("nuckal_spawn_egg", MinejagoEntityTypes.NUCKAL::get, 12698049, 6974058);
    public static final DeferredItem<SpawnEggItem> NYA_SPAWN_EGG = registerSpawnEgg("nya_spawn_egg", MinejagoEntityTypes.NYA::get, 9507597, 3223857);
    public static final DeferredItem<SpawnEggItem> SAMUKAI_SPAWN_EGG = registerSpawnEgg("samukai_spawn_egg", MinejagoEntityTypes.SAMUKAI::get, 0xdbd7bd, 0xb90e04);
    public static final DeferredItem<SpawnEggItem> SKULL_MOTORBIKE_SPAWN_EGG = registerSpawnEgg("skull_motorbike_spawn_egg", MinejagoEntityTypes.SKULL_MOTORBIKE::get, 0xd4cfae, 0x9143ff);
    public static final DeferredItem<SpawnEggItem> SKULL_TRUCK_SPAWN_EGG = registerSpawnEgg("skull_truck_spawn_egg", MinejagoEntityTypes.SKULL_TRUCK::get, 0xcbc6a5, 0x832696);
    public static final DeferredItem<SpawnEggItem> SKULKIN_SPAWN_EGG = registerSpawnEgg("skulkin_spawn_egg", MinejagoEntityTypes.SKULKIN::get, 12698049, 11348013);
    public static final DeferredItem<SpawnEggItem> SKULKIN_HORSE_SPAWN_EGG = registerSpawnEgg("skulkin_horse_spawn_egg", MinejagoEntityTypes.SKULKIN_HORSE::get, 0xfffffd, 0xad282d);
    public static final DeferredItem<SpawnEggItem> SPYKOR_SPAWN_EGG = registerSpawnEgg("spykor_spawn_egg", MinejagoEntityTypes.SPYKOR::get, 0xdbd7bd, 0x20211c);
    public static final DeferredItem<SpawnEggItem> WU_SPAWN_EGG = registerSpawnEgg("wu_spawn_egg", MinejagoEntityTypes.WU::get, 16645363, 14689295);
    public static final DeferredItem<SpawnEggItem> ZANE_SPAWN_EGG = registerSpawnEgg("zane_spawn_egg", MinejagoEntityTypes.ZANE::get, 2697513, 16769956);

    // Spinjitzu Course Elements
    public static final DeferredItem<SpinjitzuCourseElementItem> CENTER_SPINJITZU_COURSE_ELEMENT = register("center_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.CENTER_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()));
    public static final DeferredItem<SpinjitzuCourseElementItem> SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT = register("spinning_dummies_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()));
    public static final DeferredItem<SpinjitzuCourseElementItem> SPINNING_POLE_SPINJITZU_COURSE_ELEMENT = register("spinning_pole_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()));
    public static final DeferredItem<SpinjitzuCourseElementItem> SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT = register("swirling_knives_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()));
    public static final DeferredItem<SpinjitzuCourseElementItem> BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT = register("bouncing_pole_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()));
    public static final DeferredItem<SpinjitzuCourseElementItem> SPINNING_AXES_SPINJITZU_COURSE_ELEMENT = register("spinning_axes_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()));
    public static final DeferredItem<SpinjitzuCourseElementItem> ROCKING_POLE_SPINJITZU_COURSE_ELEMENT = register("rocking_pole_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()));
    public static final DeferredItem<SpinjitzuCourseElementItem> SPINNING_MACES_SPINJITZU_COURSE_ELEMENT = register("spinning_maces_spinjitzu_course_element", () -> new SpinjitzuCourseElementItem(MinejagoEntityTypes.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT::get, new Item.Properties()));

    public static void init() {}

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> itemFunction) {
        return ItemUtils.register(ITEMS, name, itemFunction);
    }

    private static DeferredItem<BannerPatternItem> registerBannerPattern(TagKey<BannerPattern> patterns, Rarity rarity) {
        return ItemUtils.registerBannerPattern(ITEMS, patterns, new Item.Properties().rarity(rarity));
    }

    private static DeferredItem<BannerPatternItem> registerBannerPattern(TagKey<BannerPattern> patterns) {
        return registerBannerPattern(patterns, Rarity.UNCOMMON);
    }

    private static DeferredItem<Item> registerSherd(String name) {
        return ItemUtils.registerSherd(ITEMS, name, new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    private static DeferredItem<SmithingTemplateItem> registerSmithingTemplate(ResourceKey<TrimPattern> pattern) {
        return ItemUtils.registerSmithingTemplate(ITEMS, pattern);
    }

    private static DeferredItem<SpawnEggItem> registerSpawnEgg(String name, Supplier<EntityType<? extends Mob>> entityType, int primaryColor, int secondaryColor) {
        return ItemUtils.registerSpawnEgg(ITEMS, name, entityType, primaryColor, secondaryColor);
    }

    private static SortedMap<DyeColor, DeferredItem<TeacupItem>> teacups() {
        SortedMap<DyeColor, DeferredItem<TeacupItem>> map = new TreeMap<>();
        for (DyeColor color : DyeColor.values()) {
            map.put(color, register(color.getName() + "_teacup", () -> TEACUP_FUNCTION.apply(FILLED_TEACUPS.get(color))));
        }
        return map;
    }

    private static SortedMap<DyeColor, DeferredItem<FilledTeacupItem>> filledTeacups() {
        SortedMap<DyeColor, DeferredItem<FilledTeacupItem>> map = new TreeMap<>();
        for (DyeColor color : DyeColor.values()) {
            map.put(color, register(color.getName() + "_filled_teacup", () -> FILLED_TEACUP_FUNCTION.apply(TEACUPS.get(color))));
        }
        return map;
    }

    public static SortedSet<TeacupItem> allTeacups() {
        SortedSet<TeacupItem> set = new ReferenceLinkedOpenHashSet<>();
        set.add(TEACUP.get());
        TEACUPS.values().forEach(cup -> set.add(cup.get()));
        set.add(MINICUP.get());
        return set;
    }

    public static SortedSet<FilledTeacupItem> allFilledTeacups() {
        SortedSet<FilledTeacupItem> set = new ReferenceLinkedOpenHashSet<>();
        set.add(FILLED_TEACUP.get());
        FILLED_TEACUPS.values().forEach(cup -> set.add(cup.get()));
        set.add(FILLED_MINICUP.get());
        return set;
    }
}
