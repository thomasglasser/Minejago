package dev.thomasglasser.minejago.mixin.minecraft.server.level;

import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaids;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.level.levelgen.SkulkinPatrolSpawner;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements SkulkinRaidsHolder
{
	@Unique
	private final ServerLevel INSTANCE = ((ServerLevel)(Object)(this));

	@Unique
	protected SkulkinRaids skulkinRaids;

	@Mutable
	@Final
	@Shadow
	private List<CustomSpawner> customSpawners;

	private ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, RegistryAccess registryAccess, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i)
	{
		super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
	}

	@Shadow public abstract DimensionDataStorage getDataStorage();

	@Inject(method = "<init>", at = @At("RETURN"))
	private void minejago_init(MinecraftServer minecraftServer, Executor executor, LevelStorageSource.LevelStorageAccess levelStorageAccess, ServerLevelData serverLevelData, ResourceKey resourceKey, LevelStem levelStem, ChunkProgressListener chunkProgressListener, boolean bl, long l, List list, boolean bl2, RandomSequences randomSequences, CallbackInfo ci)
	{
		skulkinRaids = getDataStorage()
				.computeIfAbsent(compoundTag -> SkulkinRaids.load(INSTANCE, compoundTag), () -> new SkulkinRaids(INSTANCE), SkulkinRaids.getFileId(dimensionTypeRegistration()));
		List<CustomSpawner> spawners = customSpawners;
		customSpawners = new ArrayList<>();
		customSpawners.addAll(spawners);
		customSpawners.add(new SkulkinPatrolSpawner());
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void minejago_tick(BooleanSupplier hasTimeLeft, CallbackInfo ci)
	{
		getSkulkinRaids().tick();
	}

	@Override
	public SkulkinRaids getSkulkinRaids()
	{
		return skulkinRaids;
	}
}
