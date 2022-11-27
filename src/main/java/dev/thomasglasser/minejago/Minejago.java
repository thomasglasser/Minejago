package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.core.MinejagoCoreEvents;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.data.MinejagoDataGenerators;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.GoldenWeaponItem;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.MinejagoTiers;
import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.biome.MinejagoBiomes;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.storage.PowerCapabilityAttacher;
import dev.thomasglasser.minejago.world.level.storage.ActivatedSpinjitzuCapabilityAttacher;
import dev.thomasglasser.minejago.world.level.storage.UnlockedSpinjitzuCapabilityAttacher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Minejago.MOD_ID)
public class Minejago
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "minejago";

    public Minejago() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        registerAssets(bus);

        bus.addListener(MinejagoCoreEvents::onCommonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(MinejagoClientEvents::onClientSetup));

        addModClientListeners(bus);

        addForgeListeners();

        registerCapabilities();

        bus.addListener(MinejagoDataGenerators::gatherData);
    }

    private void registerAssets(IEventBus bus)
    {
        MinejagoTiers.register();

        MinejagoEntityTypes.ENTITY_TYPES.register(bus);
        MinejagoItems.ITEMS.register(bus);
        MinejagoParticleTypes.PARTICLE_TYPES.register(bus);
        MinejagoBiomes.registerBiomes(bus);
        MinejagoPaintingVariants.PAINTING_VARIANTS.register(bus);
        MinejagoBannerPatterns.BANNER_PATTERNS.register(bus);
        MinejagoBlocks.BLOCKS.register(bus);
        MinejagoBlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        MinejagoPotions.POTIONS.register(bus);
        MinejagoSoundEvents.SOUND_EVENTS.register(bus);
        MinejagoMobEffects.MOB_EFFECTS.register(bus);
        MinejagoPowers.POWERS.register(bus);
    }

    private void addModClientListeners(IEventBus bus)
    {
        bus.addListener(MinejagoClientEvents::onRegisterParticleProviders);
        bus.addListener(MinejagoClientEvents::onRegisterColorHandlers);
        bus.addListener(MinejagoClientEvents::onRegisterRenderer);
        bus.addListener(MinejagoClientEvents::onRegisterLayers);
        bus.addListener(MinejagoClientEvents::registerModels);
        bus.addListener(MinejagoClientEvents::registerClientReloadListeners);
        bus.addListener(MinejagoClientEvents::addEntityLayers);
        bus.addListener(MinejagoClientEvents::onRegisterKeyMappings);
    }

    private void addForgeListeners()
    {
        MinecraftForge.EVENT_BUS.addListener(GoldenWeaponItem::checkForAll);
        MinecraftForge.EVENT_BUS.addListener(MinejagoPaintingVariants::onInteract);
        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onEntityTick);
    }

    private void registerCapabilities()
    {
        PowerCapabilityAttacher.register();
        ActivatedSpinjitzuCapabilityAttacher.register();
        UnlockedSpinjitzuCapabilityAttacher.register();
    }
}
