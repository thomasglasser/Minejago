package com.thomasglasser.minejago.generated;

import com.thomasglasser.minejago.MinejagoMod;
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
        generator.addProvider(onServer, blockTags);

        //Client
        generator.addProvider(onClient, new ModItemModels(generator, existingFileHelper));
        generator.addProvider(onClient, new ModEnUsLanguageProvider(generator));
    }
}
