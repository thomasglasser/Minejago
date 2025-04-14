package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredItem;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.item.armor.ArmorSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;

public class MinejagoCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Minejago.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GI = register("gi", () -> TommyLibServices.CLIENT.tabBuilder().title(Component.translatable(Minejago.modLoc("gi").toLanguageKey("item_group"))).icon(() -> MinejagoArmors.BLACK_GI_SET.HEAD.get().getDefaultInstance()).displayItems((parameters, output) -> {
        MinejagoArmors.STANDALONE_GI.forEach(item -> output.accept(item.get()));
        MinejagoArmors.NORMAL_GI_SETS.forEach(set -> output.acceptAll(set.getAllAsStacks()));
        parameters.holders()
                .lookup(MinejagoRegistries.ELEMENT)
                .ifPresent(elements -> {
                    generateElementalItems(MinejagoArmors.STANDALONE_ELEMENTAL_GI, output, elements);
                    generateElementalSpecialItems(MinejagoArmors.STANDALONE_SPECIAL_ELEMENTAL_GI, output, elements);
                    generateElementalSets(MinejagoArmors.ELEMENTAL_GI_SETS, output, elements);
                    generateElementalSpecialSets(MinejagoArmors.SPECIAL_ELEMENTAL_GI_SETS, output, elements);
                });
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MINEJAGO = register("minejago", () -> TommyLibServices.CLIENT.tabBuilder().title(Component.translatable(Minejago.modLoc(Minejago.MOD_ID).toLanguageKey("item_group"))).icon(() -> MinejagoItems.SCYTHE_OF_QUAKES.get().getDefaultInstance()).type(CreativeModeTab.Type.SEARCH).displayItems((parameters, output) -> {
        Set<ItemStack> set = ItemStackLinkedSet.createTypeAndComponentsSet();

        for (CreativeModeTab creativemodetab : parameters.holders().lookupOrThrow(Registries.CREATIVE_MODE_TAB).listElements().map(Holder::value).toList()) {
            if (creativemodetab.getType() != CreativeModeTab.Type.SEARCH) {
                for (ItemStack stack : creativemodetab.getSearchTabDisplayItems()) {
                    if (BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace().equals(Minejago.MOD_ID)) {
                        set.add(stack);
                    }
                }
            }
        }

        output.acceptAll(set);
    }).build());

    private static DeferredHolder<CreativeModeTab, CreativeModeTab> register(String name, Supplier<CreativeModeTab> tab) {
        return CREATIVE_MODE_TABS.register(name, tab);
    }

    private static void generateElementalItems(
            List<DeferredItem<? extends ArmorItem>> items,
            CreativeModeTab.Output output,
            HolderLookup.RegistryLookup<Element> elements) {
        elements.listElements()
                .forEach(
                        element -> {
                            if (element.value().hasSets()) {
                                items.forEach(item -> generateElementalArmorItem(item, element.getKey(), output));
                            }
                        });
    }

    private static void generateElementalSets(
            List<ArmorSet> armorSets,
            CreativeModeTab.Output output,
            HolderLookup.RegistryLookup<Element> elements) {
        elements.listElements()
                .forEach(
                        element -> {
                            if (element.value().hasSets()) {
                                ResourceKey<Element> key = element.getKey();
                                armorSets.forEach(set -> set.getAll().forEach(item -> generateElementalArmorItem(item, key, output)));
                            }
                        });
    }

    private static void generateElementalSpecialItems(
            List<DeferredItem<? extends ArmorItem>> items,
            CreativeModeTab.Output output,
            HolderLookup.RegistryLookup<Element> elements) {
        elements.listElements()
                .forEach(
                        element -> {
                            if (element.value().hasSpecialSets()) {
                                items.forEach(item -> generateElementalArmorItem(item, element.getKey(), output));
                            }
                        });
    }

    private static void generateElementalSpecialSets(
            List<ArmorSet> armorSets,
            CreativeModeTab.Output output,
            HolderLookup.RegistryLookup<Element> elements) {
        elements.listElements()
                .forEach(
                        element -> {
                            if (element.value().hasSpecialSets()) {
                                ResourceKey<Element> key = element.getKey();
                                armorSets.forEach(set -> set.getAll().forEach(item -> generateElementalArmorItem(item, key, output)));
                            }
                        });
    }

    private static void generateElementalArmorItem(
            DeferredItem<? extends ArmorItem> item,
            ResourceKey<Element> element,
            CreativeModeTab.Output output) {
        ItemStack stack = item.toStack();
        stack.set(MinejagoDataComponents.ELEMENT, element);
        output.accept(stack);
    }

    public static void init() {}
}
