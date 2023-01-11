package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import dev.thomasglasser.minejago.client.MinejagoForgeClientEvents;
import dev.thomasglasser.minejago.commands.MinejagoForgeCommandEvents;
import dev.thomasglasser.minejago.core.MinejagoForgeCoreEvents;
import dev.thomasglasser.minejago.data.MinejagoDataGenerators;
import dev.thomasglasser.minejago.world.entity.MinejagoForgeEntityEvents;
import dev.thomasglasser.minejago.world.level.storage.PowerCapabilityAttacher;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuCapabilityAttacher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Minejago.MOD_ID)
public class MinejagoForge
{
    public MinejagoForge()
    {
        Minejago.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(MinejagoForgeCoreEvents::onCommonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(MinejagoForgeClientEvents::onClientSetup));

        addModListeners(bus);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> addModClientListeners(bus));

        addForgeListeners();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::addForgeClientListeners);

        registerCapabilities();

        bus.addListener(MinejagoDataGenerators::gatherData);
    }

    private void addModListeners(IEventBus bus)
    {
        bus.addListener(MinejagoForgeClientEvents::onConfigChanged);
        bus.addListener(MinejagoForgeEntityEvents::onEntityAttributeCreation);
    }

    private void addModClientListeners(IEventBus bus)
    {
        bus.addListener(MinejagoForgeClientEvents::onRegisterParticleProviders);
        bus.addListener(MinejagoForgeClientEvents::onRegisterColorHandlers);
        bus.addListener(MinejagoForgeClientEvents::onRegisterRenderer);
        bus.addListener(MinejagoForgeClientEvents::onRegisterLayers);
        bus.addListener(MinejagoForgeClientEvents::registerModels);
        bus.addListener(MinejagoForgeClientEvents::registerClientReloadListeners);
        bus.addListener(MinejagoForgeClientEvents::onAddLayers);
        bus.addListener(MinejagoForgeClientEvents::onBuildCreativeTabContent);
    }

    private void addForgeListeners()
    {
        MinecraftForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onLivingTick);
        MinecraftForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onPlayerEntityInteract);
        MinecraftForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onServerPlayerLoggedIn);
        MinecraftForge.EVENT_BUS.addListener(MinejagoForgeCommandEvents::onCommandsRegister);
        MinecraftForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onLivingAttack);
    }

    private void addForgeClientListeners()
    {
        MinecraftForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingIn event) -> MinejagoClientEvents.onPlayerLoggedIn());
    }

    private void registerCapabilities()
    {
        PowerCapabilityAttacher.register();
        SpinjitzuCapabilityAttacher.register();
    }
}