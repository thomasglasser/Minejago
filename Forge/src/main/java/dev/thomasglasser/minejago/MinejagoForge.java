package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import dev.thomasglasser.minejago.core.MinejagoCoreEvents;
import dev.thomasglasser.minejago.data.MinejagoDataGenerators;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.level.storage.PowerCapabilityAttacher;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuCapabilityAttacher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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

        bus.addListener(MinejagoCoreEvents::onCommonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(MinejagoClientEvents::onClientSetup));

        addModListeners(bus);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> addModClientListeners(bus));

        addForgeListeners();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> this::addForgeClientListeners);

        registerCapabilities();

        bus.addListener(MinejagoDataGenerators::gatherData);
    }

    private void addModListeners(IEventBus bus)
    {
        bus.addListener(MinejagoClientEvents::onConfigChanged);
//        bus.addListener(MinejagoEntityEvents::onEntityAttributeCreation);
    }

    private void addModClientListeners(IEventBus bus)
    {
        bus.addListener(MinejagoClientEvents::onRegisterParticleProviders);
        bus.addListener(MinejagoClientEvents::onRegisterColorHandlers);
        bus.addListener(MinejagoClientEvents::onRegisterRenderer);
        bus.addListener(MinejagoClientEvents::onRegisterLayers);
        bus.addListener(MinejagoClientEvents::registerModels);
        bus.addListener(MinejagoClientEvents::registerClientReloadListeners);
        bus.addListener(MinejagoClientEvents::onRegisterKeyMappings);
        bus.addListener(MinejagoClientEvents::onAddLayers);
        bus.addListener(MinejagoClientEvents::onBuildCreativeTabContent);
    }

    private void addForgeListeners()
    {
//        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onLivingTick);
        MinecraftForge.EVENT_BUS.addListener((event ->
        {
            if (event instanceof PlayerInteractEvent.EntityInteract interact)
            {
                MinejagoPaintingVariants.onInteract(interact.getEntity(), interact.getLevel(), interact.getHand(), interact.getTarget());
            }
        }));
//        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onPlayerTick);
//        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onPlayerLoggedIn);
//        MinecraftForge.EVENT_BUS.addListener(MinejagoCommandEvents::onCommandsRegister);
//        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onLivingAttack);
    }

    private void addForgeClientListeners()
    {
        MinecraftForge.EVENT_BUS.addListener(MinejagoClientEvents::onPlayerLoggedIn);
    }

    private void registerCapabilities()
    {
        PowerCapabilityAttacher.register();
        SpinjitzuCapabilityAttacher.register();
    }
}