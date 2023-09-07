package dev.thomasglasser.minejago.advancements.criterion;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.power.Power;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

public class GetPowerTrigger extends SimpleCriterionTrigger<GetPowerTrigger.TriggerInstance> {
    static final ResourceLocation ID = Minejago.modLoc("get_power");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(predicate, PowerPredicate.fromJson(json.get("power")));
    }

    public void trigger(ServerPlayer player, ResourceKey<Power> key, Registry<Power> registry) {
        this.trigger(player, triggerInstance -> triggerInstance.matches(key, registry));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final PowerPredicate power;

        public TriggerInstance(ContextAwarePredicate contextAwarePredicate, PowerPredicate powerPredicate) {
            super(ID, contextAwarePredicate);
            power = powerPredicate;
        }

        public static TriggerInstance gotPower(ResourceKey<Power> key) {
            return new TriggerInstance(ContextAwarePredicate.ANY, new PowerPredicate(ImmutableSet.of(key), null));
        }

        public static TriggerInstance gotPower(TagKey<Power> key) {
            return new TriggerInstance(ContextAwarePredicate.ANY, new PowerPredicate(null, key));
        }

        public static TriggerInstance gotAnyPower()
        {
            return new TriggerInstance(ContextAwarePredicate.ANY, PowerPredicate.ANY);
        }

        public boolean matches(ResourceKey<Power> key, Registry<Power> registry) {
            return this.power.matches(key, registry);
        }

        public @NotNull JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            jsonObject.add("power", this.power.serializeToJson());
            return jsonObject;
        }
    }
}