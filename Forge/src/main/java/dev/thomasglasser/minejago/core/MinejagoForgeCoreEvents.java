package dev.thomasglasser.minejago.core;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotionBrewing;
import dev.thomasglasser.minejago.world.level.biome.MinejagoRegion;
import dev.thomasglasser.minejago.world.level.biome.MinejagoSurfaceRuleData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public class MinejagoForgeCoreEvents {
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            MinejagoMainChannel.register();

            Regions.register(new MinejagoRegion(new ResourceLocation(Minejago.MOD_ID, "overworld"), 1));

            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Minejago.MOD_ID, MinejagoSurfaceRuleData.makeRules());

            MinejagoPotionBrewing.addMixes();
        });
    }
}
