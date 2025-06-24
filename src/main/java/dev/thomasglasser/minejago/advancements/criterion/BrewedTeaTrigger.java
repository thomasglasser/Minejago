package dev.thomasglasser.minejago.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.alchemy.Potion;

public class BrewedTeaTrigger extends SimpleCriterionTrigger<BrewedTeaTrigger.TriggerInstance> {
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ResourceKey<Potion> potion) {
        this.trigger(player, instance -> instance.matches(potion));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceKey<Potion>> potion) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ResourceKey.codec(Registries.POTION).optionalFieldOf("potion").forGetter(TriggerInstance::potion))
                .apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> brewedTea() {
            return brewedTea(Optional.empty(), Optional.empty());
        }

        public static Criterion<TriggerInstance> brewedTea(ResourceKey<Potion> potion) {
            return brewedTea(Optional.empty(), Optional.of(potion));
        }

        public static Criterion<TriggerInstance> brewedTea(Optional<ContextAwarePredicate> player, Optional<ResourceKey<Potion>> potion) {
            return MinejagoCriteriaTriggers.BREWED_TEA.get().createCriterion(new TriggerInstance(player, potion));
        }

        public boolean matches(ResourceKey<Potion> potion) {
            return this.potion.map(p -> p == potion).orElse(true);
        }
    }
}
