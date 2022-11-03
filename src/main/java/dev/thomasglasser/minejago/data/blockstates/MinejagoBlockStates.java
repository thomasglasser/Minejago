package dev.thomasglasser.minejago.data.blockstates;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MinejagoBlockStates extends BlockStateProvider {
    public MinejagoBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Minejago.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(MinejagoBlocks.TEAPOT.get(), new ModelFile.ExistingModelFile(blockModel("teapot"), this.models().existingFileHelper));
    }

    public ResourceLocation blockModel(String path)
    {
        return new ResourceLocation(Minejago.MOD_ID, "block/" + path);
    }
}
