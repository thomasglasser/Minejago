package dev.thomasglasser.minejago.data.lang;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.commands.arguments.PowerArgument;
import dev.thomasglasser.minejago.server.commands.PowerCommand;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.biome.MinejagoBiomes;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraft.client.KeyMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraft.world.level.block.entity.BannerPattern;
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
        add(MinejagoItems.SKELETAL_CHESTPLATE.get(), "Skeletal Chestplate");
        add(MinejagoItems.BLACK_GI_HELMET.get(), "Black Gi Hood");
        add(MinejagoItems.BLACK_GI_CHESTPLATE.get(), "Black Gi Jacket");
        add(MinejagoItems.BLACK_GI_LEGGINGS.get(), "Black Gi Leggings");
        add(MinejagoItems.BLACK_GI_BOOTS.get(), "Black Gi Boots");
        add(MinejagoItems.IRON_KATANA.get(), "Iron Katana");
        add(MinejagoItems.IRON_SCYTHE.get(), "Iron Scythe");

        add(MinejagoBlocks.TEAPOT.get(), "Teapot");

        addDesc(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get(), "Four Weapons");

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

        add(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT.get(), "Four Weapons Left");
        add(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT.get(), "Four Weapons Right");
        add(MinejagoBannerPatterns.EDGE_LINES.get(), "Edge Lines");

        add(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons", "Golden Weapons Map");

        add(MinejagoBiomes.HIGH_MOUNTAINS, "Mountains of Impossible Height");

        add("container.teapot", "Teapot");

        addTea(MinejagoPotions.REGULAR_TEA.get(), "Regular Tea");
        add(MinejagoItems.FILLED_TEACUP.get().getDescriptionId() + ".milk", "Cup of Milk");
        addPotions(MinejagoPotions.MILK.get(), "Milk");

        add(MinejagoSoundEvents.TEAPOT_WHISTLE.get().getLocation().toLanguageKey() + ".subtitle", "*teapot whistles*");

        add(MinejagoMobEffects.CURE.get(), "Instant Cure");

        add("effect.minecraft.swiftness", "Swiftness");
        add("effect.minecraft.healing", "Healing");
        add("effect.minecraft.harming", "Harming");
        add("effect.minecraft.leaping", "Leaping");

        add(MinejagoKeyMappings.ACTIVATE_SPINJITZU, "Activate Spinjitzu");

        add(PowerCommand.SUCCESS_SELF, "Set own power to %s");
        add(PowerCommand.CHANGED, "Your power has been updated to %s");
        add(PowerCommand.SUCCESS_OTHER, "Set %s's game mode to %s");
        add(PowerCommand.QUERY, "Your power is currently set to: %s");

        add(PowerArgument.NOT_FOUND, "Unknown Power");

        add(MinejagoPowers.EARTH.get(), "Earth");
        add(MinejagoPowers.FIRE.get(), "Fire");
        add(MinejagoPowers.LIGHTNING.get(), "Lightning");
        add(MinejagoPowers.ICE.get(), "Ice");
        add(MinejagoPowers.NONE.get(), "None");
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

    public void add(ResourceKey<Biome> biome, String name)
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

    public void add(Power power, String name)
    {
        add(power.getDescriptionId(), name);
    }
}
