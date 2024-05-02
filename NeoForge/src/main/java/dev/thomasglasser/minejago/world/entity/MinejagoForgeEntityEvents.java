package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.world.entity.character.Character;
import dev.thomasglasser.minejago.world.entity.character.Cole;
import dev.thomasglasser.minejago.world.entity.character.Zane;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class MinejagoForgeEntityEvents
{
    public static void onPlayerTick(PlayerTickEvent.Post event)
    {
        Player player = event.getEntity();

        MinejagoEntityEvents.onPlayerTick(player);
    }

    public static void onServerPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        MinejagoEntityEvents.onServerPlayerLoggedIn(event.getEntity());
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType<? extends LivingEntity> type : MinejagoEntityTypes.getAllAttributes().keySet())
        {
            event.put(type, MinejagoEntityTypes.getAllAttributes().get(type));
        }
    }

    public static void onLivingTick(EntityTickEvent.Post event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity)
            MinejagoEntityEvents.onLivingTick(livingEntity);
    }

    public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        MinejagoEntityEvents.onPlayerEntityInteract(event.getEntity(), event.getLevel(), event.getHand(), event.getTarget());
    }

    public static void onSpawnPlacementsRegister(SpawnPlacementRegisterEvent event)
    {
        event.register(MinejagoEntityTypes.ZANE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.OCEAN_FLOOR_WG, Zane::checkZaneSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(MinejagoEntityTypes.COLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ((entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> Character.checkCharacterSpawnRules(Cole.class, entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource)), SpawnPlacementRegisterEvent.Operation.AND);
    }
}
