package dev.thomasglasser.minejago.mixin.minecraft.client.renderer.entity.player;

import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.client.renderer.entity.state.MinejagoPlayerRenderState;
import dev.thomasglasser.minejago.network.ClientboundStartSpinjitzuPayload;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @Inject(method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V", at = @At("TAIL"))
    private void extractRenderState(AbstractClientPlayer player, PlayerRenderState renderState, float p_364121_, CallbackInfo ci) {
        MinejagoPlayerRenderState state = (MinejagoPlayerRenderState) renderState;
        state.minejago$setRenderSnapshot(MinejagoClientUtils.renderSnapshotTesterLayer(player));
        state.minejago$setSnapshotChoice(MinejagoClientUtils.snapshotChoice(player));
        state.minejago$setRenderDev(MinejagoClientUtils.renderDevLayer(player));
        state.minejago$setRenderLegacyDev(MinejagoClientUtils.renderLegacyDevLayer(player));
        state.minejago$setSpinjitzuActive(player.getData(MinejagoAttachmentTypes.SPINJITZU).active());
        state.minejago$setSpinjitzuStartTicks(TommyLibServices.ENTITY.getPersistentData(player).getInt(ClientboundStartSpinjitzuPayload.KEY_SPINJITZUSTARTTICKS));
        state.minejago$setSpinjitzuColor(player.registryAccess().holderOrThrow(player.getData(MinejagoAttachmentTypes.POWER).power()).value().getColor().getValue());
    }
}
