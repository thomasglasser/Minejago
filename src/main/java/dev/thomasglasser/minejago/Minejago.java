package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.client.MinejagoClientConfig;
import dev.thomasglasser.minejago.client.MinejagoClientEvents;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.commands.MinejagoCommandEvents;
import dev.thomasglasser.minejago.core.MinejagoCoreEvents;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.data.MinejagoDataGenerators;
import dev.thomasglasser.minejago.network.MinejagoPayloads;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntitySerializers;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.ai.poi.MinejagoPoiTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.focus.modifier.resourcekey.ResourceKeyFocusModifiers;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.MinejagoTiers;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmorMaterials;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeSerializers;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import dev.thomasglasser.minejago.world.level.saveddata.maps.MinejagoMapDecorationTypes;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.tslat.tes.api.TESAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Minejago.MOD_ID)
public class Minejago {
    public static final String MOD_ID = "minejago";
    public static final String MOD_NAME = "Minejago";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public Minejago(IEventBus bus, ModContainer modContainer) {
        LOGGER.info("Initializing {} for {} in a {} environment...", MOD_NAME, TommyLibServices.PLATFORM.getPlatformName(), TommyLibServices.PLATFORM.getEnvironmentName());

        initRegistries();

        registerConfigs(modContainer);

        if (Dependencies.TSLAT_ENTITY_STATUS.isInstalled()) {
            TESAPI.addTESHudElement(Minejago.modLoc("power_symbol"), MinejagoClientEvents::renderPowerSymbol);
        }

        MinejagoPayloads.init();

        if (FMLEnvironment.dist.isClient()) bus.addListener(MinejagoClientEvents::onClientSetup);

        addModListeners(bus);
        if (FMLEnvironment.dist.isClient()) addModClientListeners(bus);

        addForgeListeners();
        if (FMLEnvironment.dist.isClient()) addForgeClientListeners();

        bus.addListener(MinejagoDataGenerators::gatherData);
    }

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private static void initRegistries() {
        MinejagoRegistries.init();

        MinejagoArmorMaterials.init();
        MinejagoDataComponents.init();
        MinejagoRecipeTypes.init();
        MinejagoRecipeSerializers.init();
        MinejagoArmors.init();
        MinejagoTiers.init();
        MinejagoPowers.init();
        MinejagoEntityTypes.init();
        MinejagoParticleTypes.init();
        MinejagoBlocks.init();
        MinejagoBlockEntityTypes.init();
        MinejagoItems.init();
        MinejagoPotions.init();
        MinejagoSoundEvents.init();
        MinejagoMobEffects.init();
        MinejagoKeyMappings.init();
        MinejagoCreativeModeTabs.init();
        MinejagoGameEvents.init();
        MinejagoMemoryModuleTypes.init();
        MinejagoCriteriaTriggers.init();
        ResourceKeyFocusModifiers.init();
        MinejagoAttachmentTypes.init();
        MinejagoEntitySerializers.init();
        MinejagoMapDecorationTypes.init();
        MinejagoPoiTypes.init();
    }

    private static void registerConfigs(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, MinejagoServerConfig.INSTANCE.getConfigSpec());
        modContainer.registerConfig(ModConfig.Type.CLIENT, MinejagoClientConfig.INSTANCE.getConfigSpec());
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    private void addModListeners(IEventBus bus) {
        bus.addListener(MinejagoClientEvents::onClientConfigChanged);
        bus.addListener(MinejagoEntityEvents::onEntityAttributeCreation);
        bus.addListener(MinejagoEntityEvents::onSpawnPlacementsRegister);
        bus.addListener(MinejagoCoreEvents::onAddPackFinders);
        bus.addListener(MinejagoCoreEvents::onNewDataPackRegistry);
        bus.addListener(MinejagoCoreEvents::onRegisterPackets);
        bus.addListener(MinejagoEntityEvents::onAddBlocksToBlockEntityType);
    }

    private void addModClientListeners(IEventBus bus) {
        bus.addListener(MinejagoClientEvents::onRegisterParticleProviders);
        bus.addListener(MinejagoClientEvents::onRegisterItemColorHandlers);
        bus.addListener(MinejagoClientEvents::onRegisterBlockColorHandlers);
        bus.addListener(MinejagoClientEvents::onRegisterRenderer);
        bus.addListener(MinejagoClientEvents::onRegisterLayers);
        bus.addListener(MinejagoClientEvents::registerModels);
        bus.addListener(MinejagoClientEvents::onAddLayers);
        bus.addListener(MinejagoClientEvents::onBuildCreativeTabContent);
        bus.addListener(MinejagoClientEvents::onRegisterGuiOverlays);
        bus.addListener(MinejagoClientEvents::registerClientReloadListeners);
    }

    private void addForgeListeners() {
        NeoForge.EVENT_BUS.addListener(MinejagoEntityEvents::onLivingTick);
        NeoForge.EVENT_BUS.addListener(MinejagoEntityEvents::onPlayerEntityInteract);
        NeoForge.EVENT_BUS.addListener(MinejagoEntityEvents::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(MinejagoEntityEvents::onServerPlayerLoggedIn);
        NeoForge.EVENT_BUS.addListener(MinejagoCommandEvents::onCommandsRegister);
        NeoForge.EVENT_BUS.addListener(MinejagoCoreEvents::onAddReloadListeners);
        NeoForge.EVENT_BUS.addListener(MinejagoEntityEvents::onLivingKnockBack);
        NeoForge.EVENT_BUS.addListener(MinejagoEntityEvents::onEntityJoinLevel);
        NeoForge.EVENT_BUS.addListener(MinejagoEntityEvents::onPlayerClone);
    }

    private void addForgeClientListeners() {
        NeoForge.EVENT_BUS.addListener((ClientPlayerNetworkEvent.LoggingIn event) -> MinejagoClientEvents.onPlayerLoggedIn());
        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post event) -> MinejagoClientEvents.onClientTick());
        NeoForge.EVENT_BUS.addListener((InputEvent.Key event) -> MinejagoClientEvents.onInput(event.getKey()));
        NeoForge.EVENT_BUS.addListener(MinejagoClientEvents::onPostPlayerRender);
    }

    public enum Dependencies {
        MOONLIGHT_LIB("moonlight"),
        DYNAMIC_LIGHTS("ryoamiclights"),
        TRIMMED("trimmed"),
        PLAYER_ANIMATOR("playeranimator"),
        MODONOMICON("modonomicon"),
        TSLAT_ENTITY_STATUS("tslatentitystatus");

        private final String modId;

        Dependencies(String modId) {
            this.modId = modId;
        }

        public String getModId() {
            return modId;
        }

        public boolean isInstalled() {
            return TommyLibServices.PLATFORM.isModLoaded(getModId());
        }

        public ResourceLocation modLoc(String path) {
            return ResourceLocation.fromNamespaceAndPath(modId, path);
        }
    }

    public enum Expansions {
        IMMERSION_PACK("immersion");

        private final String id;

        Expansions(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
