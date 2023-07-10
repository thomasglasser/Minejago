package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.BlockRegistryObject;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MinejagoBlockTagsProvider extends IntrinsicHolderTagsProvider<Block>
{
    public MinejagoBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, Registries.BLOCK, lookupProvider, block -> block.builtInRegistryHolder().key(), Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(MinejagoBlockTags.UNBREAKABLE)
                .add(Blocks.BEDROCK)
                .add(Blocks.BARRIER)
                .add(Blocks.CHAIN_COMMAND_BLOCK)
                .add(Blocks.COMMAND_BLOCK)
                .add(Blocks.REPEATING_COMMAND_BLOCK)
                .addTag(BlockTags.PORTALS)
                .add(Blocks.END_PORTAL_FRAME)
                .add(Blocks.JIGSAW)
                .add(Blocks.LIGHT);
        tag(BlockTags.ENDERMAN_HOLDABLE)
                .addTag(MinejagoBlockTags.TEAPOTS);

        IntrinsicTagAppender<Block> pots = tag(MinejagoBlockTags.TEAPOTS);
        pots.add(MinejagoBlocks.TEAPOT.get(),
                MinejagoBlocks.JASPOT.get());
        for (BlockRegistryObject<Block> pot : MinejagoBlocks.TEAPOTS.values())
        {
            pots.add(pot.get());
        }

        tag(MinejagoBlockTags.SHURIKEN_BREAKS)
                .addTag(BlockTags.CROPS)
                .addTag(BlockTags.FLOWERS)
                .addTag(BlockTags.SAPLINGS)
                .addTag(BlockTags.REPLACEABLE)
                .add(Blocks.SUGAR_CANE);

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get());

        tag(BlockTags.SAND)
                .add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get());
    }

    @Override
    public String getName() {
        return "Minejago Block Tags";
    }
}
