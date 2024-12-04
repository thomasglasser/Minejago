package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.GiGeoArmorItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.armor.PoweredArmorItem;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import dev.thomasglasser.tommylib.api.world.item.ItemUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

public class MinejagoCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, Minejago.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GI = register("gi", () -> TommyLibServices.ITEM.newTab(Component.translatable(Minejago.modLoc("gi").toLanguageKey("item_group")), () -> MinejagoArmors.BLACK_GI_SET.HEAD.get().getDefaultInstance(), true, (parameters, output) -> {
        MinejagoArmors.ARMOR_SETS.forEach(armorSet -> armorSet.getAll().forEach(armor -> {
            if (!(armor.get() instanceof PoweredArmorItem) && armor.get() instanceof GiGeoArmorItem)
                output.accept(armor::get);
        }));

        output.acceptAll(MinejagoPowers.getArmorForAll(parameters.holders()));
    }));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MINEJAGO = register("minejago", () -> TommyLibServices.ITEM.newTab(Component.translatable(Minejago.modLoc(Minejago.MOD_ID).toLanguageKey("item_group")), () -> MinejagoItems.SCYTHE_OF_QUAKES.get().getDefaultInstance(), true, (parameters, output) -> {
        List<ResourceLocation> itemsToAdd = new ArrayList<>();

        ItemUtils.getItemTabs().forEach((tab, rls) -> rls.forEach(rl -> {
            if (rl.getNamespace().equals(Minejago.MOD_ID) && !(tab == MinejagoCreativeModeTabs.MINEJAGO.getKey()) && !itemsToAdd.contains(rl))
                itemsToAdd.add(rl);
        }));

        output.acceptAll(itemsToAdd.stream().map(rl -> BuiltInRegistries.ITEM.getValue(rl).getDefaultInstance()).toList());

        MinejagoArmors.ARMOR_SETS.forEach(armorSet -> output.acceptAll(armorSet.getAll().stream().map(ro -> ro.get().getDefaultInstance()).toList()));
        MinejagoArmors.SKELETAL_CHESTPLATE_SET.getAll().forEach(chestplate -> output.accept(chestplate.get()));
        output.accept(MinejagoArmors.SAMUKAIS_CHESTPLATE.get());
    }));

    private static DeferredHolder<CreativeModeTab, CreativeModeTab> register(String name, Supplier<CreativeModeTab> tab) {
        return CREATIVE_MODE_TABS.register(name, tab);
    }

    public static void init() {}
}
