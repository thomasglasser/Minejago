package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.GeoArmorItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.item.armor.PoweredArmorItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;

import java.util.ArrayList;
import java.util.List;

public class MinejagoCreativeModeTabs
{
    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Minejago.MOD_ID);

    public static final RegistryObject<CreativeModeTab> GI = CREATIVE_MODE_TABS.register("gi", () -> Services.ITEM.newTab(Component.translatable(Minejago.modLoc("gi").toLanguageKey("item_group")), () -> MinejagoArmors.BLACK_GI_SET.HEAD.get().getDefaultInstance(), false, (parameters, output) ->
    {
        MinejagoArmors.ARMOR_SETS.forEach(armorSet ->
                armorSet.getAll().forEach(armor ->
                {
                    if (!(armor.get() instanceof PoweredArmorItem) && armor.get() instanceof GeoArmorItem geoArmorItem && geoArmorItem.isGi())
                        output.accept(armor::get);
                }));

        output.acceptAll(MinejagoPowers.getArmorForAll(MinejagoPowers.getBasePowers()));
    }, CreativeModeTabs.COMBAT));

    public static final RegistryObject<CreativeModeTab> MINEJAGO = CREATIVE_MODE_TABS.register("minejago", () -> Services.ITEM.newTab(Component.translatable(Minejago.modLoc("minejago").toLanguageKey("item_group")), () -> MinejagoItems.SCYTHE_OF_QUAKES.get().getDefaultInstance(), true, (parameters, output) ->
    {
        List<ResourceLocation> itemsToAdd = new ArrayList<>();

        MinejagoItems.getItemTabs().values().forEach(rls ->
                rls.forEach(rl ->
                {
                    if (!itemsToAdd.contains(rl))
                        itemsToAdd.add(rl);
                }));

        output.acceptAll(itemsToAdd.stream().map(rl -> BuiltInRegistries.ITEM.get(rl).getDefaultInstance()).toList());

        MinejagoArmors.ARMOR_SETS.forEach(armorSet ->
                output.acceptAll(armorSet.getAll().stream().map(ro -> ro.get().getDefaultInstance()).toList()));

        output.acceptAll(MinejagoPowers.getArmorForAll(parameters.holders()));

    }, CreativeModeTabs.SPAWN_EGGS));

    public static void init() {}
}
