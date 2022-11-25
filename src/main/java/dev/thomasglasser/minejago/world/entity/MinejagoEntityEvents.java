package dev.thomasglasser.minejago.world.entity;

import dev.thomasglasser.minejago.client.animation.MinejagoPlayerAnimator;
import dev.thomasglasser.minejago.client.animation.definitions.SpinjitzuAnimation;
import dev.thomasglasser.minejago.client.particle.SpinjitzuParticle;
import dev.thomasglasser.minejago.core.particles.SpinjitzuParticleOptions;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuCapability;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuCapabilityAttacher;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;

public class MinejagoEntityEvents
{
    public static void onEntityTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        if ((/* TODO: Check unlocked Spinjitzu */true) && player.getCapability(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY).orElse(new SpinjitzuCapability(player)).isActive())
        {
            if (player.isCrouching())
            {
                player.getCapability(SpinjitzuCapabilityAttacher.SPINJITZU_CAPABILITY).orElse(new SpinjitzuCapability(player)).setActive(false, true);
            }
            if (event.side == LogicalSide.CLIENT && !player.getPersistentData().getBoolean("StartedSpinjitzu"))
            {
                SpinjitzuAnimation.startAnimation();
                player.getPersistentData().putBoolean("StartedSpinjitzu", true);
            }
            if (/* TODO: Check for element */ false)
            {
                // TODO: Elemental spinjitzu
                // SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.ELEMENT_ORANGE, SpinjitzuParticleOptions.ELEMENT_YELLOW, 12, false);
                // SparksParticle.renderPlayerSpinjitzuBorder(event.player, 4, false);
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
                        SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_GRAY, SpinjitzuParticleOptions.TEAM_DARK_GRAY, 12, false);
                    }
                }
            }
            else
            {
                SpinjitzuParticle.renderPlayerSpinjitzu(event.player, SpinjitzuParticleOptions.TEAM_GRAY, SpinjitzuParticleOptions.TEAM_DARK_GRAY, 12, false);
            }
        }
    }
}
