package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.power.Power;
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
                .lookup(MinejagoRegistries.POWER)
                .ifPresent(powers -> {
                    generatePoweredItems(MinejagoArmors.STANDALONE_POWERED_GI, output, powers);
                    generatePoweredSpecialItems(MinejagoArmors.STANDALONE_SPECIAL_POWERED_GI, output, powers);
                    generatePoweredSets(MinejagoArmors.POWERED_GI_SETS, output, powers);
                    generatePoweredSpecialSets(MinejagoArmors.SPECIAL_POWERED_GI_SETS, output, powers);
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

    private static void generatePoweredItems(
            List<DeferredItem<? extends ArmorItem>> items,
            CreativeModeTab.Output output,
            HolderLookup.RegistryLookup<Power> powers) {
        powers.listElements()
                .forEach(
                        power -> {
                            if (power.value().hasSets()) {
                                items.forEach(item -> generatePoweredArmorItem(item, power.getKey(), output));
                            }
                        });
    }

    private static void generatePoweredSets(
            List<ArmorSet> armorSets,
            CreativeModeTab.Output output,
            HolderLookup.RegistryLookup<Power> powers) {
        powers.listElements()
                .forEach(
                        power -> {
                            if (power.value().hasSets()) {
                                ResourceKey<Power> key = power.getKey();
                                armorSets.forEach(set -> set.getAll().forEach(item -> generatePoweredArmorItem(item, key, output)));
                            }
                        });
    }

    private static void generatePoweredSpecialItems(
            List<DeferredItem<? extends ArmorItem>> items,
            CreativeModeTab.Output output,
            HolderLookup.RegistryLookup<Power> powers) {
        powers.listElements()
                .forEach(
                        power -> {
                            if (power.value().hasSpecialSets()) {
                                items.forEach(item -> generatePoweredArmorItem(item, power.getKey(), output));
                            }
                        });
    }

    private static void generatePoweredSpecialSets(
            List<ArmorSet> armorSets,
            CreativeModeTab.Output output,
            HolderLookup.RegistryLookup<Power> powers) {
        powers.listElements()
                .forEach(
                        power -> {
                            if (power.value().hasSpecialSets()) {
                                ResourceKey<Power> key = power.getKey();
                                armorSets.forEach(set -> set.getAll().forEach(item -> generatePoweredArmorItem(item, key, output)));
                            }
                        });
    }

    private static void generatePoweredArmorItem(
            DeferredItem<? extends ArmorItem> item,
            ResourceKey<Power> power,
            CreativeModeTab.Output output) {
        ItemStack stack = item.toStack();
        stack.set(MinejagoDataComponents.POWER, power);
        output.accept(stack);
    }

    public static void init() {}
}
