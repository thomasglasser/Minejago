package dev.thomasglasser.minejago.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.element.Element;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;

public class ReceivedElementTrigger extends SimpleCriterionTrigger<ReceivedElementTrigger.TriggerInstance> {
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, ResourceKey<Element> element) {
        this.trigger(player, instance -> instance.matches(element));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceKey<Element>> element) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ResourceKey.codec(MinejagoRegistries.ELEMENT).optionalFieldOf("element").forGetter(TriggerInstance::element))
                .apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> receivedElement() {
            return receivedElement(Optional.empty(), Optional.empty());
        }

        public static Criterion<TriggerInstance> receivedElement(ResourceKey<Element> element) {
            return receivedElement(Optional.empty(), Optional.of(element));
        }

        public static Criterion<TriggerInstance> receivedElement(Optional<ContextAwarePredicate> player, Optional<ResourceKey<Element>> element) {
            return MinejagoCriteriaTriggers.RECEIVED_ELEMENT.get().createCriterion(new TriggerInstance(player, element));
        }

        public boolean matches(ResourceKey<Element> element) {
            return this.element.map(e -> e == element).orElse(true);
        }
    }
}
