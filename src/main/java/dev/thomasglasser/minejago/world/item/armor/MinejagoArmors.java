package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.item.equipment.MinejagoArmorMaterials;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import dev.thomasglasser.tommylib.api.world.item.armor.ArmorSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.equipment.ArmorType;

public class MinejagoArmors {
    public static final DeferredRegister.Items ARMORS = DeferredRegister.createItems(Minejago.MOD_ID);

    public static final List<ArmorSet> ARMOR_SETS = new ArrayList<>();
    public static final List<ArmorSet> GI_SETS = new ArrayList<>();
    public static final List<ArmorSet> POWER_SETS = new ArrayList<>();

    private static final UnaryOperator<Item.Properties> DEFAULT_PROPERTIES = properties -> properties.stacksTo(1);

    public static final SkeletalChestplateSet SKELETAL_CHESTPLATE_SET = new SkeletalChestplateSet();
    public static final DeferredItem<SamukaisChestplateItem> SAMUKAIS_CHESTPLATE = register("samukais_chestplate", properties -> new SamukaisChestplateItem(MinejagoArmorMaterials.SAMUKAI, DEFAULT_PROPERTIES.apply(properties.rarity(Rarity.RARE))));

    public static final ArmorSet BLACK_GI_SET = createGi("black_gi", "Black Gi", false, BlackGiItem::new, DEFAULT_PROPERTIES);
    public static final ArmorSet TRAINEE_GI_SET = createGi("trainee_gi", "Trainee Gi", true, TraineeGiItem::new, properties -> DEFAULT_PROPERTIES.apply(properties.rarity(Rarity.UNCOMMON)));

    public static class SkeletalChestplateSet {
        private final DeferredItem<SkeletalChestplateItem> RED;
        private final DeferredItem<SkeletalChestplateItem> BLUE;
        private final DeferredItem<SkeletalChestplateItem> WHITE;
        private final DeferredItem<SkeletalChestplateItem> BLACK;
        private final DeferredItem<SkeletalChestplateItem> BONE;

        public SkeletalChestplateSet() {
            String name = "skeletal_chestplate";
            RED = register(name + "_red", properties -> new SkeletalChestplateItem(Skulkin.Variant.STRENGTH, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES.apply(properties.rarity(Rarity.UNCOMMON))));
            BLUE = register(name + "_blue", properties -> new SkeletalChestplateItem(Skulkin.Variant.SPEED, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES.apply(properties.rarity(Rarity.UNCOMMON))));
            WHITE = register(name + "_white", properties -> new SkeletalChestplateItem(Skulkin.Variant.BOW, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES.apply(properties.rarity(Rarity.UNCOMMON))));
            BLACK = register(name + "_black", properties -> new SkeletalChestplateItem(Skulkin.Variant.KNIFE, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES.apply(properties.rarity(Rarity.UNCOMMON))));
            BONE = register(name + "_bone", properties -> new SkeletalChestplateItem(Skulkin.Variant.BONE, MinejagoArmorMaterials.SKELETAL, DEFAULT_PROPERTIES.apply(properties.rarity(Rarity.UNCOMMON))));
        }

        public DeferredItem<SkeletalChestplateItem> getForVariant(Skulkin.Variant variant) {
            return switch (variant) {

                case STRENGTH -> RED;
                case SPEED -> BLUE;
                case BOW -> WHITE;
                case KNIFE -> BLACK;
                case BONE -> BONE;
            };
        }

        public List<DeferredItem<SkeletalChestplateItem>> getAll() {
            return List.of(RED, BLUE, WHITE, BLACK, BONE);
        }

        public List<ItemStack> getAllAsStacks() {
            return List.of(RED.toStack(), BLUE.toStack(), WHITE.toStack(), BLACK.toStack(), BONE.toStack());
        }
    }

    private static ArmorSet create(String name, String displayName, boolean powered, BiFunction<ArmorType, Item.Properties, ArmorItem> item, UnaryOperator<Item.Properties> properties) {
        Function<Item.Properties, ArmorItem> headItem;
        Function<Item.Properties, ArmorItem> chestItem;
        Function<Item.Properties, ArmorItem> legsItem;
        Function<Item.Properties, ArmorItem> feetItem;

        headItem = idProps -> item.apply(ArmorType.HELMET, properties.apply(idProps));
        chestItem = idProps -> item.apply(ArmorType.CHESTPLATE, properties.apply(idProps));
        legsItem = idProps -> item.apply(ArmorType.LEGGINGS, properties.apply(idProps));
        feetItem = idProps -> item.apply(ArmorType.BOOTS, properties.apply(idProps));

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

    private static ArmorSet createGi(String name, String displayName, boolean powered, BiFunction<ArmorType, Item.Properties, ArmorItem> item, UnaryOperator<Item.Properties> properties) {
        ArmorSet set = create(name, displayName, powered, item, properties);
        GI_SETS.add(set);
        return set;
    }

    private static <T extends ArmorItem> DeferredItem<T> register(String name, Function<Item.Properties, T> item) {
        return ItemUtils.register(ARMORS, name, item);
    }

    public static boolean isWearingFullGi(LivingEntity entity) {
        for (ItemStack stack : entity.getArmorSlots()) {
            if (!(stack.getItem() instanceof GiGeoArmorItem)) {
                return false;
            }
        }
        return true;
    }

    public static void init() {}
}
