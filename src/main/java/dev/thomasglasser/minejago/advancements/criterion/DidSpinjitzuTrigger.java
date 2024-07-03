package dev.thomasglasser.minejago.advancements.criterion;

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
    public DidSpinjitzuTrigger() {}

    public Codec<DidSpinjitzuTrigger.TriggerInstance> codec() {
        return DidSpinjitzuTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer serverPlayer) {
        this.trigger(serverPlayer, triggerInstance -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<DidSpinjitzuTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DidSpinjitzuTrigger.TriggerInstance::player))
                .apply(instance, DidSpinjitzuTrigger.TriggerInstance::new));

        public static Criterion<DidSpinjitzuTrigger.TriggerInstance> didSpinjitzu() {
            return MinejagoCriteriaTriggers.DID_SPINJITZU.get().createCriterion(new DidSpinjitzuTrigger.TriggerInstance(Optional.empty()));
        }
    }
}
