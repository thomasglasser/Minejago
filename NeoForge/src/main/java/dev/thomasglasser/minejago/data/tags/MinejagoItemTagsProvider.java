package dev.thomasglasser.minejago.data.tags;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.block.LeavesSet;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class MinejagoItemTagsProvider extends ItemTagsProvider
{
    public static final Pair<ResourceLocation, ResourceLocation> SWORDS = Pair.of(forgeLoc("tools/swords"), cLoc("swords"));
    public static final Pair<ResourceLocation, ResourceLocation> HELMETS = Pair.of(forgeLoc("armors/helmets"), cLoc("helmets"));
    public static final Pair<ResourceLocation, ResourceLocation> CHESTPLATES = Pair.of(forgeLoc("armors/chestplates"), cLoc("chestplates"));
    public static final Pair<ResourceLocation, ResourceLocation> LEGGINGS = Pair.of(forgeLoc("armors/leggings"), cLoc("leggings"));
    public static final Pair<ResourceLocation, ResourceLocation> BOOTS = Pair.of(forgeLoc("armors/boots"), cLoc("boots"));
    public static final Pair<ResourceLocation, ResourceLocation> WOODEN_RODS = Pair.of(forgeLoc("rods/wooden"), cLoc("wooden_rods"));

    public MinejagoItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(output, future, blockTagsProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider)
    {
        tag(ItemTags.PIGLIN_LOVED)
                .addTag(MinejagoItemTags.GOLDEN_WEAPONS);
        tagPair(SWORDS,
                MinejagoItems.BONE_KNIFE.get(),
                MinejagoItems.IRON_KATANA.get());
        tag(MinejagoItemTags.GOLDEN_WEAPONS)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        MinejagoArmors.ARMOR_SETS.forEach(set ->
                set.getAll().forEach(item ->
                {
                    switch (set.getForItem(item.get())) {
                        case HEAD -> tagPair(HELMETS, item.get());
                        case CHEST -> tagPair(CHESTPLATES, item.get());
                        case LEGS -> tagPair(LEGGINGS, item.get());
                        case FEET -> tagPair(BOOTS, item.get());
                    }
                }));
        MinejagoArmors.POWER_SETS.forEach(set ->
                set.getAll().forEach(item ->
                {
                    switch (set.getForItem(item.get())) {
                        case HEAD -> tagPair(HELMETS, item.get());
                        case CHEST -> tagPair(CHESTPLATES, item.get());
                        case LEGS -> tagPair(LEGGINGS, item.get());
                        case FEET -> tagPair(BOOTS, item.get());
                    }
                }));
        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
                tagPair(CHESTPLATES, item.get()));
        tagPair(CHESTPLATES, MinejagoArmors.SAMUKAIS_CHESTPLATE.get());

        tag(MinejagoItemTags.WOODEN_RODS)
                .add(Items.STICK)
                .addOptionalTag(WOODEN_RODS.getFirst())
                .addOptionalTag(WOODEN_RODS.getSecond())
                .addOptionalTag(cLoc("wood_sticks"));
        tag(MinejagoItemTags.IRON_INGOTS)
                .add(Items.IRON_INGOT)
                .addOptionalTag(forgeLoc("ingots/iron"))
                .addOptionalTag(cLoc("iron_ingots"));
        MinejagoItemTags.DYES_MAP.forEach((dyeColor, itemTagKey) ->
                tag(itemTagKey)
                        .add(DyeItem.byColor(dyeColor))
                        .addOptionalTag(forgeLoc("dyes/" + dyeColor.getName()))
                        .addOptionalTag(cLoc(dyeColor.getName() + "_dyes")));

        copy(MinejagoBlockTags.TEAPOTS, MinejagoItemTags.TEAPOTS);

        tag(MinejagoItemTags.LECTERN_SCROLLS)
                .add(MinejagoItems.WRITABLE_SCROLL.get())
                .add(MinejagoItems.WRITTEN_SCROLL.get());

        tag(ItemTags.LECTERN_BOOKS)
                .addTag(MinejagoItemTags.LECTERN_SCROLLS);

        tag(MinejagoItemTags.SCROLL_SHELF_SCROLLS)
                .addTag(MinejagoItemTags.LECTERN_SCROLLS)
                .add(MinejagoItems.SCROLL.get());

        IntrinsicTagAppender<Item> meats = tag(MinejagoItemTags.MEATS);
        for (Item item : BuiltInRegistries.ITEM.stream().toList())
        {
            if (item.isEdible() && item.getFoodProperties().isMeat())
                meats.add(item);
        }

        tag(MinejagoItemTags.DRAGON_FOODS)
                .addTag(MinejagoItemTags.MEATS)
                .add(Items.SALMON)
                .add(Items.TROPICAL_FISH)
                .add(Items.COOKED_COD)
                .add(Items.COD);

        tag(MinejagoItemTags.DRAGON_TREATS)
                .add(Items.COOKED_SALMON);

        // Wood sets
        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);
    }

    @Override
    public String getName()
    {
        return "Minejago Item Tags";
    }

    private static ResourceLocation forgeLoc(String path)
    {
        return new ResourceLocation("forge", path);
    }

    private static ResourceLocation cLoc(String path)
    {
        return new ResourceLocation("c", path);
    }

    private void tagPair(Pair<ResourceLocation, ResourceLocation> tags, Item... items)
    {
        tag(TagKey.create(Registries.ITEM, tags.getFirst())).add(items);
        tag(TagKey.create(Registries.ITEM, tags.getSecond())).add(items);
    }

    private void woodSet(WoodSet set)
    {
        copy(set.logsBlockTag().get(), set.logsItemTag().get());

        tag(ItemTags.PLANKS)
                .add(set.planks().get().asItem());

        tag(ItemTags.LOGS_THAT_BURN)
                .addTag(set.logsItemTag().get());
    }

    private void leavesSet(LeavesSet set)
    {
        tag(ItemTags.LEAVES)
                .add(set.leaves().get().asItem());

        tag(ItemTags.SAPLINGS)
                .add(set.sapling().get().asItem());
    }
}
