package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.client.MinejagoClientConfig;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.commands.arguments.MinejagoArguments;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.MinejagoTiers;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class Minejago {

	public static final String MOD_ID = "minejago";
	public static final String MOD_NAME = "Minejago";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation modLoc(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

	public enum Dependencies
	{
		MOONLIGHT_LIB("moonlight"),
		DYNAMIC_LIGHTS("dynamiclights");

		private final String modid;

		Dependencies(String modid)
		{
			this.modid = modid;
		}

		public String getModId() {
			return modid;
		}
	}

	public static void init()
	{
		initRegistries();

		registerConfigs();

		GeckoLib.initialize();
	}

	private static void initRegistries()
	{
		MinejagoRegistries.init();

		MinejagoTiers.init();
		MinejagoPowers.init();
		MinejagoEntityTypes.init();
		MinejagoItems.init();
		MinejagoArmor.init();
		MinejagoParticleTypes.init();
		MinejagoPaintingVariants.init();
		MinejagoBannerPatterns.init();
		MinejagoBlocks.init();
		MinejagoBlockEntityTypes.init();
		MinejagoPotions.init();
		MinejagoSoundEvents.init();
		MinejagoMobEffects.init();
		MinejagoArguments.init();
		MinejagoKeyMappings.init();
	}

	private static void registerConfigs()
	{
		MinejagoServerConfig.register();
		MinejagoClientConfig.register();
	}
}