package dev.thomasglasser.minejago.advancements;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.advancements.criterion.BrewedTeaTrigger;
import dev.thomasglasser.minejago.advancements.criterion.DoSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GetPowerTrigger;
import dev.thomasglasser.minejago.advancements.criterion.SkulkinRaidTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class MinejagoCriteriaTriggers
{
    public static final DoSpinjitzuTrigger DO_SPINJITZU = CriteriaTriggers.register(new DoSpinjitzuTrigger());
    public static final GetPowerTrigger GET_POWER = CriteriaTriggers.register(new GetPowerTrigger());
    public static final BrewedTeaTrigger BREWED_TEA = CriteriaTriggers.register(new BrewedTeaTrigger());
    public static final SkulkinRaidTrigger SKULKIN_RAID_STARTED = CriteriaTriggers.register(new SkulkinRaidTrigger(Minejago.modLoc("skulkin_raid_started")));
    public static final SkulkinRaidTrigger SKULKIN_RAID_WON = CriteriaTriggers.register(new SkulkinRaidTrigger(Minejago.modLoc("skulkin_raid_won")));

    public static void init() {}
}
