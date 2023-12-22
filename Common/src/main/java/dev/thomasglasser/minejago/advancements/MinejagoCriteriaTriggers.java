package dev.thomasglasser.minejago.advancements;

import dev.thomasglasser.minejago.advancements.criterion.BrewedTeaTrigger;
import dev.thomasglasser.minejago.advancements.criterion.DidSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GotPowerTrigger;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class MinejagoCriteriaTriggers
{
    // TODO: Custom registration
    public static final Supplier<DidSpinjitzuTrigger> DID_SPINJITZU = register("do_spinjitzu", DidSpinjitzuTrigger::new);
    public static final Supplier<GotPowerTrigger> GOT_POWER = register("get_power", GotPowerTrigger::new);
    public static final Supplier<BrewedTeaTrigger> BREWED_TEA = register("brewed_tea", BrewedTeaTrigger::new);
    public static final Supplier<SkulkinRaidTrigger> SKULKIN_RAID_STATUS_CHANGED = register("skulkin_raid_status_changed", SkulkinRaidTrigger::new);
    
    private static <T extends CriterionTrigger<?>> Supplier<T> register(String name, Supplier<T> trigger)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.TRIGGER_TYPES, name, trigger);
    }

    public static void init() {}
}
