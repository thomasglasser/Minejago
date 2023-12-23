package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.data.tags.MinejagoBiomeTags;
import dev.thomasglasser.minejago.data.worldgen.placement.MinejagoVegetationPlacements;
import dev.thomasglasser.minejago.network.*;
import dev.thomasglasser.minejago.packs.MinejagoPacks;
import dev.thomasglasser.minejago.packs.PackHolder;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.character.Zane;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;

public class MinejagoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Minejago.init();

        registerEvents();

        registerEntityAttributes();

        registerPackets();

        addBiomeModifications();

        registerEntitySpawnPlacements();

        for (PackHolder holder : MinejagoPacks.getPacks())
        {
            if (holder.type() == PackType.SERVER_DATA) ResourceManagerHelper.registerBuiltinResourcePack(holder.id(), FabricLoader.getInstance().getModContainer(Minejago.MOD_ID).get(), Component.translatable(holder.titleKey()), ResourcePackActivationType.NORMAL);
            else if (holder.type() == PackType.CLIENT_RESOURCES) ResourceManagerHelper.registerBuiltinResourcePack(holder.id(), FabricLoader.getInstance().getModContainer(Minejago.MOD_ID).get(), Component.translatable(holder.titleKey()), ResourcePackActivationType.NORMAL);
        }

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
        ServerPlayNetworking.registerGlobalReceiver(ServerboundChangeVipDataPacket.ID, (server, player, handler, buf, responseSender) ->
                new ServerboundChangeVipDataPacket(buf).handle(player));
        ServerPlayNetworking.registerGlobalReceiver(ServerboundStartSpinjitzuPacket.ID, (server, player, handler, buf, responseSender) ->
                new ServerboundStartSpinjitzuPacket().handle(player));
        ServerPlayNetworking.registerGlobalReceiver(ServerboundSetPowerDataPacket.ID, (server, player, handler, buf, responseSender) ->
                new ServerboundSetPowerDataPacket(buf).handle(player));
        ServerPlayNetworking.registerGlobalReceiver(ServerboundStopSpinjitzuPacket.ID, (server, player, handler, buf, responseSender) ->
                new ServerboundStopSpinjitzuPacket().handle(player));
        ServerPlayNetworking.registerGlobalReceiver(ServerboundFlyVehiclePacket.ID, (server, player, handler, buf, responseSender) ->
                new ServerboundFlyVehiclePacket(buf).handle(player));
        ServerPlayNetworking.registerGlobalReceiver(ServerboundStartMeditationPacket.ID, (server, player, handler, buf, responseSender) ->
                new ServerboundStartMeditationPacket().handle(player));
        ServerPlayNetworking.registerGlobalReceiver(ServerboundStopMeditationPacket.ID, (server, player, handler, buf, responseSender) ->
                new ServerboundStopMeditationPacket(buf).handle(player));
    }

    private void addBiomeModifications()
    {
        BiomeModifications.addSpawn(context -> context.getBiomeKey() == Biomes.STONY_PEAKS, MobCategory.CREATURE, MinejagoEntityTypes.COLE.get(), 1, 1, 1);
        BiomeModifications.addSpawn(context -> context.getBiomeKey() == Biomes.FROZEN_RIVER, MobCategory.WATER_CREATURE, MinejagoEntityTypes.ZANE.get(), 1, 1, 1);

        BiomeModifications.addFeature(biomeSelectionContext -> biomeSelectionContext.hasTag(MinejagoBiomeTags.HAS_FOCUS_TREES), GenerationStep.Decoration.VEGETAL_DECORATION, MinejagoVegetationPlacements.MEADOW_FOCUS_TREES);
    }

    private void registerEntitySpawnPlacements()
    {
        SpawnPlacements.register(MinejagoEntityTypes.ZANE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zane::checkSurfaceWaterAnimalSpawnRules);
    }

    private void registerDynamicRegistries()
    {
        DynamicRegistries.registerSynced(MinejagoRegistries.POWER, Power.CODEC);
    }
}
