package dev.thomasglasser.minejago.data.lang;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoAdventureAdvancementKeys;
import dev.thomasglasser.minejago.data.advancements.packs.MinejagoStoryAdvancementKeys;
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
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.biome.MinejagoBiomes;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraft.client.KeyMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

public class MinejagoEnUsLanguage extends LanguageProvider
{

    public MinejagoEnUsLanguage(PackOutput output)
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

        add(MinejagoItems.WU_SPAWN_EGG.get(), "Wu Spawn Egg");
        add(MinejagoItems.KAI_SPAWN_EGG.get(), "Kai Spawn Egg");
        add(MinejagoItems.NYA_SPAWN_EGG.get(), "Nya Spawn Egg");
        add(MinejagoItems.COLE_SPAWN_EGG.get(), "Cole Spawn Egg");
        add(MinejagoItems.JAY_SPAWN_EGG.get(), "Jay Spawn Egg");
        add(MinejagoItems.ZANE_SPAWN_EGG.get(), "Zane Spawn Egg");
        add(MinejagoItems.UNDERWORLD_SKELETON_SPAWN_EGG.get(), "Underworld Skeleton Spawn Egg");
        add(MinejagoItems.KRUNCHA_SPAWN_EGG.get(), "Kruncha Spawn Egg");
        add(MinejagoItems.NUCKAL_SPAWN_EGG.get(), "Nuckal Spawn Egg");

        add(MinejagoBlocks.TEAPOT.get(), "Teapot");

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
        add(MinejagoEntityTypes.WU.get(), "Wu");
        add(MinejagoEntityTypes.KAI.get(), "Kai");
        add(MinejagoEntityTypes.NYA.get(), "Nya");
        add(MinejagoEntityTypes.JAY.get(), "Jay");
        add(MinejagoEntityTypes.COLE.get(), "Cole");
        add(MinejagoEntityTypes.ZANE.get(), "Zane");
        add(MinejagoEntityTypes.UNDERWORLD_SKELETON.get(), "Underworld Skeleton");
        add(MinejagoEntityTypes.KRUNCHA.get(), "Kruncha");
        add(MinejagoEntityTypes.NUCKAL.get(), "Nuckal");

        add(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT.get(), "Four Weapons Left");
        add(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT.get(), "Four Weapons Right");
        add(MinejagoBannerPatterns.EDGE_LINES.get(), "Edge Lines");

        add(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons", "Golden Weapons Map");

        addBiome(MinejagoBiomes.HIGH_MOUNTAINS, "Mountains of Impossible Height");

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
        add(MinejagoPaintingVariants.FOUR_WEAPONS, "Four Weapons", "Saikaro");
        add(MinejagoPaintingVariants.THE_FOURTH_MOUNTAIN, "The Fourth Mountain", "waifu_png_pl");
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

        if (!potion.getEffects().isEmpty())
        {
            add(Items.TIPPED_ARROW, potion, "Arrow of " + name);
        }
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
}
