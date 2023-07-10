package dev.thomasglasser.minejago.data.blockstates;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.block.DiscBlock;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.TeapotBlock;
import dev.thomasglasser.minejago.world.level.block.TopPostBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MinejagoBlockStates extends BlockStateProvider {
    protected static final ExistingFileHelper.ResourceType TEXTURE = new ExistingFileHelper.ResourceType(PackType.CLIENT_RESOURCES, ".png", "textures");

    private final Map<Block, BlockStateGenerator> STATE_MAP = Maps.newHashMap();
    private final Map<ResourceLocation, Supplier<JsonElement>> MODEL_MAP = Maps.newHashMap();
    private final MinejagoBlockModelGenerators blockModelGenerators;
    private final PackOutput output;
    private final ExistingFileHelper existingFileHelper;

    public MinejagoBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Minejago.MOD_ID, exFileHelper);
        blockModelGenerators = makeBlockModelGenerators();
        this.output = output;
        existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(MinejagoBlocks.TEAPOT.get()).forAllStates(blockState ->
                ConfiguredModel.builder()
                        .modelFile(blockState.getValue(TeapotBlock.FILLED) ? models().withExistingParent("teapot_filled", blockModel("teapot_filled_base")) : models().withExistingParent("teapot", blockModel("teapot_base")))
                        .build());
        getVariantBuilder(MinejagoBlocks.JASPOT.get()).forAllStates(blockState ->
                ConfiguredModel.builder()
                        .modelFile(blockState.getValue(TeapotBlock.FILLED) ? models().withExistingParent("jaspot_filled", blockModel("teapot_filled_base")).texture("pot", blockModel("jaspot")).texture("particle", blockModel("jaspot")) : models().withExistingParent("jaspot", blockModel("teapot_base")).texture("pot", blockModel("jaspot")).texture("particle", blockModel("jaspot")))
                        .build());
        MinejagoBlocks.TEAPOTS.forEach((dyeColor, blockBlockRegistryObject) ->
        {
            if (models().existingFileHelper.exists(blockModel(dyeColor.getName() + "_teapot"), TEXTURE))
            {
                getVariantBuilder(blockBlockRegistryObject.get()).forAllStates(blockState ->
                        ConfiguredModel.builder()
                                .modelFile(blockState.getValue(TeapotBlock.FILLED) ? models().withExistingParent(dyeColor.getName() + "_teapot_filled", blockModel("teapot_filled_base")).texture("pot", blockModel(dyeColor.getName() + "_teapot")).texture("particle", blockModel(dyeColor.getName() + "_teapot")) : models().withExistingParent(dyeColor.getName() + "_teapot", blockModel("teapot_base")).texture("pot", blockModel(dyeColor.getName() + "_teapot")).texture("particle", blockModel(dyeColor.getName() + "_teapot")))
                                .build());
            }
        });


        getVariantBuilder(MinejagoBlocks.GOLD_DISC.get()).forAllStates(blockState ->
        {
            Direction facing = blockState.getValue(DiscBlock.FACING);
            ModelFile model = models().withExistingParent("gold_disc_" + blockState.getValue(DiscBlock.ROW).getSerializedName() + "_" + blockState.getValue(DiscBlock.COLUMN).getSerializedName(), blockModel("disc_" + blockState.getValue(DiscBlock.ROW).getSerializedName() + "_" + blockState.getValue(DiscBlock.COLUMN).getSerializedName())).texture("base", blockModel("gold_disc_base")).texture("bump", blockModel("gold_disc_bump"));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY((int) (facing.getOpposite()).toYRot())
                    .build();
        });
      
        getVariantBuilder(MinejagoBlocks.TOP_POST.get()).forAllStates(blockState ->
        {
            Direction facing = blockState.getValue(TopPostBlock.FACING);
            ModelFile model = models().getExistingFile(blockModel("top_post"));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY((int) (facing.getOpposite()).toYRot())
                    .build();
        });

        getVariantBuilder(MinejagoBlocks.EARTH_DRAGON_HEAD.get()).forAllStates(blockState ->
        {
            Direction facing = blockState.getValue(TopPostBlock.FACING);
            ModelFile model = new ModelFile.ExistingModelFile(modLoc("block/earth_dragon_head.geo.json"), existingFileHelper)
            {
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
    }

    private MinejagoBlockModelGenerators makeBlockModelGenerators()
    {
        Consumer<BlockStateGenerator> consumer = (p_125120_) -> {
            Block block = p_125120_.getBlock();
            BlockStateGenerator blockstategenerator = STATE_MAP.put(block, p_125120_);
            if (blockstategenerator != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + block);
            }
        };
        Set<Item> set = Sets.newHashSet();
        BiConsumer<ResourceLocation, Supplier<JsonElement>> biconsumer = (p_125123_, p_125124_) -> {
            Supplier<JsonElement> supplier = MODEL_MAP.put(p_125123_, p_125124_);
            if (supplier != null) {
                throw new IllegalStateException("Duplicate model definition for " + p_125123_);
            }
        };
        Consumer<Item> consumer1 = set::add;
        return (new MinejagoBlockModelGenerators(consumer, biconsumer, consumer1));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        blockModelGenerators.run();
        return CompletableFuture.allOf(super.run(cache), blockModelGenerators.generateAll(cache));
    }

    public static ResourceLocation blockModel(String path)
    {
        return Minejago.modLoc("block/" + path);
    }

    private class MinejagoBlockModelGenerators extends BlockModelGenerators
    {
        public MinejagoBlockModelGenerators(Consumer<BlockStateGenerator> pBlockStateOutput, BiConsumer<ResourceLocation, Supplier<JsonElement>> pModelOutput, Consumer<Item> pSkippedAutoModelsOutput) {
            super(pBlockStateOutput, pModelOutput, pSkippedAutoModelsOutput);
        }

        private void createChiseledShelf(Block block)
        {
            ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(block);
            MultiPartGenerator multipartgenerator = MultiPartGenerator.multiPart(block);
            Map.of(Direction.NORTH, VariantProperties.Rotation.R0, Direction.EAST, VariantProperties.Rotation.R90, Direction.SOUTH, VariantProperties.Rotation.R180, Direction.WEST, VariantProperties.Rotation.R270).forEach((p_262541_, p_262542_) -> {
                Condition.TerminalCondition condition$terminalcondition = Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, p_262541_);
                multipartgenerator.with(condition$terminalcondition, Variant.variant().with(VariantProperties.MODEL, resourcelocation).with(VariantProperties.Y_ROT, p_262542_).with(VariantProperties.UV_LOCK, true));
                addScrollSlotStateAndRotationVariants(multipartgenerator, condition$terminalcondition, p_262542_);
            });
            blockStateOutput.accept(multipartgenerator);
            delegateItemModel(block, ModelLocationUtils.getModelLocation(block, "_inventory"));
            CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.clear();
        }

        public void addScrollSlotModel(MultiPartGenerator p_261839_, Condition.TerminalCondition p_261634_, VariantProperties.Rotation p_262044_, BooleanProperty p_262163_, ModelTemplate p_261986_, boolean p_261790_)
        {
            String s = p_261790_ ? "_occupied" : "_empty";
            TextureMapping texturemapping = (new TextureMapping()).put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), s));
            BlockModelGenerators.BookSlotModelCacheKey blockmodelgenerators$bookslotmodelcachekey = new BlockModelGenerators.BookSlotModelCacheKey(p_261986_, s);
            ResourceLocation resourcelocation = CHISELED_BOOKSHELF_SLOT_MODEL_CACHE.computeIfAbsent(blockmodelgenerators$bookslotmodelcachekey, (p_261415_) -> p_261986_.createWithSuffix(MinejagoBlocks.CHISELED_SCROLL_SHELF.get(), s, texturemapping, super.modelOutput));
            p_261839_.with(Condition.and(p_261634_, Condition.condition().term(p_262163_, p_261790_)), Variant.variant().with(VariantProperties.MODEL, resourcelocation).with(VariantProperties.Y_ROT, p_262044_));
        }

        public void addScrollSlotStateAndRotationVariants(MultiPartGenerator p_261951_, Condition.TerminalCondition p_261482_, VariantProperties.Rotation p_262169_) {
            Map.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_0_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_LEFT, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_1_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_MID, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_2_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_TOP_RIGHT, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_3_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_4_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_MID, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_5_OCCUPIED, ModelTemplates.CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT).forEach((p_261410_, p_261411_) -> {
                this.addScrollSlotModel(p_261951_, p_261482_, p_262169_, p_261410_, p_261411_, true);
                this.addScrollSlotModel(p_261951_, p_261482_, p_262169_, p_261410_, p_261411_, false);
            });
        }

        @Override
        public void run() {
            createChiseledShelf(MinejagoBlocks.CHISELED_SCROLL_SHELF.get());
            createBrushableBlock(MinejagoBlocks.SUSPICIOUS_RED_SAND.get());
        }

        public CompletableFuture<?> generateAll(CachedOutput cache)
        {
            List<CompletableFuture<?>> futures = new ArrayList<>();
            for (Map.Entry<ResourceLocation, Supplier<JsonElement>> entry : MODEL_MAP.entrySet()) {
                futures.add(DataProvider.saveStable(cache, entry.getValue().get().getAsJsonObject(), getPath(entry.getKey())));
            }
            for (Map.Entry<Block, BlockStateGenerator> entry : STATE_MAP.entrySet()) {
                futures.add(saveBlockState(cache, entry.getValue().get().getAsJsonObject(), entry.getKey()));
            }
            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
        }

        private CompletableFuture<?> saveBlockState(CachedOutput cache, JsonObject stateJson, Block owner) {
            ResourceLocation blockName = Preconditions.checkNotNull(key(owner));
            Path outputPath = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                    .resolve(blockName.getNamespace()).resolve("blockstates").resolve(blockName.getPath() + ".json");
            return DataProvider.saveStable(cache, stateJson, outputPath);
        }

        private ResourceLocation key(Block block) {
            return ForgeRegistries.BLOCKS.getKey(block);
        }

        protected Path getPath(ResourceLocation loc) {
            return output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(loc.getNamespace()).resolve("models").resolve(loc.getPath() + ".json");
        }
    }
}