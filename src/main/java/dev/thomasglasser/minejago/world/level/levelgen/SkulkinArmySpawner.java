package dev.thomasglasser.minejago.world.level.levelgen;

import dev.thomasglasser.minejago.server.MinejagoServerConfig;
import dev.thomasglasser.minejago.tags.MinejagoStructureTags;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityTypes;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaider;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

public class SkulkinArmySpawner implements CustomSpawner {
    private long time;
    private int nextTick;

    @Override
    public int tick(ServerLevel level, boolean spawnEnemies, boolean spawnFriendlies) {
        if (!spawnEnemies) {
            return 0;
        } else if (!((SkulkinRaidsHolder) level).getSkulkinRaids().isMapTaken()) {
            return 0;
        } else if (!MinejagoServerConfig.get().enableSkulkinRaids.get()) {
            return 0;
        } else {
            RandomSource randomSource = level.random;
            if (this.nextTick > 0) {
                --this.nextTick;
                return 0;
            }
            int i = level.players().size();
            if (i < 1) {
                return 0;
            } else {
                Player player = level.players().get(randomSource.nextInt(i));
                BlockPos structurePos = level.findNearestMapStructure(MinejagoStructureTags.HAS_GOLDEN_WEAPON, player.blockPosition(), 32, false);
                if (player.isSpectator()) {
                    return 0;
                } else if (structurePos == null) {
                    this.nextTick += (120 + randomSource.nextInt(120)) * 20;
                    return 0;
                } else {
                    int j = (16 + randomSource.nextInt(16)) * (randomSource.nextBoolean() ? -1 : 1);
                    int k = (16 + randomSource.nextInt(16)) * (randomSource.nextBoolean() ? -1 : 1);
                    BlockPos.MutableBlockPos mutableBlockPos = structurePos.mutable().move(j, 0, k);
                    if (!level.hasChunksAt(mutableBlockPos.getX() - 10, mutableBlockPos.getZ() - 10, mutableBlockPos.getX() + 10, mutableBlockPos.getZ() + 10)) {
                        this.nextTick += (120 + randomSource.nextInt(120)) * 20;
                        return 0;
                    }
                    mutableBlockPos.setY(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutableBlockPos).getY());
                    List<Skulkin> list = level.getEntitiesOfClass(Skulkin.class, AABB.ofSize(mutableBlockPos.getCenter(), 128, 128, 128));
                    if (list.size() >= randomSource.nextInt(30)) {
                        return 0;
                    } else {
                        int n = 0;
                        int o = (int) Math.ceil(level.getCurrentDifficultyAt(mutableBlockPos).getEffectiveDifficulty()) + 1;

                        for (int p = 0; p < o; ++p) {
                            ++n;
                            mutableBlockPos.setY(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutableBlockPos).getY());

                            this.spawnPatrolMember(level, mutableBlockPos, randomSource);

                            mutableBlockPos.setX(mutableBlockPos.getX() + randomSource.nextInt(5) - randomSource.nextInt(5));
                            mutableBlockPos.setZ(mutableBlockPos.getZ() + randomSource.nextInt(5) - randomSource.nextInt(5));
                        }

                        if (!level.isNight()) {
                            time = level.getDayTime() + 16000L;
                        } else if (time == 0) {
                            time = level.getDayTime();
                        }

                        level.setDayTime(time);

                        return n;
                    }
                }
            }
        }
    }

    private boolean spawnPatrolMember(ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState blockState = level.getBlockState(pos);
        if (!NaturalSpawner.isValidEmptySpawnBlock(level, pos, blockState, blockState.getFluidState(), MinejagoEntityTypes.SKULKIN.get())) {
            return false;
        } else if (!SkulkinRaider.checkSpawnRules(MinejagoEntityTypes.SKULKIN.get(), level, MobSpawnType.PATROL, pos, random)) {
            return false;
        } else {
            SkulkinRaider raider = MinejagoEntityTypes.SKULKIN.get().create(level);
            if (raider != null) {
                raider.setPos(pos.getX(), pos.getY(), pos.getZ());
                raider.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.PATROL, null);
                level.addFreshEntityWithPassengers(raider);
                return true;
            } else {
                return false;
            }
        }
    }
}
