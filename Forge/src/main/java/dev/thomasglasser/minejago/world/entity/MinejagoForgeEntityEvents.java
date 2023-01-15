package dev.thomasglasser.minejago.world.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class MinejagoForgeEntityEvents
{
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;

        MinejagoEntityEvents.onPlayerTick(player);
    }

    public static void onServerPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        MinejagoEntityEvents.onServerPlayerLoggedIn(event.getEntity());
    }

    public static void onLivingAttack(LivingAttackEvent event)
    {
        MinejagoEntityEvents.onLivingAttack(event.getSource());
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType<? extends LivingEntity> type : MinejagoEntityTypes.getAllAttributes().keySet())
        {
            event.put(type, MinejagoEntityTypes.getAllAttributes().get(type));
        }
    }

    public static void onLivingTick(LivingEvent.LivingTickEvent event)
    {
        LivingEntity entity = event.getEntity();

        MinejagoEntityEvents.onLivingTick(entity);
    }

    public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        MinejagoEntityEvents.onPlayerEntityInteract(event.getEntity(), event.getLevel(), event.getHand(), event.getTarget());
    }
}
