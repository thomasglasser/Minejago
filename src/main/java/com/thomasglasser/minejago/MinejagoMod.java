package com.thomasglasser.minejago;

import com.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import com.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import com.thomasglasser.minejago.world.item.GoldenWeaponItem;
import com.thomasglasser.minejago.world.item.MinejagoItems;
import com.thomasglasser.minejago.world.item.MinejagoTiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.thomasglasser.minejago.client.ClientSetup;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MinejagoMod.MODID)
public class MinejagoMod
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "minejago";

    public MinejagoMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        MinejagoTiers.register();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientSetup::onRegisterParticleProviders));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientSetup::onRegisterColorHandlers));

        MinejagoEntityTypes.ENTITY_TYPES.register(bus);
        MinejagoItems.ITEMS.register(bus);
        MinejagoParticleTypes.PARTICLE_TYPES.register(bus);

        MinecraftForge.EVENT_BUS.addListener(GoldenWeaponItem::checkForAll);
    }
}
