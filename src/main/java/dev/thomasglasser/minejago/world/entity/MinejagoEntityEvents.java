package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.client.MinejagoKeyMappings;
import dev.thomasglasser.minejago.client.animation.definitions.SpinjitzuAnimation;
import dev.thomasglasser.minejago.client.particle.SpinjitzuParticle;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.network.ActivateSpinjitzuPacket;
import dev.thomasglasser.minejago.network.MinejagoMainChannel;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.level.storage.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;

public class MinejagoEntityEvents
{
    public static void onEntityTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        UnlockedSpinjitzuCapability unlocked = player.getCapability(UnlockedSpinjitzuCapabilityAttacher.UNLOCKED_SPINJITZU_CAPABILITY).orElse(new UnlockedSpinjitzuCapability(player));
        ActivatedSpinjitzuCapability activated = player.getCapability(ActivatedSpinjitzuCapabilityAttacher.ACTIVATED_SPINJITZU_CAPABILITY).orElse(new ActivatedSpinjitzuCapability(player));

        if (unlocked.isUnlocked())
        {
            if (activated.isActive())
            {
                if (player.isCrouching())
                {
                    activated.setActive(false);
                    SpinjitzuAnimation.stopAnimation();
                }
                Power power = player.getCapability(PowerCapabilityAttacher.POWER_CAPABILITY).orElse(new PowerCapability(player)).getPower();
                if (power != MinejagoPowers.EMPTY.get())
                {
                    SpinjitzuParticle.renderPlayerSpinjitzu(event.player, power.getMainSpinjitzuColor(), power.getAltSpinjitzuColor(), 10.5, false);
                    if (power.getBorderParticle() != null) SpinjitzuParticle.renderPlayerSpinjitzuBorder(power.getBorderParticle(), event.player, 4, false);
                }
                else if (player.getTeam() != null)
                {
                    switch (player.getTeam().getColor())
                    {
                        case RED -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_RED, SpinjitzuParticleOptions.TEAM_RED, 12, false);
                        }
                        case AQUA -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_AQUA, SpinjitzuParticleOptions.TEAM_AQUA, 12, false);
                        }
                        case BLUE -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_BLUE, SpinjitzuParticleOptions.TEAM_BLUE, 12, false);
                        }
                        case GOLD -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_GOLD, SpinjitzuParticleOptions.TEAM_GOLD, 12, false);
                        }
                        case GRAY -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_GRAY, SpinjitzuParticleOptions.TEAM_GRAY, 12, false);
                        }
                        case BLACK -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_BLACK, SpinjitzuParticleOptions.TEAM_BLACK, 12, false);
                        }
                        case GREEN -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_GREEN, SpinjitzuParticleOptions.TEAM_GREEN, 12, false);
                        }
                        case WHITE -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_WHITE, SpinjitzuParticleOptions.TEAM_WHITE, 11, false);
                        }
                        case YELLOW -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_YELLOW, SpinjitzuParticleOptions.TEAM_YELLOW, 12, false);
                        }
                        case DARK_RED -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_DARK_RED, SpinjitzuParticleOptions.TEAM_DARK_RED, 12, false);
                        }
                        case DARK_AQUA -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_DARK_AQUA, SpinjitzuParticleOptions.TEAM_DARK_AQUA, 12, false);
                        }
                        case DARK_BLUE -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_DARK_BLUE, SpinjitzuParticleOptions.TEAM_DARK_BLUE, 12, false);
                        }
                        case DARK_GRAY -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_DARK_GRAY, SpinjitzuParticleOptions.TEAM_DARK_GRAY, 12, false);
                        }
                        case DARK_GREEN -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_DARK_GREEN, SpinjitzuParticleOptions.TEAM_DARK_GREEN, 12, false);
                        }
                        case DARK_PURPLE -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_DARK_PURPLE, SpinjitzuParticleOptions.TEAM_DARK_PURPLE, 12, false);
                        }
                        case LIGHT_PURPLE -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_LIGHT_PURPLE, SpinjitzuParticleOptions.TEAM_LIGHT_PURPLE, 12, false);
                        }
                        default -> {
                            SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.DEFAULT_MAIN, SpinjitzuParticleOptions.DEFAULT_ALT, 12, false);
                        }
                    }
                }
                else
                {
                    SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.DEFAULT_MAIN, SpinjitzuParticleOptions.DEFAULT_ALT, 12, false);
                }
            }
            else if (MinejagoKeyMappings.ACTIVATE_SPINJITZU.isDown())
            {
                if (event.side.isClient())
                {
                    MinejagoMainChannel.sendToServer(new ActivateSpinjitzuPacket());
                    SpinjitzuAnimation.startAnimation();
                }
            }
        }
        else if (activated.isActive())
        {
            activated.setActive(false);
            SpinjitzuAnimation.stopAnimation();
        }
    }
}
