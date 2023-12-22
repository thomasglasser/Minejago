package dev.thomasglasser.minejago.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class MinejagoSoundEvents
{
    // Teapot
    public static final Supplier<SoundEvent> TEAPOT_WHISTLE = register("teapot", "block", "whistle");

    // Spinjitzu
    public static final Supplier<SoundEvent> SPINJITZU_START = register("spinjitzu", "start");
    public static final Supplier<SoundEvent> SPINJITZU_ACTIVE = register("spinjitzu", "active");
    public static final Supplier<SoundEvent> SPINJITZU_STOP = register("spinjitzu", "stop");

    // Scythe of Quakes
    public static final Supplier<SoundEvent> SCYTHE_OF_QUAKES_FAIL = register("scythe_of_quakes", "item", "fail");
    public static final Supplier<SoundEvent> SCYTHE_OF_QUAKES_CASCADE = register("scythe_of_quakes", "item", "cascade");
    public static final Supplier<SoundEvent> SCYTHE_OF_QUAKES_EXPLOSION = register("scythe_of_quakes", "item", "explosion");
    public static final Supplier<SoundEvent> SCYTHE_OF_QUAKES_PATH = register("scythe_of_quakes", "item", "path");

    // Bamboo Staff
    public static final Supplier<SoundEvent> BAMBOO_STAFF_IMPACT = register("bamboo_staff", "item", "impact");
    public static final Supplier<SoundEvent> BAMBOO_STAFF_THROW = register("bamboo_staff", "item", "throw");

    // Bone Knife
    public static final Supplier<SoundEvent> BONE_KNIFE_IMPACT = register("bone_knife", "item", "impact");
    public static final Supplier<SoundEvent> BONE_KNIFE_THROW = register("bone_knife", "item", "throw");

    // Shuriken
    public static final Supplier<SoundEvent> SHURIKEN_IMPACT = register("shuriken", "item", "impact");
    public static final Supplier<SoundEvent> SHURIKEN_THROW = register("shuriken", "item", "throw");

    // Spear
    public static final Supplier<SoundEvent> SPEAR_IMPACT = register("spear", "item", "impact");
    public static final Supplier<SoundEvent> SPEAR_THROW = register("spear", "item", "throw");

    // Earth Dragon
    public static final Supplier<SoundEvent> EARTH_DRAGON_AMBIENT = register("earth_dragon", "entity", "ambient");
    public static final Supplier<SoundEvent> EARTH_DRAGON_AWAKEN = register("earth_dragon", "entity", "awaken");
    public static final Supplier<SoundEvent> EARTH_DRAGON_DEATH = register("earth_dragon", "entity", "death");
    public static final Supplier<SoundEvent> EARTH_DRAGON_FLAP = register("earth_dragon", "entity", "flap");
    public static final Supplier<SoundEvent> EARTH_DRAGON_HURT = register("earth_dragon", "entity", "hurt");
    public static final Supplier<SoundEvent> EARTH_DRAGON_ROAR = register("earth_dragon", "entity", "roar");
    public static final Supplier<SoundEvent> EARTH_DRAGON_STEP = register("earth_dragon", "entity", "step");

    private static Supplier<SoundEvent> register(String subject, String type, String name)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.SOUND_EVENT, subject + "_" + name, () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc(type + "." + subject + "." + name)));
    }

    private static Supplier<SoundEvent> register(String subject, String name)
    {
        return Services.REGISTRATION.register(BuiltInRegistries.SOUND_EVENT, subject + "_" + name, () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc(subject + "." + name)));
    }

    public static void init() {}
}
