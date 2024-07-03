package dev.thomasglasser.minejago.data.blockstates;

import com.google.gson.JsonElement;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.DiscBlock;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.TopPostBlock;
import dev.thomasglasser.tommylib.api.data.blockstates.ExtendedBlockStateProvider;
import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.function.TriFunction;

public class MinejagoBlockStates extends ExtendedBlockStateProvider {
    public MinejagoBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Minejago.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(MinejagoBlocks.TEAPOT.get()).forAllStates(blockState -> ConfiguredModel.builder()
                .rotationY((int) (blockState.getValue(TeapotBlock.FACING).getOpposite()).toYRot())
                .modelFile(blockState.getValue(TeapotBlock.FILLED) ? models().withExistingParent("teapot_filled", modBlockModel("teapot_filled_base")) : models().withExistingParent("teapot", modBlockModel("teapot_base")))
                .build());
        MinejagoBlocks.TEAPOTS.forEach((dyeColor, blockBlockRegistryObject) -> {
            if (models().existingFileHelper.exists(modBlockModel(dyeColor.getName() + "_teapot"), TEXTURE)) {
                getVariantBuilder(blockBlockRegistryObject.get()).forAllStates(blockState -> ConfiguredModel.builder()
                        .rotationY((int) (blockState.getValue(TeapotBlock.FACING).getOpposite()).toYRot())
                        .modelFile(blockState.getValue(TeapotBlock.FILLED) ? models().withExistingParent(dyeColor.getName() + "_teapot_filled", modBlockModel("teapot_filled_base")).texture("pot", modBlockModel(dyeColor.getName() + "_teapot")).texture("particle", modItemModel(dyeColor.getName() + "_teapot")) : models().withExistingParent(dyeColor.getName() + "_teapot", modBlockModel("teapot_base")).texture("pot", modBlockModel(dyeColor.getName() + "_teapot")).texture("particle", modItemModel(dyeColor.getName() + "_teapot")))
                        .build());
            }
        });
        getVariantBuilder(MinejagoBlocks.JASPOT.get()).forAllStates(blockState -> ConfiguredModel.builder()
                .rotationY((int) (blockState.getValue(TeapotBlock.FACING).getOpposite()).toYRot())
                .modelFile(blockState.getValue(TeapotBlock.FILLED) ? models().withExistingParent("jaspot_filled", modBlockModel("teapot_filled_base")).texture("pot", modBlockModel("jaspot")).texture("particle", modItemModel("jaspot")) : models().withExistingParent("jaspot", modBlockModel("teapot_base")).texture("pot", modBlockModel("jaspot")).texture("particle", modItemModel("jaspot")))
                .build());
        getVariantBuilder(MinejagoBlocks.FLAME_TEAPOT.get()).forAllStates(blockState -> ConfiguredModel.builder()
                .rotationY((int) (blockState.getValue(TeapotBlock.FACING).getOpposite()).toYRot())
                .modelFile(blockState.getValue(TeapotBlock.FILLED) ? models().withExistingParent("flame_teapot_filled", modBlockModel("teapot_filled_base")).texture("pot", modBlockModel("flame_teapot")).texture("particle", modItemModel("flame_teapot")) : models().withExistingParent("flame_teapot", modBlockModel("teapot_base")).texture("pot", modBlockModel("flame_teapot")).texture("particle", modItemModel("flame_teapot")))
                .build());

        getVariantBuilder(MinejagoBlocks.GOLD_DISC.get()).forAllStates(blockState -> {
            Direction facing = blockState.getValue(DiscBlock.FACING);
            ModelFile model = models().withExistingParent("gold_disc_" + blockState.getValue(DiscBlock.ROW).getSerializedName() + "_" + blockState.getValue(DiscBlock.COLUMN).getSerializedName(), modBlockModel("disc_" + blockState.getValue(DiscBlock.ROW).getSerializedName() + "_" + blockState.getValue(DiscBlock.COLUMN).getSerializedName())).texture("base", modBlockModel("gold_disc_base")).texture("bump", modBlockModel("gold_disc_bump"));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY((int) (facing.getOpposite()).toYRot())
                    .build();
        });

        getVariantBuilder(MinejagoBlocks.TOP_POST.get()).forAllStates(blockState -> {
            Direction facing = blockState.getValue(TopPostBlock.FACING);
            ModelFile model = models().getExistingFile(modBlockModel("top_post"));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY((int) (facing.getOpposite()).toYRot())
                    .build();
        });

        getVariantBuilder(MinejagoBlocks.EARTH_DRAGON_HEAD.get()).forAllStates(blockState -> {
            Direction facing = blockState.getValue(TopPostBlock.FACING);
            ModelFile model = new ModelFile.ExistingModelFile(modLoc("block/earth_dragon_head.geo.json"), existingFileHelper) {
                @Override
                protected boolean exists() {
                    return existingFileHelper.exists(getUncheckedLocation(), new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, "", "geo"));
                }
            };

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY((int) (facing.getOpposite()).toYRot())
                    .build();
        });

        woodSet(MinejagoBlocks.ENCHANTED_WOOD_SET);
        leavesSet(MinejagoBlocks.FOCUS_LEAVES_SET);
    }

    @Override
    protected TriFunction<Consumer<BlockStateGenerator>, BiConsumer<ResourceLocation, Supplier<JsonElement>>, Consumer<Item>, ? extends ExtendedBlockModelGenerators> getBlockModelGenerators() {
        return MinejagoBlockModelGenerators::new;
    }

    private class MinejagoBlockModelGenerators extends ExtendedBlockModelGenerators {
        public MinejagoBlockModelGenerators(Consumer<BlockStateGenerator> pBlockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> pModelOutput, Consumer<Item> pSkippedAutoModelsOutput) {
            super(pBlockStateOutput, pModelOutput, pSkippedAutoModelsOutput);
        }

        private void createChiseledShelf(Block block) {
            ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
            MultiPartGenerator multipartgenerator = MultiPartGenerator.multiPart(block);
            SortedMap<Direction, VariantProperties.Rotation> sm = new TreeMap<>();
            sm.put(Direction.NORTH, VariantProperties.Rotation.R0);
            sm.put(Direction.EAST, VariantProperties.Rotation.R90);
            sm.put(Direction.SOUTH, VariantProperties.Rotation.R180);
            sm.put(Direction.WEST, VariantProperties.Rotation.R270);
            sm.forEach((p_262541_, p_262542_) -> {
                Condition.TerminalCondition condition$terminalcondition = Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, p_262541_);
                multipartgenerator.with(condition$terminalcondition, Variant.variant().with(VariantProperties.MODEL, resourcelocation).with(VariantProperties.Y_ROT, p_262542_).with(VariantProperties.UV_LOCK, true));
                addScrollSlotStateAndRotationVariants(multipartgenerator, condition$terminalcondition, p_262542_);
            });
            blockStateOutput.accept(multipartgenerator);
            delegateItemModel(block, ModelLocationUtils.getModelLocation(block, "_inventory"));
            CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.clear();
        }

        public void addScrollSlotModel(MultiPartGenerator p_261839_, Condition.TerminalCondition p_261634_, VariantProperties.Rotation p_262044_, BooleanProperty p_262163_, ModelTemplate p_261986_, boolean p_261790_) {
            String s = p_261790_ ? "_occupied" : "_empty";
            TextureMapping texturemapping = (new TextureMapping()).put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), s));
            BookSlotModelCacheKey blockmodelgenerators$bookslotmodelcachekey = new BookSlotModelCacheKey(p_261986_, s);
            ResourceLocation resourcelocation = CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.computeIfAbsent(blockmodelgenerators$bookslotmodelcachekey, (p_261415_) -> p_261986_.createWithSuffix(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), s, texturemapping, super.modelOutput));
            p_261839_.with(Condition.and(p_261634_, Condition.condition().term(p_262163_, p_261790_)), Variant.variant().with(VariantProperties.MODEL, resourcelocation).with(VariantProperties.Y_ROT, p_262044_));
        }

        public void addScrollSlotStateAndRotationVariants(MultiPartGenerator p_261951_, Condition.TerminalCondition p_261482_, VariantProperties.Rotation p_262169_) {
            LinkedHashMap<BooleanProperty, ModelTemplate> sortedmap = new LinkedHashMap<>();
            sortedmap.put(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_0_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_LEFT);
            sortedmap.put(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_1_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_MID);
            sortedmap.put(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_2_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_RIGHT);
            sortedmap.put(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_3_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT);
            sortedmap.put(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_4_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_MID);
            sortedmap.put(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_5_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT);
            sortedmap.forEach((p_261961_, p_261962_) -> {
                addScrollSlotModel(p_261951_, p_261482_, p_262169_, p_261961_, p_261962_, false);
                addScrollSlotModel(p_261951_, p_261482_, p_262169_, p_261961_, p_261962_, true);
            });
        }

        @Override
        public void run() {
            createChiseledShelf(MinejagoBlocks.CHISELED_SCROLL_SHELF.get());
            createBrushableBlock(MinejagoBlocks.SUSPICIOUS_RED_SAND.get());
        }
    }
}
