package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.tommylib.api.registration.RegistrationProvider;
import dev.thomasglasser.tommylib.api.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MinejagoArmors
{
    public static final RegistrationProvider<Item> ARMORS = RegistrationProvider.get(BuiltInRegistries.ITEM, Minejago.MOD_ID);

    public static final List<ArmorSet> ARMOR_SETS = new ArrayList<>();
    public static final List<ArmorSet> POWER_SETS = new ArrayList<>();

    private static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties().stacksTo(1);

    public static final SkeletalChestplateSet SKELETAL_CHESTPLATE_SET = new SkeletalChestplateSet();
    public static final RegistryObject<SamukaisChestplateItem> SAMUKAIS_CHESTPLATE = register("samukais_chestplate", () -> new SamukaisChestplateItem(MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));

    public static final ArmorSet BLACK_GI_SET = create("black_gi", "Black Gi", false, BlackGiItem.class, DEFAULT_PROPERTIES);
    public static final ArmorSet TRAINING_GI_SET = create("training_gi", "Training Gi", true, TrainingGiItem.class, DEFAULT_PROPERTIES);

    public static class SkeletalChestplateSet
    {
        private final RegistryObject<SkeletalChestplateItem> RED;
        private final RegistryObject<SkeletalChestplateItem> BLUE;
        private final RegistryObject<SkeletalChestplateItem> WHITE;
        private final RegistryObject<SkeletalChestplateItem> BLACK;
        private final RegistryObject<SkeletalChestplateItem> BONE;

        public SkeletalChestplateSet()
        {
            String name = "skeletal_chestplate";
            RED = register(name + "_red", () -> new SkeletalChestplateItem(Skulkin.Variant.STRENGTH, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            BLUE = register(name + "_blue", () -> new SkeletalChestplateItem(Skulkin.Variant.SPEED, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            WHITE = register(name + "_white", () -> new SkeletalChestplateItem(Skulkin.Variant.BOW, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            BLACK = register(name + "_black", () -> new SkeletalChestplateItem(Skulkin.Variant.KNIFE, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            BONE = register(name + "_bone", () -> new SkeletalChestplateItem(Skulkin.Variant.BONE, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
        }

        public RegistryObject<SkeletalChestplateItem> getForVariant(Skulkin.Variant variant) {
            return switch (variant)
                    {

                        case STRENGTH -> RED;
                        case SPEED -> BLUE;
                        case BOW -> WHITE;
                        case KNIFE -> BLACK;
                        case BONE -> BONE;
                    };
        }

        public List<RegistryObject<SkeletalChestplateItem>> getAll()
        {
            return new ArrayList<>(List.of(RED, BLUE, WHITE, BLACK, BONE));
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

        public List<ArmorItem> getAllAsItems()
        {
            return List.of(HEAD.get(), CHEST.get(), LEGS.get(), FEET.get());
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

    private static ArmorSet create(String name, String displayName, boolean powered, Class<? extends ArmorItem> clazz, Item.Properties properties)
    {
        Supplier<ArmorItem> headItem;
        Supplier<ArmorItem> chestItem;
        Supplier<ArmorItem> legsItem;
        Supplier<ArmorItem> feetItem;

        headItem = () -> {
            try {
                return clazz.getDeclaredConstructor(ArmorItem.Type.class, Item.Properties.class).newInstance(ArmorItem.Type.HELMET, properties);
            } catch (Exception e) {
                throw new RuntimeException("Armor construction failed! Error: " + e);
            }
        };
        chestItem = () -> {
            try {
                return clazz.getDeclaredConstructor(ArmorItem.Type.class, Item.Properties.class).newInstance(ArmorItem.Type.CHESTPLATE, properties);
            } catch (Exception e) {
                throw new RuntimeException("Armor construction failed! Error: " + e);
            }
        };
        legsItem = () -> {
            try {
                return clazz.getDeclaredConstructor(ArmorItem.Type.class, Item.Properties.class).newInstance(ArmorItem.Type.LEGGINGS, properties);
            } catch (Exception e) {
                throw new RuntimeException("Armor construction failed! Error: " + e);
            }
        };
        feetItem = () -> {
            try {
                return clazz.getDeclaredConstructor(ArmorItem.Type.class, Item.Properties.class).newInstance(ArmorItem.Type.BOOTS, properties);
            } catch (Exception e) {
                throw new RuntimeException("Armor construction failed! Error: " + e);
            }
        };

        RegistryObject<ArmorItem> head = register(name + "_hood", headItem);
        RegistryObject<ArmorItem> chest = register(name + "_jacket", chestItem);
        RegistryObject<ArmorItem> legs = register(name + "_pants", legsItem);
        RegistryObject<ArmorItem> feet = register(name + "_boots", feetItem);

        ArmorSet set = new ArmorSet(name, displayName, head, chest, legs, feet);

        if (powered)
            POWER_SETS.add(set);
        else
            ARMOR_SETS.add(set);

        return set;
    }
    
    private static <T extends ArmorItem> RegistryObject<T> register(String name, Supplier<T> item)
    {
        return ARMORS.register(name, item);
    }

    public static void init() {}
}
