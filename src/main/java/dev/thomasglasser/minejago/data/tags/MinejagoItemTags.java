package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoItemTags extends ItemTagsProvider
{
    public static final TagKey<Item> GOLDEN_WEAPONS = ItemTags.create(new ResourceLocation(Minejago.MOD_ID, "golden_weapons"));

    public static final String DYNAMIC_LIGHTS_MODID = "dynamiclights";

    public MinejagoItemTags(DataGenerator generator, BlockTagsProvider blockTags, @Nullable ExistingFileHelper helper) {
        super(generator, blockTags, Minejago.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        tag(Tags.Items.TOOLS)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        tag(ItemTags.PIGLIN_LOVED)
                .addTag(GOLDEN_WEAPONS);
        tag(Tags.Items.TOOLS_SWORDS)
                .add(MinejagoItems.BONE_KNIFE.get())
                .add(MinejagoItems.IRON_SPEAR.get());
        tag(GOLDEN_WEAPONS)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        tagDynamicLight("dropped", 10)
                .addTag(GOLDEN_WEAPONS);
        tagDynamicLight("self", 10)
                .addTag(GOLDEN_WEAPONS);
        tag(Tags.Items.ARMORS)
                .add(MinejagoItems.SKELETAL_CHESTPLATE.get())
                .add(MinejagoItems.BLACK_GI_HELMET.get())
                .add(MinejagoItems.BLACK_GI_CHESTPLATE.get())
                .add(MinejagoItems.BLACK_GI_LEGGINGS.get())
                .add(MinejagoItems.BLACK_GI_BOOTS.get());
        tag(Tags.Items.ARMORS_CHESTPLATES)
                .add(MinejagoItems.SKELETAL_CHESTPLATE.get())
                .add(MinejagoItems.BLACK_GI_CHESTPLATE.get());
        tag(Tags.Items.ARMORS_BOOTS)
                .add(MinejagoItems.BLACK_GI_BOOTS.get());
        tag(Tags.Items.ARMORS_HELMETS)
                .add(MinejagoItems.BLACK_GI_HELMET.get());
        tag(Tags.Items.ARMORS_LEGGINGS)
                .add(MinejagoItems.BLACK_GI_LEGGINGS.get());
    }

    public TagsProvider.TagAppender<Item> tagDynamicLight(String tag, int level)
    {
        level = Math.min(level, 15);
        return tag(ItemTags.create(new ResourceLocation(DYNAMIC_LIGHTS_MODID, tag + "_" + level)));
    }

    @Override
    public String getName()
    {
        return "Minejago Item Tags";
    }
}
