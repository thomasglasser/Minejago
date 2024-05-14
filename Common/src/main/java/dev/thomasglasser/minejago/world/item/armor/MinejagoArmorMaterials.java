package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class MinejagoArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, Minejago.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SKELETAL = register("skeletal", Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
        defense.put(ArmorItem.Type.BOOTS, 2);
        defense.put(ArmorItem.Type.LEGGINGS, 3);
        defense.put(ArmorItem.Type.CHESTPLATE, 4);
        defense.put(ArmorItem.Type.HELMET, 2);
        defense.put(ArmorItem.Type.BODY, 6);
    }), 6, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, () -> Ingredient.of(Items.BONE));
    
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BLACK_GI = register("black_gi", Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
        defense.put(ArmorItem.Type.BOOTS, 1);
        defense.put(ArmorItem.Type.LEGGINGS, 2);
        defense.put(ArmorItem.Type.CHESTPLATE, 3);
        defense.put(ArmorItem.Type.HELMET, 1);
        defense.put(ArmorItem.Type.BODY, 4);
    }), 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.1F, 0.1F, () -> Ingredient.EMPTY);
    
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TRAINING_GI = register("training_gi", Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
        defense.put(ArmorItem.Type.BOOTS, 2);
        defense.put(ArmorItem.Type.LEGGINGS, 4);
        defense.put(ArmorItem.Type.CHESTPLATE, 6);
        defense.put(ArmorItem.Type.HELMET, 2);
        defense.put(ArmorItem.Type.BODY, 8);
    }), 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.2F, 0.2F, () -> Ingredient.EMPTY);

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Minejago.modLoc(name)));

        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(defense, enchantmentValue, equipSound, repairIngredient, list, toughness, knockbackResistance));
    }

    public static void init() {}
}
