package dev.thomasglasser.minejago.world.entity.element.tornadoofcreation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import java.util.List;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public record TornadoOfCreationConfig(List<StructureSelectionEntry> creations, List<BlockRequirement> requiredBlocks, List<EntityRequirement> requiredEntities) {

    public static final Codec<TornadoOfCreationConfig> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            StructureSelectionEntry.CODEC.listOf().fieldOf("creations").forGetter(TornadoOfCreationConfig::creations),
            BlockRequirement.CODEC.listOf().fieldOf("required_blocks").forGetter(TornadoOfCreationConfig::requiredBlocks),
            EntityRequirement.CODEC.listOf().fieldOf("required_entities").forGetter(TornadoOfCreationConfig::requiredEntities)).apply(instance, TornadoOfCreationConfig::new));
    public static final Codec<Holder<TornadoOfCreationConfig>> CODEC = RegistryFileCodec.create(MinejagoRegistries.TORNADO_OF_CREATION_CONFIG, DIRECT_CODEC);

    public TornadoOfCreationConfig(Holder<Structure> structure, List<BlockRequirement> requiredBlocks, List<EntityRequirement> requiredEntities) {
        this(List.of(new StructureSelectionEntry(structure, 1)), requiredBlocks, requiredEntities);
    }
    public record StructureSelectionEntry(Holder<Structure> structure, Weight weight) implements WeightedEntry {
        public static final Codec<StructureSelectionEntry> CODEC = RecordCodecBuilder.create((p_210034_) -> p_210034_.group(
                Structure.CODEC.fieldOf("structure").forGetter(StructureSelectionEntry::structure),
                Weight.CODEC.fieldOf("weight").forGetter(StructureSelectionEntry::weight)).apply(p_210034_, StructureSelectionEntry::new));

        public StructureSelectionEntry(Holder<Structure> structure, int weight) {
            this(structure, Weight.of(weight));
        }

        @Override
        public Weight getWeight() {
            return weight;
        }
    }

    public record BlockRequirement(BlockPredicate predicate, int count) {
        public static final Codec<BlockRequirement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPredicate.CODEC.fieldOf("predicate").forGetter(BlockRequirement::predicate),
                Codec.INT.fieldOf("count").forGetter(BlockRequirement::count)).apply(instance, BlockRequirement::new));

        public BlockRequirement(Block block, int count) {
            this(BlockPredicate.Builder.block().of(block).build(), count);
        }

        public BlockRequirement(TagKey<Block> tag, int count) {
            this(BlockPredicate.Builder.block().of(tag).build(), count);
        }
    }

    public record EntityRequirement(EntityPredicate predicate, int count) {
        public static final Codec<EntityRequirement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.CODEC.fieldOf("predicate").forGetter(EntityRequirement::predicate),
                Codec.INT.fieldOf("count").forGetter(EntityRequirement::count)).apply(instance, EntityRequirement::new));

        public EntityRequirement(EntityType<?> entity, int count) {
            this(EntityPredicate.Builder.entity().of(entity).build(), count);
        }

        public EntityRequirement(TagKey<EntityType<?>> tag, int count) {
            this(EntityPredicate.Builder.entity().of(tag).build(), count);
        }
    }
}
