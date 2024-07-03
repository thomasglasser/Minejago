package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedItemTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MinejagoItemTagsProvider extends ExtendedItemTagsProvider {
    public MinejagoItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> future, CompletableFuture<TagLookup<Block>> blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(output, future, blockTagsProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(ItemTags.PIGLIN_LOVED)
                .addTag(MinejagoItemTags.GOLDEN_WEAPONS);
        tag(ItemTags.SWORDS)
                .add(MinejagoItems.BONE_KNIFE.get())
                .add(MinejagoItems.IRON_KATANA.get());
        tag(MinejagoItemTags.GOLDEN_WEAPONS)
                .add(MinejagoItems.SCYTHE_OF_QUAKES.get());
        MinejagoArmors.ARMOR_SETS.forEach(this::armorSet);
        MinejagoArmors.POWER_SETS.forEach(this::armorSet);
        tag(ItemTags.CHEST_ARMOR)
                .add(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.BONE).value())
                .add(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.STRENGTH).value())
                .add(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.SPEED).value())
                .add(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.BOW).value())
                .add(MinejagoArmors.SKELETAL_CHESTPLATE_SET.getForVariant(Skulkin.Variant.KNIFE).value())
                .add(MinejagoArmors.SAMUKAIS_CHESTPLATE.get());

        copy(MinejagoBlockTags.TEAPOTS, MinejagoItemTags.TEAPOTS);

        tag(MinejagoItemTags.LECTERN_SCROLLS)
                .add(MinejagoItems.WRITABLE_SCROLL.get())
                .add(MinejagoItems.WRITTEN_SCROLL.get());

        tag(ItemTags.LECTERN_BOOKS)
                .addTag(MinejagoItemTags.LECTERN_SCROLLS);

        tag(MinejagoItemTags.SCROLL_SHELF_SCROLLS)
                .addTag(MinejagoItemTags.LECTERN_SCROLLS)
                .add(MinejagoItems.SCROLL.get());

        tag(MinejagoItemTags.DRAGON_FOODS)
                .add(Items.SALMON)
                .add(Items.TROPICAL_FISH)
                .add(Items.COOKED_COD)
                .add(Items.COD)
                .addTag(ItemTags.MEAT);

        tag(MinejagoItemTags.DRAGON_TREATS)
                .add(Items.COOKED_SALMON);

        // Wood sets
        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);
    }
}
