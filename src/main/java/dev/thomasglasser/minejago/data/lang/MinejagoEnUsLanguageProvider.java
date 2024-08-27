package dev.thomasglasser.minejago.data.lang;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientConfig;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.client.gui.screens.inventory.PowerSelectionScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollEditScreen;
import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollViewScreen;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.plugins.MinejagoWailaPlugin;
import dev.thomasglasser.minejago.plugins.jei.TeapotBrewingRecipeCategory;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.server.commands.PowerCommand;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.character.Wu;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import dev.thomasglasser.minejago.world.entity.spinjitzucourse.AbstractSpinjitzuCourseElement;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.SpinjitzuCourseElementItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import dev.thomasglasser.tommylib.api.data.lang.ExtendedEnUsLanguageProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.apache.commons.lang3.text.WordUtils;

public class MinejagoEnUsLanguageProvider extends ExtendedEnUsLanguageProvider {
    public MinejagoEnUsLanguageProvider(PackOutput output) {
        super(output, Minejago.MOD_ID);
    }

    @Override
    protected void addTranslations() {
        add(MinejagoItems.BONE_KNIFE.get(), "Bone Knife");
        add(MinejagoItems.BAMBOO_STAFF.get(), "Bamboo Staff");
        add(MinejagoItems.SCYTHE_OF_QUAKES.get(), "Scythe of Quakes");
        add(MinejagoItems.TEACUP.get(), "Teacup");
        add(MinejagoItems.FILLED_TEACUP.get(), "Tea");
        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item -> add(item.get(), "Skeletal Chestplate"));
        MinejagoArmors.ARMOR_SETS.forEach(set -> set.getAll().forEach(item -> {
            String nameForSlot = switch (set.getForItem(item.get())) {
                case FEET -> "Boots";
                case LEGS -> "Pants";
                case CHEST -> "Jacket";
                case HEAD -> "Hood";
                default -> null;
            };

            add(item.get(), set.getDisplayName() + " " + nameForSlot);
        }));
        MinejagoArmors.POWER_SETS.forEach(set -> set.getAll().forEach(item -> {
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
        add(MinejagoItems.SCROLL.get(), "Scroll");
        add(MinejagoItems.WRITABLE_SCROLL.get(), "Scroll and Quill");
        add(MinejagoItems.WRITTEN_SCROLL.get(), "Written Scroll");
        add(MinejagoItems.EMPTY_GOLDEN_WEAPONS_MAP.get(), "Empty Golden Weapons Map");

        add(MinejagoBlocks.TEAPOT.get(), "Teapot");
        MinejagoBlocks.TEAPOTS.forEach((color, pot) -> add(pot.get(), WordUtils.capitalize(color.getName().replace('_', ' ')) + " Teapot"));
        add(MinejagoBlocks.JASPOT.get(), "Jaspot");
        add(MinejagoBlocks.FLAME_TEAPOT.get(), "Flame Teapot");

        add(MinejagoBlocks.GOLD_DISC.get(), "Gold Disc");
        add(MinejagoBlocks.TOP_POST.get(), "Top Post");
        add(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), "Chiseled Scroll Shelf");
        add(MinejagoBlocks.EARTH_DRAGON_HEAD.get(), "Earth Dragon Head");
        add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get(), "Suspicious Red Sand");
        add(MinejagoBlocks.DRAGON_BUTTON.get(), "Dragon Button");
        add(MinejagoBlocks.SCROLL_SHELF.get(), "Scroll Shelf");

        add(MinejagoBlocks.ENCHANTED_WOOD_SET, "Enchanted");

        add(MinejagoBlocks.FOCUS_LEAVES_SET, "Focus");

        add(SkulkinRaid.SKULKINS_BANNER_PATTERN_NAME, "Cursed Banner");

        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item -> {
            String nameForVariant = switch (item.get().getVariant()) {
                case STRENGTH -> "Red";
                case SPEED -> "Blue";
                case BOW -> "White";
                case KNIFE -> "Black";
                case BONE -> "Bone";
            };

            addDesc(item.get(), nameForVariant);
        });

        ItemStack uncraftableTea = new ItemStack(MinejagoItems.FILLED_TEACUP.get());
        uncraftableTea.set(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        add(uncraftableTea, "Uncraftable Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.WATER, "Cup of Water");
        add(MinejagoItems.FILLED_TEACUP.get(), MinejagoPotions.MILK, "Cup of Milk");

        for (Holder<Potion> potion : BuiltInRegistries.POTION.holders().filter(ref -> ref.key().location().getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE)).toList()) {
            ResourceLocation location = potion.unwrapKey().orElseThrow().location();
            if (!(potion == Potions.WATER) && !(location.getPath().contains("long") || location.getPath().contains("strong")))
                add(MinejagoItems.FILLED_TEACUP.get(), potion, Items.POTION.getName(PotionContents.createItemStack(Items.POTION, potion)).getString().replace("Potion", "Tea"));
        }

        addSmithingTemplate(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        addSmithingTemplate(MinejagoItems.TERRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        addSmithingTemplate(MinejagoItems.LOTUS_ARMOR_TRIM_SMITHING_TEMPLATE.get());

        add(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), "Bone Knife");
        add(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), "Bamboo Staff");
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

        addSpinjitzuCourseElement(MinejagoEntityTypes.CENTER_SPINJITZU_COURSE_ELEMENT, MinejagoItems.CENTER_SPINJITZU_COURSE_ELEMENT, "Center");
        addSpinjitzuCourseElement(MinejagoEntityTypes.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT, MinejagoItems.BOUNCING_POLE_SPINJITZU_COURSE_ELEMENT, "Bouncing Pole");
        addSpinjitzuCourseElement(MinejagoEntityTypes.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT, MinejagoItems.ROCKING_POLE_SPINJITZU_COURSE_ELEMENT, "Rocking Pole");
        addSpinjitzuCourseElement(MinejagoEntityTypes.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT, MinejagoItems.SPINNING_POLE_SPINJITZU_COURSE_ELEMENT, "Spinning Pole");
        addSpinjitzuCourseElement(MinejagoEntityTypes.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT, MinejagoItems.SPINNING_MACES_SPINJITZU_COURSE_ELEMENT, "Spinning Maces");
        addSpinjitzuCourseElement(MinejagoEntityTypes.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT, MinejagoItems.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT, "Spinning Dummies");
        addSpinjitzuCourseElement(MinejagoEntityTypes.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT, MinejagoItems.SWIRLING_KNIVES_SPINJITZU_COURSE_ELEMENT, "Swirling Knives");
        addSpinjitzuCourseElement(MinejagoEntityTypes.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT, MinejagoItems.SPINNING_AXES_SPINJITZU_COURSE_ELEMENT, "Spinning Axes");

        addPattern(MinejagoBannerPatterns.EDGE_LINES, "Edge Lines");

        addPattern(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT, "Four Weapons Left");
        addPattern(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT, "Four Weapons Right");
        addPatternItem(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN, "Four Weapons");

        addPatternAndItem(MinejagoBannerPatterns.NINJA, MinejagoItems.NINJA_BANNER_PATTERN, "Ninja");

        add(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons", "Golden Weapons Map");

        add("container.teapot", "Teapot");

        addTea(MinejagoPotions.ACACIA_TEA, "Acacia Tea");
        addTea(MinejagoPotions.OAK_TEA, "Oak Tea");
        addTea(MinejagoPotions.CHERRY_TEA, "Cherry Tea");
        addTea(MinejagoPotions.SPRUCE_TEA, "Spruce Tea");
        addTea(MinejagoPotions.MANGROVE_TEA, "Mangrove Tea");
        addTea(MinejagoPotions.JUNGLE_TEA, "Jungle Tea");
        addTea(MinejagoPotions.DARK_OAK_TEA, "Dark Oak Tea");
        addTea(MinejagoPotions.BIRCH_TEA, "Birch Tea");
        addTea(MinejagoPotions.AZALEA_TEA, "Azalea Tea");
        addTea(MinejagoPotions.FLOWERING_AZALEA_TEA, "Flowering Azalea Tea");

        addTea(MinejagoPotions.FOCUS_TEA, "Focus Tea");

        addPotions(MinejagoPotions.MILK, "Milk");

        add(MinejagoSoundEvents.TEAPOT_WHISTLE.get(), "Teapot whistles");
        add(MinejagoSoundEvents.SPINJITZU_START.get(), "Spinjitzu activates");
        add(MinejagoSoundEvents.SPINJITZU_ACTIVE.get(), "Spinjitzu whooshes");
        add(MinejagoSoundEvents.SPINJITZU_STOP.get(), "Spinjitzu fades");
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_FAIL.get(), "Scythe flickers");
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_EXPLOSION.get(), "Ground quakes");
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_CASCADE.get(), "Scythe drags");
        add(MinejagoSoundEvents.SCYTHE_OF_QUAKES_PATH.get(), "Scythe beams");
        add(MinejagoSoundEvents.BONE_KNIFE_THROW.get(), "Knife flies");
        add(MinejagoSoundEvents.BONE_KNIFE_IMPACT.get(), "Knife sticks");
        add(MinejagoSoundEvents.BAMBOO_STAFF_THROW.get(), "Staff tosses");
        add(MinejagoSoundEvents.BAMBOO_STAFF_IMPACT.get(), "Staff lands");
        add(MinejagoSoundEvents.EARTH_DRAGON_AMBIENT.get(), "Earth Dragon breathes");
        add(MinejagoSoundEvents.EARTH_DRAGON_AWAKEN.get(), "Earth Dragon awakens");
        add(MinejagoSoundEvents.EARTH_DRAGON_DEATH.get(), "Earth Dragon dies");
        add(MinejagoSoundEvents.EARTH_DRAGON_FLAP.get(), "Earth Dragon awakens");
        add(MinejagoSoundEvents.EARTH_DRAGON_HURT.get(), "Earth Dragon flaps");
        add(MinejagoSoundEvents.EARTH_DRAGON_ROAR.get(), "Earth Dragon roars");
        add(MinejagoSoundEvents.EARTH_DRAGON_STEP.get(), "Earth Dragon steps");
        add(MinejagoSoundEvents.SKULL_TRUCK_AMBIENT_ACTIVE.get(), "Skull Truck revs");
        add(MinejagoSoundEvents.SKULL_TRUCK_AMBIENT_IDLE.get(), "Skull Truck idles");
        add(MinejagoSoundEvents.SKULL_TRUCK_DEATH.get(), "Skull Truck breaks");
        add(MinejagoSoundEvents.SKULL_TRUCK_HURT.get(), "Skull Truck dents");
        add(MinejagoSoundEvents.SKULL_TRUCK_IGNITION.get(), "Skull Truck starts");
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_AMBIENT_ACTIVE.get(), "Skull Motorbike revs");
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_AMBIENT_IDLE.get(), "Skull Motorbike idles");
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_DEATH.get(), "Skull Motorbike breaks");
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_HURT.get(), "Skull Motorbike dents");
        add(MinejagoSoundEvents.SKULL_MOTORBIKE_IGNITION.get(), "Skull Motorbike starts");
        add(MinejagoSoundEvents.SKULKIN_RAID_HORN.get(), "Skeletal horn blares");

        add(MinejagoMobEffects.CURE.get(), "Instant Cure");
        add(MinejagoMobEffects.HYPERFOCUS.get(), "Hyperfocus");

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

        addCreativeTab(MinejagoCreativeModeTabs.GI, "Gi");
        addCreativeTab(MinejagoCreativeModeTabs.MINEJAGO, "Minejago");

        addPaintingVariant(MinejagoPaintingVariants.A_MORNING_BREW, "A Morning Brew", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.NEEDS_HAIR_GEL, "Needs Hair Gel", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.AMBUSHED, "Ambushed", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.BEFORE_THE_STORM, "Before the Storm", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.CREATION, "Creation", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.EARTH, "Earth", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.FIRE, "Fire", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.FRUIT_COLORED_NINJA, "Fruit Colored Ninja", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.ICE, "Ice", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.LIGHTNING, "Lightning", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.NOT_FOR_FURNITURE, "Not for Furniture", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.FOUR_WEAPONS, "Four Weapons", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.THE_FOURTH_MOUNTAIN, "The Fourth Mountain", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE, "It Takes A Village", "waifu_png_pl");
        addPaintingVariant(MinejagoPaintingVariants.IT_TAKES_A_VILLAGE_WRECKED, "It Takes A Village (Wrecked)", "waifu_png_pl");

        addSherd(MinejagoItems.POTTERY_SHERD_ICE_CUBE.get(), "Ice Cube");
        addSherd(MinejagoItems.POTTERY_SHERD_THUNDER.get(), "Thunder");
        addSherd(MinejagoItems.POTTERY_SHERD_PEAKS.get(), "Peaks");
        addSherd(MinejagoItems.POTTERY_SHERD_MASTER.get(), "Master");
        addSherd(MinejagoItems.POTTERY_SHERD_YIN_YANG.get(), "Yin Yang");
        addSherd(MinejagoItems.POTTERY_SHERD_DRAGONS_HEAD.get(), "Dragon's Head");
        addSherd(MinejagoItems.POTTERY_SHERD_DRAGONS_TAIL.get(), "Dragon's Tail");

        add(MinejagoItems.MOD_NEEDED, "To get the full functionality of this item, please install the %s mod.");

        add(MinejagoPacks.IMMERSION, "Minejago Immersion Pack", "Increases Ninjago immersion with slight cosmetic changes");
        add(MinejagoPacks.POTION_POT, "Minejago Potion Pot Pack", "Makes vanilla potions brewable in the teapot");

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

        add(ScrollViewScreen.TAKE_SCROLL, "Take Scroll");

        add(SkulkinRaid.RAID_NAME_COMPONENT, "Skulkin Raid");

        add(TeapotBrewingRecipeCategory.RECIPE_KEY, "Teapot Brewing");

        add(TeapotBlock.POTION, "%s");
        add(TeapotBlock.POTION_AND_ITEM, "%s with %s");

        addConfigs();
    }

    protected void addTea(Holder<Potion> tea, String name) {
        addPotions(tea, name);
        add(MinejagoItems.FILLED_TEACUP.get(), tea, name);
    }

    protected void addConfigs() {
        addConfigTitle(Minejago.MOD_NAME);

        // Server
        addConfigSection(MinejagoServerConfig.FEATURES, "Feature Toggles", "Optional features that enhance the mod, but may not match the desired experience of some players");
        addConfig(MinejagoServerConfig.INSTANCE.enableTech, "Enable Technology", "Enable the technology of the mod, such as vehicles and computers");
        addConfig(MinejagoServerConfig.INSTANCE.enableSkulkinRaids, "Enable Skulkin Raids", "Enable Skulkin Raids on Four Weapons structures");

        addConfigSection(MinejagoServerConfig.POWERS, "Powers", "Settings for powers (elemental or otherwise)");
        addConfig(MinejagoServerConfig.INSTANCE.allowChoose, "Allow Choosing Power", "Allow players to choose the power given to them by interacting with Master Wu");
        addConfig(MinejagoServerConfig.INSTANCE.allowChange, "Allow Changing Power", "Allow players to get a new power by interacting with Master Wu again");
        addConfig(MinejagoServerConfig.INSTANCE.drainPool, "Drain Power Pool", "Remove a power from the option list once given and reset when all powers have been given");
        addConfig(MinejagoServerConfig.INSTANCE.enableNoPower, "Enable No Power", "Enable players to receive no power from Master Wu");

        addConfigSection(MinejagoServerConfig.SPINJITZU, "Spinjitzu", "Settings for Spinjitzu");
        addConfig(MinejagoServerConfig.INSTANCE.requireCourseCompletion, "Require Course Completion", "Require players to complete the Spinjitzu course to use Spinjitzu");
        addConfig(MinejagoServerConfig.INSTANCE.courseTimeLimit, "Course Time Limit", "The amount of time (in seconds) a player has to complete the Spinjitzu course to unlock Spinjitzu");
        addConfig(MinejagoServerConfig.INSTANCE.courseRadius, "Course Radius", "The radius that the center Spinjitzu element will search for other course elements");

        addConfigSection(MinejagoServerConfig.GOLDEN_WEAPONS, "Golden Weapons", "Settings for the four Golden Weapons");
        addConfig(MinejagoServerConfig.INSTANCE.requireCompatiblePower, "Require Compatible Power", "Require users to have a compatible power");
        addConfig(MinejagoServerConfig.INSTANCE.enableMalfunction, "Enable Malfunction", "Enable an abnormal reaction when handled by someone without a compatible power");

        // Client
        addConfigSection(MinejagoClientConfig.COSMETICS, "Player Cosmetics", "Settings for player cosmetics");
        addConfig(MinejagoClientConfig.INSTANCE.displaySnapshotTesterCosmetic, "Display Snapshot Tester Cosmetic", "Display your preferred Snapshot Tester Cosmetic (if eligible)");
        addConfig(MinejagoClientConfig.INSTANCE.snapshotTesterCosmeticChoice, "Snapshot Tester Cosmetic Choice", "The Snapshot Tester Cosmetic to be displayed (if eligible)");
        addConfig(MinejagoClientConfig.INSTANCE.displayDevTeamCosmetic, "Display Dev Team Cosmetic", "Display the Dev Team cosmetic (if eligible)");
        addConfig(MinejagoClientConfig.INSTANCE.displayLegacyDevTeamCosmetic, "Display Legacy Dev Team Cosmetic", "Display the Legacy Dev Team cosmetic (if eligible)");

        addConfigSection(MinejagoClientConfig.FOCUS_BAR, "Focus Bar", "Settings for focus bar");
        addConfig(MinejagoClientConfig.INSTANCE.xOffset, "Horizontal Offset", "Horizontal pixels off from the normal position");
        addConfig(MinejagoClientConfig.INSTANCE.yOffset, "Vertical Offset", "Vertical pixels off from the normal position");
    }

    protected void addPluginConfig(ResourceLocation location, String name) {
        addPluginConfig(location, Minejago.MOD_NAME, name);
    }

    protected <T extends AbstractSpinjitzuCourseElement<T>> void addSpinjitzuCourseElement(DeferredHolder<EntityType<?>, EntityType<T>> entity, DeferredItem<? extends SpinjitzuCourseElementItem> item, String name) {
        add(entity.get(), name + " Spinjitzu Course Element");
        add(item.get(), name + " Spinjitzu Course Element");
    }
}
