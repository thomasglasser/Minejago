package com.thomasglasser.minejago;

import com.thomasglasser.minejago.server.entity.MinejagoEntityTypes;
import com.thomasglasser.minejago.server.item.GoldenWeaponItem;
import com.thomasglasser.minejago.server.item.MinejagoItems;
import com.thomasglasser.minejago.server.item.property.MinejagoTiers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.thomasglasser.minejago.init.ClientSetup;

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

        MinejagoEntityTypes.ENTITY_TYPES.register(bus);
        MinejagoItems.ITEMS.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(ClientSetup::init));
        MinecraftForge.EVENT_BUS.addListener(GoldenWeaponItem::checkForAll);
    }
}
