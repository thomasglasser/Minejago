package dev.thomasglasser.minejago.advancements.criterion;

import com.google.gson.JsonObject;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class DoSpinjitzuTrigger extends SimpleCriterionTrigger<DoSpinjitzuTrigger.TriggerInstance> {
    static final ResourceLocation ID = Minejago.modLoc("do_spinjitzu");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public DoSpinjitzuTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new DoSpinjitzuTrigger.TriggerInstance(predicate);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate contextAwarePredicate) {
            super(ID, contextAwarePredicate);
        }

        public static TriggerInstance didSpinjitzu() {
            return new TriggerInstance(
                    ContextAwarePredicate.ANY
            );
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            return jsonObject;
        }
    }
}