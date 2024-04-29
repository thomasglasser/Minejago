package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoVegetationPlacements;
import dev.thomasglasser.minejago.network.MinejagoPayloads;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.tags.MinejagoBiomeTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.minejago.world.entity.character.Cole;
import dev.thomasglasser.minejago.world.entity.character.Zane;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.focus.modifier.blockstate.BlockStateFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.entity.EntityTypeFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.itemstack.ItemStackFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.resourcekey.ResourceKeyFocusModifiers;
import dev.thomasglasser.minejago.world.focus.modifier.world.WorldFocusModifiers;
import dev.thomasglasser.tommylib.api.network.FabricNetworkUtils;
import dev.thomasglasser.tommylib.api.packs.PackInfo;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.impl.resource.loader.ResourceManagerHelperImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.NotNull;

public class MinejagoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Minejago.init();

        registerEvents();

        registerEntityAttributes();

        registerPackets();

        addBiomeModifications();

        registerEntitySpawnPlacements();

        for (PackInfo info : MinejagoPacks.getPacks())
        {
            if ((info.type() == PackType.CLIENT_RESOURCES && TommyLibServices.PLATFORM.isClientSide()) || info.type() == PackType.SERVER_DATA)
                ResourceManagerHelperImpl.registerBuiltinResourcePack(new ResourceLocation(info.knownPack().namespace(), info.knownPack().id()), "packs/" + info.knownPack().namespace() + "/" + info.knownPack().id(), FabricLoader.getInstance().getModContainer(Minejago.MOD_ID).orElseThrow(), Component.translatable(info.titleKey()), ResourcePackActivationType.NORMAL);
        }

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            public ResourceLocation getFabricId() {
                return Minejago.modLoc("focus_modifiers");
            }

            public void onResourceManagerReload(@NotNull ResourceManager manager) {
                ResourceKeyFocusModifiers.load(manager);
                BlockStateFocusModifiers.load(manager);
                EntityTypeFocusModifiers.load(manager);
                ItemStackFocusModifiers.load(manager);
                WorldFocusModifiers.load(manager);
            }
        });

        registerDynamicRegistries();
    }

    private void registerEvents()
    {
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
        {
            MinejagoEntityEvents.onPlayerEntityInteract(player, world, hand, entity);
            return InteractionResult.PASS;
        });
    }

    private void registerEntityAttributes()
    {
        for (EntityType<? extends LivingEntity> type : MinejagoEntityTypes.getAllAttributes().keySet())
        {
            FabricDefaultAttributeRegistry.register(type, MinejagoEntityTypes.getAllAttributes().get(type));
        }
    }

    private void registerPackets() {
        MinejagoPayloads.PAYLOADS.forEach(FabricNetworkUtils::register);
    }

    private void addBiomeModifications()
    {
        BiomeModifications.addSpawn(context -> context.hasTag(BiomeTags.IS_MOUNTAIN), MobCategory.CREATURE, MinejagoEntityTypes.COLE.get(), 100, 1, 1);
        BiomeModifications.addSpawn(context -> context.getBiomeKey() == Biomes.FROZEN_RIVER, MobCategory.WATER_CREATURE, MinejagoEntityTypes.ZANE.get(), 100, 1, 1);

        BiomeModifications.addFeature(biomeSelectionContext -> biomeSelectionContext.hasTag(MinejagoBiomeTags.HAS_FOCUS_TREES), GenerationStep.Decoration.VEGETAL_DECORATION, MinejagoVegetationPlacements.MEADOW_FOCUS_TREES);
    }

    private void registerEntitySpawnPlacements()
    {
        SpawnPlacements.register(MinejagoEntityTypes.ZANE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR_WG, Zane::checkZaneSpawnRules);
        SpawnPlacements.register(MinejagoEntityTypes.COLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> Character.checkCharacterSpawnRules(Cole.class, entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource)));
    }

    private void registerDynamicRegistries()
    {
        DynamicRegistries.registerSynced(MinejagoRegistries.POWER, Power.CODEC);
    }
}
