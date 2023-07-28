package dev.thomasglasser.minejago;

import dev.thomasglasser.minejago.network.*;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.character.Zane;
import dev.thomasglasser.minejago.world.item.brewing.MinejagoPotionBrewing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class MinejagoFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Minejago.init();

        registerEvents();

        registerEntityAttributes();

        MinejagoPotionBrewing.addMixes();

        registerPackets();

        addBiomeModifications();

        registerEntitySpawnPlacements();
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
    }

    private void addBiomeModifications()
    {
        BiomeModifications.addSpawn(context -> context.getBiomeKey() == Biomes.STONY_PEAKS, MobCategory.CREATURE, MinejagoEntityTypes.COLE.get(), 1, 1, 1);
        BiomeModifications.addSpawn(context -> context.getBiomeKey() == Biomes.FROZEN_RIVER, MobCategory.WATER_CREATURE, MinejagoEntityTypes.ZANE.get(), 1, 1, 1);
    }

    private void registerEntitySpawnPlacements()
    {
        SpawnPlacements.register(MinejagoEntityTypes.ZANE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Zane::checkSurfaceWaterAnimalSpawnRules);
    }
}
