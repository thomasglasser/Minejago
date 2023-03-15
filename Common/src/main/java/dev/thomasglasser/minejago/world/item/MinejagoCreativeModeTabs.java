package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.item.armor.IGeoArmorItem;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmor;
import dev.thomasglasser.minejago.world.item.armor.PoweredArmorItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class MinejagoCreativeModeTabs
{
    public static CreativeModeTab GI = Services.ITEM.newTab(Minejago.modLoc("gi"), Component.translatable(Minejago.modLoc("gi").toLanguageKey("item_group")), () -> MinejagoArmor.BLACK_GI_SET.HEAD.get().getDefaultInstance(), (featureFlagSet, output, bl) ->
    {
        MinejagoArmor.ARMOR_SETS.forEach(armorSet ->
        {
            armorSet.getAll().forEach(armor ->
            {
                if (!(armor.get() instanceof PoweredArmorItem) && armor.get() instanceof IGeoArmorItem iGeoArmorItem && iGeoArmorItem.isGi())
                    output.accept(armor::get);
            });
        });

        final RegistryAccess.Frozen access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        final RegistrySetBuilder builder = new RegistrySetBuilder();
        MinejagoPowers.POWERS.addToSet(builder);
        HolderLookup.Provider provider = builder.build(access);
        output.acceptAll(MinejagoPowers.getArmorForAll(provider));
    });

    public static void init() {}
}
