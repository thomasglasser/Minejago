package com.thomasglasser.minejago.data.lang;

import com.thomasglasser.minejago.MinejagoMod;
import com.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import com.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.data.LanguageProvider;

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

        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.EMPTY), "Uncraftable Tea");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.WATER), "Cup of Water");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.MUNDANE), "Mundane Tea");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.THICK), "Thick Tea");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.AWKWARD), "Awkward Tea");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.NIGHT_VISION), "Tea of Night Vision");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.INVISIBILITY), "Tea of Invisibility");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.LEAPING), "Tea of Leaping");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.FIRE_RESISTANCE), "Tea of Fire Resistance");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.SWIFTNESS), "Tea of Swiftness");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.SLOWNESS), "Tea of Slowness");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.TURTLE_MASTER), "Tea of the Turtle Master");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.WATER_BREATHING), "Tea of Water Breathing");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.HEALING), "Tea of Healing");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.HARMING), "Tea of Harming");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.POISON), "Tea of Poison");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.REGENERATION), "Tea of Regeneration");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.STRENGTH), "Tea of Strength");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.WEAKNESS), "Tea of Weakness");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.LUCK), "Tea of Luck");
        add(PotionUtils.setPotion(new ItemStack(MinejagoItems.FILLED_TEACUP.get()), Potions.SLOW_FALLING), "Tea of Slow Falling");

        add(MinejagoEntityTypes.THROWN_BONE_KNIFE.get(), "Bone Knife");
        add(MinejagoEntityTypes.THROWN_BAMBOO_STAFF.get(), "Bamboo Staff");
    }
}
