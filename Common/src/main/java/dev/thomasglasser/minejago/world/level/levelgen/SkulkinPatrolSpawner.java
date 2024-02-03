package dev.thomasglasser.minejago.world.level.levelgen;

import dev.thomasglasser.minejago.tags.MinejagoBiomeTags;
import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.world.level.MinejagoLevelUtils;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.MeleeCompatibleSkeletonRaider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class SkulkinPatrolSpawner implements CustomSpawner
{
	private int nextTick;

	@Override
	public int tick(ServerLevel level, boolean spawnEnemies, boolean spawnFriendlies) {
		if (!spawnEnemies) {
			return 0;
		} else if (!MinejagoServerConfig.enableSkulkinRaids) {
			return 0;
		} else {
			RandomSource randomSource = level.random;
			--this.nextTick;
			if (this.nextTick > 0) {
				return 0;
			} else {
				this.nextTick += 12000 + randomSource.nextInt(1200);
				long l = level.getDayTime() / 24000L;
				if (l < 5L || level.isDay()) {
					return 0;
				} else if (randomSource.nextInt(5) != 0) {
					return 0;
				} else {
					int i = level.players().size();
					if (i < 1) {
						return 0;
					} else {
						Player player = level.players().get(randomSource.nextInt(i));
						if (player.isSpectator()) {
							return 0;
						} else if (MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(player, 32)) {
							return 0;
						} else {
							int j = (24 + randomSource.nextInt(24)) * (randomSource.nextBoolean() ? -1 : 1);
							int k = (24 + randomSource.nextInt(24)) * (randomSource.nextBoolean() ? -1 : 1);
							BlockPos.MutableBlockPos mutableBlockPos = player.blockPosition().mutable().move(j, 0, k);
							int m = 10;
							if (!level.hasChunksAt(mutableBlockPos.getX() - 10, mutableBlockPos.getZ() - 10, mutableBlockPos.getX() + 10, mutableBlockPos.getZ() + 10)) {
								return 0;
							} else {
								Holder<Biome> holder = level.getBiome(mutableBlockPos);
								if (holder.is(MinejagoBiomeTags.WITHOUT_SKULKIN_PATROL_SPAWNS)) {
									return 0;
								} else {
									int n = 0;
									int o = (int)Math.ceil(level.getCurrentDifficultyAt(mutableBlockPos).getEffectiveDifficulty()) + 1;

									for(int p = 0; p < o; ++p) {
										++n;
										mutableBlockPos.setY(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutableBlockPos).getY());
										if (p == 0) {
											if (!this.spawnPatrolMember(level, mutableBlockPos, randomSource, true)) {
												break;
											}
										} else {
											this.spawnPatrolMember(level, mutableBlockPos, randomSource, false);
										}

										mutableBlockPos.setX(mutableBlockPos.getX() + randomSource.nextInt(5) - randomSource.nextInt(5));
										mutableBlockPos.setZ(mutableBlockPos.getZ() + randomSource.nextInt(5) - randomSource.nextInt(5));
									}

									return n;
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean spawnPatrolMember(ServerLevel level, BlockPos pos, RandomSource random, boolean leader) {
		BlockState blockState = level.getBlockState(pos);
		if (!NaturalSpawner.isValidEmptySpawnBlock(level, pos, blockState, blockState.getFluidState(), MinejagoEntityTypes.SKULKIN.get())) {
			return false;
		} else if (!MeleeCompatibleSkeletonRaider.checkSpawnRules(MinejagoEntityTypes.SKULKIN.get(), level, MobSpawnType.PATROL, pos, random)) {
			return false;
		} else {
			MeleeCompatibleSkeletonRaider raider = MinejagoEntityTypes.SKULKIN.get().create(level);
			if (raider != null) {
				if (leader) {
					raider.setPatrolLeader(true);
					raider.findPatrolTarget();
				}

				raider.setPos((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
				raider.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.PATROL, null, null);
				level.addFreshEntityWithPassengers(raider);
				return true;
			} else {
				return false;
			}
		}
	}
}
