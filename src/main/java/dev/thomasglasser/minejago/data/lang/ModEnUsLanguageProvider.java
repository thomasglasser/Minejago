package dev.thomasglasser.minejago.data.lang;

import dev.thomasglasser.minejago.MinejagoMod;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModEnUsLanguageProvider extends LanguageProvider
{

    public ModEnUsLanguageProvider(DataGenerator generator)
    {
        super(generator, MinejagoMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(MinejagoItems.BONE_KNIFE.get(), "Bone Knife");
        add(MinejagoItems.BAMBOO_STAFF.get(), "Bamboo Staff");
        add(MinejagoItems.SCYTHE_OF_QUAKES.get(), "Scythe of Quakes");
        add(MinejagoItems.TEACUP.get(), "Teacup");
        add(MinejagoItems.FILLED_TEACUP.get(), "Tea");
        add(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get(), "Banner Pattern");

        addDesc(MinejagoItems.FOUR_WEAPONS_BANNER_PATTERN.get(), "Four Weapons");

        add(MinejagoItems.FILLED_TEACUP.get().getDescriptionId() + ".potion", "Tea of %1$s");

        add(MinejagoItems.FILLED_TEACUP.get(), Potions.EMPTY, "Uncraftable Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.MUNDANE, "Mundane Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.THICK, "Thick Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.AWKWARD, "Awkward Tea");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.TURTLE_MASTER, "Tea of the Turtle Master");
        add(MinejagoItems.FILLED_TEACUP.get(), Potions.WATER, "Cup of Water");

        add(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), "Bone Knife");
        add(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), "Bamboo Staff");

        add(MinejagoBannerPatterns.FOUR_WEAPONS_LEFT.get(), "Four Weapons Left");
        add(MinejagoBannerPatterns.FOUR_WEAPONS_RIGHT.get(), "Four Weapons Right");
        add(MinejagoBannerPatterns.EDGE_LINES.get(), "Edge Lines");

        add(Items.FILLED_MAP.getDescriptionId() + ".golden_weapons", "Golden Weapons Map");
    }

    public void addDesc(Item item, String desc)
    {
        add(item.getDescriptionId() + ".desc", desc);
    }

    public void add(BannerPattern pattern, String name)
    {
        for (DyeColor color: DyeColor.values())
        {
            add("block.minecraft.banner." + MinejagoMod.MODID + "." + pattern.getHashname() + "." + color.getName(), color.getName().substring(0, 1).toUpperCase() + color.getName().substring(1) + " " + name);
        }
    }

    public void add(Item key, Potion potion, String name) {
        add(PotionUtils.setPotion(new ItemStack(key), potion), name);
    }
}
