package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.core.CommonSetup;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.item.GoldenWeaponItem;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.MinejagoTiers;
import dev.thomasglasser.minejago.client.ClientSetup;
import dev.thomasglasser.minejago.world.level.biome.MinejagoBiomes;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MinejagoMod.MODID)
public class MinejagoMod
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "minejago";

    public MinejagoMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(CommonSetup::onCommonSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientSetup::onRegisterParticleProviders));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientSetup::onRegisterColorHandlers));

        MinejagoTiers.register();

        MinejagoEntityTypes.ENTITY_TYPES.register(bus);
        MinejagoItems.ITEMS.register(bus);
        MinejagoParticleTypes.PARTICLE_TYPES.register(bus);
        MinejagoBiomes.registerBiomes(bus);
        MinejagoPaintingVariants.PAINTING_VARIANTS.register(bus);
        MinejagoBannerPatterns.BANNER_PATTERNS.register(bus);

        MinecraftForge.EVENT_BUS.addListener(GoldenWeaponItem::checkForAll);
        MinecraftForge.EVENT_BUS.addListener(MinejagoPaintingVariants::onInteract);
    }
}
