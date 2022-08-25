package com.thomasglasser.minejago.data.tags;

import com.thomasglasser.minejago.MinejagoMod;
import com.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ModItemTags extends ItemTagsProvider
{

    public static final TagKey<Item> GOLDEN_WEAPON = ItemTags.create(new ResourceLocation(MinejagoMod.MODID, "golden_weapons"));

    public ModItemTags(DataGenerator generator, BlockTagsProvider blockTags, @Nullable ExistingFileHelper helper) {
        super(generator, blockTags, MinejagoMod.MODID, helper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Items.TOOLS)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        tag(ItemTags.PIGLIN_LOVED)
                .addTag(GOLDEN_WEAPON);
        tag(Tags.Items.TOOLS_SWORDS)
                .add(MinejagoItems.BONE_KNIFE.get());
        tag(GOLDEN_WEAPON)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
    }

    @Override
    public String getName()
    {
        return "Minejago Tags";
    }
}
