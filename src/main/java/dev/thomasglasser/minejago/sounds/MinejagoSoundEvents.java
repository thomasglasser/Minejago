package dev.thomasglasser.minejago.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class MinejagoSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Minejago.MOD_ID);

    // Teapot
    public static final DeferredHolder<SoundEvent, SoundEvent> TEAPOT_WHISTLE = register("teapot", "block", "whistle");

    // Spinjitzu
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINJITZU_START = register("spinjitzu", "start");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINJITZU_ACTIVE = register("spinjitzu", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINJITZU_STOP = register("spinjitzu", "stop");

    // Scythe of Quakes
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_OF_QUAKES_FAIL = register("scythe_of_quakes", "item", "fail");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_OF_QUAKES_CASCADE = register("scythe_of_quakes", "item", "cascade");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_OF_QUAKES_EXPLOSION = register("scythe_of_quakes", "item", "explosion");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_OF_QUAKES_PATH = register("scythe_of_quakes", "item", "path");

    // Bamboo Staff
    public static final DeferredHolder<SoundEvent, SoundEvent> BAMBOO_STAFF_IMPACT = register("bamboo_staff", "item", "impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> BAMBOO_STAFF_THROW = register("bamboo_staff", "item", "throw");

    // Bone Knife
    public static final DeferredHolder<SoundEvent, SoundEvent> BONE_KNIFE_IMPACT = register("bone_knife", "item", "impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> BONE_KNIFE_THROW = register("bone_knife", "item", "throw");

    // Shuriken
    public static final DeferredHolder<SoundEvent, SoundEvent> SHURIKEN_IMPACT = register("shuriken", "item", "impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHURIKEN_THROW = register("shuriken", "item", "throw");

    // Spear
    public static final DeferredHolder<SoundEvent, SoundEvent> SPEAR_IMPACT = register("spear", "item", "impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPEAR_THROW = register("spear", "item", "throw");

    // Earth Dragon
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_AMBIENT = register("earth_dragon", "entity", "ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_AWAKEN = register("earth_dragon", "entity", "awaken");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_DEATH = register("earth_dragon", "entity", "death");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_FLAP = register("earth_dragon", "entity", "flap");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_HURT = register("earth_dragon", "entity", "hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_ROAR = register("earth_dragon", "entity", "roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_STEP = register("earth_dragon", "entity", "step");

    // Skull Truck
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_TRUCK_AMBIENT_ACTIVE = register("skull_truck", "entity", "ambient_active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_TRUCK_AMBIENT_IDLE = register("skull_truck", "entity", "ambient_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_TRUCK_DEATH = register("skull_truck", "entity", "death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_TRUCK_HURT = register("skull_truck", "entity", "hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_TRUCK_IGNITION = register("skull_truck", "entity", "ignition");

    // Skull Motorbike
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_MOTORBIKE_AMBIENT_ACTIVE = register("skull_motorbike", "entity", "ambient_active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_MOTORBIKE_AMBIENT_IDLE = register("skull_motorbike", "entity", "ambient_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_MOTORBIKE_DEATH = register("skull_motorbike", "entity", "death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_MOTORBIKE_HURT = register("skull_motorbike", "entity", "hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULL_MOTORBIKE_IGNITION = register("skull_motorbike", "entity", "ignition");

    // Skulkin Raid
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULKIN_RAID_HORN = register("skulkin_raid", "event", "horn");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String subject, String type, String name) {
        return SOUND_EVENTS.register(subject + "_" + name, () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc(type + "." + subject + "." + name)));
    }

    private static DeferredHolder<SoundEvent, SoundEvent> register(String subject, String name) {
        return SOUND_EVENTS.register(subject + "_" + name, () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc(subject + "." + name)));
    }

    public static void init() {}
}
