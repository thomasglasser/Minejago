package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.client.MinejagoClientConfig;
import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleTypes;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.network.MinejagoPackets;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.ai.memory.MinejagoMemoryModuleTypes;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.inventory.MinejagoMenuTypes;
import dev.thomasglasser.minejago.world.item.MinejagoCreativeModeTabs;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.MinejagoTiers;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotions;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeSerializers;
import dev.thomasglasser.minejago.world.item.crafting.MinejagoRecipeTypes;
import dev.thomasglasser.minejago.world.level.block.MinejagoBlocks;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBannerPatterns;
import dev.thomasglasser.minejago.world.level.block.entity.MinejagoBlockEntityTypes;
import dev.thomasglasser.minejago.world.level.gameevent.MinejagoGameEvents;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.tslat.tes.api.TESAPI;
import net.tslat.tes.api.util.TESClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Minejago {

	public static final String MOD_ID = "minejago";
	public static final String MOD_NAME = "Minejago";
	public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

	public static ResourceLocation modLoc(String path)
	{
		return new ResourceLocation(MOD_ID, path);
	}

	public static void init()
	{
		initRegistries();

		registerConfigs();

		if (Dependencies.TSLAT_ENTITY_STATUS.isInstalled())
		{
			TESAPI.addTESHudElement(Minejago.modLoc("power_symbol"), (guiGraphics, mc, partialTick, entity, opacity, inWorldHud) ->
			{
				if (mc.level != null && Services.DATA.getPowerData(entity) != null && !inWorldHud) {
					Registry<Power> powers = mc.level.registryAccess().registry(MinejagoRegistries.POWER).get();
					Power power = powers.get(Services.DATA.getPowerData(entity).power());
					if (power != null)
					{
						TESClientUtil.prepRenderForTexture(power.getIcon());
						guiGraphics.pose().pushPose();
						guiGraphics.pose().scale(0.5f, 0.5f, 1.0f);
						TESClientUtil.drawSimpleTexture(guiGraphics, 0, 0, 32, 32, 0, 0, 32);
						guiGraphics.pose().popPose();
						return 16;
					}
				}

				return 0;
			});
		}

		MinejagoPackets.init();
	}

	private static void initRegistries()
	{
		MinejagoRegistries.init();

		MinejagoRecipeTypes.init();
		MinejagoRecipeSerializers.init();
		MinejagoArmors.init();
		MinejagoTiers.init();
		MinejagoPowers.init();
		MinejagoEntityTypes.init();
		MinejagoItems.init();
		MinejagoParticleTypes.init();
		MinejagoPaintingVariants.init();
		MinejagoBannerPatterns.init();
		MinejagoBlocks.init();
		MinejagoBlockEntityTypes.init();
		MinejagoPotions.init();
		MinejagoSoundEvents.init();
		MinejagoMobEffects.init();
		MinejagoKeyMappings.init();
		MinejagoCreativeModeTabs.init();
		MinejagoGameEvents.init();
		MinejagoMemoryModuleTypes.init();
		MinejagoMenuTypes.init();
		MinejagoCriteriaTriggers.init();
	}

	private static void registerConfigs()
	{
		MidnightConfig.init(Minejago.MOD_ID, MinejagoServerConfig.class);
		if (Services.PLATFORM.isClientSide()) MidnightConfig.init(Minejago.MOD_ID, MinejagoClientConfig.class);
	}

	public enum Dependencies
	{
		MOONLIGHT_LIB("moonlight"),
		DYNAMIC_LIGHTS("ryoamiclights", "lambdynlights"),
		TRIMMED("trimmed"),
		SHERDSAPI("sherdsapi"),
		PLAYER_ANIMATOR("playeranimator", "player-animator"),
		REACH_ENTITY_ATTRIBUTES("forge", "reach-entity-attributes"),
		MODONOMICON("modonomicon"),
		TSLAT_ENTITY_STATUS("tslatentitystatus");

		private final String forge;
		private final String fabric;

		Dependencies(String modId)
		{
			this(modId, modId);
		}
		Dependencies(String forge, String fabric)
		{
			this.forge = forge;
			this.fabric = fabric;
		}

		public String getModId() {
			return Services.PLATFORM.getPlatformName().equals("Forge") ? forge : fabric;
		}

		public boolean isInstalled()
		{
			return Services.PLATFORM.isModLoaded(getModId());
		}

		public ResourceLocation modLoc(String path)
		{
			return new ResourceLocation(getModId(), path);
		}
	}

	public enum Expansions
	{
		IMMERSION_PACK("minejago_immersion_pack");

		private final String id;

		Expansions(String id)
		{
			this.id = id;
		}

		public String getId() {
			return id;
		}
	}
}