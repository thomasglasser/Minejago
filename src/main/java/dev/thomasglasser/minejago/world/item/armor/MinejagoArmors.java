package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.entity.skulkin.Skulkin;
import dev.thomasglasser.minejago.world.item.equipment.MinejagoArmorMaterials;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import dev.thomasglasser.tommylib.api.world.item.armor.ArmorSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class MinejagoArmors {
    public static final DeferredRegister.Items ARMORS = DeferredRegister.createItems(Minejago.MOD_ID);

    public static final List<ArmorSet> ARMOR_SETS = new ArrayList<>();
    public static final List<ArmorSet> NORMAL_GI_SETS = new ArrayList<>();
    public static final List<ArmorSet> POWERED_GI_SETS = new ArrayList<>();
    public static final List<ArmorSet> SPECIAL_POWERED_GI_SETS = new ArrayList<>();
    public static final List<DeferredItem<? extends ArmorItem>> STANDALONE = new ArrayList<>();
    public static final List<DeferredItem<? extends ArmorItem>> STANDALONE_GI = new ArrayList<>();
    public static final List<DeferredItem<? extends ArmorItem>> STANDALONE_POWERED_GI = new ArrayList<>();
    public static final List<DeferredItem<? extends ArmorItem>> STANDALONE_SPECIAL_POWERED_GI = new ArrayList<>();

    // Skulkin
    public static final SkeletalChestplateSet SKELETAL_CHESTPLATE_SET = new SkeletalChestplateSet();
    public static final DeferredItem<SamukaisChestplateItem> SAMUKAIS_CHESTPLATE = createStandalone("samukais_chestplate", () -> new SamukaisChestplateItem(MinejagoArmorMaterials.SAMUKAI, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    // Gi
    public static final ArmorSet BLACK_GI_SET = createGi("black_gi", "Black Gi", BlackGiItem::new, new Item.Properties().stacksTo(1), false, false);
    public static final ArmorSet TRAINEE_GI_SET = createGi("trainee_gi", "Trainee Gi", TraineeGiItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), true, false);
    public static final ArmorSet DRAGON_EXTREME_GI_SET = createGi("dragon_extreme_gi", "Dragon eXtreme Gi", DragonExtremeGiItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), true, true);

    public static class SkeletalChestplateSet {
        private final DeferredItem<SkeletalChestplateItem> STRENGTH;
        private final DeferredItem<SkeletalChestplateItem> SPEED;
        private final DeferredItem<SkeletalChestplateItem> BOW;
        private final DeferredItem<SkeletalChestplateItem> KNIFE;
        private final DeferredItem<SkeletalChestplateItem> BONE;

        public SkeletalChestplateSet() {
            String name = "skeletal_chestplate";
            STRENGTH = register(name + "_strength", () -> new SkeletalChestplateItem(Skulkin.Variant.STRENGTH, MinejagoArmorMaterials.SKELETAL, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
            SPEED = register(name + "_speed", () -> new SkeletalChestplateItem(Skulkin.Variant.SPEED, MinejagoArmorMaterials.SKELETAL, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
            BOW = register(name + "_bow", () -> new SkeletalChestplateItem(Skulkin.Variant.BOW, MinejagoArmorMaterials.SKELETAL, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
            KNIFE = register(name + "_knife", () -> new SkeletalChestplateItem(Skulkin.Variant.KNIFE, MinejagoArmorMaterials.SKELETAL, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
            BONE = register(name + "_bone", () -> new SkeletalChestplateItem(Skulkin.Variant.BONE, MinejagoArmorMaterials.SKELETAL, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
        }

        public DeferredItem<SkeletalChestplateItem> getForVariant(Skulkin.Variant variant) {
            return switch (variant) {

                case STRENGTH -> STRENGTH;
                case SPEED -> SPEED;
                case BOW -> BOW;
                case KNIFE -> KNIFE;
                case BONE -> BONE;
            };
        }

        public List<DeferredItem<SkeletalChestplateItem>> getAll() {
            return List.of(STRENGTH, SPEED, BOW, KNIFE, BONE);
        }

        public List<ResourceKey<Item>> getAllKeys() {
            return List.of(STRENGTH.getKey(), SPEED.getKey(), BOW.getKey(), KNIFE.getKey(), BONE.getKey());
        }

        public List<SkeletalChestplateItem> getAllAsItems() {
            return List.of(STRENGTH.get(), SPEED.get(), BOW.get(), KNIFE.get(), BONE.get());
        }

        public List<ItemStack> getAllAsStacks() {
            return List.of(STRENGTH.toStack(), SPEED.toStack(), BOW.toStack(), KNIFE.toStack(), BONE.toStack());
        }
    }

    private static ArmorSet create(String name, Map<ArmorItem.Type, String> suffixes, String displayName, BiFunction<ArmorItem.Type, Item.Properties, ArmorItem> item, Item.Properties properties) {
        Supplier<ArmorItem> headItem;
        Supplier<ArmorItem> chestItem;
        Supplier<ArmorItem> legsItem;
        Supplier<ArmorItem> feetItem;

        headItem = () -> item.apply(ArmorItem.Type.HELMET, properties);
        chestItem = () -> item.apply(ArmorItem.Type.CHESTPLATE, properties);
        legsItem = () -> item.apply(ArmorItem.Type.LEGGINGS, properties);
        feetItem = () -> item.apply(ArmorItem.Type.BOOTS, properties);

        DeferredItem<ArmorItem> head = register(name + "_" + suffixes.get(ArmorItem.Type.HELMET), headItem);
        DeferredItem<ArmorItem> chest = register(name + "_" + suffixes.get(ArmorItem.Type.CHESTPLATE), chestItem);
        DeferredItem<ArmorItem> legs = register(name + "_" + suffixes.get(ArmorItem.Type.LEGGINGS), legsItem);
        DeferredItem<ArmorItem> feet = register(name + "_" + suffixes.get(ArmorItem.Type.BOOTS), feetItem);

        ArmorSet set = new ArmorSet(name, displayName, head, chest, legs, feet);

        ARMOR_SETS.add(set);

        return set;
    }

    private static ArmorSet create(String name, String displayName, boolean powered, BiFunction<ArmorItem.Type, Item.Properties, ArmorItem> item, Item.Properties properties) {
        return create(name, Map.of(
                ArmorItem.Type.HELMET, "helmet",
                ArmorItem.Type.CHESTPLATE, "chestplate",
                ArmorItem.Type.LEGGINGS, "leggings",
                ArmorItem.Type.BOOTS, "boots"), displayName, item, properties);
    }

    private static ArmorSet createGi(String name, String displayName, BiFunction<ArmorItem.Type, Item.Properties, ArmorItem> item, Item.Properties properties, boolean powered, boolean special) {
        ArmorSet set = create(name, Map.of(
                ArmorItem.Type.HELMET, "hood",
                ArmorItem.Type.CHESTPLATE, "jacket",
                ArmorItem.Type.LEGGINGS, "pants",
                ArmorItem.Type.BOOTS, "boots"), displayName, item, properties);
        if (powered && special)
            SPECIAL_POWERED_GI_SETS.add(set);
        else if (powered)
            POWERED_GI_SETS.add(set);
        else
            NORMAL_GI_SETS.add(set);
        return set;
    }

    private static <T extends ArmorItem> DeferredItem<T> createStandalone(String name, Supplier<T> item) {
        DeferredItem<T> armorItem = register(name, item);
        STANDALONE.add(armorItem);
        return armorItem;
    }

    private static <T extends ArmorItem> DeferredItem<T> createStandaloneGi(String name, Supplier<T> item, boolean powered, boolean special) {
        DeferredItem<T> armorItem = createStandalone(name, item);
        if (powered && special)
            STANDALONE_SPECIAL_POWERED_GI.add(armorItem);
        else if (powered)
            STANDALONE_POWERED_GI.add(armorItem);
        else
            STANDALONE_GI.add(armorItem);
        return armorItem;
    }

    private static <T extends ArmorItem> DeferredItem<T> register(String name, Supplier<T> item) {
        return ItemUtils.register(ARMORS, name, item);
    }

    public static boolean isWearingFullGi(LivingEntity entity) {
        for (ItemStack stack : entity.getArmorSlots()) {
            if (!stack.is(MinejagoItemTags.GI)) {
                return false;
            }
        }
        return true;
    }

    public static void init() {}
}
