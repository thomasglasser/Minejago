package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MinejagoBlockTags extends BlockTagsProvider
{

    public static final TagKey<Block> UNBREAKABLE = BlockTags.create(new ResourceLocation(Minejago.MOD_ID, "unbreakable"));

    public MinejagoBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Minejago.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(UNBREAKABLE)
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
                .add(MinejagoBlocks.TEAPOT.get());
    }

    @Override
    public String getName() {
        return "Minejago Block Tags";
    }
}
