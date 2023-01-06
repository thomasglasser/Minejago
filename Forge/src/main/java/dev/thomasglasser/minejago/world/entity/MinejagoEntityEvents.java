package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.network.ClientboundRefreshVipDataPacket;
import dev.thomasglasser.minejago.network.ClientboundStopAnimationPacket;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.network.ServerboundStartSpinjitzuPacket;
import dev.thomasglasser.minejago.world.entity.decoration.MinejagoPaintingVariants;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.item.GoldenWeaponItem;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.WoodenNunchucksItem;
import dev.thomasglasser.minejago.world.level.storage.PowerCapability;
import dev.thomasglasser.minejago.world.level.storage.PowerCapabilityAttacher;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuCapability;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuCapabilityAttacher;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
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

public class MinejagoEntityEvents
{
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        if (player instanceof ServerPlayer serverPlayer)
        {
            SpinjitzuCapability spinjitzu = serverPlayer.getCapability(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY).orElse(new SpinjitzuCapability(serverPlayer));

            if (spinjitzu.isUnlocked() || true /* TODO: Unlock system */) {
                if (spinjitzu.isActive()) {
                    if (serverPlayer.isCrouching()) {
                        spinjitzu.setActive(false);
                        MinejagoMainChannel.sendToAllClients(new ClientboundStopAnimationPacket(serverPlayer.getUUID()), serverPlayer);
                    }
                    Power power = serverPlayer.getCapability(PowerCapabilityAttacher.POWER_CAPABILITY).orElse(new PowerCapability(serverPlayer)).getPower();
                    if (power != MinejagoPowers.NONE.get()) {
                        MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, power.getMainSpinjitzuColor(), power.getAltSpinjitzuColor(), 10.5, false);
                        if (power.getBorderParticle() != null)
                            MinejagoParticleUtils.renderPlayerSpinjitzuBorder(power.getBorderParticle(), serverPlayer, 4, false);
                    } else if (serverPlayer.getTeam() != null) {
                        switch (serverPlayer.getTeam().getColor()) {
                            case RED -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_RED, SpinjitzuParticleOptions.TEAM_RED, 12, false);
                            }
                            case AQUA -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_AQUA, SpinjitzuParticleOptions.TEAM_AQUA, 12, false);
                            }
                            case BLUE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_BLUE, SpinjitzuParticleOptions.TEAM_BLUE, 12, false);
                            }
                            case GOLD -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_GOLD, SpinjitzuParticleOptions.TEAM_GOLD, 12, false);
                            }
                            case GRAY -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_GRAY, SpinjitzuParticleOptions.TEAM_GRAY, 12, false);
                            }
                            case BLACK -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_BLACK, SpinjitzuParticleOptions.TEAM_BLACK, 12, false);
                            }
                            case GREEN -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_GREEN, SpinjitzuParticleOptions.TEAM_GREEN, 12, false);
                            }
                            case WHITE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_WHITE, SpinjitzuParticleOptions.TEAM_WHITE, 11, false);
                            }
                            case YELLOW -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_YELLOW, SpinjitzuParticleOptions.TEAM_YELLOW, 12, false);
                            }
                            case DARK_RED -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_RED, SpinjitzuParticleOptions.TEAM_DARK_RED, 12, false);
                            }
                            case DARK_AQUA -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_AQUA, SpinjitzuParticleOptions.TEAM_DARK_AQUA, 12, false);
                            }
                            case DARK_BLUE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_BLUE, SpinjitzuParticleOptions.TEAM_DARK_BLUE, 12, false);
                            }
                            case DARK_GRAY -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_GRAY, SpinjitzuParticleOptions.TEAM_DARK_GRAY, 12, false);
                            }
                            case DARK_GREEN -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_GREEN, SpinjitzuParticleOptions.TEAM_DARK_GREEN, 12, false);
                            }
                            case DARK_PURPLE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_DARK_PURPLE, SpinjitzuParticleOptions.TEAM_DARK_PURPLE, 12, false);
                            }
                            case LIGHT_PURPLE -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.TEAM_LIGHT_PURPLE, SpinjitzuParticleOptions.TEAM_LIGHT_PURPLE, 12, false);
                            }
                            default -> {
                                MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.DEFAULT, SpinjitzuParticleOptions.DEFAULT, 12, false);
                            }
                        }
                    } else {
                        MinejagoParticleUtils.renderPlayerSpinjitzu(serverPlayer, SpinjitzuParticleOptions.DEFAULT, SpinjitzuParticleOptions.DEFAULT, 12, false);
                    }
                }
            } else if (spinjitzu.isActive()) {
                spinjitzu.setActive(false);
                MinejagoMainChannel.sendToAllClients(new ClientboundStopAnimationPacket(serverPlayer.getUUID()), serverPlayer);
            }
        } else if (MinejagoKeyMappings.ACTIVATE_SPINJITZU.isDown()) {
            MinejagoMainChannel.sendToServer(new ServerboundStartSpinjitzuPacket());
        }
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
        event.put(MinejagoEntityTypes.WU.get(), Wu.createAttributes().build());
        event.put(MinejagoEntityTypes.KAI.get(), Kai.createAttributes().build());
        event.put(MinejagoEntityTypes.NYA.get(), Character.createAttributes().build());
        event.put(MinejagoEntityTypes.COLE.get(), Cole.createAttributes().build());
        event.put(MinejagoEntityTypes.JAY.get(), Jay.createAttributes().build());
        event.put(MinejagoEntityTypes.ZANE.get(), Zane.createAttributes().build());
        event.put(MinejagoEntityTypes.UNDERWORLD_SKELETON.get(), UnderworldSkeleton.createAttributes().build());
        event.put(MinejagoEntityTypes.KRUNCHA.get(), Kruncha.createAttributes().build());
        event.put(MinejagoEntityTypes.NUCKAL.get(), Nuckal.createAttributes().build());
    }

    public static void onLivingTick(LivingEvent.LivingTickEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player)
        {
            Inventory i = player.getInventory();

            if (i.contains(MinejagoItems.SCYTHE_OF_QUAKES.get().getDefaultInstance()) /*&& i.contains(MinejagoElements.SHURIKENS_OF_ICE.get().getDefaultInstance()) && i.contains(MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get().getDefaultInstance()) && i.contains(MinejagoElements.SWORD_OF_FIRE.get().getDefaultInstance())*/)
                GoldenWeaponItem.overload(entity);
        }
        else if (entity instanceof InventoryCarrier carrier)
        {
            boolean f = false, e = false, i = false, l = false;

            for (ItemStack stack : entity.getAllSlots())
            {
                if (stack.getItem() == MinejagoItems.SCYTHE_OF_QUAKES.get())
                {
                    e = true;
                }
                /*else if (stack.getItem() == MinejagoElements.SWORD_OF_FIRE.get())
                {
                    f = true;
                }
                else if (stack.getItem() == MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())
                {
                    l = true;
                }
                else if (stack.getItem() == MinejagoElements.SHURIKENS_OF_ICE.get())
                {
                    i = true;
                }*/
            }

            SimpleContainer inventory = carrier.getInventory();

            if ((inventory.hasAnyOf(Set.of(MinejagoItems.SCYTHE_OF_QUAKES.get())) || e) /*&& (inventory.hasAnyOf(Set.of(MinejagoElements.SWORD_OF_FIRE.get())) || f) && (inventory.hasAnyOf(Set.of(MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())) || l) && (inventory.hasAnyOf(Set.of(MinejagoElements.SHURIKENS_OF_ICE.get())) || i)*/)
                GoldenWeaponItem.overload(entity);
        }
        else {
            boolean f = false, e = false, i = false, l = false;

            for (ItemStack stack : entity.getAllSlots())
            {
                if (stack.getItem() == MinejagoItems.SCYTHE_OF_QUAKES.get())
                {
                    e = true;
                }
                /*else if (stack.getItem() == MinejagoElements.SWORD_OF_FIRE.get())
                {
                    f = true;
                }
                else if (stack.getItem() == MinejagoElements.NUNCHUCKS_OF_LIGHTNING.get())
                {
                    l = true;
                }
                else if (stack.getItem() == MinejagoElements.SHURIKENS_OF_ICE.get())
                {
                    i = true;
                }*/
            }

            if (f && e && i && l)
                GoldenWeaponItem.overload(entity);
        }
    }

    public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        MinejagoPaintingVariants.onInteract(event.getEntity(), event.getLevel(), event.getHand(), event.getTarget());
    }
}
