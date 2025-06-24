package dev.thomasglasser.minejago.advancements;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.BrewedTeaTrigger;
import dev.thomasglasser.minejago.advancements.criterion.DidSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.IncreasedSkillTrigger;
import dev.thomasglasser.minejago.advancements.criterion.ReceivedElementTrigger;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;

public class MinejagoCriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> CRITERION_TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Minejago.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, BrewedTeaTrigger> BREWED_TEA = CRITERION_TRIGGERS.register("brewed_tea", BrewedTeaTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, DidSpinjitzuTrigger> DID_SPINJITZU = CRITERION_TRIGGERS.register("did_spinjitzu", DidSpinjitzuTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, IncreasedSkillTrigger> INCREASED_SKILL = CRITERION_TRIGGERS.register("increased_skill", IncreasedSkillTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, ReceivedElementTrigger> RECEIVED_ELEMENT = CRITERION_TRIGGERS.register("received_element", ReceivedElementTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, SkulkinRaidTrigger> STARTED_SKULKIN_RAID = CRITERION_TRIGGERS.register("started_skulkin_raid", SkulkinRaidTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, SkulkinRaidTrigger> WON_SKULKIN_RAID = CRITERION_TRIGGERS.register("won_skulkin_raid", SkulkinRaidTrigger::new);

    public static void init() {}
}
