package dev.thomasglasser.minejago.advancements;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.BrewedTeaTrigger;
import dev.thomasglasser.minejago.advancements.criterion.DidSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GotPowerTrigger;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class MinejagoCriteriaTriggers
{
    public static final RegistrationProvider<CriterionTrigger<?>> CRITERION_TRIGGERS = RegistrationProvider.get(BuiltInRegistries.TRIGGER_TYPES, Minejago.MOD_ID);

    public static final RegistryObject<DidSpinjitzuTrigger> DID_SPINJITZU = register("do_spinjitzu", DidSpinjitzuTrigger::new);
    public static final RegistryObject<GotPowerTrigger> GOT_POWER = register("get_power", GotPowerTrigger::new);
    public static final RegistryObject<BrewedTeaTrigger> BREWED_TEA = register("brewed_tea", BrewedTeaTrigger::new);
    public static final RegistryObject<SkulkinRaidTrigger> SKULKIN_RAID_STATUS_CHANGED = register("skulkin_raid_status_changed", SkulkinRaidTrigger::new);
    
    private static <T extends CriterionTrigger<?>> RegistryObject<T> register(String name, Supplier<T> trigger)
    {
        return CRITERION_TRIGGERS.register(name, trigger);
    }

    public static void init() {}
}
