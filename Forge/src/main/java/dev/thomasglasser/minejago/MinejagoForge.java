package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.data.MinejagoDataGenerators;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Minejago.MOD_ID)
public class MinejagoForge
{
    public MinejagoForge()
    {
        Minejago.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(MinejagoDataGenerators::gatherData);
    }
}