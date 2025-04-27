package dev.thomasglasser.minejago.world.level.storage;

import dev.thomasglasser.minejago.world.entity.element.tornadoofcreation.TornadoOfCreationConfig;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import java.util.List;
import java.util.Set;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.world.entity.Entity;

public record TornadoOfCreationData(Set<Entity> originalParticipants, List<TornadoOfCreationConfig.StructureSelectionEntry> structures, Object2IntArrayMap<BlockPredicate> blocksRemaining, Object2IntArrayMap<EntityPredicate> entitiesRemaining) {
    public static TornadoOfCreationData create(Set<Entity> originalParticipants, TornadoOfCreationConfig config) {
        Object2IntArrayMap<BlockPredicate> blocksRemaining = new Object2IntArrayMap<>();
        for (TornadoOfCreationConfig.BlockRequirement blockRequirement : config.requiredBlocks()) {
            blocksRemaining.put(blockRequirement.predicate(), blockRequirement.count());
        }
        Object2IntArrayMap<EntityPredicate> entitiesRemaining = new Object2IntArrayMap<>();
        for (TornadoOfCreationConfig.EntityRequirement entityRequirement : config.requiredEntities()) {
            entitiesRemaining.put(entityRequirement.predicate(), entityRequirement.count());
        }
        return new TornadoOfCreationData(originalParticipants, config.creations(), blocksRemaining, entitiesRemaining);
    }

    public void absorbedBlock(BlockPredicate predicate) {
        if (blocksRemaining.containsKey(predicate)) {
            int left = blocksRemaining.getInt(predicate) - 1;
            if (left <= 0) {
                blocksRemaining.removeInt(predicate);
            } else {
                blocksRemaining.put(predicate, left);
            }
        }
    }

    public void absorbedEntity(EntityPredicate predicate) {
        if (entitiesRemaining.containsKey(predicate)) {
            int left = entitiesRemaining.getInt(predicate) - 1;
            if (left <= 0) {
                entitiesRemaining.removeInt(predicate);
            } else {
                entitiesRemaining.put(predicate, left);
            }
        }
    }
}
