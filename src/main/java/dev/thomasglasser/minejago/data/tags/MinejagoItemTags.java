package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class MinejagoItemTags extends ItemTagsProvider
{
    public static final TagKey<Item> GOLDEN_WEAPONS = ItemTags.create(new ResourceLocation(Minejago.MOD_ID, "golden_weapons"));

    public static final String DYNAMIC_LIGHTS_MODID = "dynamiclights";

    public MinejagoItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future, TagsProvider<Block> blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(output, future, blockTagsProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider)
    {
        tag(Tags.Items.TOOLS)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get())
                .add(MinejagoItems.IRON_SCYTHE.get());
        tag(ItemTags.PIGLIN_LOVED)
                .addTag(GOLDEN_WEAPONS);
        tag(Tags.Items.TOOLS_SWORDS)
                .add(MinejagoItems.BONE_KNIFE.get())
                .add(MinejagoItems.IRON_SPEAR.get())
                .add(MinejagoItems.IRON_KATANA.get());
        tag(Tags.Items.TOOLS_AXES)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        tag(Tags.Items.TOOLS_SHOVELS)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        tag(Tags.Items.TOOLS_PICKAXES)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        tag(GOLDEN_WEAPONS)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        tagDynamicLight("dropped", 10)
                .addTag(GOLDEN_WEAPONS);
        tagDynamicLight("self", 10)
                .addTag(GOLDEN_WEAPONS);
        MinejagoArmor.SETS.forEach(set ->
                {
                    set.getAll().forEach(item ->
                    {
                        tag(Tags.Items.ARMORS).add(item.get());

                        switch (set.getForItem(item)) {
                            case HEAD -> tag(Tags.Items.ARMORS_HELMETS).add(item.get());
                            case CHEST -> tag(Tags.Items.ARMORS_CHESTPLATES).add(item.get());
                            case LEGS -> tag(Tags.Items.ARMORS_LEGGINGS).add(item.get());
                            case FEET -> tag(Tags.Items.ARMORS_BOOTS).add(item.get());
                        }
                    });
                });
        MinejagoArmor.POWERED_SETS.forEach(set ->
                {
                    set.getAll().forEach(item ->
                    {
                        tag(Tags.Items.ARMORS).add(item.get());

                        switch (set.getForItem(item)) {
                            case HEAD -> tag(Tags.Items.ARMORS_HELMETS).add(item.get());
                            case CHEST -> tag(Tags.Items.ARMORS_CHESTPLATES).add(item.get());
                            case LEGS -> tag(Tags.Items.ARMORS_LEGGINGS).add(item.get());
                            case FEET -> tag(Tags.Items.ARMORS_BOOTS).add(item.get());
                        }
                    });
                });
        MinejagoArmor.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
        {
            tag(Tags.Items.ARMORS).add(item.get());
            tag(Tags.Items.ARMORS_CHESTPLATES).add(item.get());
        });
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
