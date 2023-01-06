package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.UnderworldSkeleton;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class MinejagoArmor
{
    public static final RegistrationProvider<Item> ARMOR = RegistrationProvider.get(Registries.ITEM, Minejago.MOD_ID);
    public static final List<ArmorSet> SETS = new ArrayList<>();
    public static final List<PoweredArmorSet> POWERED_SETS = new ArrayList<>();

    private static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties().stacksTo(1);

    public static final ArmorSet BLACK_GI_SET = new ArmorSet("black_gi", MinejagoArmorMaterials.BLACK_GI, BlackGiItem.class);
    public static final SkeletalChestplateSet SKELETAL_CHESTPLATE_SET = new SkeletalChestplateSet();

    public static class ArmorSet
    {
        private final RegistryObject<ArmorItem> HELMET;
        private final RegistryObject<ArmorItem> CHESTPLATE;
        private final RegistryObject<ArmorItem> LEGGINGS;
        private final RegistryObject<ArmorItem> BOOTS;

        private final String name;

        public ArmorSet(String setName, ArmorMaterial material, Class<? extends ArmorItem> armorClass) {
            name = setName;

            HELMET = ARMOR.register(setName + "_helmet", () -> {
                try {
                    return armorClass.getDeclaredConstructor(ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(material, EquipmentSlot.HEAD, DEFAULT_PROPERTIES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            CHESTPLATE = ARMOR.register(setName + "_chestplate", () -> {
                try {
                    return armorClass.getDeclaredConstructor(ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(material, EquipmentSlot.CHEST, DEFAULT_PROPERTIES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            LEGGINGS = ARMOR.register(setName + "_leggings", () -> {
                try {
                    return armorClass.getDeclaredConstructor(ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(material, EquipmentSlot.LEGS, DEFAULT_PROPERTIES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            BOOTS = ARMOR.register(setName + "_boots", () -> {
                try {
                    return armorClass.getDeclaredConstructor(ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(material, EquipmentSlot.FEET, DEFAULT_PROPERTIES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            SETS.add(this);
        }

        public RegistryObject<ArmorItem> getForSlot(EquipmentSlot slot) {
            return switch (slot)
                    {

                        case MAINHAND, OFFHAND -> null;
                        case FEET -> BOOTS;
                        case LEGS -> LEGGINGS;
                        case CHEST -> CHESTPLATE;
                        case HEAD -> HELMET;
                    };
        }

        public EquipmentSlot getForItem(RegistryObject<ArmorItem> item)
        {
            if (item == HELMET)
                return EquipmentSlot.HEAD;
            else if (item == CHESTPLATE)
                return EquipmentSlot.CHEST;
            else if (item == LEGGINGS)
                return EquipmentSlot.LEGS;
            else if (item == BOOTS)
                return EquipmentSlot.FEET;

            return null;
        }

        public String getName() {
            return name;
        }

        public List<RegistryObject<ArmorItem>> getAll()
        {
            return new ArrayList<>(List.of(HELMET, CHESTPLATE, LEGGINGS, BOOTS));
        }
    }

    public static class SkeletalChestplateSet
    {
        private final RegistryObject<Item> RED;
        private final RegistryObject<Item> BLUE;
        private final RegistryObject<Item> WHITE;
        private final RegistryObject<Item> BLACK;

        public SkeletalChestplateSet()
        {
            String name = "skeletal_chestplate";
            RED = ARMOR.register(name + "_red", () -> new SkeletalChestplateItem(UnderworldSkeleton.Variant.STRENGTH, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            BLUE = ARMOR.register(name + "_blue", () -> new SkeletalChestplateItem(UnderworldSkeleton.Variant.SPEED, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            WHITE = ARMOR.register(name + "_white", () -> new SkeletalChestplateItem(UnderworldSkeleton.Variant.BOW, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            BLACK = ARMOR.register(name + "_black", () -> new SkeletalChestplateItem(UnderworldSkeleton.Variant.KNIFE, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
        }

        public RegistryObject<Item> getForVariant(UnderworldSkeleton.Variant variant) {
            return switch (variant)
                    {

                        case STRENGTH -> RED;
                        case SPEED -> BLUE;
                        case BOW -> WHITE;
                        case KNIFE -> BLACK;
                    };
        }

        public List<RegistryObject<Item>> getAll()
        {
            return new ArrayList<>(List.of(RED, BLUE, WHITE, BLACK));
        }
    }

    public static class PoweredArmorSet {
        private final RegistryObject<ArmorItem> HELMET;
        private final RegistryObject<ArmorItem> CHESTPLATE;
        private final RegistryObject<ArmorItem> LEGGINGS;
        private final RegistryObject<ArmorItem> BOOTS;

        private final String SET_NAME;
        private final String POWER_NAME;

        private PoweredArmorSet(String powerName, String setName, ArmorMaterial material, Class<? extends PoweredArmorItem> armorClass) {
            SET_NAME = setName;
            POWER_NAME = powerName;

            HELMET = ARMOR.register(powerName + "_" + setName + "_helmet", () -> {
                try {
                    return armorClass.getDeclaredConstructor(String.class, ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(powerName, material, EquipmentSlot.HEAD, DEFAULT_PROPERTIES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            CHESTPLATE = ARMOR.register(powerName + "_" + setName + "_chestplate", () -> {
                try {
                    return armorClass.getDeclaredConstructor(String.class, ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(powerName, material, EquipmentSlot.CHEST, DEFAULT_PROPERTIES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            LEGGINGS = ARMOR.register(powerName + "_" + setName + "_leggings", () -> {
                try {
                    return armorClass.getDeclaredConstructor(String.class, ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(powerName, material, EquipmentSlot.LEGS, DEFAULT_PROPERTIES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            BOOTS = ARMOR.register(powerName + "_" + setName + "_boots", () -> {
                try {
                    return armorClass.getDeclaredConstructor(String.class, ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(powerName, material, EquipmentSlot.FEET, DEFAULT_PROPERTIES);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        public static PoweredArmorSet create(String powerName, String setName, ArmorMaterial material, Class<? extends PoweredArmorItem> clazz)
        {
            PoweredArmorSet set = new PoweredArmorSet(powerName, setName, material, clazz);
            POWERED_SETS.add(set);
            return set;
        }

        public RegistryObject<ArmorItem> getForSlot(EquipmentSlot slot) {
            return switch (slot)
                    {

                        case MAINHAND, OFFHAND -> null;
                        case FEET -> BOOTS;
                        case LEGS -> LEGGINGS;
                        case CHEST -> CHESTPLATE;
                        case HEAD -> HELMET;
                    };
        }

        public EquipmentSlot getForItem(RegistryObject<ArmorItem> item)
        {
            if (item == HELMET)
                return EquipmentSlot.HEAD;
            else if (item == CHESTPLATE)
                return EquipmentSlot.CHEST;
            else if (item == LEGGINGS)
                return EquipmentSlot.LEGS;
            else if (item == BOOTS)
                return EquipmentSlot.FEET;

            return null;
        }

        public String getPowerName() {
            return POWER_NAME;
        }

        public String getSetName() {
            return SET_NAME;
        }

        public String getName()
        {
            return POWER_NAME + "_" + SET_NAME;
        }

        public List<RegistryObject<ArmorItem>> getAll()
        {
            return new ArrayList<>(List.of(HELMET, CHESTPLATE, LEGGINGS, BOOTS));
        }


        public static PoweredArmorSet getSetForPower(Power power, List<PoweredArmorSet> sets) {
            for (PoweredArmorSet set : sets) {
                if (set.POWER_NAME.equals(power.getName()))
                    return set;
            }

            return null;
        }
    }

    public static void init() {}
}
