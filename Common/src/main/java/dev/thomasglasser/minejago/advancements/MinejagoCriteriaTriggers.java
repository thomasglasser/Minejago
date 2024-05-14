package dev.thomasglasser.minejago.advancements;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.BrewedTeaTrigger;
import dev.thomasglasser.minejago.advancements.criterion.DidSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GotPowerTrigger;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class MinejagoCriteriaTriggers
{
    public static final DeferredRegister<CriterionTrigger<?>> CRITERION_TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, Minejago.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, DidSpinjitzuTrigger> DID_SPINJITZU = register("do_spinjitzu", DidSpinjitzuTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, GotPowerTrigger> GOT_POWER = register("get_power", GotPowerTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, BrewedTeaTrigger> BREWED_TEA = register("brewed_tea", BrewedTeaTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, SkulkinRaidTrigger> SKULKIN_RAID_STATUS_CHANGED = register("skulkin_raid_status_changed", SkulkinRaidTrigger::new);
    
    private static <T extends CriterionTrigger<?>> DeferredHolder<CriterionTrigger<?>, T> register(String name, Supplier<T> trigger)
    {
        return CRITERION_TRIGGERS.register(name, trigger);
    }

    public static void init() {}
}
