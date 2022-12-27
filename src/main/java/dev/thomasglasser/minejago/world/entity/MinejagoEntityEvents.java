package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.core.particles.MinejagoParticleUtils;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.network.ClientboundRefreshVipDataPacket;
import dev.thomasglasser.minejago.network.ClientboundStopAnimationPacket;
import dev.thomasglasser.minejago.network.ServerboundStartSpinjitzuPacket;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.entity.npc.Character;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.item.WoodenNunchucksItem;
import dev.thomasglasser.minejago.world.level.storage.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

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
        event.put(MinejagoEntityTypes.WU.get(), Character.createAttributes().build());
        event.put(MinejagoEntityTypes.KAI.get(), Character.createAttributes().build());
        event.put(MinejagoEntityTypes.NYA.get(), Character.createAttributes().build());
        event.put(MinejagoEntityTypes.UNDERWORLD_SKELETON.get(), UnderworldSkeleton.createAttributes().build());
        event.put(MinejagoEntityTypes.KRUNCHA.get(), Kruncha.createAttributes().build());
        event.put(MinejagoEntityTypes.NUCKAL.get(), Nuckal.createAttributes().build());
    }
}
