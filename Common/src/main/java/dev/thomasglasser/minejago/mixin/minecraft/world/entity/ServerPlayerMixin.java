package dev.thomasglasser.minejago.mixin.minecraft.world.entity;

import com.mojang.datafixers.util.Either;
import dev.thomasglasser.minejago.network.ClientboundOpenScrollPacket;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin
{
    private final ServerPlayer INSTANCE = (ServerPlayer)(Object)this;

    @Inject(method = "attack", at = @At("HEAD"))
    private void minejago_attack(Entity target, CallbackInfo ci)
    {
        if (target instanceof ServerPlayer sp && (INSTANCE.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + EnchantmentHelper.getKnockbackBonus(INSTANCE)) > 2)
            MinejagoEntityEvents.stopSpinjitzu(Services.DATA.getSpinjitzuData(sp), sp, true);
    }

    @Inject(method = "crit", at = @At("HEAD"))
    private void minejago_crit(Entity entityHit, CallbackInfo ci)
    {
        if (entityHit instanceof ServerPlayer sp)
            MinejagoEntityEvents.stopSpinjitzu(Services.DATA.getSpinjitzuData(sp), sp, true);
    }

    @Inject(method = "magicCrit", at = @At("HEAD"))
    private void minejago_magicCrit(Entity entityHit, CallbackInfo ci)
    {
        if (entityHit instanceof ServerPlayer sp)
            MinejagoEntityEvents.stopSpinjitzu(Services.DATA.getSpinjitzuData(sp), sp, true);
    }

    @Inject(method = "openItemGui", at = @At("HEAD"))
    private void minejago_openItemGui(ItemStack stack, InteractionHand hand, CallbackInfo ci)
    {
        if (stack.is(MinejagoItems.WRITTEN_SCROLL.get())) {
            if (WrittenBookItem.resolveBookComponents(stack, INSTANCE.createCommandSourceStack(), INSTANCE)) {
                INSTANCE.containerMenu.broadcastChanges();
            }

            Services.NETWORK.sendToClient(ClientboundOpenScrollPacket.class, ClientboundOpenScrollPacket.toBytes(hand), INSTANCE);
        }
    }

    @Inject(method = "startSleepInBed", at = @At("HEAD"), cancellable = true)
    private void minejago_startSleepInBed(BlockPos bedPos, CallbackInfoReturnable<Either<Player.BedSleepingProblem, Unit>> cir)
    {
        if (/*TODO: Make world boolean for skeleton event*/false)
            cir.setReturnValue(Either.left(Player.BedSleepingProblem.NOT_SAFE));
    }
}
