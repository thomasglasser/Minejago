package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.world.entity.shadow.ShadowSource;
import dev.thomasglasser.tommylib.api.client.animation.PlayerAnimationHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ShadowSourceRenderer<T extends ShadowSource> extends AbstractShadowCopyRenderer<T> {
    public ShadowSourceRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        owner = entity.getOwner();
        if (owner != null) {
            // TODO: Fix
//            ModifierLayer<IAnimation> animation = PlayerAnimationHandler.animationData.get(owner);
//            if (owner instanceof Player player) {
//                PlayerAnimationHandler.animationData.get(player).setAnimation(new KeyframeAnimationPlayer(PlayerAnimations.Meditation.FLOAT.getAnimation(), (int) (player.tickCount + partialTick % PlayerAnimations.Meditation.FLOAT.getAnimation().endTick)));
//            }
//            super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
//            if (owner instanceof Player player) {
//                PlayerAnimationHandler.stopAnimation(player);
//                PlayerAnimationHandler.animationData.put(player, animation);
//            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (hasOwner())
            return ownerRenderer.getTextureLocation(owner);
        return super.getTextureLocation(entity);
    }
}
