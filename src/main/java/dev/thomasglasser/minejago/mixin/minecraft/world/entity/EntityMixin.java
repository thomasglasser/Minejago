package dev.thomasglasser.minejago.mixin.minecraft.world.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    @Final
    protected RandomSource random;
    @Unique
    private final Entity minejago$INSTANCE = ((Entity) (Object) this);

    @Inject(method = "playerTouch", at = @At("TAIL"))
    private void minejago_playerTouch(Player player, CallbackInfo ci) {
        if (player.getData(MinejagoAttachmentTypes.SPINJITZU).active() && minejago$INSTANCE instanceof LivingEntity livingEntity) {
            livingEntity.knockback(random.nextDouble(), player.getX() - minejago$INSTANCE.getX(), player.getZ() - minejago$INSTANCE.getZ());
        }
    }

    @ModifyReturnValue(method = "dampensVibrations", at = @At("TAIL"))
    private boolean minejago_dampensVibrations(boolean original) {
        if (minejago$INSTANCE instanceof LivingEntity livingEntity)
            return MinejagoArmors.isWearingFullGi(livingEntity) || livingEntity.getData(MinejagoAttachmentTypes.SKILL).get(Skill.STEALTH).level() >= 10 || original;
        return original;
    }

    @Inject(method = "makeStuckInBlock", at = @At("TAIL"))
    private void minejago_makeStuckInBlock(BlockState state, Vec3 motionMultiplier, CallbackInfo ci) {
        if (minejago$INSTANCE instanceof LivingEntity livingEntity) {
            SpinjitzuData spinjitzuData = livingEntity.getData(MinejagoAttachmentTypes.SPINJITZU);
            if (spinjitzuData.active() && livingEntity instanceof ServerPlayer player) {
                MinejagoEntityEvents.stopSpinjitzu(spinjitzuData, player, true);
            }
        }
    }
}
