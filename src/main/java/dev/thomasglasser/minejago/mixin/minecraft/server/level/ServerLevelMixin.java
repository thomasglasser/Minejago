package dev.thomasglasser.minejago.mixin.minecraft.server.level;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaids;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin implements SkulkinRaidsHolder {
    @Unique
    private final ServerLevel minejago$INSTANCE = ((ServerLevel) (Object) (this));

    @Unique
    protected SkulkinRaids minejago$skulkinRaids;

    @Shadow
    public abstract DimensionDataStorage getDataStorage();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey resourceKey, LevelStem levelStem, ChunkProgressListener chunkProgressListener, boolean bl, long l, List list, boolean bl2, RandomSequences randomSequences, CallbackInfo ci) {
        minejago$skulkinRaids = this.getDataStorage().computeIfAbsent(SkulkinRaids.factory(minejago$INSTANCE), SkulkinRaids.getFileId());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        minejago$getSkulkinRaids().tick();
    }

    @Override
    public SkulkinRaids minejago$getSkulkinRaids() {
        return minejago$skulkinRaids;
    }

    @Inject(method = "lambda$wakeUpAllPlayers$3", at = @At("TAIL"))
    private static void lambda$wakeUpAllPlayers$7(ServerPlayer serverPlayer, CallbackInfo ci) {
        serverPlayer.getData(MinejagoAttachmentTypes.FOCUS).meditate(false, 2, FocusConstants.FOCUS_SATURATION_LOW);
    }

    @ModifyExpressionValue(method = "tickTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean tickTime(boolean original) {
        if (minejago$getSkulkinRaids().hasAnyRaidsActive())
            return false;
        return original;
    }
}
