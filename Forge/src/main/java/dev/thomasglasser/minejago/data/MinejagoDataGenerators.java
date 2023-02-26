package dev.thomasglasser.minejago.data;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.data.advancements.MinejagoAdvancementProvider;
import dev.thomasglasser.minejago.data.blockstates.MinejagoBlockStates;
import dev.thomasglasser.minejago.data.lang.MinejagoEnUsLanguage;
import dev.thomasglasser.minejago.data.loot.MinejagoLootTables;
import dev.thomasglasser.minejago.data.models.MinejagoItemModels;
import dev.thomasglasser.minejago.data.recipes.MinejagoRecipes;
import dev.thomasglasser.minejago.data.sounds.MinejagoSoundDefinitions;
import dev.thomasglasser.minejago.data.tags.*;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MinejagoDataGenerators
{
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean onServer = event.includeServer();
        boolean onClient = event.includeClient();

        MinejagoBlockTagsProvider blockTags = new MinejagoBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        //Server
        generator.addProvider(onServer, new MinejagoItemTagsProvider(packOutput, lookupProvider, blockTags, existingFileHelper));
        generator.addProvider(onServer, new MinejagoRecipes(packOutput));
        generator.addProvider(onServer, blockTags);
        generator.addProvider(onServer, new MinejagoBannerPatternTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(onServer, new MinejagoLootTables(packOutput));
        generator.addProvider(onServer, new MinejagoAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(onServer, new MinejagoEntityTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(onServer, new DatapackBuiltinEntriesProvider(packOutput,
                CompletableFuture.supplyAsync(() -> {
                final RegistryAccess.Frozen access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
                final RegistrySetBuilder builder = new RegistrySetBuilder();
                MinejagoPowers.POWERS.addToSet(builder);
                return builder.build(access);
            }, Util.backgroundExecutor()),
            Set.of(Minejago.MOD_ID)));
        generator.addProvider(onServer, new PowerTagsProvider(packOutput, Minejago.MOD_ID, existingFileHelper));

//        Example call for datagenning powers
//        generator.addProvider(onServer, new MinejagoPowerProvider(packOutput, existingFileHelper));

        //Client
        generator.addProvider(onClient, new MinejagoItemModels(packOutput, existingFileHelper));
        generator.addProvider(onClient, new MinejagoEnUsLanguage(packOutput));
        generator.addProvider(onClient, new MinejagoBlockStates(packOutput, existingFileHelper));
        generator.addProvider(onClient, new MinejagoSoundDefinitions(packOutput, existingFileHelper));
    }
}
