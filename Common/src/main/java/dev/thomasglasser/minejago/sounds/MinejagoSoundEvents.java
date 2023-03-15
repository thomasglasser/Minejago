package dev.thomasglasser.minejago.sounds;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class MinejagoSoundEvents
{
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS = RegistrationProvider.get(Registries.SOUND_EVENT, Minejago.MOD_ID);

    public static final RegistryObject<SoundEvent> TEAPOT_WHISTLE = SOUND_EVENTS.register("teapot_whistle", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("block.teapot.teapot_whistle")));
    public static final RegistryObject<SoundEvent> SPINJITZU_START = SOUND_EVENTS.register("spinjitzu_start", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("spinjitzu.spinjitzu_start")));
    public static final RegistryObject<SoundEvent> SPINJITZU_ACTIVE = SOUND_EVENTS.register("spinjitzu_active", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("spinjitzu.spinjitzu_active")));
    public static final RegistryObject<SoundEvent> SPINJITZU_STOP = SOUND_EVENTS.register("spinjitzu_stop", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("spinjitzu.spinjitzu_stop")));
    public static final RegistryObject<SoundEvent> SCYTHE_OF_QUAKES_FAIL = SOUND_EVENTS.register("scythe_of_quakes_fail", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.scythe_of_quakes.scythe_of_quakes_fail")));
    public static final RegistryObject<SoundEvent> SCYTHE_OF_QUAKES_CASCADE = SOUND_EVENTS.register("scythe_of_quakes_cascade", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.scythe_of_quakes.scythe_of_quakes_cascade")));
    public static final RegistryObject<SoundEvent> SCYTHE_OF_QUAKES_EXPLOSION = SOUND_EVENTS.register("scythe_of_quakes_explosion", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.scythe_of_quakes.scythe_of_quakes_explosion")));
    public static final RegistryObject<SoundEvent> SCYTHE_OF_QUAKES_PATH = SOUND_EVENTS.register("scythe_of_quakes_path", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.scythe_of_quakes.scythe_of_quakes_path")));
    public static final RegistryObject<SoundEvent> BAMBOO_STAFF_IMPACT = SOUND_EVENTS.register("bamboo_staff_impact", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.bamboo_staff.bamboo_staff_impact")));
    public static final RegistryObject<SoundEvent> BAMBOO_STAFF_THROW = SOUND_EVENTS.register("bamboo_staff_throw", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.bamboo_staff.bamboo_staff_throw")));
    public static final RegistryObject<SoundEvent> BONE_KNIFE_IMPACT = SOUND_EVENTS.register("bone_knife_impact", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.bone_knife.bone_knife_impact")));
    public static final RegistryObject<SoundEvent> BONE_KNIFE_THROW = SOUND_EVENTS.register("bone_knife_throw", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.bone_knife.bone_knife_throw")));
    public static final RegistryObject<SoundEvent> SHURIKEN_IMPACT = SOUND_EVENTS.register("shuriken_impact", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.shuriken.shuriken_impact")));
    public static final RegistryObject<SoundEvent> SHURIKEN_THROW = SOUND_EVENTS.register("shuriken_throw", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.shuriken.shuriken_throw")));
    public static final RegistryObject<SoundEvent> SPEAR_IMPACT = SOUND_EVENTS.register("spear_impact", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.spear.spear_impact")));
    public static final RegistryObject<SoundEvent> SPEAR_THROW = SOUND_EVENTS.register("spear_throw", () -> SoundEvent.createVariableRangeEvent(Minejago.modLoc("item.spear.spear_throw")));

    public static void init() {}
}
