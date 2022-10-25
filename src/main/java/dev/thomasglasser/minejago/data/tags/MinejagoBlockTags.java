package dev.thomasglasser.minejago.data.tags;

import dev.thomasglasser.minejago.Minejago;
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

    public static final TagKey<Block> UNBREAKABLE = BlockTags.create(new ResourceLocation("minejago", "unbreakable"));

    public MinejagoBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Minejago.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(UNBREAKABLE)
                .add(Blocks.BEDROCK)
                .add(Blocks.BARRIER)
                .add(Blocks.CHAIN_COMMAND_BLOCK)
                .add(Blocks.COMMAND_BLOCK)
                .addTag(BlockTags.PORTALS)
                .add(Blocks.END_PORTAL_FRAME)
                .add(Blocks.JIGSAW)
                .add(Blocks.LIGHT);
    }

    @Override
    public String getName() {
        return "Minejago Tags";
    }
}
