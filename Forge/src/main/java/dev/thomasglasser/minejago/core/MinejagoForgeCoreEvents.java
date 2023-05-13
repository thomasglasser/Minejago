package dev.thomasglasser.minejago.core;

import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotionBrewing;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class MinejagoForgeCoreEvents {
    public static void onCommonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            MinejagoMainChannel.register();

            MinejagoPotionBrewing.addMixes();
        });
    }
}
