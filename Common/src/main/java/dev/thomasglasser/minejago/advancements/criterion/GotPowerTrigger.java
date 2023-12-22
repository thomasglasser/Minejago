package dev.thomasglasser.minejago.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public class GotPowerTrigger extends SimpleCriterionTrigger<GotPowerTrigger.TriggerInstance> {
    public GotPowerTrigger() {
    }

    public Codec<GotPowerTrigger.TriggerInstance> codec() {
        return GotPowerTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer serverPlayer, ResourceKey<Power> power) {
        this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(power));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ResourceKey<Power>> power) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<GotPowerTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                        ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(GotPowerTrigger.TriggerInstance::player),
                        ExtraCodecs.strictOptionalField(ResourceKey.codec(MinejagoRegistries.POWER), "power").forGetter(GotPowerTrigger.TriggerInstance::power))
                .apply(instance, GotPowerTrigger.TriggerInstance::new));

        public static Criterion<GotPowerTrigger.TriggerInstance> gotPower() {
            return MinejagoCriteriaTriggers.GOT_POWER.get().createCriterion(new GotPowerTrigger.TriggerInstance(Optional.empty(), Optional.empty()));
        }

        public boolean matches(ResourceKey<Power> otherPower) {
            return this.power.isEmpty() || this.power.get().equals(otherPower);
        }
    }
}