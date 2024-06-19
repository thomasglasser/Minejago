package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.level.MinejagoLevelUtils;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.MeleeCompatibleSkeletonRaider;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaidsHolder;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class SeekAndTakeFourWeaponsMap<T extends MeleeCompatibleSkeletonRaider> extends ExtendedBehaviour<T>
{
	private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED));

	private double speedModifier;
	private final List<BlockPos> visited = Lists.<BlockPos>newArrayList();
	private boolean stuck;

	public SeekAndTakeFourWeaponsMap<T> speedModifier(float speedMod)
	{
		this.speedModifier = speedMod;

		return this;
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, T entity)
	{
		return MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(entity, 16) && this.isValidRaid(entity) && (entity.getTarget() == null || entity.getTarget().isRemoved());
	}

	private boolean isValidRaid(T entity) {
		return entity.hasActiveSkulkinRaid() && !entity.getCurrentSkulkinRaid().isOver();
	}

	@Override
	protected boolean shouldKeepRunning(T entity)
	{
		return MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(entity, 16);
	}

	@Override
	protected void tick(ServerLevel level, T entity, long gameTime)
	{
		super.tick(level, entity, gameTime);
		if (!BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET))
		{
			if (visited.contains(entity.blockPosition()))
			{
				Vec3 toCheck;
				do
				{
					toCheck = DefaultRandomPos.getPosTowards(entity, 15, 8, Vec3.atBottomCenterOf(MinejagoLevelUtils.getGoldenWeaponsMapHolderNearby(entity, 16).blockPosition()), (float) (Math.PI / 2));
				} while (toCheck == null || visited.contains(new BlockPos((int) toCheck.x, (int) toCheck.y, (int) toCheck.z)));
				BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(toCheck, 1.0f, 0));
				entity.stopRiding();
			}
			else if (MinejagoLevelUtils.isGoldenWeaponsMapHolderNearby(entity, 1))
			{
				Painting fw = MinejagoLevelUtils.getGoldenWeaponsMapHolderNearby(entity, 1);
				CompoundTag persistentData = TommyLibServices.ENTITY.getPersistentData(fw);
				persistentData.putBoolean("MapTaken", true);
				TommyLibServices.ENTITY.setPersistentData(fw, persistentData, true);
				((SkulkinRaidsHolder)level).getSkulkinRaids().setMapTaken();
				entity.setItemSlot(EquipmentSlot.OFFHAND, MinejagoItems.EMPTY_GOLDEN_WEAPONS_MAP.get().getDefaultInstance());
				entity.setDropChance(EquipmentSlot.OFFHAND, 2.0F);
				Vec3 escapePos = null;
				while (escapePos == null)
				{
					escapePos = DefaultRandomPos.getPosAway(entity, 64, 32, Vec3.atBottomCenterOf(entity.getCurrentSkulkinRaid().getCenter()));
				}
				entity.getCurrentSkulkinRaid().setEscapePos(escapePos);
			}
			else
			{
				visited.add(entity.blockPosition());
			}
		}
	}

	@Override
	protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements()
	{
		return MEMORY_REQUIREMENTS;
	}
}
