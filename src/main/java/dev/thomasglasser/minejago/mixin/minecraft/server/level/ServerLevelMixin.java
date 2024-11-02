package dev.thomasglasser.minejago.mixin.minecraft.server.level;

import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaids;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.level.levelgen.SkulkinArmySpawner;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements SkulkinRaidsHolder {
    @Unique
    private final ServerLevel minejago$INSTANCE = ((ServerLevel) (Object) (this));

    @Unique
    protected SkulkinRaids minejago$skulkinRaids;

    @Mutable
    @Final
    @Shadow
    private List<CustomSpawner> customSpawners;

    protected ServerLevelMixin(WritableLevelData p_270739_, ResourceKey<Level> p_270683_, RegistryAccess p_270200_, Holder<DimensionType> p_270240_, boolean p_270904_, boolean p_270470_, long p_270248_, int p_270466_) {
        super(p_270739_, p_270683_, p_270200_, p_270240_, p_270904_, p_270470_, p_270248_, p_270466_);
    }

    @Shadow
    public abstract DimensionDataStorage getDataStorage();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void minejago_init(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey resourceKey, LevelStem levelStem, ChunkProgressListener chunkProgressListener, boolean bl, long l, List list, boolean bl2, RandomSequences randomSequences, CallbackInfo ci) {
        minejago$skulkinRaids = this.getDataStorage().computeIfAbsent(SkulkinRaids.factory(minejago$INSTANCE), SkulkinRaids.getFileId());
        List<CustomSpawner> spawners = customSpawners;
        customSpawners = new ArrayList<>();
        customSpawners.addAll(spawners);
        customSpawners.add(new SkulkinArmySpawner());
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void minejago_tick(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        getSkulkinRaids().tick();
    }

    @Override
    public SkulkinRaids getSkulkinRaids() {
        return minejago$skulkinRaids;
    }

    @Inject(method = "lambda$wakeUpAllPlayers$3", at = @At("TAIL"))
    private static void minejago_lambda$wakeUpAllPlayers$7(ServerPlayer serverPlayer, CallbackInfo ci) {
        serverPlayer.getData(MinejagoAttachmentTypes.FOCUS).meditate(false, 2, FocusConstants.FOCUS_SATURATION_LOW);
    }
}
