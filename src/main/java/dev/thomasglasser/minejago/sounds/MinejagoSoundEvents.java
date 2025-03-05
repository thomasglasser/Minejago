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

    // Dragon Button
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAGON_BUTTON_OPEN = register("dragon_button", "block", "open");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAGON_BUTTON_CLICK = register("dragon_button", "block", "click");
    public static final DeferredHolder<SoundEvent, SoundEvent> DRAGON_BUTTON_CLOSE = register("dragon_button", "block", "close");

    // Enchanted Wood Set
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_BREAK = register("enchanted_wood", "block", "break");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_FALL = register("enchanted_wood", "block", "fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_HIT = register("enchanted_wood", "block", "hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_PLACE = register("enchanted_wood", "block", "place");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_STEP = register("enchanted_wood", "block", "step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_HANGING_SIGN_STEP = register("enchanted_wood_hanging_sign", "block", "step");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_HANGING_SIGN_BREAK = register("enchanted_wood_hanging_sign", "block", "break");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_HANGING_SIGN_FALL = register("enchanted_wood_hanging_sign", "block", "fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_HANGING_SIGN_HIT = register("enchanted_wood_hanging_sign", "block", "hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_HANGING_SIGN_PLACE = register("enchanted_wood_hanging_sign", "block", "place");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_DOOR_CLOSE = register("enchanted_wood_door", "block", "close");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_DOOR_OPEN = register("enchanted_wood_door", "block", "open");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_TRAPDOOR_CLOSE = register("enchanted_wood_trapdoor", "block", "close");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_TRAPDOOR_OPEN = register("enchanted_wood_trapdoor", "block", "open");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_BUTTON_CLICK_OFF = register("enchanted_wood_button", "block", "click_off");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_BUTTON_CLICK_ON = register("enchanted_wood_button", "block", "click_on");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_PRESSURE_PLATE_CLICK_OFF = register("enchanted_wood_pressure_plate", "block", "click_off");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_PRESSURE_PLATE_CLICK_ON = register("enchanted_wood_pressure_plate", "block", "click_on");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_FENCE_GATE_CLOSE = register("enchanted_wood_fence_gate", "block", "close");
    public static final DeferredHolder<SoundEvent, SoundEvent> ENCHANTED_WOOD_FENCE_GATE_OPEN = register("enchanted_wood_fence_gate", "block", "open");

    // Spinjitzu
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINJITZU_START = register("spinjitzu", "start");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINJITZU_ACTIVE = register("spinjitzu", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINJITZU_STOP = register("spinjitzu", "stop");

    // Scythe of Quakes
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_OF_QUAKES_FAIL = register("scythe_of_quakes", "item", "fail");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_OF_QUAKES_CASCADE = register("scythe_of_quakes", "item", "cascade");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_OF_QUAKES_EXPLOSION = register("scythe_of_quakes", "item", "explosion");
    public static final DeferredHolder<SoundEvent, SoundEvent> SCYTHE_OF_QUAKES_PATH = register("scythe_of_quakes", "item", "path");

    // Shuriken of Ice
    public static final DeferredHolder<SoundEvent, SoundEvent> SHURIKEN_OF_ICE_THROW = register("shuriken_of_ice", "item", "throw");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHURIKEN_OF_ICE_IMPACT = register("shuriken_of_ice", "item", "impact");

    // Bamboo Staff
    public static final DeferredHolder<SoundEvent, SoundEvent> BAMBOO_STAFF_IMPACT = register("bamboo_staff", "item", "impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> BAMBOO_STAFF_THROW = register("bamboo_staff", "item", "throw");

    // Bone Knife
    public static final DeferredHolder<SoundEvent, SoundEvent> BONE_KNIFE_IMPACT = register("bone_knife", "item", "impact");
    public static final DeferredHolder<SoundEvent, SoundEvent> BONE_KNIFE_THROW = register("bone_knife", "item", "throw");

    // Earth Dragon
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_AMBIENT = register("earth_dragon", "entity", "ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_AWAKEN = register("earth_dragon", "entity", "awaken");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_DEATH = register("earth_dragon", "entity", "death");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_FLAP = register("earth_dragon", "entity", "flap");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_HURT = register("earth_dragon", "entity", "hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_ROAR = register("earth_dragon", "entity", "roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_STEP = register("earth_dragon", "entity", "step");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_BOND_UP = register("earth_dragon", "entity", "bond_up");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_BOND_DOWN = register("earth_dragon", "entity", "bond_down");
    public static final DeferredHolder<SoundEvent, SoundEvent> EARTH_DRAGON_TAME = register("earth_dragon", "entity", "tame");

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

    // Spinjitzu Course
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINJITZU_COURSE_RISE = registerSpinjitzuCourseElement("rise");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINJITZU_COURSE_FALL = registerSpinjitzuCourseElement("fall");
    public static final DeferredHolder<SoundEvent, SoundEvent> BOUNCING_POLE_ACTIVE = registerSpinjitzuCourseElement("bouncing_pole", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> CENTER_ACTIVE = registerSpinjitzuCourseElement("center", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> ROCKING_POLE_ACTIVE = registerSpinjitzuCourseElement("rocking_pole", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINNING_AXES_ACTIVE = registerSpinjitzuCourseElement("spinning_axes", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINNING_DUMMIES_ACTIVE = registerSpinjitzuCourseElement("spinning_dummies", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINNING_DUMMIES_HIT = registerSpinjitzuCourseElement("spinning_dummies", "hit");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINNING_MACES_ACTIVE = registerSpinjitzuCourseElement("spinning_maces", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPINNING_POLE_ACTIVE = registerSpinjitzuCourseElement("spinning_pole", "active");
    public static final DeferredHolder<SoundEvent, SoundEvent> SWIRLING_KNIVES_ACTIVE = registerSpinjitzuCourseElement("swirling_knives", "active");

    // Skulkin Raid
    public static final DeferredHolder<SoundEvent, SoundEvent> SKULKIN_RAID_HORN = register("skulkin_raid", "event", "horn");

    // Armor
    public static final DeferredHolder<SoundEvent, SoundEvent> ARMOR_EQUIP_SKELETAL = register("armor", "item", "equip_skeletal");

    // Player
    public static final DeferredHolder<SoundEvent, SoundEvent> PLAYER_SKILL_LEVELUP = register("player", "entity", "skill_levelup");

    // Music
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_MONASTERY_OF_SPINJITZU = register("music", "monastery_of_spinjitzu");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_CAVE_OF_DESPAIR = register("music", "cave_of_despair");
    public static final DeferredHolder<SoundEvent, SoundEvent> MUSIC_SKULKIN_RAID = register("music", "skulkin_raid");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String subject, String type, String name) {
        return SOUND_EVENTS.register(subject + "_" + name, () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc(type + "." + subject + "." + name)));
    }

    private static DeferredHolder<SoundEvent, SoundEvent> register(String subject, String name) {
        return SOUND_EVENTS.register(subject + "_" + name, () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc(subject + "." + name)));
    }

    private static DeferredHolder<SoundEvent, SoundEvent> registerSpinjitzuCourseElement(String subject, String name) {
        return register("spinjitzu_course." + subject, "entity", name);
    }

    private static DeferredHolder<SoundEvent, SoundEvent> registerSpinjitzuCourseElement(String name) {
        return register("spinjitzu_course", "entity", name);
    }

    public static void init() {}
}
