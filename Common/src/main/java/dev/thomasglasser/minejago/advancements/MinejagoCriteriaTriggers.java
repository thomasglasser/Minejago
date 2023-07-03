package dev.thomasglasser.minejago.advancements;

import dev.thomasglasser.minejago.advancements.criterion.DoSpinjitzuTrigger;
import dev.thomasglasser.minejago.advancements.criterion.GetPowerTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class MinejagoCriteriaTriggers
{
    public static final DoSpinjitzuTrigger DO_SPINJITZU = CriteriaTriggers.register(new DoSpinjitzuTrigger());
    public static final GetPowerTrigger GET_POWER = CriteriaTriggers.register(new GetPowerTrigger());

    public static void init() {}
}
