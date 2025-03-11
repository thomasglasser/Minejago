package dev.thomasglasser.minejago.mixin.minecraft.world.entity.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Shadow
    @Final
    private Abilities abilities;

    private PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "causeFoodExhaustion", at = @At("TAIL"))
    private void causeFoodExhaustion(float exhaustion, CallbackInfo ci) {
        if (!abilities.invulnerable) {
            if (!level().isClientSide) {
                this.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(exhaustion);
            }
        }
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isSpectator()Z", ordinal = 0))
    private boolean tick(Player instance, Operation<Boolean> original) {
        if (original.call(instance))
            return true;
        return instance.getData(MinejagoAttachmentTypes.SHADOW_SOURCE).isPresent() && instance.getAbilities().flying;
    }
}
