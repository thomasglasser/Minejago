package dev.thomasglasser.minejago.advancements.criterion;

import com.google.common.base.Predicates;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class DidSpinjitzuTrigger extends SimpleCriterionTrigger<DidSpinjitzuTrigger.TriggerInstance> {
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Predicates.alwaysTrue());
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player))
                .apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> didSpinjitzu() {
            return didSpinjitzu(Optional.empty());
        }

        public static Criterion<TriggerInstance> didSpinjitzu(Optional<ContextAwarePredicate> player) {
            return MinejagoCriteriaTriggers.DID_SPINJITZU.get().createCriterion(new TriggerInstance(player));
        }
    }
}
