package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.MeleeCompatibleSkeletonRaider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class LongDistancePatrol<T extends MeleeCompatibleSkeletonRaider> extends ExtendedBehaviour<T>
{
	private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED));

	protected float leaderSpeedModifier = 1;
	protected float speedModifier = 1;
	private long cooldownUntil;

	public LongDistancePatrol()
	{
		cooldownProvider = entity -> 200;
	}

	public LongDistancePatrol<T> speedModifier(float speedMod)
	{
		this.speedModifier = speedMod;

		return this;
	}

	public LongDistancePatrol<T> leaderSpeedModifier(float speedMod)
	{
		this.leaderSpeedModifier = speedMod;

		return this;
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, T entity)
	{
		boolean bl = entity.level().getGameTime() < this.cooldownUntil;
		return entity.isPatrolling() && entity.getTarget() == null && !entity.isVehicle() && entity.hasPatrolTarget() && !bl && super.checkExtraStartConditions(level, entity);
	}

	@Override
	protected void tick(T entity)
	{
		boolean bl = entity.isPatrolLeader();
		if (!BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
		{
			List<PatrollingMonster> list = this.findPatrolCompanions(entity);
			if (entity.isPatrolling() && list.isEmpty())
			{
				entity.setPatrolling(false);
			}
			else if (bl && entity.getPatrolTarget().closerToCenterThan(entity.position(), 10.0))
			{
				entity.findPatrolTarget();
			}
			else
			{
				Vec3 vec3 = Vec3.atBottomCenterOf(entity.getPatrolTarget());
				Vec3 vec32 = entity.position();
				Vec3 vec33 = vec32.subtract(vec3);
				vec3 = vec33.yRot(90.0F).scale(0.4).add(vec3);
				Vec3 vec34 = vec3.subtract(vec32).normalize().scale(10.0).add(vec32);
				BlockPos blockPos = BlockPos.containing(vec34);
				blockPos = entity.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockPos);
				BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(blockPos, bl ? leaderSpeedModifier : speedModifier, 1));
				if (BrainUtils.hasMemory(entity, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE))
				{
					this.moveRandomly(entity);
					this.cooldownUntil = entity.level().getGameTime() + cooldownProvider.apply(entity);
				}
				else if (bl)
				{
					for (PatrollingMonster patrollingMonster : list)
					{
						patrollingMonster.setPatrolTarget(blockPos);
					}
				}
			}
		}
	}

	@Override
	protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements()
	{
		return MEMORY_REQUIREMENTS;
	}

	private List<PatrollingMonster> findPatrolCompanions(T entity)
	{
		return entity.level().getEntitiesOfClass(PatrollingMonster.class, entity.getBoundingBox().inflate(16.0), patrollingMonster -> patrollingMonster.canJoinPatrol() && !patrollingMonster.is(entity));
	}

	private void moveRandomly(T entity)
	{
		RandomSource randomSource = entity.getRandom();
		BlockPos blockPos = entity.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, entity.blockPosition().offset(-8 + randomSource.nextInt(16), 0, -8 + randomSource.nextInt(16)));
		BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(blockPos, speedModifier, 1));
	}
}