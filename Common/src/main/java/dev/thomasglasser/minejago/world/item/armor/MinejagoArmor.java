package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.UnderworldSkeleton;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MinejagoArmor
{
    public static final RegistrationProvider<Item> ARMOR = RegistrationProvider.get(Registries.ITEM, Minejago.MOD_ID);
    public static final List<ArmorSet> ARMOR_SETS = new ArrayList<>();
    public static final List<ArmorSet> POWER_SETS = new ArrayList<>();

    private static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties().stacksTo(1);

    public static final SkeletalChestplateSet SKELETAL_CHESTPLATE_SET = new SkeletalChestplateSet();

    public static final ArmorSet BLACK_GI_SET = create("black_gi", "Black Gi", false, BlackGiItem.class, MinejagoArmorMaterials.BLACK_GI, DEFAULT_PROPERTIES);
    public static final ArmorSet TRAINING_GI_SET = create("training_gi", "Training Gi", true, TrainingGiItem.class, MinejagoArmorMaterials.TRAINING_GI, DEFAULT_PROPERTIES);

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

    public static class ArmorSet
    {
        public final RegistryObject<ArmorItem> HEAD;
        public final RegistryObject<ArmorItem> CHEST;
        public final RegistryObject<ArmorItem> LEGS;
        public final RegistryObject<ArmorItem> FEET;

        private final String name;
        private final String displayName;

        public ArmorSet(String name, String displayName, RegistryObject<ArmorItem> head, RegistryObject<ArmorItem> chest, RegistryObject<ArmorItem> legs, RegistryObject<ArmorItem> feet)
        {
            this.name = name;
            this.displayName = displayName;

            HEAD = head;
            CHEST = chest;
            LEGS = legs;
            FEET = feet;
        }

        public RegistryObject<ArmorItem> getForSlot(EquipmentSlot slot)
        {
            return switch (slot)
                    {

                        case MAINHAND, OFFHAND -> null;
                        case FEET -> FEET;
                        case LEGS -> LEGS;
                        case CHEST -> CHEST;
                        case HEAD -> HEAD;
                    };
        }

        public EquipmentSlot getForItem(ArmorItem item)
        {
            if (item == HEAD.get())
            {
                return EquipmentSlot.HEAD;
            }
            else if (item == CHEST.get())
            {
                return EquipmentSlot.CHEST;
            } else if (item == LEGS.get()) {
                return EquipmentSlot.LEGS;
            } else if (item == FEET.get()) {
                return EquipmentSlot.FEET;
            }

            return null;
        }

        public List<RegistryObject<ArmorItem>> getAll()
        {
            return List.of(HEAD, CHEST, LEGS, FEET);
        }

        public String getDisplayName()
        {
            return displayName;
        }

        public String getName()
        {
            return name;
        }
    }

    private static ArmorSet create(String name, String displayName, boolean powered, Class<? extends ArmorItem> clazz, ArmorMaterial material, Item.Properties properties)
    {
        Supplier<ArmorItem> headItem;
        Supplier<ArmorItem> chestItem;
        Supplier<ArmorItem> legsItem;
        Supplier<ArmorItem> feetItem;

        headItem = () -> {
            try {
                return clazz.getDeclaredConstructor(ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(material, EquipmentSlot.HEAD, properties);
            } catch (Exception e) {
                throw new RuntimeException("Armor construction failed! Error: " + e);
            }
        };
        chestItem = () -> {
            try {
                return clazz.getDeclaredConstructor(ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(material, EquipmentSlot.CHEST, properties);
            } catch (Exception e) {
                throw new RuntimeException("Armor construction failed! Error: " + e);
            }
        };
        legsItem = () -> {
            try {
                return clazz.getDeclaredConstructor(ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(material, EquipmentSlot.LEGS, properties);
            } catch (Exception e) {
                throw new RuntimeException("Armor construction failed! Error: " + e);
            }
        };
        feetItem = () -> {
            try {
                return clazz.getDeclaredConstructor(ArmorMaterial.class, EquipmentSlot.class, Item.Properties.class).newInstance(material, EquipmentSlot.FEET, properties);
            } catch (Exception e) {
                throw new RuntimeException("Armor construction failed! Error: " + e);
            }
        };

        RegistryObject<ArmorItem> head = ARMOR.register(name + "_hood", headItem);
        RegistryObject<ArmorItem> chest = ARMOR.register(name + "_jacket", chestItem);
        RegistryObject<ArmorItem> legs = ARMOR.register(name + "_pants", legsItem);
        RegistryObject<ArmorItem> feet = ARMOR.register(name + "_boots", feetItem);

        ArmorSet set = new ArmorSet(name, displayName, head, chest, legs, feet);

        ARMOR_SETS.add(set);

        if (powered)
            POWER_SETS.add(set);

        return set;
    }

    public static void init() {}
}
