package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.tommylib.api.data.tags.ExtendedBlockTagsProvider;
import dev.thomasglasser.tommylib.api.registration.DeferredBlock;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoBlockTagsProvider extends ExtendedBlockTagsProvider {
    public MinejagoBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        tag(BlockTags.ENDERMAN_HOLDABLE)
                .addTag(MinejagoBlockTags.TEAPOTS);

        IntrinsicTagAppender<Block> pots = tag(MinejagoBlockTags.TEAPOTS);
        pots.add(MinejagoBlocks.TEAPOT.get(),
                MinejagoBlocks.JASPOT.get(),
                MinejagoBlocks.FLAME_TEAPOT.get());
        for (DeferredBlock<TeapotBlock> pot : MinejagoBlocks.TEAPOTS.values()) {
            pots.add(pot.get());
        }

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get());

        tag(BlockTags.SAND)
                .add(MinejagoBlocks.SUSPICIOUS_RED_SAND.get());

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);
    }
}
