package dev.thomasglasser.minejago.world.entity.powers;

import net.minecraftforge.common.ForgeConfigSpec;

public class MinejagoPowersConfig
{
    public static ForgeConfigSpec.BooleanValue ALLOW_CHOOSE;
    public static ForgeConfigSpec.BooleanValue ALLOW_CHANGE;
    public static ForgeConfigSpec.BooleanValue DRAIN_POOL;
    public static ForgeConfigSpec.BooleanValue ALLOW_NONE;

    public static ForgeConfigSpec.BooleanValue REQUIRE_FOR_GOLDEN_WEAPON;
    public static ForgeConfigSpec.BooleanValue WEAPON_GOES_CRAZY;

    public static void registerServer(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Settings for powers (elemental or otherwise)").push("powers");

        ALLOW_CHOOSE = builder
                .comment("Allow players to choose the power given to them by interacting with Master Wu")
                .define("choice", false);
        ALLOW_CHANGE = builder
                .comment("Allow players to get a new power by interacting with Master Wu again")
                .define("change", false);
        ALLOW_NONE = builder
                .comment("Allow players to receive no power from Master Wu")
                .define("can_be_none", true);
        DRAIN_POOL = builder
                .comment("Remove a power from the list once given and reset when all powers have been given")
                .define("drain", true);

        REQUIRE_FOR_GOLDEN_WEAPON = builder
                .comment("Make the Golden Weapons only work properly for those with the corresponding power(s)")
                .define("require", true);

        WEAPON_GOES_CRAZY = builder
                .comment("Make the Golden Weapons react abnormally when handled by someone without the corresponding power(s)")
                .define("crazy", true);

        builder.pop();
    }
}
