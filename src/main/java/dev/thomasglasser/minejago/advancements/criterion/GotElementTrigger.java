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

public class GotElementTrigger extends SimpleCriterionTrigger<GotElementTrigger.TriggerInstance> {
    public GotElementTrigger() {}

    public Codec<GotElementTrigger.TriggerInstance> codec() {
        return GotElementTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer serverPlayer, ResourceKey<Element> element) {
        this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(element));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceKey<Element>> element) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<GotElementTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(GotElementTrigger.TriggerInstance::player),
                ResourceKey.codec(MinejagoRegistries.ELEMENT).optionalFieldOf("element").forGetter(GotElementTrigger.TriggerInstance::element))
                .apply(instance, GotElementTrigger.TriggerInstance::new));

        public static Criterion<GotElementTrigger.TriggerInstance> gotElement() {
            return MinejagoCriteriaTriggers.GOT_ELEMENT.get().createCriterion(new GotElementTrigger.TriggerInstance(Optional.empty(), Optional.empty()));
        }

        public boolean matches(ResourceKey<Element> other) {
            return this.element.isEmpty() || this.element.get() == other;
        }
    }
}
