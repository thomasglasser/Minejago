package dev.thomasglasser.minejago.data.lang;

import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.client.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.client.gui.screens.inventory.PowerSelectionScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollEditScreen;
import dev.thomasglasser.minejago.client.rei.display.category.TeapotBrewingCategory;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.server.commands.PowerCommand;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.LeavesSet;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.WoodSet;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public class MinejagoEnUsLanguageProvider extends LanguageProvider implements ModonomiconLanguageProvider
{

    public MinejagoEnUsLanguageProvider(PackOutput output)
    {
        super(output, Minejago.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(MinejagoItems.BONE_KNIFE.get(), "Bone Knife");
        add(MinejagoItems.BAMBOO_STAFF.get(), "Bamboo Staff");
        add(MinejagoItems.SCYTHE_OF_QUAKES.get(), "Scythe of Quakes");
        add(MinejagoItems.TEACUP.get(), "Teacup");
        add(MinejagoItems.FILLED_TEACUP.get(), "Tea");
        add(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get(), "Banner Pattern");
        add(MinejagoItems.IRON_SPEAR.get(), "Iron Spear");
        add(MinejagoItems.IRON_SHURIKEN.get(), "Iron Shuriken");
        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
                add(item.get(), "Skeletal Chestplate"));
        MinejagoArmors.ARMOR_SETS.forEach(set ->
                set.getAll().forEach(item ->
                {
                    String nameForSlot = switch (set.getForItem(item.get())) {
                        case FEET -> "Boots";
                        case LEGS -> "Pants";
                        case CHEST -> "Jacket";
                        case HEAD -> "Hood";
                        default -> null;
                    };

                    add(item.get(), set.getDisplayName() + " " + nameForSlot);
                }));
        MinejagoArmors.POWER_SETS.forEach(set ->
                set.getAll().forEach(item ->
                {
                    String nameForSlot = switch (set.getForItem(item.get())) {
                        case FEET -> "Boots";
                        case LEGS -> "Pants";
                        case CHEST -> "Jacket";
                        case HEAD -> "Hood";
                        default -> null;
                    };

                    add(item.get(), set.getDisplayName() + " " + nameForSlot);
                }));
        add(MinejagoArmors.SAMUKAIS_CHESTPLATE.get(), "Samukai's Chestplate");
        add(MinejagoItems.IRON_KATANA.get(), "Iron Katana");
        add(MinejagoItems.IRON_SCYTHE.get(), "Iron Scythe");
        add(MinejagoItems.WOODEN_NUNCHUCKS.get(), "Wooden Nunchucks");
        add(MinejagoItems.SCROLL.get(), "Scroll");
        add(MinejagoItems.WRITABLE_SCROLL.get(), "Scroll and Quill");
        add(MinejagoItems.WRITTEN_SCROLL.get(), "Written Scroll");
        add(MinejagoItems.EMPTY_GOLDEN_WEAPONS_MAP.get(), "Empty Golden Weapons Map");

        add(MinejagoBlocks.TEAPOT.get(), "Teapot");
        MinejagoBlocks.TEAPOTS.forEach((color, pot) ->
                add(pot.get(), toCapitalCase(color.getName().replace('_', ' ')) + " Teapot"));
        add(MinejagoBlocks.JASPOT.get(), "Jaspot");
        add(MinejagoBlocks.FLAME_TEAPOT.get(), "Flame Teapot");

        add(MinejagoBlocks.GOLD_DISC.get(), "Gold Disc");
        add(MinejagoBlocks.TOP_POST.get(), "Top Post");
        add(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), "Chiseled Scroll Shelf");
        add(MinejagoBlocks.EARTH_DRAGON_HEAD.get(), "Earth Dragon Head");
        add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get(), "Suspicious Red Sand");

        add(MinejagoBlocks.ENCHANTED_WOOD_SET, "Enchanted");

        add(MinejagoBlocks.FOCUS_LEAVES_SET, "Focus");

        add(SkulkinRaid.SKULKINS_BANNER_PATTERN_NAME, "Cursed Banner");

        addDesc(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get(), "Four Weapons");

        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
        {
            String nameForVariant = switch (item.get().getVariant())
            {
                case STRENGTH -> "Red";
                case SPEED -> "Blue";
                case BOW -> "White";
                case KNIFE -> "Black";
                case BONE -> "Bone";
            };

            addDesc(item.get(), nameForVariant);
        });

        add(MinejagoItems.FILLED_TEACUP.get().getDescriptionId() + ".potion", "Tea of %s");

        add(MinejagoItems.FILLED_TEACUP.get(), Potions.EMPTY, "Uncraftable Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.MUNDANE, "Mundane Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.THICK, "Thick Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.AWKWARD, "Awkward Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.TURTLE_MASTER, "Tea of the Turtle Master");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.WATER, "Cup of Water");

        add(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), "Bone Knife");
        add(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), "Bamboo Staff");
        add(MinejagoEntityTypes.THROWN_IRON_SHURIKEN.get(), "Iron Shuriken");
        add(MinejagoEntityTypes.THROWN_IRON_SPEAR.get(), "Iron Spear");
        add(MinejagoEntityTypes.EARTH_BLAST.get(), "Earth Blast");
        add(MinejagoEntityTypes.WU.get(), "Wu", MinejagoItems.WU_SPAWN_EGG.get());
        add(MinejagoEntityTypes.KAI.get(), "Kai", MinejagoItems.KAI_SPAWN_EGG.get());
        add(MinejagoEntityTypes.NYA.get(), "Nya", MinejagoItems.NYA_SPAWN_EGG.get());
        add(MinejagoEntityTypes.JAY.get(), "Jay", MinejagoItems.JAY_SPAWN_EGG.get());
        add(MinejagoEntityTypes.COLE.get(), "Cole", MinejagoItems.COLE_SPAWN_EGG.get());
        add(MinejagoEntityTypes.ZANE.get(), "Zane", MinejagoItems.ZANE_SPAWN_EGG.get());
        add(MinejagoEntityTypes.SKULKIN.get(), "Skulkin", MinejagoItems.SKULKIN_SPAWN_EGG.get());
        add(MinejagoEntityTypes.KRUNCHA.get(), "Kruncha", MinejagoItems.KRUNCHA_SPAWN_EGG.get());
        add(MinejagoEntityTypes.NUCKAL.get(), "Nuckal", MinejagoItems.NUCKAL_SPAWN_EGG.get());
        add(MinejagoEntityTypes.SKULKIN_HORSE.get(), "Skulkin Horse", MinejagoItems.SKULKIN_HORSE_SPAWN_EGG.get());
        add(MinejagoEntityTypes.EARTH_DRAGON.get(), "Earth Dragon", MinejagoItems.EARTH_DRAGON_SPAWN_EGG.get());
        add(MinejagoEntityTypes.SAMUKAI.get(), "Samukai", MinejagoItems.SAMUKAI_SPAWN_EGG.get());
        add(MinejagoEntityTypes.SKULL_TRUCK.get(), "Skull Truck", MinejagoItems.SKULL_TRUCK_SPAWN_EGG.get());
        add(MinejagoEntityTypes.SKULL_MOTORBIKE.get(), "Skull Motorbike", MinejagoItems.SKULL_MOTORBIKE_SPAWN_EGG.get());

        add(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT.get(), "Four Weapons Left");
        add(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT.get(), "Four Weapons Right");
        add(MinejagoBannerPatterns.EDGE_LINES.get(), "Edge Lines");

        add(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons", "Golden Weapons Map");

        add("container.teapot", "Teapot");

        addTea(MinejagoPotions.ACACIA_TEA.get(), "Acacia Tea");
        addTea(MinejagoPotions.OAK_TEA.get(), "Oak Tea");
        addTea(MinejagoPotions.CHERRY_TEA.get(), "Cherry Tea");
        addTea(MinejagoPotions.SPRUCE_TEA.get(), "Spruce Tea");
        addTea(MinejagoPotions.MANGROVE_TEA.get(), "Mangrove Tea");
        addTea(MinejagoPotions.JUNGLE_TEA.get(), "Jungle Tea");
        addTea(MinejagoPotions.DARK_OAK_TEA.get(), "Dark Oak Tea");
        addTea(MinejagoPotions.BIRCH_TEA.get(), "Birch Tea");

        addTea(MinejagoPotions.FOCUS_TEA.get(), "Focus Tea");

        add(MinejagoItems.FILLED_TEACUP.get().getDescriptionId() + ".milk", "Cup of Milk");
        addPotions(MinejagoPotions.MILK.get(), "Milk");

        add(MinejagoSoundEvents.TEAPOT_WHISTLE, "Teapot whistles");
        add(MinejagoSoundEvents.SPINJITZU_START, "Spinjitzu activates");
        add(MinejagoSoundEvents.SPINJITZU_ACTIVE, "Spinjitzu whooshes");
        add(MinejagoSoundEvents.SPINJITZU_STOP, "Spinjitzu fades*");
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL, "Scythe flickers");
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION, "Ground quakes");
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE, "Scythe drags");
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH, "Scythe beams");
        add(MinejagoSoundEvents.BONE_KNIFE_THROW, "Knife flies");
        add(MinejagoSoundEvents.BONE_KNIFE_IMPACT, "Knife sticks");
        add(MinejagoSoundEvents.BAMBOO_STAFF_THROW, "Staff tosses");
        add(MinejagoSoundEvents.BAMBOO_STAFF_IMPACT, "Staff lands");
        add(MinejagoSoundEvents.SHURIKEN_THROW, "Shuriken flies");
        add(MinejagoSoundEvents.SHURIKEN_IMPACT, "Shuriken sticks");
        add(MinejagoSoundEvents.SPEAR_THROW, "Spear tosses");
        add(MinejagoSoundEvents.SPEAR_IMPACT, "Spear lands");
        add(MinejagoSoundEvents.EARTH_DRAGON_AMBIENT, "Earth Dragon breathes");
        add(MinejagoSoundEvents.EARTH_DRAGON_AWAKEN, "Earth Dragon awakens");
        add(MinejagoSoundEvents.EARTH_DRAGON_DEATH, "Earth Dragon dies");
        add(MinejagoSoundEvents.EARTH_DRAGON_FLAP, "Earth Dragon awakens");
        add(MinejagoSoundEvents.EARTH_DRAGON_HURT, "Earth Dragon flaps");
        add(MinejagoSoundEvents.EARTH_DRAGON_ROAR, "Earth Dragon roars");
        add(MinejagoSoundEvents.EARTH_DRAGON_STEP, "Earth Dragon steps");

        add(MinejagoMobEffects.ACACIA_TEA.get(), "Acacia Tea");
        add(MinejagoMobEffects.OAK_TEA.get(), "Oak Tea");
        add(MinejagoMobEffects.CHERRY_TEA.get(), "Cherry Tea");
        add(MinejagoMobEffects.SPRUCE_TEA.get(), "Spruce Tea");
        add(MinejagoMobEffects.MANGROVE_TEA.get(), "Mangrove Tea");
        add(MinejagoMobEffects.JUNGLE_TEA.get(), "Jungle Tea");
        add(MinejagoMobEffects.DARK_OAK_TEA.get(), "Dark Oak Tea");
        add(MinejagoMobEffects.BIRCH_TEA.get(), "Birch Tea");
        add(MinejagoMobEffects.CURE.get(), "Instant Cure");
        add(MinejagoMobEffects.SKULKINS_CURSE.get(), "Skulkin's Curse");
        add(MinejagoMobEffects.HYPERFOCUS.get(), "Hyperfocus");

        add("effect.minecraft.swiftness", "Swiftness");
        add("effect.minecraft.healing", "Healing");
        add("effect.minecraft.harming", "Harming");
        add("effect.minecraft.leaping", "Leaping");

        add(MinejagoKeyMappings.ACTIVATE_SPINJITZU, "Activate Spinjitzu");
        add(MinejagoKeyMappings.MEDITATE, "Meditate");
        add(MinejagoKeyMappings.ASCEND, "Ascend");
        add(MinejagoKeyMappings.DESCEND, "Descend");

        add(PowerCommand.SUCCESS_SELF, "Set own power to %s");
        add(PowerCommand.CHANGED, "Your power has been updated to %s");
        add(PowerCommand.SUCCESS_OTHER, "Set %s's power to %s");
        add(PowerCommand.SUCCESS_CLEARED_SELF, "Reset own power to %s and enabled power discovery");
        add(PowerCommand.CLEARED, "Your power has been reset to %s and power discovery has been enabled");
        add(PowerCommand.SUCCESS_CLEARED_OTHER, "Reset %s's power to %s and enabled power discovery");
        add(PowerCommand.QUERY, "Your power is currently set to: %s");
        add(PowerCommand.INVALID, "Power not found in world. Check enabled data packs.");
        add(PowerCommand.NOT_LIVING_ENTITY, "Target %s (%s) is not a LivingEntity");

        addCreativeTab(MinejagoCreativeModeTabs.GI.get(), "Gi");
        addCreativeTab(MinejagoCreativeModeTabs.MINEJAGO.get(), "Minejago");

        add(MinejagoPaintingVariants.A_MORNING_BREW.get(), "A Morning Brew", "waifu_png_pl");
        add(MinejagoPaintingVariants.NEEDS_HAIR_GEL.get(), "Needs Hair Gel", "waifu_png_pl");
        add(MinejagoPaintingVariants.AMBUSHED.get(), "Ambushed", "waifu_png_pl");
        add(MinejagoPaintingVariants.BEFORE_THE_STORM.get(), "Before the Storm", "waifu_png_pl");
        add(MinejagoPaintingVariants.CREATION.get(), "Creation", "waifu_png_pl");
        add(MinejagoPaintingVariants.EARTH.get(), "Earth", "waifu_png_pl");
        add(MinejagoPaintingVariants.FIRE.get(), "Fire", "waifu_png_pl");
        add(MinejagoPaintingVariants.FRUIT_COLORED_NINJA.get(), "Fruit Colored Ninja", "waifu_png_pl");
        add(MinejagoPaintingVariants.ICE.get(), "Ice", "waifu_png_pl");
        add(MinejagoPaintingVariants.LIGHTNING.get(), "Lightning", "waifu_png_pl");
        add(MinejagoPaintingVariants.NOT_FOR_FURNITURE.get(), "Not for Furniture", "waifu_png_pl");
        add(MinejagoPaintingVariants.FOUR_WEAPONS.get(), "Four Weapons", "waifu_png_pl");
        add(MinejagoPaintingVariants.THE_FOURTH_MOUNTAIN.get(), "The Fourth Mountain", "waifu_png_pl");
        add(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE.get(), "It Takes A Village", "waifu_png_pl");
        add(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE_WRECKED.get(), "It Takes A Village (Wrecked)", "waifu_png_pl");

        addSherd(MinejagoItems.POTTERY_SHERD_ICE_CUBE.get(), "Ice Cube");
        addSherd(MinejagoItems.POTTERY_SHERD_THUNDER.get(), "Thunder");
        addSherd(MinejagoItems.POTTERY_SHERD_PEAKS.get(), "Peaks");
        addSherd(MinejagoItems.POTTERY_SHERD_MASTER.get(), "Master");
        addSherd(MinejagoItems.POTTERY_SHERD_YIN_YANG.get(), "Yin Yang");
        addSherd(MinejagoItems.POTTERY_SHERD_DRAGONS_HEAD.get(), "Dragon's Head");
        addSherd(MinejagoItems.POTTERY_SHERD_DRAGONS_TAIL.get(), "Dragon's Tail");

        add(MinejagoItems.MOD_NEEDED, "To get the full functionality of this item, please install the %s mod.");

        add(MinejagoPacks.IMMERSION.titleKey(), "Minejago Immersion Pack");
        add(MinejagoPacks.POTION_POT.titleKey(), "Minejago Potion Pot Pack");

        add(Wu.NO_POWER_GIVEN_KEY, "You feel no new power rise from within. You are not an elemental master...");
        add(Wu.POWER_GIVEN_KEY, "<%s> %s, Master of %s. %s");

        add("gui.choose", "Choose");
        add(PowerSelectionScreen.TITLE, "Select Power");

        add("block.minejago.teapot.waila.potion", "Potion: %s");
        add("block.minejago.teapot.waila.item", "Item: %s");
        add("block.minejago.teapot.waila.cups", "Cups: %s");
        add("block.minejago.teapot.waila.time", "Brew Time: %s");
        add("block.minejago.teapot.waila.temp", "Temperature: %s");
        add("block.minejago.teapot.waila.empty", "Empty");

        add("entity.minejago.living.waila.power", "Power: %s");
        add("entity.minejago.dragon.waila.bond", "Bond: %s");
        add("entity.minejago.painting.waila.map", "Has Golden Weapons Map");

        addPluginConfig(MinejagoWailaPlugin.LIVING_ENTITY, "Living Entity");
        addPluginConfig(MinejagoWailaPlugin.DRAGON, "Dragon");
        addPluginConfig(MinejagoWailaPlugin.PAINTING, "Painting");
        addPluginConfig(MinejagoWailaPlugin.TEAPOT_BLOCK, "Teapot");

        add(ScrollEditScreen.EDIT_TITLE_LABEL, "Enter Scroll Title:");
        add(ScrollEditScreen.FINALIZE_WARNING_LABEL, "Note! When you sign the scroll, it will no longer be editable.");

        add("lectern.take_scroll", "Take Scroll");

        add(TeapotBrewingCategory.CATEGORY_KEY, "Teapot Brewing");

        add(SkulkinRaid.RAID_NAME_COMPONENT, "Skulkin Raid");
    }

    public void addDesc(Item item, String desc)
    {
        add(item.getDescriptionId() + ".desc", desc);
    }

    public void add(BannerPattern pattern, String name)
    {
        for (DyeColor color: DyeColor.values())
        {
            add("block.minecraft.banner." + Minejago.MOD_ID + "." + pattern.getHashname() + "." + color.getName(), toCapitalCase(color.getName().replace('_', ' ')) + " " + name);
        }
    }

    public void add(Item key, Potion potion, String name) {
        add(PotionUtils.setPotion(new ItemStack(key), potion), name);
    }

    public void addBiome(ResourceKey<Biome> biome, String name)
    {
        add("biome." + biome.location().getNamespace() + "." + biome.location().getPath(), name);
    }

    public void addTea(Potion tea, String name)
    {
        add(MinejagoItems.FILLED_TEACUP.get().getDescriptionId() + tea.getName("."), name);
        addPotions(tea, name);
    }

    public void addPotions(Potion potion, String name)
    {
        add(Items.POTION, potion, "Bottle of " + name);
        add(Items.SPLASH_POTION, potion, "Splash Bottle of " + name);
        add(Items.LINGERING_POTION, potion, "Lingering Bottle of " + name);
        add(Items.TIPPED_ARROW, potion, "Arrow of " + name);
    }

    public void add(KeyMapping key, String name)
    {
        add(key.getName(), name);
    }

    public void addAdvancement(String category, String key, String titleString, String descString) {
        String title = "advancement." + Minejago.MOD_ID + "." + category + "." + key + ".title";
        String desc = "advancement." + Minejago.MOD_ID + "." + category + "." + key + ".desc";

        add(title, titleString);
        add(desc, descString);
    }

    public void addCreativeTab(CreativeModeTab tab, String name)
    {
        add(BuiltInRegistries.CREATIVE_MODE_TAB.getKey(tab).toLanguageKey("item_group"), name);
    }

    public void add(Supplier<SoundEvent> sound, String name)
    {
        add("subtitles." + sound.get().getLocation().getPath(), name);
    }

    public void add(PaintingVariant painting, String title, String author)
    {
        add(BuiltInRegistries.PAINTING_VARIANT.getKey(painting).toLanguageKey("painting") + ".title", title);
        add(BuiltInRegistries.PAINTING_VARIANT.getKey(painting).toLanguageKey("painting") + ".author", author);
    }

    public void add(EntityType<?> key, String name, Item egg) {
        add(key.getDescriptionId(), name);
        add(egg, name + " Spawn Egg");
    }

    public void addSherd(Item item, String name)
    {
        add(item, name + " Pottery Sherd");
    }

    public void addPluginConfig(ResourceLocation location, String name)
    {
        add("config.jade.plugin_" + location.toLanguageKey(), "Minejago " + name + " Config");
    }

    public void add(Component component, String name)
    {
        add(((TranslatableContents)component.getContents()).getKey(), name);
    }

    public void add(WoodSet set, String name)
    {
        add(set.planks().get(), name + " Planks");
        add(set.log().get(), name + " Log");
        add(set.strippedLog().get(), "Stripped " + name + " Log");
        add(set.wood().get(), name + " Wood");
        add(set.strippedWood().get(), "Stripped " + name + " Wood");
    }

    public void add(LeavesSet set, String name)
    {
        add(set.sapling().get(), name + " Sapling");
        add(set.leaves().get(), name + " Leaves");
        add(set.pottedSapling().get(), "Potted " + name + " Sapling");
    }

    public static String toCapitalCase(String s, char... delimiters)
    {
        final int delimLen = delimiters == null ? -1 : delimiters.length;
        if (s.isEmpty() || delimLen == 0) {
            return s;
        }
        final char[] buffer = s.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; i++) {
            final char ch = buffer[i];
            if (delimiters == null ? Character.isWhitespace(ch) : Arrays.asList(delimiters).contains(ch)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer[i] = Character.toTitleCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(buffer);
    }

    @Override
    public String locale()
    {
	    try
	    {
		    Field locale = this.getClass().getDeclaredField("locale");
            locale.setAccessible(true);
            return (String) locale.get(this);
	    } catch (Exception e)
	    {
		    throw new RuntimeException(e);
	    }
    }

    @Override
    public Map<String, String> data()
    {
        try
        {
            Field data = this.getClass().getDeclaredField("data");
            data.setAccessible(true);
            return (Map<String, String>) data.get(this);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
