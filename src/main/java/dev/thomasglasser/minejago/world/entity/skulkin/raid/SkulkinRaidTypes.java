package dev.thomasglasser.minejago.world.entity.skulkin.raid;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;

public class SkulkinRaidTypes {
    private static final DeferredRegister<SkulkinRaidType> SKULKIN_RAID_TYPES = DeferredRegister.create(MinejagoRegistries.SKULKIN_RAID_TYPES, Minejago.MOD_ID);

    public static final DeferredHolder<SkulkinRaidType, SkulkinRaidType> FOUR_WEAPONS = SKULKIN_RAID_TYPES.register("four_weapons", () -> new SkulkinRaidType(FourWeaponsSkulkinRaid::new, FourWeaponsSkulkinRaid::new, FourWeaponsSkulkinRaid::findValidRaidCenter));
    public static final DeferredHolder<SkulkinRaidType, SkulkinRaidType> GOLDEN_WEAPONS = SKULKIN_RAID_TYPES.register("golden_weapons", () -> new SkulkinRaidType(GoldenWeaponSkulkinRaid::new, GoldenWeaponSkulkinRaid::new, GoldenWeaponSkulkinRaid::findValidRaidCenter));

    public static void init() {}
}
