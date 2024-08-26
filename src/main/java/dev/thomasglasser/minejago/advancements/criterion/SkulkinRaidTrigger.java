package dev.thomasglasser.minejago.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

public class SkulkinRaidTrigger extends SimpleCriterionTrigger<SkulkinRaidTrigger.TriggerInstance> {
    public SkulkinRaidTrigger() {}

    public Codec<SkulkinRaidTrigger.TriggerInstance> codec() {
        return SkulkinRaidTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer serverPlayer, Status status) {
        this.trigger(serverPlayer, triggerInstance -> triggerInstance.status.isPresent() && triggerInstance.status.get() == status);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Status> status) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<SkulkinRaidTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SkulkinRaidTrigger.TriggerInstance::player),
                Codec.STRING.comapFlatMap(s -> DataResult.success(Status.of(s)), Status::toString).optionalFieldOf("status").forGetter(TriggerInstance::status))
                .apply(instance, SkulkinRaidTrigger.TriggerInstance::new));

        public static Criterion<SkulkinRaidTrigger.TriggerInstance> raidStarted() {
            return MinejagoCriteriaTriggers.SKULKIN_RAID_STATUS_CHANGED.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.of(Status.STARTED)));
        }

        public static Criterion<SkulkinRaidTrigger.TriggerInstance> raidWon() {
            return MinejagoCriteriaTriggers.SKULKIN_RAID_STATUS_CHANGED.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.of(Status.WON)));
        }
    }

    public enum Status {
        STARTED,
        WON;

        public static Status of(String s) {
            return valueOf(s.toUpperCase());
        }
    }
}
