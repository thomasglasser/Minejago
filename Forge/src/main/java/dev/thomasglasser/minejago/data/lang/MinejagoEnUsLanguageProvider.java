package dev.thomasglasser.minejago.data.lang;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.server.commands.PowerCommand;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armor.SkeletalChestplateItem;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

public class MinejagoEnUsLanguageProvider extends LanguageProvider
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
        MinejagoArmor.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
                add(item.get(), "Skeletal Chestplate"));
        MinejagoArmor.ARMOR_SETS.forEach(set ->
                {
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
                    });
                });
        add(MinejagoItems.IRON_KATANA.get(), "Iron Katana");
        add(MinejagoItems.IRON_SCYTHE.get(), "Iron Scythe");
        add(MinejagoItems.WOODEN_NUNCHUCKS.get(), "Wooden Nunchucks");

        add(MinejagoBlocks.TEAPOT.get(), "Teapot");
        add(MinejagoBlocks.JASPOT.get(), "Jaspot");
        MinejagoBlocks.TEAPOTS.forEach((color, pot) ->
                add(pot.get(), WordUtils.capitalize(color.getName().replace('_', ' ')) + " Teapot"));

        add(MinejagoBlocks.GOLD_DISC.get(), "Gold Disc");
        add(MinejagoBlocks.TOP_POST.get(), "Top Post");

        addDesc(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get(), "Four Weapons");

        MinejagoArmor.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
        {
            if (item.get() instanceof SkeletalChestplateItem chestplate)
            {
                String nameForVariant = switch (chestplate.getVariant())
                        {
                            case STRENGTH -> "Red";
                            case SPEED -> "Blue";
                            case BOW -> "White";
                            case KNIFE -> "Black";
                        };

                addDesc(item.get(), nameForVariant);
            }
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

        add(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT.get(), "Four Weapons Left");
        add(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT.get(), "Four Weapons Right");
        add(MinejagoBannerPatterns.EDGE_LINES.get(), "Edge Lines");

        add(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons", "Golden Weapons Map");

        add("container.teapot", "Teapot");

        addTea(MinejagoPotions.REGULAR_TEA.get(), "Regular Tea");
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

        add(MinejagoMobEffects.CURE.get(), "Instant Cure");

        add("effect.minecraft.swiftness", "Swiftness");
        add("effect.minecraft.healing", "Healing");
        add("effect.minecraft.harming", "Harming");
        add("effect.minecraft.leaping", "Leaping");

        add(MinejagoKeyMappings.ACTIVATE_SPINJITZU, "Activate Spinjitzu");

        add(PowerCommand.SUCCESS_SELF, "Set own power to %s");
        add(PowerCommand.CHANGED, "Your power has been updated to %s");
        add(PowerCommand.SUCCESS_OTHER, "Set %s's power to %s");
        add(PowerCommand.QUERY, "Your power is currently set to: %s");
        add(PowerCommand.INVALID, "Power not found in world. Check enabled data packs.");

        addPower(MinejagoPowers.EARTH, "Earth");
        addPower(MinejagoPowers.FIRE, "Fire");
        addPower(MinejagoPowers.LIGHTNING, "Lightning");
        addPower(MinejagoPowers.ICE, "Ice");
        addPower(MinejagoPowers.NONE, "None");

        addCreativeTab(Minejago.modLoc("gi"), "Gi");

        addAdvancement(MinejagoAdventureAdvancementKeys.CATEGORY, MinejagoAdventureAdvancementKeys.KILL_A_SKULKIN, "Redead", "Kill a Skulkin Warrior");
        addAdvancement(MinejagoAdventureAdvancementKeys.CATEGORY, MinejagoAdventureAdvancementKeys.COLLECT_ALL_SKELETAL_CHESTPLATES, "It's Always You Four Colors", "Collect all 4 Skeletal Chestplate variants");
        addAdvancement(MinejagoStoryAdvancementKeys.CATEGORY, MinejagoStoryAdvancementKeys.ROOT, "Minejago", "Long before time had a name...");
        addAdvancement(MinejagoStoryAdvancementKeys.CATEGORY, MinejagoStoryAdvancementKeys.INTERACT_WITH_MAIN_SIX, "Meet N' Greet", "Interact with Wu, Nya, and the Four Ninja");

        add(MinejagoPaintingVariants.A_MORNING_BREW, "A Morning Brew", "waifu_png_pl");
        add(MinejagoPaintingVariants.NEEDS_HAIR_GEL, "Needs Hair Gel", "waifu_png_pl");
        add(MinejagoPaintingVariants.AMBUSHED, "Ambushed", "waifu_png_pl");
        add(MinejagoPaintingVariants.BEFORE_THE_STORM, "Before the Storm", "waifu_png_pl");
        add(MinejagoPaintingVariants.CREATION, "Creation", "waifu_png_pl");
        add(MinejagoPaintingVariants.EARTH, "Earth", "waifu_png_pl");
        add(MinejagoPaintingVariants.FIRE, "Fire", "waifu_png_pl");
        add(MinejagoPaintingVariants.FRUIT_COLORED_NINJA, "Fruit Colored Ninja", "waifu_png_pl");
        add(MinejagoPaintingVariants.ICE, "Ice", "waifu_png_pl");
        add(MinejagoPaintingVariants.LIGHTNING, "Lightning", "waifu_png_pl");
        add(MinejagoPaintingVariants.NOT_FOR_FURNITURE, "Not for Furniture", "waifu_png_pl");
        add(MinejagoPaintingVariants.FOUR_WEAPONS, "Four Weapons", "waifu_png_pl");
        add(MinejagoPaintingVariants.THE_FOURTH_MOUNTAIN, "The Fourth Mountain", "waifu_png_pl");

        addSherd(MinejagoItems.POTTERY_SHERD_ICE_CUBE.get(), "Ice Cube");
        addSherd(MinejagoItems.POTTERY_SHERD_THUNDER.get(), "Thunder");
        addSherd(MinejagoItems.POTTERY_SHERD_PEAKS.get(), "Peaks");
        addSherd(MinejagoItems.POTTERY_SHERD_MASTER.get(), "Master");
        addSherd(MinejagoItems.POTTERY_SHERD_YIN_YANG.get(), "Yin Yang");
        addSherd(MinejagoItems.POTTERY_SHERD_DRAGONS_HEAD.get(), "Dragon's Head");
        addSherd(MinejagoItems.POTTERY_SHERD_DRAGONS_TAIL.get(), "Dragon's Tail");

        addPattern(MinejagoTrimPatterns.FOUR_WEAPONS, "Four Weapons");

        add(MinejagoItems.MOD_NEEDED, "To get the full functionality of this item, please install the %s mod.");

        add(MinejagoPacks.IMMERSION.titleKey(), "Minejago Immersion Pack");
    }

    public void addDesc(Item item, String desc)
    {
        add(item.getDescriptionId() + ".desc", desc);
    }

    public void add(BannerPattern pattern, String name)
    {
        for (DyeColor color: DyeColor.values())
        {
            add("block.minecraft.banner." + Minejago.MOD_ID + "." + pattern.getHashname() + "." + color.getName(), WordUtils.capitalize(color.getName().replace('_', ' ')) + " " + name);
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

    public void addPower(ResourceKey<Power> power, String name)
    {
        add(power.location().toLanguageKey("power"), name);
    }

    public void addAdvancement(String category, String key, String titleString, String descString) {
        String title = "advancement." + Minejago.MOD_ID + "." + category + "." + key + ".title";
        String desc = "advancement." + Minejago.MOD_ID + "." + category + "." + key + ".desc";

        add(title, titleString);
        add(desc, descString);
    }

    public void addCreativeTab(ResourceLocation location, String name)
    {
        add(location.toLanguageKey("item_group"), name);
    }
    
    public void add(RegistryObject<SoundEvent> sound, String name)
    {
        add("subtitles." + sound.getId().getPath(), name);
    }

    public void add(RegistryObject<PaintingVariant> painting, String title, String author)
    {
        add(painting.getId().toLanguageKey("painting") + ".title", title);
        add(painting.getId().toLanguageKey("painting") + ".author", author);
    }

    public void add(EntityType<?> key, String name, Item egg) {
        add(key.getDescriptionId(), name);
        add(egg, name + " Spawn Egg");
    }

    public void addSherd(Item item, String name)
    {
        add(item, name + " Pottery Sherd");
    }

    public void addPattern(ResourceKey<TrimPattern> pattern, String name)
    {
        add(Util.makeDescriptionId("trim_pattern", pattern.location()), name + " Armor Trim");
    }
}
