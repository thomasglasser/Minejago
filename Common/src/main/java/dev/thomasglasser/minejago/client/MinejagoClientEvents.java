package dev.thomasglasser.minejago.client;

import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.thomasglasser.minejago.client.animation.MinejagoPlayerAnimator;
import dev.thomasglasser.minejago.client.renderer.entity.layers.BetaTesterLayer;
import dev.thomasglasser.minejago.client.renderer.entity.layers.DevLayer;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class MinejagoClientEvents
{
    public static void onPlayerLoggedIn()
    {
        MinejagoClientUtils.refreshVip();
    }
}
