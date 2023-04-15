package dev.thomasglasser.minejago.data.blockstates;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MinejagoBlockStates extends BlockStateProvider {
    protected static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");

    public MinejagoBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Minejago.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(MinejagoBlocks.TEAPOT.get()).forAllStates(blockState ->
                ConfiguredModel.builder()
                        .modelFile(blockState.getValue(TeapotBlock.TEA_FILLED) ? models().withExistingParent("teapot_filled", blockModel("teapot_filled_base")) : models().withExistingParent("teapot", blockModel("teapot_base")))
                        .build());
        getVariantBuilder(MinejagoBlocks.JASPOT.get()).forAllStates(blockState ->
                ConfiguredModel.builder()
                        .modelFile(blockState.getValue(TeapotBlock.TEA_FILLED) ? models().withExistingParent("jaspot_filled", blockModel("teapot_filled_base")).texture("pot", blockModel("jaspot")).texture("particle", blockModel("jaspot")) : models().withExistingParent("jaspot", blockModel("teapot_base")).texture("pot", blockModel("jaspot")).texture("particle", blockModel("jaspot")))
                        .build());
        MinejagoBlocks.TEAPOTS.forEach((dyeColor, blockBlockRegistryObject) ->
        {
            if (models().existingFileHelper.exists(blockModel(dyeColor.getName() + "_teapot"), TEXTURE))
            {
                getVariantBuilder(blockBlockRegistryObject.get()).forAllStates(blockState ->
                        ConfiguredModel.builder()
                                .modelFile(blockState.getValue(TeapotBlock.TEA_FILLED) ? models().withExistingParent(dyeColor.getName() + "_teapot_filled", blockModel("teapot_filled_base")).texture("pot", blockModel(dyeColor.getName() + "_teapot")).texture("particle", blockModel(dyeColor.getName() + "_teapot")) : models().withExistingParent(dyeColor.getName() + "_teapot", blockModel("teapot_base")).texture("pot", blockModel(dyeColor.getName() + "_teapot")).texture("particle", blockModel(dyeColor.getName() + "_teapot")))
                                .build());
            }
        });
    }

    public ResourceLocation blockModel(String path)
    {
        return Minejago.modLoc("block/" + path);
    }
}
