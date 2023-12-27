package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.level.block.LeavesSet;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
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
                MinejagoBlocks.JASPOT.get(),
                MinejagoBlocks.FLAME_TEAPOT.get());
        for (RegistryObject<TeapotBlock> pot : MinejagoBlocks.TEAPOTS.values())
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

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);
    }

    private void woodSet(WoodSet set)
    {
        tag(set.logsBlockTag().get())
                .add(set.log().get(), set.strippedLog().get(), set.wood().get(), set.strippedWood().get());

        tag(BlockTags.PLANKS)
                .add(set.planks().get());

        tag(BlockTags.LOGS_THAT_BURN)
                .addTag(set.logsBlockTag().get());

        tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(set.log().get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(set.logsBlockTag().get())
                .add(set.planks().get());
    }

    private void leavesSet(LeavesSet set)
    {
        tag(BlockTags.LEAVES)
                .add(set.leaves().get());

        tag(BlockTags.SAPLINGS)
                .add(set.sapling().get());
    }

    @Override
    public String getName() {
        return "Minejago Block Tags";
    }
}
