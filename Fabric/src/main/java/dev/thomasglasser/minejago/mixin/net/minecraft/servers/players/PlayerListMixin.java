package dev.thomasglasser.minejago.mixin.net.minecraft.servers.players;

import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin
{
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void placeNewPlayer(Connection netManager, ServerPlayer player, CallbackInfo ci)
    {
        MinejagoEntityEvents.onServerPlayerLoggedIn(player);
    }
}
