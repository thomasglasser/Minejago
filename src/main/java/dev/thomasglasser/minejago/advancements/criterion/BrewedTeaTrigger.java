package dev.thomasglasser.minejago.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.alchemy.Potion;

public class BrewedTeaTrigger extends SimpleCriterionTrigger<BrewedTeaTrigger.TriggerInstance> {
    public BrewedTeaTrigger() {}

    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer serverPlayer, Holder<Potion> holder) {
        this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(holder));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Holder<Potion>> potion) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                BuiltInRegistries.POTION.holderByNameCodec().optionalFieldOf("potion").forGetter(TriggerInstance::potion))
                .apply(instance, TriggerInstance::new));

        public static Criterion<TriggerInstance> brewedTea() {
            return MinejagoCriteriaTriggers.BREWED_TEA.get().createCriterion(new TriggerInstance(Optional.empty(), Optional.empty()));
        }

        public boolean matches(Holder<Potion> holder) {
            return this.potion.isEmpty() || this.potion.get().equals(holder);
        }

        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }

        public Optional<Holder<Potion>> potion() {
            return this.potion;
        }
    }
}
