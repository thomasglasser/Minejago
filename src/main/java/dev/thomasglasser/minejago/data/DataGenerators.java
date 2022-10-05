package dev.thomasglasser.minejago.data;

import dev.thomasglasser.minejago.MinejagoMod;
import dev.thomasglasser.minejago.data.lang.ModEnUsLanguageProvider;
import dev.thomasglasser.minejago.data.loot.MinejagoLootTableProvider;
import dev.thomasglasser.minejago.data.models.ModItemModels;
import dev.thomasglasser.minejago.data.recipes.MinejagoRecipes;
import dev.thomasglasser.minejago.data.tags.MinejagoBannerPatternTags;
import dev.thomasglasser.minejago.data.tags.ModBlockTags;
import dev.thomasglasser.minejago.data.tags.ModItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = MinejagoMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        boolean onServer = event.includeServer();
        boolean onClient = event.includeClient();

        ModBlockTags blockTags = new ModBlockTags(generator, existingFileHelper);

        //Server
        generator.addProvider(onServer, new ModItemTags(generator, blockTags, existingFileHelper));
        generator.addProvider(onServer, new MinejagoRecipes(generator));
        generator.addProvider(onServer, blockTags);
        generator.addProvider(onServer, new MinejagoBannerPatternTags(generator, existingFileHelper));
        generator.addProvider(onServer, new MinejagoLootTableProvider(generator));

        //Client
        generator.addProvider(onClient, new ModItemModels(generator, existingFileHelper));
        generator.addProvider(onClient, new ModEnUsLanguageProvider(generator));
    }
}
