package dev.thomasglasser.minejago.world.item.equipment;

import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.tommylib.api.world.item.equipment.ExtendedArmorMaterial;
import java.util.EnumMap;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorType;

public interface MinejagoArmorMaterials {
    ExtendedArmorMaterial SKELETAL = new ExtendedArmorMaterial(13, Util.make(new EnumMap<>(ArmorType.class), defense -> {
        defense.put(ArmorType.BOOTS, 2);
        defense.put(ArmorType.LEGGINGS, 3);
        defense.put(ArmorType.CHESTPLATE, 4);
        defense.put(ArmorType.HELMET, 2);
        defense.put(ArmorType.BODY, 6);
    }), 10, SoundEvents.ARMOR_EQUIP_GENERIC/*TODO:Armor equip sound*/, 0, 0, Optional.of(MinejagoItemTags.REPAIRS_SKELETAL_ARMOR), Optional.empty());

    ExtendedArmorMaterial SAMUKAI = new ExtendedArmorMaterial(30, Util.make(new EnumMap<>(ArmorType.class), defense -> {
        defense.put(ArmorType.BOOTS, 4);
        defense.put(ArmorType.LEGGINGS, 6);
        defense.put(ArmorType.CHESTPLATE, 8);
        defense.put(ArmorType.HELMET, 4);
        defense.put(ArmorType.BODY, 12);
    }), 12, SoundEvents.ARMOR_EQUIP_GENERIC/*TODO:Armor equip sound*/, 1.0F, 0.1F, Optional.empty(), Optional.empty());

    ExtendedArmorMaterial BLACK_GI = new ExtendedArmorMaterial(20, Util.make(new EnumMap<>(ArmorType.class), defense -> {
        defense.put(ArmorType.BOOTS, 1);
        defense.put(ArmorType.LEGGINGS, 2);
        defense.put(ArmorType.CHESTPLATE, 3);
        defense.put(ArmorType.HELMET, 1);
        defense.put(ArmorType.BODY, 4);
    }), 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.1F, 0.1F, Optional.empty(), Optional.empty());

    ExtendedArmorMaterial TRAINING_GI = new ExtendedArmorMaterial(20, Util.make(new EnumMap<>(ArmorType.class), defense -> {
        defense.put(ArmorType.BOOTS, 2);
        defense.put(ArmorType.LEGGINGS, 4);
        defense.put(ArmorType.CHESTPLATE, 6);
        defense.put(ArmorType.HELMET, 2);
        defense.put(ArmorType.BODY, 8);
    }), 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.2F, 0.2F, Optional.empty(), Optional.empty());

    static void init() {}
}
