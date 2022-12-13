package dev.thomasglasser.minejago.data;

import dev.thomasglasser.minejago.data.blockstates.MinejagoBlockStates;
import dev.thomasglasser.minejago.data.lang.MinejagoEnUsLanguage;
import dev.thomasglasser.minejago.data.loot.MinejagoLootTables;
import dev.thomasglasser.minejago.data.models.MinejagoItemModels;
import dev.thomasglasser.minejago.data.recipes.MinejagoRecipes;
import dev.thomasglasser.minejago.data.sounds.MinejagoSoundDefinitions;
import dev.thomasglasser.minejago.data.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.data.tags.MinejagoBlockTags;
import dev.thomasglasser.minejago.data.tags.MinejagoItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

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

        MinejagoBlockTags blockTags = new MinejagoBlockTags(packOutput, lookupProvider, existingFileHelper);

        //Server
        generator.addProvider(onServer, new MinejagoItemTags(packOutput, lookupProvider, blockTags, existingFileHelper));
        generator.addProvider(onServer, new MinejagoRecipes(packOutput));
        generator.addProvider(onServer, blockTags);
        generator.addProvider(onServer, new MinejagoBannerPatternTags(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(onServer, new MinejagoLootTables(packOutput));

        //Client
        generator.addProvider(onClient, new MinejagoItemModels(packOutput, existingFileHelper));
        generator.addProvider(onClient, new MinejagoEnUsLanguage(packOutput));
        generator.addProvider(onClient, new MinejagoBlockStates(packOutput, existingFileHelper));
        generator.addProvider(onClient, new MinejagoSoundDefinitions(packOutput, existingFileHelper));
    }
}
