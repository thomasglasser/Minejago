package dev.thomasglasser.minejago.mixin.minecraft.world.level.gameevent.vibrations;

import com.llamalad7.mixinextras.sugar.Local;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.skill.MinejagoSkills;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VibrationSystem.User.class)
public interface VibrationSystemUserMixin {
    @Shadow
    boolean canTriggerAvoidVibration();

    @Inject(method = "isValidVibration", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/gameevent/vibrations/VibrationSystem$User;canTriggerAvoidVibration()Z"))
    default void isValidVibration(Holder<GameEvent> gameEvent, GameEvent.Context context, CallbackInfoReturnable<Boolean> cir, @Local Entity entity) {
        if (canTriggerAvoidVibration() && entity instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide) {
            livingEntity.getData(MinejagoAttachmentTypes.SKILL).addPractice(livingEntity, MinejagoSkills.STEALTH, 1 / 20f);
        }
    }
}
