package dev.thomasglasser.minejago.data;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.data.advancements.MinejagoAdvancementProvider;
import dev.thomasglasser.minejago.data.blockstates.MinejagoBlockStates;
import dev.thomasglasser.minejago.data.lang.MinejagoEnUsLanguageProvider;
import dev.thomasglasser.minejago.data.lang.expansions.MinejagoImmersionPackEnUsLanguageProvider;
import dev.thomasglasser.minejago.data.loot.MinejagoLootTables;
import dev.thomasglasser.minejago.data.models.MinejagoItemModels;
import dev.thomasglasser.minejago.data.recipes.MinejagoRecipes;
import dev.thomasglasser.minejago.data.sounds.MinejagoSoundDefinitions;
import dev.thomasglasser.minejago.data.tags.*;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armortrim.MinejagoTrimPatterns;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.DataPackRegistriesHooks;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MinejagoDataGenerators
{
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.TRIM_PATTERN, MinejagoTrimPatterns::bootstrap)
            .add(MinejagoRegistries.POWER, MinejagoPowers::bootstrap);

    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        lookupProvider = constructRegistries(lookupProvider.getNow(null), BUILDER);

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean includeServer = event.includeServer();
        boolean includeClient = event.includeClient();

        genMain(generator, packOutput, lookupProvider, existingFileHelper, includeServer, includeClient);
        genImmersionPack(generator, new PackOutput(packOutput.getOutputFolder().resolve("resourcepacks/minejago_immersion_pack")), lookupProvider, existingFileHelper, includeServer, includeClient);
    }
    
    private static void genMain(DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient)
    {
        MinejagoBlockTagsProvider blockTags = new MinejagoBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        //Server
        generator.addProvider(includeServer, new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, BUILDER, Set.of(Minejago.MOD_ID)));
        generator.addProvider(includeServer, blockTags);
        generator.addProvider(includeServer, new MinejagoItemTagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        generator.addProvider(includeServer, new MinejagoRecipes(packOutput));
        generator.addProvider(includeServer, new MinejagoBannerPatternTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoLootTables(packOutput));
        generator.addProvider(includeServer, new MinejagoAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoPaintingVariantTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new PowerTagsProvider(packOutput, Minejago.MOD_ID,  lookupProvider, existingFileHelper));
        generator.addProvider(includeServer, new MinejagoGameEventTagsProvider(packOutput, lookupProvider, existingFileHelper));

        //Client
        generator.addProvider(includeClient, new MinejagoItemModels(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoEnUsLanguageProvider(packOutput));
        generator.addProvider(includeClient, new MinejagoBlockStates(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoSoundDefinitions(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new MinejagoClientTagsProvider(packOutput, existingFileHelper));
    }
    
    private static void genImmersionPack(DataGenerator generator, PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper, boolean includeServer, boolean includeClient)
    {
        generator.addProvider(includeClient, new MinejagoImmersionPackEnUsLanguageProvider(packOutput));
    }

    private static CompletableFuture<HolderLookup.Provider> constructRegistries(HolderLookup.Provider original, RegistrySetBuilder datapackEntriesBuilder)
    {
        if (original == null) throw new IllegalArgumentException("Provider should not be null!");

        var builderKeys = new HashSet<>(datapackEntriesBuilder.getEntryKeys());
        DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().filter(data -> !builderKeys.contains(data.key())).forEach(data -> datapackEntriesBuilder.add(data.key(), context -> {}));
        var provider = datapackEntriesBuilder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
        return CompletableFuture.supplyAsync(() -> provider, Util.backgroundExecutor());
    }
}
