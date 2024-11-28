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

public class SkillIncreasedTrigger extends SimpleCriterionTrigger<SkillIncreasedTrigger.TriggerInstance> {
    @Override
    public Codec<TriggerInstance> codec() {
        return SkillIncreasedTrigger.TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Skill type, int level) {
        this.trigger(player, (instance) -> instance.matches(type, level));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Skill type, int level)
            implements SimpleInstance {

        public static final Codec<SkillIncreasedTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                p_337367_ -> p_337367_.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SkillIncreasedTrigger.TriggerInstance::player),
                        Codec.STRING.xmap(Skill::valueOf, Skill::name).fieldOf("type").forGetter(SkillIncreasedTrigger.TriggerInstance::type),
                        Codec.INT.fieldOf("level").forGetter(SkillIncreasedTrigger.TriggerInstance::level)).apply(p_337367_, SkillIncreasedTrigger.TriggerInstance::new));
        public static Criterion<SkillIncreasedTrigger.TriggerInstance> increasedSkill(Skill type, int level) {
            return MinejagoCriteriaTriggers.INCREASED_SKILL.get().createCriterion(new TriggerInstance(Optional.empty(), type, level));
        }

        public boolean matches(Skill type, int level) {
            return this.type == type && this.level == level;
        }
    }
}
