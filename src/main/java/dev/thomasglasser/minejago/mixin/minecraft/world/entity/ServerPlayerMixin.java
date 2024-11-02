package dev.thomasglasser.minejago.mixin.minecraft.world.entity;

import com.mojang.datafixers.util.Either;
import dev.thomasglasser.minejago.network.ClientboundOpenScrollPayload;
import dev.thomasglasser.minejago.network.ClientboundSetFocusPayload;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Shadow
    public abstract ServerLevel serverLevel();

    @Unique
    private final ServerPlayer minejago$INSTANCE = (ServerPlayer) (Object) this;

    @Unique
    private FocusData minejago$focusData;
    @Unique
    private int minejago$lastSentFocus;
    @Unique
    private boolean minejago$lastFoodSaturationZero;

    @Inject(method = "crit", at = @At("HEAD"))
    private void minejago_crit(Entity entityHit, CallbackInfo ci) {
        if (entityHit instanceof ServerPlayer sp)
            MinejagoEntityEvents.stopSpinjitzu(sp.getData(MinejagoAttachmentTypes.SPINJITZU), sp, true);
    }

    @Inject(method = "magicCrit", at = @At("HEAD"))
    private void minejago_magicCrit(Entity entityHit, CallbackInfo ci) {
        if (entityHit instanceof ServerPlayer sp)
            MinejagoEntityEvents.stopSpinjitzu(sp.getData(MinejagoAttachmentTypes.SPINJITZU), sp, true);
    }

    @Inject(method = "openItemGui", at = @At("HEAD"), cancellable = true)
    private void minejago_openItemGui(ItemStack stack, InteractionHand hand, CallbackInfo ci) {
        if (stack.is(MinejagoItems.WRITTEN_SCROLL.get())) {
            if (WrittenBookItem.resolveBookComponents(stack, minejago$INSTANCE.createCommandSourceStack(), minejago$INSTANCE)) {
                minejago$INSTANCE.containerMenu.broadcastChanges();
            }

            TommyLibServices.NETWORK.sendToClient(new ClientboundOpenScrollPayload(hand), minejago$INSTANCE);
            ci.cancel();
        }
    }

    @Inject(method = "startSleepInBed", at = @At("HEAD"), cancellable = true)
    private void minejago_startSleepInBed(BlockPos bedPos, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> cir) {
        SkulkinRaid raid = ((SkulkinRaidsHolder) serverLevel()).getSkulkinRaidAt(bedPos);
        if (raid != null && raid.isActive())
            cir.setReturnValue(Either.left(Player.BedSleepingProblem.NOT_SAFE));
    }

    @Inject(method = "doTick", at = @At("TAIL"))
    private void minejago_doTick(CallbackInfo ci) {
        if (minejago$focusData == null)
            minejago$focusData = minejago$INSTANCE.getData(MinejagoAttachmentTypes.FOCUS);
        if (this.minejago$lastSentFocus != minejago$focusData.getFocusLevel() || this.minejago$focusData.getSaturationLevel() == 0.0F != this.minejago$lastFoodSaturationZero) {
            TommyLibServices.NETWORK.sendToClient(new ClientboundSetFocusPayload(minejago$focusData.getFocusLevel(), minejago$focusData.getSaturationLevel()), minejago$INSTANCE);
            this.minejago$lastSentFocus = this.minejago$focusData.getFocusLevel();
            this.minejago$lastFoodSaturationZero = this.minejago$focusData.getSaturationLevel() == 0.0F;
        }
    }
}
