package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.client.MinejagoClientConfig;
import dev.thomasglasser.minejago.commands.MinejagoCommandEvents;
import dev.thomasglasser.minejago.commands.arguments.MinejagoArguments;
import dev.thomasglasser.minejago.core.MinejagoCoreEvents;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.data.MinejagoDataGenerators;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.MinejagoTiers;
import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.storage.PowerCapabilityAttacher;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuCapabilityAttacher;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.GeckoLib;

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

        registerConfigs();

        bus.addListener(MinejagoCoreEvents::onCommonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(MinejagoClientEvents::onClientSetup));

        addModListeners(bus);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> addModClientListeners(bus));

        addForgeListeners();
        addForgeClientListeners();

        registerCapabilities();

        bus.addListener(MinejagoDataGenerators::gatherData);

        GeckoLib.initialize();
    }

    private void registerAssets(IEventBus bus)
    {
        MinejagoTiers.register();

        MinejagoPowers.POWERS.register(bus);
        MinejagoEntityTypes.ENTITY_TYPES.register(bus);
        MinejagoItems.ITEMS.register(bus);
        MinejagoArmor.ARMOR.register(bus);
        MinejagoParticleTypes.PARTICLE_TYPES.register(bus);
        MinejagoPaintingVariants.PAINTING_VARIANTS.register(bus);
        MinejagoBannerPatterns.BANNER_PATTERNS.register(bus);
        MinejagoBlocks.BLOCKS.register(bus);
        MinejagoBlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        MinejagoPotions.POTIONS.register(bus);
        MinejagoSoundEvents.SOUND_EVENTS.register(bus);
        MinejagoMobEffects.MOB_EFFECTS.register(bus);
        MinejagoArguments.ARGUMENT_TYPES.register(bus);
    }

    private void registerConfigs()
    {
        MinejagoServerConfig.register();
        MinejagoClientConfig.register();
    }

    private void addModListeners(IEventBus bus)
    {
        bus.addListener(MinejagoClientEvents::onConfigChanged);
        bus.addListener(MinejagoEntityEvents::onEntityAttributeCreation);
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
        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onLivingTick);
        MinecraftForge.EVENT_BUS.addListener(MinejagoPaintingVariants::onInteract);
        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onPlayerLoggedIn);
        MinecraftForge.EVENT_BUS.addListener(MinejagoCommandEvents::onCommandsRegister);
        MinecraftForge.EVENT_BUS.addListener(MinejagoEntityEvents::onLivingAttack);
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

    public static ResourceLocation modLoc(String name)
    {
        return new ResourceLocation(MOD_ID, name);
    }
}
