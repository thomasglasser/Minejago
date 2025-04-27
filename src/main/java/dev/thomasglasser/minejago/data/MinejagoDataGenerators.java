package dev.thomasglasser.minejago.data;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.data.advancements.MinejagoAdvancementProvider;
import dev.thomasglasser.minejago.data.blockstates.MinejagoBlockStateProvider;
import dev.thomasglasser.minejago.data.datamaps.MinejagoDataMapsProvider;
import dev.thomasglasser.minejago.data.dynamiclights.MinejagoEntityLightSourceProvider;
import dev.thomasglasser.minejago.data.dynamiclights.MinejagoItemLightSourceProvider;
import dev.thomasglasser.minejago.data.lang.MinejagoEnUsLanguageProvider;
import dev.thomasglasser.minejago.data.lang.expansions.MinejagoImmersionPackEnUsLanguageProvider;
import dev.thomasglasser.minejago.data.loot.MinejagoLootTables;
import dev.thomasglasser.minejago.data.models.MinejagoItemModelProvider;
import dev.thomasglasser.minejago.data.modonomicons.MinejagoBookProvider;
import dev.thomasglasser.minejago.data.particles.MinejagoParticleDescriptionProvider;
import dev.thomasglasser.minejago.data.recipes.MinejagoRecipeProvider;
import dev.thomasglasser.minejago.data.recipes.expansions.MinejagoPotionPotPackRecipes;
import dev.thomasglasser.minejago.data.sounds.MinejagoSoundDefinitions;
import dev.thomasglasser.minejago.data.tags.ElementTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoBannerPatternTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoBiomeTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoBlockTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoDamageTypeTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoEntityTypeTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoGameEventTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoItemTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoPaintingVariantTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoStructureTagsProvider;
import dev.thomasglasser.minejago.data.trimmed.MinejagoTrimDatagenSuite;
import dev.thomasglasser.minejago.data.worldgen.MinejagoProcessorLists;
import dev.thomasglasser.minejago.data.worldgen.biome.MinejagoBiomeModifiers;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoTreeFeatures;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoVegetationFeatures;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoTreePlacements;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoVegetationPlacements;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.element.Elements;
import dev.thomasglasser.minejago.world.entity.element.tornadoofcreation.TornadoOfCreationConfigs;
import dev.thomasglasser.minejago.world.focus.modifier.FocusModifiers;
import dev.thomasglasser.minejago.world.level.biome.MinejagoBiomes;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import dev.thomasglasser.minejago.world.level.dimension.MinejagoDimensionTypes;
import dev.thomasglasser.minejago.world.level.dimension.MinejagoLevelStems;
import dev.thomasglasser.minejago.world.level.levelgen.MinejagoNoiseGeneratorSettings;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import dev.thomasglasser.minejago.world.level.levelgen.structure.placement.MinejagoStructureSets;
import dev.thomasglasser.minejago.world.level.levelgen.structure.pools.MinejagoPools;
import dev.thomasglasser.tommylib.api.data.info.ModRegistryDumpReport;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MinejagoDataGenerators {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(MinejagoRegistries.ELEMENT, Elements::bootstrap)
            .add(Registries.TEMPLATE_POOL, MinejagoPools::bootstrap)
            .add(Registries.STRUCTURE, MinejagoStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, MinejagoStructureSets::bootstrap)
            .add(Registries.PROCESSOR_LIST, MinejagoProcessorLists::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MinejagoBiomeModifiers::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, (pContext -> {
                MinejagoTreeFeatures.bootstrap(pContext);
                MinejagoVegetationFeatures.bootstrap(pContext);
            }))
            .add(Registries.PLACED_FEATURE, (pContext -> {
                MinejagoTreePlacements.bootstrap(pContext);
                MinejagoVegetationPlacements.bootstrap(pContext);
            }))
            .add(Registries.BANNER_PATTERN, MinejagoBannerPatterns::bootstrap)
            .add(Registries.PAINTING_VARIANT, MinejagoPaintingVariants::boostrap)
            .add(MinejagoRegistries.FOCUS_MODIFIER, FocusModifiers::bootstrap)
            .add(Registries.BIOME, MinejagoBiomes::bootstrap)
            .add(Registries.DIMENSION_TYPE, MinejagoDimensionTypes::bootstrap)
            .add(Registries.LEVEL_STEM, MinejagoLevelStems::bootstrap)
            .add(Registries.NOISE_SETTINGS, MinejagoNoiseGeneratorSettings::bootstrap)
            .add(MinejagoRegistries.TORNADO_OF_CREATION_CONFIG, TornadoOfCreationConfigs::bootstrap);

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean includeServer = event.includeServer();
        boolean includeClient = event.includeClient();

        genMain(event, generator, packOutput, lookupProvider, existingFileHelper, includeServer, includeClient);
        genImmersionPack(event, generator, new PackOutput(packOutput.getOutputFolder().resolve("packs/" + MinejagoPacks.IMMERSION.knownPack().namespace() + "/" + MinejagoPacks.IMMERSION.knownPack().id())), lookupProvider, existingFileHelper, includeServer, includeClient);
        genPotionPotPack(event, generator, new PackOutput(packOutput.getOutputFolder().resolve("packs/" + MinejagoPacks.POTION_POT.knownPack().namespace() + "/" + MinejagoPacks.POTION_POT.knownPack().id())), lookupProvider, existingFileHelper, includeServer, includeClient);
    }

    private static void genMain(GatherDataEvent event, DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient) {
        // Registry Entries
        DatapackBuiltinEntriesProvider datapackEntries = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, BUILDER, Set.of(Minejago.MOD_ID));
        generator.addProvider(includeServer, datapackEntries);
        lookupProvider = datapackEntries.getRegistryProvider();

        // LanguageProviders
        LanguageProvider enUs = new MinejagoEnUsLanguageProvider(packOutput, lookupProvider);

        // Trims
        new MinejagoTrimDatagenSuite(event, enUs);

        //Server
        generator.addProvider(includeServer, new ModRegistryDumpReport(packOutput, Minejago.MOD_ID, lookupProvider));
        BlockTagsProvider blockTags = generator.addProvider(includeServer, new MinejagoBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(includeServer, new MinejagoEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoLootTables(packOutput, lookupProvider));
        generator.addProvider(includeServer, new MinejagoGameEventTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoDataMapsProvider(packOutput, lookupProvider));
        generator.addProvider(includeServer, new MinejagoBannerPatternTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoPaintingVariantTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoStructureTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoDamageTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new ElementTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoAdvancementProvider(packOutput, lookupProvider, existingFileHelper, enUs));
        generator.addProvider(includeServer, new MinejagoRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(includeServer, new MinejagoBookProvider(packOutput, lookupProvider, enUs::add));

        //Client
        generator.addProvider(includeClient, new MinejagoBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoItemModelProvider(packOutput, existingFileHelper, lookupProvider));
        generator.addProvider(includeClient, new MinejagoSoundDefinitions(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoParticleDescriptionProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoItemLightSourceProvider(packOutput, lookupProvider));
        generator.addProvider(includeClient, new MinejagoEntityLightSourceProvider(packOutput, lookupProvider));
        generator.addProvider(includeClient, enUs);
    }

    private static void genImmersionPack(GatherDataEvent event, DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient) {
        generator.addProvider(includeClient, new MinejagoImmersionPackEnUsLanguageProvider(packOutput));
    }

    private static void genPotionPotPack(GatherDataEvent event, DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient) {
        generator.addProvider(includeServer, new MinejagoPotionPotPackRecipes(packOutput, lookupProvider));
    }
}
