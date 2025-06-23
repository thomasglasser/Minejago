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

public class SkulkinRaidTrigger extends SimpleCriterionTrigger<SkulkinRaidTrigger.TriggerInstance> {
    public Codec<SkulkinRaidTrigger.TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Predicates.alwaysTrue());
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player))
                .apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> raidStarted() {
            return raidStarted(Optional.empty());
        }

        public static Criterion<TriggerInstance> raidWon() {
            return raidWon(Optional.empty());
        }

        public static Criterion<TriggerInstance> raidStarted(Optional<ContextAwarePredicate> player) {
            return MinejagoCriteriaTriggers.STARTED_SKULKIN_RAID.get().createCriterion(new TriggerInstance(player));
        }

        public static Criterion<TriggerInstance> raidWon(Optional<ContextAwarePredicate> player) {
            return MinejagoCriteriaTriggers.WON_SKULKIN_RAID.get().createCriterion(new TriggerInstance(player));
        }
    }
}
