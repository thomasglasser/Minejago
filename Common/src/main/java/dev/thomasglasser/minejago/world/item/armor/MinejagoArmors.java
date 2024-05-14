package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.world.item.armor.ArmorSet;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MinejagoArmors
{
    public static final DeferredRegister.Items ARMORS = DeferredRegister.createItems(Minejago.MOD_ID);

    public static final List<ArmorSet> ARMOR_SETS = new ArrayList<>();
    public static final List<ArmorSet> POWER_SETS = new ArrayList<>();

    private static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties().stacksTo(1);

    public static final SkeletalChestplateSet SKELETAL_CHESTPLATE_SET = new SkeletalChestplateSet();
    public static final DeferredItem<SamukaisChestplateItem> SAMUKAIS_CHESTPLATE = register("samukais_chestplate", () -> new SamukaisChestplateItem(MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));

    public static final ArmorSet BLACK_GI_SET = create("black_gi", "Black Gi", false, BlackGiItem.class, DEFAULT_PROPERTIES);
    public static final ArmorSet TRAINING_GI_SET = create("training_gi", "Training Gi", true, TrainingGiItem.class, DEFAULT_PROPERTIES);

    public static class SkeletalChestplateSet
    {
        private final DeferredItem<SkeletalChestplateItem> RED;
        private final DeferredItem<SkeletalChestplateItem> BLUE;
        private final DeferredItem<SkeletalChestplateItem> WHITE;
        private final DeferredItem<SkeletalChestplateItem> BLACK;
        private final DeferredItem<SkeletalChestplateItem> BONE;

        public SkeletalChestplateSet()
        {
            String name = "skeletal_chestplate";
            RED = register(name + "_red", () -> new SkeletalChestplateItem(Skulkin.Variant.STRENGTH, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            BLUE = register(name + "_blue", () -> new SkeletalChestplateItem(Skulkin.Variant.SPEED, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            WHITE = register(name + "_white", () -> new SkeletalChestplateItem(Skulkin.Variant.BOW, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            BLACK = register(name + "_black", () -> new SkeletalChestplateItem(Skulkin.Variant.KNIFE, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
            BONE = register(name + "_bone", () -> new SkeletalChestplateItem(Skulkin.Variant.BONE, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES));
        }

        public DeferredItem<SkeletalChestplateItem> getForVariant(Skulkin.Variant variant) {
            return switch (variant)
                    {

                        case STRENGTH -> RED;
                        case SPEED -> BLUE;
                        case BOW -> WHITE;
                        case KNIFE -> BLACK;
                        case BONE -> BONE;
                    };
        }

        public List<DeferredItem<SkeletalChestplateItem>> getAll()
        {
            return new ArrayList<>(List.of(RED, BLUE, WHITE, BLACK, BONE));
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

        DeferredItem<ArmorItem> head = register(name + "_hood", headItem);
        DeferredItem<ArmorItem> chest = register(name + "_jacket", chestItem);
        DeferredItem<ArmorItem> legs = register(name + "_pants", legsItem);
        DeferredItem<ArmorItem> feet = register(name + "_boots", feetItem);

        ArmorSet set = new ArmorSet(name, displayName, head, chest, legs, feet);

        if (powered)
            POWER_SETS.add(set);
        else
            ARMOR_SETS.add(set);

        return set;
    }
    
    private static <T extends ArmorItem> DeferredItem<T> register(String name, Supplier<T> item)
    {
        return ARMORS.register(name, item);
    }

    public static void init() {}
}
