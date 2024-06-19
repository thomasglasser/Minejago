package dev.thomasglasser.minejago.world.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.MeleeCompatibleSkeletonRaider;
import dev.thomasglasser.minejago.world.entity.skulkin.raid.SkulkinRaid;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class ObtainNearbyRaidBanner<T extends MeleeCompatibleSkeletonRaider> extends ExtendedBehaviour<T>
{
	private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = List.of(Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));

	private final Predicate<ItemEntity> entityPredicate;
	private final BiPredicate<ItemStack, HolderLookup<BannerPattern>> stackPredicate;

	public ObtainNearbyRaidBanner(Predicate<ItemEntity> entityPredicate, BiPredicate<ItemStack, HolderLookup<BannerPattern>> stackPredicate)
	{
		this.entityPredicate = entityPredicate;
		this.stackPredicate = stackPredicate;
	}
	
	@Override
	protected boolean checkExtraStartConditions(ServerLevel level, T entity)
	{
		SkulkinRaid raid = entity.getCurrentSkulkinRaid();
		if (entity.hasActiveSkulkinRaid()
				&& !raid.isOver()
				&& entity.canBeLeader()
				&& !stackPredicate.test(entity.getItemBySlot(EquipmentSlot.HEAD), entity.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN))) {
			MeleeCompatibleSkeletonRaider raider = raid.getLeader(entity.getWave());
			if (raider == null || !raider.isAlive()) {
				List<ItemEntity> list = entity.level().getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(16.0, 8.0, 16.0), entityPredicate);
				return !list.isEmpty();
			}

			return false;
		} else {
			return false;
		}
	}

	@Override
	protected void start(T entity)
	{
		super.start(entity);
		List<ItemEntity> list = entity.level().getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(16.0, 8.0, 16.0), entityPredicate);
		BrainUtils.setMemory(entity, MemoryModuleType.WALK_TARGET, new WalkTarget(list.get(0), 1.15f, 0));
	}

	@Override
	protected boolean shouldKeepRunning(T entity)
	{
		return entity.hasActiveSkulkinRaid() && entity.getCurrentSkulkinRaid().getLeader(entity.getWave()) == null;
	}

	@Override
	protected void tick(T entity)
	{
		super.tick(entity);
		if (BrainUtils.hasMemory(entity, MemoryModuleType.WALK_TARGET) && BrainUtils.getMemory(entity, MemoryModuleType.WALK_TARGET).getTarget().currentBlockPosition().closerToCenterThan(entity.position(), 1.5))
		{
			List<ItemEntity> list = entity.level().getEntitiesOfClass(ItemEntity.class, entity.getBoundingBox().inflate(4.0, 4.0, 4.0), entityPredicate);
			if (!list.isEmpty())
			{
				entity.pickUpItem(list.get(0));
			}
		}
	}

	@Override
	protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements()
	{
		return MEMORY_REQUIREMENTS;
	}
}
