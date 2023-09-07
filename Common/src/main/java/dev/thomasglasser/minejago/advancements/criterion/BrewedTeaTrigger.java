package dev.thomasglasser.minejago.advancements.criterion;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.alchemy.Potion;
import org.jetbrains.annotations.Nullable;

public class BrewedTeaTrigger extends SimpleCriterionTrigger<BrewedTeaTrigger.TriggerInstance>
{
    static final ResourceLocation ID = Minejago.modLoc("brewed_tea");

    public BrewedTeaTrigger() {
    }

    public ResourceLocation getId() {
        return ID;
    }

    public BrewedTeaTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        Potion potion = null;
        if (json.has("potion")) {
            ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(json, "potion"));
            potion = BuiltInRegistries.POTION.getOptional(resourceLocation).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + resourceLocation + "'"));
        }

        return new BrewedTeaTrigger.TriggerInstance(predicate, potion);
    }

    public void trigger(ServerPlayer player, Potion potion) {
        this.trigger(player, (triggerInstance) -> triggerInstance.matches(potion));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Potion potion;

        public TriggerInstance(ContextAwarePredicate contextAwarePredicate, @Nullable Potion potion) {
            super(BrewedTeaTrigger.ID, contextAwarePredicate);
            this.potion = potion;
        }

        public static BrewedTeaTrigger.TriggerInstance brewedTea() {
            return new BrewedTeaTrigger.TriggerInstance(ContextAwarePredicate.ANY, null);
        }

        public boolean matches(Potion potion) {
            return this.potion == null || this.potion == potion;
        }

        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            if (this.potion != null) {
                jsonObject.addProperty("potion", BuiltInRegistries.POTION.getKey(this.potion).toString());
            }

            return jsonObject;
        }
    }
}
