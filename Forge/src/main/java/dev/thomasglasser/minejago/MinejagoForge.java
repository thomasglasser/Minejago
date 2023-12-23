package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import dev.thomasglasser.minejago.client.MinejagoForgeClientEvents;
import dev.thomasglasser.minejago.commands.MinejagoForgeCommandEvents;
import dev.thomasglasser.minejago.core.MinejagoForgeCoreEvents;
import dev.thomasglasser.minejago.data.MinejagoDataGenerators;
import dev.thomasglasser.minejago.platform.ForgeDataHelper;
import dev.thomasglasser.minejago.platform.ForgeRegistrationHelper;
import dev.thomasglasser.minejago.world.entity.MinejagoForgeEntityEvents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.TickEvent;

@Mod(Minejago.MOD_ID)
public class MinejagoForge
{
    public MinejagoForge(IEventBus bus)
    {
        ForgeRegistrationHelper.DEFERRED_REGISTERS.values().forEach(dr -> dr.register(bus));
        ForgeDataHelper.ATTACHMENT_TYPES.register(bus);

        Minejago.init();
        
        bus.addListener(MinejagoForgeCoreEvents::onCommonSetup);
        if (FMLEnvironment.dist.isClient()) bus.addListener(MinejagoForgeClientEvents::onClientSetup);

        addModListeners(bus);
        if (FMLEnvironment.dist.isClient()) addModClientListeners(bus);

        addForgeListeners();
        if (FMLEnvironment.dist.isClient()) addForgeClientListeners();

        bus.addListener(MinejagoDataGenerators::gatherData);

        Minejago.LOGGER.info("Installed mods: ");
        ModList.get().getMods().forEach(iModInfo -> Minejago.LOGGER.info(iModInfo.getModId()));
    }

    private void addModListeners(IEventBus bus)
    {
        bus.addListener(MinejagoForgeClientEvents::onClientConfigChanged);
        bus.addListener(MinejagoForgeEntityEvents::onEntityAttributeCreation);
        bus.addListener(MinejagoForgeEntityEvents::onSpawnPlacementsRegister);
        bus.addListener(MinejagoForgeCoreEvents::onAddPackFinders);
        bus.addListener(MinejagoForgeCoreEvents::onNewDataPackRegistry);
    }

    private void addModClientListeners(IEventBus bus)
    {
        bus.addListener(MinejagoForgeClientEvents::onRegisterParticleProviders);
        bus.addListener(MinejagoForgeClientEvents::onRegisterItemColorHandlers);
        bus.addListener(MinejagoForgeClientEvents::onRegisterBlockColorHandlers);
        bus.addListener(MinejagoForgeClientEvents::onRegisterRenderer);
        bus.addListener(MinejagoForgeClientEvents::onRegisterLayers);
        bus.addListener(MinejagoForgeClientEvents::registerModels);
        bus.addListener(MinejagoForgeClientEvents::registerClientReloadListeners);
        bus.addListener(MinejagoForgeClientEvents::onAddLayers);
        bus.addListener(MinejagoForgeClientEvents::onBuildCreativeTabContent);
        bus.addListener(MinejagoForgeClientEvents::onRegisterGuiOverlays);
    }

    private void addForgeListeners()
    {
        NeoForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onLivingTick);
        NeoForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onPlayerEntityInteract);
        NeoForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(MinejagoForgeEntityEvents::onServerPlayerLoggedIn);
        NeoForge.EVENT_BUS.addListener(MinejagoForgeCommandEvents::onCommandsRegister);
    }

    private void addForgeClientListeners()
    {
        NeoForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingIn event) -> MinejagoClientEvents.onPlayerLoggedIn());
        NeoForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent event) -> MinejagoClientEvents.onClientTick());
        NeoForge.EVENT_BUS.addListener((InputEvent event) -> MinejagoClientEvents.onInput(event instanceof InputEvent.Key keyEvent ? keyEvent.getKey() : -1));
    }
}