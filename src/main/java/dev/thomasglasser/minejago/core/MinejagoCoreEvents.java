package dev.thomasglasser.minejago.core;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.level.biome.MinejagoRegion;
import dev.thomasglasser.minejago.world.level.biome.MinejagoSurfaceRuleData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public class MinejagoCoreEvents {
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            // Given we only add two biomes, we should keep our weight relatively low.
            Regions.register(new MinejagoRegion(new ResourceLocation(Minejago.MODID, "overworld"), 1));

            // Register our surface rules
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Minejago.MODID, MinejagoSurfaceRuleData.makeRules());
        });
    }
}
