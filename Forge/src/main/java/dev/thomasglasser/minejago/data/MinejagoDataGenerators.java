package dev.thomasglasser.minejago.data;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.advancements.MinejagoAdvancementProvider;
import dev.thomasglasser.minejago.data.blockstates.MinejagoBlockStates;
import dev.thomasglasser.minejago.data.lang.MinejagoEnUsLanguageProvider;
import dev.thomasglasser.minejago.data.lang.expansions.MinejagoImmersionPackEnUsLanguageProvider;
import dev.thomasglasser.minejago.data.loot.MinejagoLootTables;
import dev.thomasglasser.minejago.data.models.MinejagoItemModels;
import dev.thomasglasser.minejago.data.modonomicons.wiki.MinejagoWikiBookProvider;
import dev.thomasglasser.minejago.data.particles.MinejagoParticleDescriptionProvider;
import dev.thomasglasser.minejago.data.powers.MinejagoForgePowerDatagenSuite;
import dev.thomasglasser.minejago.data.recipes.MinejagoRecipes;
import dev.thomasglasser.minejago.data.recipes.expansions.MinejagoPotionPotPackRecipes;
import dev.thomasglasser.minejago.data.sherds.MinejagoForgeSherdDatagenSuite;
import dev.thomasglasser.minejago.data.sounds.MinejagoSoundDefinitions;
import dev.thomasglasser.minejago.data.tags.MinejagoBannerPatternTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoBiomeTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoBlockTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoDimensionTypeTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoEntityTypeTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoGameEventTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoItemTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoPaintingVariantTagsProvider;
import dev.thomasglasser.minejago.data.tags.MinejagoStructureTagsProvider;
import dev.thomasglasser.minejago.data.tags.PowerTagsProvider;
import dev.thomasglasser.minejago.data.worldgen.MinejagoProcessorLists;
import dev.thomasglasser.minejago.data.worldgen.biome.MinejagoBiomeModifiers;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoTreeFeatures;
import dev.thomasglasser.minejago.data.worldgen.features.MinejagoVegetationFeatures;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoTreePlacements;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoVegetationPlacements;
import dev.thomasglasser.minejago.world.level.levelgen.structure.MinejagoStructures;
import dev.thomasglasser.minejago.world.level.levelgen.structure.placement.MinejagoStructureSets;
import dev.thomasglasser.minejago.world.level.levelgen.structure.pools.MinejagoPools;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MinejagoDataGenerators
{
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TEMPLATE_POOL, MinejagoPools::bootstrap)
            .add(Registries.STRUCTURE, MinejagoStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, MinejagoStructureSets::bootstrap)
            .add(Registries.PROCESSOR_LIST, MinejagoProcessorLists::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MinejagoBiomeModifiers::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, (pContext ->
            {
                MinejagoTreeFeatures.bootstrap(pContext);
                MinejagoVegetationFeatures.bootstrap(pContext);
            }))
            .add(Registries.PLACED_FEATURE, (pContext ->
            {
                MinejagoTreePlacements.bootstrap(pContext);
                MinejagoVegetationPlacements.bootstrap(pContext);
            }));

    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean includeServer = event.includeServer();
        boolean includeClient = event.includeClient();

        genMain(event, generator, packOutput, lookupProvider, existingFileHelper, includeServer, includeClient);
        genImmersionPack(event, generator, new PackOutput(packOutput.getOutputFolder().resolve("resourcepacks/minejago_immersion_pack")), lookupProvider, existingFileHelper, includeServer, includeClient);
        genPotionPotPack(event, generator, new PackOutput(packOutput.getOutputFolder().resolve("resourcepacks/minejago_potion_pot_pack")), lookupProvider, existingFileHelper, includeServer, includeClient);
    }

    private static void genMain(GatherDataEvent event, DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient)
    {
        MinejagoBlockTagsProvider blockTags = new MinejagoBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        // Modonomicons (on server)
        AbstractModonomiconLanguageProvider mconEnUs = new AbstractModonomiconLanguageProvider(packOutput, Minejago.MOD_ID, "en_us") {
            @Override
            protected void addTranslations()
            {}
        };
        generator.addProvider(includeServer, new MinejagoWikiBookProvider(packOutput, mconEnUs));

        // LanugageProviders
        LanguageProvider enUs = new MinejagoEnUsLanguageProvider(packOutput)
        {
            @Override
            protected void addTranslations()
            {
                super.addTranslations();
                mconEnUs.data().forEach(this::add);
            }
        };

        // AdvancementProvider
        generator.addProvider(includeServer, new MinejagoAdvancementProvider(packOutput, lookupProvider, existingFileHelper, enUs));

        // Trims
        // TODO: Update when neo'd
//        new MinejagoTrimDatagenSuite(event, enUs);

        // Powers
        MinejagoForgePowerDatagenSuite powerDatagenSuite = new MinejagoForgePowerDatagenSuite(event, enUs);
        CompletableFuture<HolderLookup.Provider> powerLookupProvider = powerDatagenSuite.getRegistryProvider();
        generator.addProvider(includeServer, new PowerTagsProvider(packOutput, Minejago.MOD_ID,  powerLookupProvider, existingFileHelper));

        // Sherds
        new MinejagoForgeSherdDatagenSuite(event);

        // Lang gen (on client)
        generator.addProvider(includeClient, enUs);

        //Server
        DatapackBuiltinEntriesProvider datapackEntries = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, BUILDER, Set.of(Minejago.MOD_ID));
        generator.addProvider(includeServer, datapackEntries);
        lookupProvider = datapackEntries.getRegistryProvider();
        generator.addProvider(includeServer, blockTags);
        generator.addProvider(includeServer, new MinejagoItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(includeServer, new MinejagoRecipes(packOutput, lookupProvider));
        generator.addProvider(includeServer, new MinejagoBannerPatternTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoLootTables(packOutput));
        generator.addProvider(includeServer, new MinejagoEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoPaintingVariantTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoGameEventTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoStructureTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoDimensionTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

        //Client
        generator.addProvider(includeClient, new MinejagoBlockStates(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoItemModels(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoSoundDefinitions(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoParticleDescriptionProvider(packOutput, existingFileHelper));
    }

    private static void genImmersionPack(GatherDataEvent event, DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient)
    {
        generator.addProvider(includeClient, new MinejagoImmersionPackEnUsLanguageProvider(packOutput));
    }

    private static void genPotionPotPack(GatherDataEvent event, DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient)
    {
        generator.addProvider(includeServer, new MinejagoPotionPotPackRecipes(packOutput, lookupProvider));
    }
}
