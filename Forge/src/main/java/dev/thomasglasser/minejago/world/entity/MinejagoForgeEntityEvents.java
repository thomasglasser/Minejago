package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.network.ClientboundRefreshVipDataPacket;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.item.GoldenWeaponItem;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.WoodenNunchucksItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.Set;
import java.util.function.Supplier;

public class MinejagoForgeEntityEvents
{
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;

        MinejagoEntityEvents.onPlayerTick(player);
    }

    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        for (ServerPlayer player : ((ServerLevel) event.getEntity().getLevel()).getPlayers(serverPlayer -> true))
        {
            MinejagoMainChannel.sendToAllClients(new ClientboundRefreshVipDataPacket(), player);
        }
    }

    public static void onLivingAttack(LivingAttackEvent event)
    {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity && livingEntity.getMainHandItem().is(MinejagoItems.WOODEN_NUNCHUCKS.get()))
            WoodenNunchucksItem.resetDamage(livingEntity.getMainHandItem());
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        for (EntityType type : MinejagoEntityTypes.getAllAttributes().keySet())
        {
            event.put(type, MinejagoEntityTypes.getAllAttributes().get(type));
        }
    }

    public static void onLivingTick(LivingEvent.LivingTickEvent event)
    {
        LivingEntity entity = event.getEntity();


    }

    public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        MinejagoEntityEvents.onPlayerEntityInteract(event.getEntity(), event.getLevel(), event.getHand(), event.getTarget());
    }
}
