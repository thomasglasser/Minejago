package dev.thomasglasser.minejago.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.thomasglasser.minejago.client.animation.definitions.PlayerAnimations;
import dev.thomasglasser.minejago.world.entity.ShadowSource;
import dev.thomasglasser.tommylib.api.client.animation.AnimationUtils;
import java.util.EnumMap;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ShadowSourceRenderer<T extends ShadowSource> extends EntityRenderer<T> {
    public ShadowSourceRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T shadowSource, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        LivingEntity owner = shadowSource.getOwner();
        if (owner != null) {
            EntityRenderer<? super LivingEntity> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(owner);
            if (owner instanceof Player player) {
                ModifierLayer<IAnimation> animation = AnimationUtils.animationData.get(player);
                AnimationUtils.animationData.get(player).setAnimation(new KeyframeAnimationPlayer(PlayerAnimations.Meditation.FLOAT.getAnimation(), (int) (player.tickCount + partialTick % PlayerAnimations.Meditation.FLOAT.getAnimation().endTick)));
                EnumMap<EquipmentSlot, ItemStack> items = Util.make(new EnumMap<>(EquipmentSlot.class), map -> {
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        map.put(slot, owner.getItemBySlot(slot));
                        owner.setItemSlot(slot, Optional.ofNullable(shadowSource.getItemBySlot(slot)).orElse(ItemStack.EMPTY));
                    }
                });
                renderer.render(owner, entityYaw, partialTick, poseStack, bufferSource, packedLight);
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    owner.setItemSlot(slot, items.get(slot));
                }
                AnimationUtils.stopAnimation(player);
                AnimationUtils.animationData.put(player, animation);
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }
}
