package dev.thomasglasser.minejago.advancements.criterion;

import com.google.gson.JsonObject;
import dev.thomasglasser.minejago.advancements.MinejagoCriteriaTriggers;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SkulkinRaidTrigger extends SimpleCriterionTrigger<SkulkinRaidTrigger.TriggerInstance>
{
	final ResourceLocation id;

	public SkulkinRaidTrigger(ResourceLocation resourceLocation) {
		this.id = resourceLocation;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
		return new TriggerInstance(this.id, predicate);
	}

	public void trigger(ServerPlayer player) {
		this.trigger(player, triggerInstance -> true);
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		public TriggerInstance(ResourceLocation resourceLocation, ContextAwarePredicate contextAwarePredicate) {
			super(resourceLocation, contextAwarePredicate);
		}

		public static TriggerInstance raidStarted() {
			return new TriggerInstance(MinejagoCriteriaTriggers.SKULKIN_RAID_STARTED.id, ContextAwarePredicate.ANY);
		}

		public static TriggerInstance raidWon() {
			return new TriggerInstance(MinejagoCriteriaTriggers.SKULKIN_RAID_WON.id, ContextAwarePredicate.ANY);
		}
	}
}