package dev.thomasglasser.minejago.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

public class IncreasedSkillTrigger extends SimpleCriterionTrigger<IncreasedSkillTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Skill skill, int level) {
        this.trigger(player, instance -> instance.matches(skill, level));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<Skill> skill, Optional<Integer> level) implements SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                Codec.STRING.xmap(Skill::valueOf, Skill::name).optionalFieldOf("type").forGetter(TriggerInstance::skill),
                ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("level").forGetter(TriggerInstance::level)).apply(instance, TriggerInstance::new));
        public static Criterion<TriggerInstance> increasedSkill() {
            return increasedSkill(Optional.empty(), Optional.empty(), Optional.empty());
        }

        public static Criterion<TriggerInstance> increasedSkill(Skill skill) {
            return increasedSkill(Optional.empty(), Optional.of(skill), Optional.empty());
        }

        public static Criterion<TriggerInstance> increasedSkill(Skill skill, int level) {
            return increasedSkill(Optional.empty(), Optional.of(skill), Optional.of(level));
        }

        public static Criterion<IncreasedSkillTrigger.TriggerInstance> increasedSkill(Optional<ContextAwarePredicate> player, Optional<Skill> skill, Optional<Integer> level) {
            return MinejagoCriteriaTriggers.INCREASED_SKILL.get().createCriterion(new TriggerInstance(player, skill, level));
        }

        public boolean matches(Skill skill, int level) {
            return this.skill.map(s -> s == skill).orElse(true) && this.level.map(l -> l == level).orElse(true);
        }
    }
}
