package dev.thomasglasser.minejago.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class MinejagoSoundEvents
{
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(BuiltInRegistries.SOUND_EVENT, Minejago.MOD_ID);

    // Teapot
    public static final RegistryObject<SoundEvent> TEAPOT_WHISTLE = register("teapot", "block", "whistle");

    // Spinjitzu
    public static final RegistryObject<SoundEvent> SPINJITZU_START = register("spinjitzu", "start");
    public static final RegistryObject<SoundEvent> SPINJITZU_ACTIVE = register("spinjitzu", "active");
    public static final RegistryObject<SoundEvent> SPINJITZU_STOP = register("spinjitzu", "stop");

    // Scythe of Quakes
    public static final RegistryObject<SoundEvent> SCYTHE_OF_QUAKES_FAIL = register("scythe_of_quakes", "item", "fail");
    public static final RegistryObject<SoundEvent> SCYTHE_OF_QUAKES_CASCADE = register("scythe_of_quakes", "item", "cascade");
    public static final RegistryObject<SoundEvent> SCYTHE_OF_QUAKES_EXPLOSION = register("scythe_of_quakes", "item", "explosion");
    public static final RegistryObject<SoundEvent> SCYTHE_OF_QUAKES_PATH = register("scythe_of_quakes", "item", "path");

    // Bamboo Staff
    public static final RegistryObject<SoundEvent> BAMBOO_STAFF_IMPACT = register("bamboo_staff", "item", "impact");
    public static final RegistryObject<SoundEvent> BAMBOO_STAFF_THROW = register("bamboo_staff", "item", "throw");

    // Bone Knife
    public static final RegistryObject<SoundEvent> BONE_KNIFE_IMPACT = register("bone_knife", "item", "impact");
    public static final RegistryObject<SoundEvent> BONE_KNIFE_THROW = register("bone_knife", "item", "throw");

    // Shuriken
    public static final RegistryObject<SoundEvent> SHURIKEN_IMPACT = register("shuriken", "item", "impact");
    public static final RegistryObject<SoundEvent> SHURIKEN_THROW = register("shuriken", "item", "throw");

    // Spear
    public static final RegistryObject<SoundEvent> SPEAR_IMPACT = register("spear", "item", "impact");
    public static final RegistryObject<SoundEvent> SPEAR_THROW = register("spear", "item", "throw");

    // Earth Dragon
    public static final RegistryObject<SoundEvent> EARTH_DRAGON_AMBIENT = register("earth_dragon", "entity", "ambient");
    public static final RegistryObject<SoundEvent> EARTH_DRAGON_AWAKEN = register("earth_dragon", "entity", "awaken");
    public static final RegistryObject<SoundEvent> EARTH_DRAGON_DEATH = register("earth_dragon", "entity", "death");
    public static final RegistryObject<SoundEvent> EARTH_DRAGON_FLAP = register("earth_dragon", "entity", "flap");
    public static final RegistryObject<SoundEvent> EARTH_DRAGON_HURT = register("earth_dragon", "entity", "hurt");
    public static final RegistryObject<SoundEvent> EARTH_DRAGON_ROAR = register("earth_dragon", "entity", "roar");
    public static final RegistryObject<SoundEvent> EARTH_DRAGON_STEP = register("earth_dragon", "entity", "step");

    private static RegistryObject<SoundEvent> register(String subject, String type, String name)
    {
        return SOUND_EVENTS.register(subject + "_" + name, () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc(type + "." + subject + "." + name)));
    }

    private static RegistryObject<SoundEvent> register(String subject, String name)
    {
        return SOUND_EVENTS.register(subject + "_" + name, () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc(subject + "." + name)));
    }

    public static void init() {}
}
