package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.armor.IGeoArmorItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.sherdsapi.api.PotterySherdRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.armortrim.TrimPattern;

import java.util.*;
import java.util.function.Supplier;

public class MinejagoItems
{
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Minejago.MOD_ID);
    private static final HashMap<ResourceKey<CreativeModeTab>, ArrayList<ResourceLocation>> ITEM_TABS = new HashMap<>();
    public static final String MOD_NEEDED = "error.mod_needed";

    public static final RegistryObject<Item> BAMBOO_STAFF = register("bamboo_staff", () -> new BambooStaffItem(Tiers.WOOD, 2, -1, new Item.Properties()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> BONE_KNIFE = register("bone_knife", () -> new BoneKnifeItem(MinejagoTiers.BONE, 3, -2, new Item.Properties()), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> IRON_SPEAR = register("iron_spear", () -> new SpearItem(Tiers.IRON, 4, -2.8F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> SCYTHE_OF_QUAKES = register("scythe_of_quakes", () -> new ScytheOfQuakesItem(new Item.Properties().defaultDurability(0).rarity(Rarity.EPIC).fireResistant().stacksTo(1)), CreativeModeTabs.COMBAT, CreativeModeTabs.TOOLS_AND_UTILITIES);
    public static final RegistryObject<Item> IRON_SHURIKEN = register("iron_shuriken", () -> new ShurikenItem(Tiers.IRON, 1, 1F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> IRON_KATANA = register("iron_katana", () -> new SwordItem(Tiers.IRON, 1, -1.4F, (new Item.Properties())), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> TEACUP = register("teacup", () -> new TeacupItem(new Item.Properties()), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<Item> FILLED_TEACUP = register("filled_teacup", () -> new FilledTeacupItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FOUR_WEAPONS_BANNER_PATTERN = register("four_weapons_banner_pattern", () -> new BannerPatternItem(MinejagoBannerPatternTags.PATTERN_ITEM_FOUR_WEAPONS, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<Item> IRON_SCYTHE = register("iron_scythe", () -> new IronScytheItem(Tiers.IRON, 8, -3.5F, BlockTags.MINEABLE_WITH_HOE, new Item.Properties()), CreativeModeTabs.TOOLS_AND_UTILITIES, CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> WOODEN_NUNCHUCKS = register("wooden_nunchucks", () -> new NunchucksItem(new Item.Properties().stacksTo(1)), CreativeModeTabs.COMBAT);
    public static final RegistryObject<Item> SCROLL = register("scroll", () -> new ScrollItem(new Item.Properties()), CreativeModeTabs.INGREDIENTS);
    public static final RegistryObject<Item> WRITABLE_SCROLL = register("writable_scroll", () -> new WritableScrollItem(new Item.Properties().stacksTo(1)), CreativeModeTabs.TOOLS_AND_UTILITIES);
    public static final RegistryObject<Item> WRITTEN_SCROLL = register("written_scroll", () -> new WrittenScrollItem(new Item.Properties().stacksTo(1)));

    // POTTERY SHERDS
    public static final RegistryObject<Item> POTTERY_SHERD_ICE_CUBE = registerSherd("pottery_sherd_ice_cube");
    public static final RegistryObject<Item> POTTERY_SHERD_THUNDER = registerSherd("pottery_sherd_thunder");
    public static final RegistryObject<Item> POTTERY_SHERD_PEAKS = registerSherd("pottery_sherd_peaks");
    public static final RegistryObject<Item> POTTERY_SHERD_MASTER = registerSherd("pottery_sherd_master");
    public static final RegistryObject<Item> POTTERY_SHERD_YIN_YANG = registerSherd("pottery_sherd_yin_yang");
    public static final RegistryObject<Item> POTTERY_SHERD_DRAGONS_HEAD = registerSherd("pottery_sherd_dragons_head");
    public static final RegistryObject<Item> POTTERY_SHERD_DRAGONS_TAIL = registerSherd("pottery_sherd_dragons_tail");

    // SMITHING TEMPLATES
    public static final RegistryObject<Item> FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE = registerSmithingTemplate(MinejagoTrimPatterns.FOUR_WEAPONS);

    // SPAWN EGGS
    public static final RegistryObject<Item> WU_SPAWN_EGG = register("wu_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.WU::get, 16645363, 14689295, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> KAI_SPAWN_EGG = register("kai_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.KAI::get, 9507597, 5185296, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> NYA_SPAWN_EGG = register("nya_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.NYA::get, 9507597, 3223857, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> COLE_SPAWN_EGG = register("cole_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.COLE::get, 1647949, 2697513, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> JAY_SPAWN_EGG = register("jay_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.JAY::get, 10057, 4854026, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> ZANE_SPAWN_EGG = register("zane_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.ZANE::get, 2697513, 16769956, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> SKULKIN_SPAWN_EGG = register("skulkin_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.SKULKIN::get, 12698049, 11348013, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> KRUNCHA_SPAWN_EGG = register("kruncha_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.KRUNCHA::get, 12698049, 4802889, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> NUCKAL_SPAWN_EGG = register("nuckal_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.NUCKAL::get, 12698049, 6974058, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);
    public static final RegistryObject<Item> SKULKIN_HORSE_SPAWN_EGG = register("skulkin_horse_spawn_egg", Services.ITEM.makeSpawnEgg(MinejagoEntityTypes.SKULKIN_HORSE::get, 0xfffffd, 0xad282d, new Item.Properties()), CreativeModeTabs.SPAWN_EGGS);

    // POT ITEMS
    public static final RegistryObject<Item> TEAPOT = register("teapot", () -> new BlockItem(MinejagoBlocks.TEAPOT.get(), new Item.Properties().stacksTo(1)), CreativeModeTabs.FUNCTIONAL_BLOCKS);
    public static final RegistryObject<Item> JASPOT = register("jaspot", () -> new BlockItem(MinejagoBlocks.JASPOT.get(), new Item.Properties().stacksTo(1)), CreativeModeTabs.FUNCTIONAL_BLOCKS);
    public static final Map<DyeColor, RegistryObject<Item>> TEAPOTS = teapots();

    // BLOCK ITEMS
    public static final RegistryObject<Item> GOLD_DISC = register("gold_disc", () -> new BlockItem(MinejagoBlocks.GOLD_DISC.get(), new Item.Properties()), CreativeModeTabs.BUILDING_BLOCKS);
    public static final RegistryObject<Item> TOP_POST = register("top_post", () -> new BlockItem(MinejagoBlocks.TOP_POST.get(), new Item.Properties()), CreativeModeTabs.BUILDING_BLOCKS);
    public static final RegistryObject<Item> CHISELED_SCROLL_SHELF = register("chiseled_scroll_shelf", () -> new BlockItem(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), new Item.Properties()), CreativeModeTabs.FUNCTIONAL_BLOCKS);

    @SafeVarargs
    private static RegistryObject<Item> register(String name, Supplier<? extends Item> supplier, ResourceKey<CreativeModeTab>... tabs)
    {
        for (ResourceKey<CreativeModeTab> tab: tabs) {
            ArrayList<ResourceLocation> list = ITEM_TABS.computeIfAbsent(tab, empty -> new ArrayList<>());
            list.add(new ResourceLocation(Minejago.MOD_ID, name));
        }
        return ITEMS.register(name, supplier);
    }

    private static RegistryObject<Item> registerSherd(String name)
    {
        RegistryObject<Item> sherd = register(name, () -> new Item(new Item.Properties()), CreativeModeTabs.INGREDIENTS);

        if (Minejago.Dependencies.SHERDSAPI.isInstalled()) PotterySherdRegistry.register(sherd.getId(), sherd.getId());

        return sherd;
    }

    private static RegistryObject<Item> registerSmithingTemplate(ResourceKey<TrimPattern> key)
    {
        return register(key.location().getPath() + "_armor_trim_smithing_template", () -> (SmithingTemplateItem.createArmorTrimTemplate(key)), CreativeModeTabs.INGREDIENTS);
    }

    public static Map<ResourceKey<CreativeModeTab>, ArrayList<ResourceLocation>> getItemTabs() {
        return ITEM_TABS;
    }

    public static void init() {}

    public static List<ItemStack> getItemsForTab(ResourceKey<CreativeModeTab> tab)
    {
        List<ItemStack> items = new ArrayList<>();

        getItemTabs().forEach((itemTab, itemLikes) -> {
            if (tab == itemTab)
            {
                itemLikes.forEach((itemLike) -> items.add(Objects.requireNonNull(BuiltInRegistries.ITEM.get(itemLike)).getDefaultInstance()));
            }
        });

        if (tab == CreativeModeTabs.FOOD_AND_DRINKS)
        {
            for (Potion potion : BuiltInRegistries.POTION) {
                if (potion != Potions.EMPTY) {
                    items.add(PotionUtils.setPotion(new ItemStack(BuiltInRegistries.ITEM.get(MinejagoItems.FILLED_TEACUP.getId())), potion));
                }
            }
        }

        if (tab == CreativeModeTabs.COMBAT)
        {
            for (RegistryObject<Item> item : MinejagoArmor.ARMOR.getEntries())
            {
                if (!(item.get() instanceof IGeoArmorItem iGeoArmorItem && iGeoArmorItem.isGi()))
                {
                    items.add(item.get().getDefaultInstance());
                }
            }
        }

        return items;
    }

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
