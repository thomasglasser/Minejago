package dev.thomasglasser.minejago.mixin.minecraft.client.renderer.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.thomasglasser.minejago.client.renderer.entity.AbstractShadowCopyRenderer;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @ModifyExpressionValue(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;"))
    private ResourceLocation getRenderType(ResourceLocation original, LivingEntity livingEntity) {
        if (livingEntity.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent())
            return AbstractShadowCopyRenderer.getShadowTexture(original);
        return original;
    }
}
