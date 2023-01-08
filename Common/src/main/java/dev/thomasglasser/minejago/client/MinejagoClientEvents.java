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
    public static void registerAnimations()
    {
        //Set the player construct callback. It can be a lambda function.
        // TODO: Fix missing class error
//        PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.register(MinejagoPlayerAnimator::registerPlayerAnimation);
    }

    public static void onPlayerLoggedIn()
    {
        MinejagoClientUtils.refreshVip();
    }

    public static void onAddLayers(EntityModelSet models, Map<EntityType<?>, EntityRenderer<?>> renderers, Map<String, EntityRenderer<? extends Player>> playerRenderers)
    {
        for (String skin : playerRenderers.keySet()) {
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> player = (LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) playerRenderers.get(skin);

            if (player != null)
            {
                player.addLayer(new BetaTesterLayer<>(player, models));
                player.addLayer(new DevLayer(player, models));
            }
        }

        LivingEntityRenderer<Mob, PlayerModel<Mob>> wu = (LivingEntityRenderer<Mob, PlayerModel<Mob>>) renderers.get(MinejagoEntityTypes.WU.get());
        if (wu != null)
        {
            wu.addLayer(new BetaTesterLayer<>(wu, models));
            wu.addLayer(new DevLayer<>(wu, models));
        }
    }
}
