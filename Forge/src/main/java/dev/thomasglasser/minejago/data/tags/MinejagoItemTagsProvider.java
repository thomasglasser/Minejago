package dev.thomasglasser.minejago.data.tags;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import net.minecraft.core.HolderLookup;
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
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class MinejagoItemTagsProvider extends ItemTagsProvider
{
    public static final Pair<ResourceLocation, ResourceLocation> SWORDS = Pair.of(forgeLoc("tools/swords"), cLoc("swords"));
    public static final Pair<ResourceLocation, ResourceLocation> HELMETS = Pair.of(forgeLoc("armors/helmets"), cLoc("helmets"));
    public static final Pair<ResourceLocation, ResourceLocation> CHESTPLATES = Pair.of(forgeLoc("armors/chestplates"), cLoc("chestplates"));
    public static final Pair<ResourceLocation, ResourceLocation> LEGGINGS = Pair.of(forgeLoc("armors/leggings"), cLoc("leggings"));
    public static final Pair<ResourceLocation, ResourceLocation> BOOTS = Pair.of(forgeLoc("armors/boots"), cLoc("boots"));

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
        tagDynamicLight("dropped", 10)
                .addTag(MinejagoItemTags.GOLDEN_WEAPONS);
        tagDynamicLight("self", 10)
                .addTag(MinejagoItemTags.GOLDEN_WEAPONS);
        MinejagoArmor.ARMOR_SETS.forEach(set ->
                set.getAll().forEach(item ->
                {
                    switch (set.getForItem(item.get())) {
                        case HEAD -> tagPair(HELMETS, item.get());
                        case CHEST -> tagPair(CHESTPLATES, item.get());
                        case LEGS -> tagPair(LEGGINGS, item.get());
                        case FEET -> tagPair(BOOTS, item.get());
                    }
                }));
        MinejagoArmor.SKELETAL_CHESTPLATE_SET.getAll().forEach(item ->
                tagPair(CHESTPLATES, item.get()));

        tag(MinejagoItemTags.WOODEN_RODS)
                .add(Items.STICK)
                .addOptionalTag(forgeLoc("rods/wooden"))
                .addOptionalTag(cLoc("wood_sticks"))
                .addOptionalTag(cLoc("wooden_rods"));
        tag(MinejagoItemTags.IRON_INGOTS)
                .add(Items.IRON_INGOT)
                .addOptionalTag(forgeLoc("ingots/iron"))
                .addOptionalTag(cLoc("iron_ingots"));
        MinejagoItemTags.DYES_TAGS.forEach((dyeColor, itemTagKey) ->
                tag(itemTagKey)
                        .add(DyeItem.byColor(dyeColor))
                        .addOptionalTag(forgeLoc("dyes/" + dyeColor.getName()))
                        .addOptionalTag(cLoc(dyeColor.getName() + "_dyes")));

        tag(ItemTags.DECORATED_POT_SHARDS)
                .add(MinejagoItems.POTTERY_SHERD_ICE_CUBE.get())
                .add(MinejagoItems.POTTERY_SHERD_THUNDER.get())
                .add(MinejagoItems.POTTERY_SHERD_PEAKS.get())
                .add(MinejagoItems.POTTERY_SHERD_MASTER.get())
                .add(MinejagoItems.POTTERY_SHERD_YIN_YANG.get())
                .add(MinejagoItems.POTTERY_SHERD_DRAGONS_HEAD.get())
                .add(MinejagoItems.POTTERY_SHERD_DRAGONS_TAIL.get());

        tag(ItemTags.TRIM_TEMPLATES)
                .add(MinejagoItems.FOUR_WEAPONS_ARMOR_TRIM_SMITHING_TEMPLATE.get());

        IntrinsicTagAppender<Item> pots = tag(MinejagoItemTags.TEAPOTS);
        pots.add(MinejagoItems.TEAPOT.get(), MinejagoItems.JASPOT.get());
        MinejagoItems.TEAPOTS.forEach((color, pot) -> pots.add(pot.get()));

        tag(MinejagoItemTags.LECTERN_SCROLLS)
                .add(MinejagoItems.WRITABLE_SCROLL.get())
                .add(MinejagoItems.WRITTEN_SCROLL.get());

        tag(ItemTags.LECTERN_BOOKS)
                .addTag(MinejagoItemTags.LECTERN_SCROLLS);
    }

    public TagAppender<Item> tagDynamicLight(String tag, int level)
    {
        level = Math.min(level, 15);
        return tag(ItemTags.create(new ResourceLocation(Minejago.Dependencies.DYNAMIC_LIGHTS.getModId(), tag + "_" + level)));
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
}
