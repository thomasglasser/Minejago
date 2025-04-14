package dev.thomasglasser.minejago.advancements;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.BrewedTeaTrigger;
import dev.thomasglasser.minejago.advancements.criterion.DidSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GotElementTrigger;
import dev.thomasglasser.minejago.advancements.criterion.SkillIncreasedTrigger;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import java.util.function.Supplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;

public class MinejagoCriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> CRITERION_TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Minejago.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, DidSpinjitzuTrigger> DID_SPINJITZU = register("did_spinjitzu", DidSpinjitzuTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, GotElementTrigger> GOT_ELEMENT = register("got_element", GotElementTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, BrewedTeaTrigger> BREWED_TEA = register("brewed_tea", BrewedTeaTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, SkulkinRaidTrigger> SKULKIN_RAID_STATUS_CHANGED = register("skulkin_raid_status_changed", SkulkinRaidTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, SkillIncreasedTrigger> INCREASED_SKILL = register("increased_skill", SkillIncreasedTrigger::new);

    private static <T extends CriterionTrigger<?>> DeferredHolder<CriterionTrigger<?>, T> register(String name, Supplier<T> trigger) {
        return CRITERION_TRIGGERS.register(name, trigger);
    }

    public static void init() {}
}
