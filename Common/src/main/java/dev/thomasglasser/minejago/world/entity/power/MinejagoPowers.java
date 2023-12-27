package dev.thomasglasser.minejago.world.entity.power;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MinejagoPowers
{
    public static final ResourceKey<Power> NONE = create("none");
    public static final ResourceKey<Power> ICE = create("ice");
    public static final ResourceKey<Power> EARTH = create("earth");
    public static final ResourceKey<Power> FIRE = create("fire");
    public static final ResourceKey<Power> LIGHTNING = create("lightning");
    public static final ResourceKey<Power> CREATION = create("creation");

    private static ResourceKey<Power> create(String id)
    {
        return ResourceKey.create(MinejagoRegistries.POWER, Minejago.modLoc(id));
    }

    public static void init() {}

    public static List<ItemStack> getArmorForAll(HolderLookup.Provider access)
    {
        List<ItemStack> list = new ArrayList<>();
        access.lookupOrThrow(MinejagoRegistries.POWER).listElements().forEach(powerReference ->
        {
            Power power = powerReference.value();
            if (power.hasSets)
            {
                MinejagoArmors.POWER_SETS.forEach(armorSet ->
                        armorSet.getAll().forEach(item ->
                            list.add(PowerUtils.setPower(new ItemStack(item.get()), powerReference.key()))
                        ));
            }
        });
        return list;
    }

    public static Power getPowerOrThrow(RegistryAccess registryAccess, ResourceKey<Power> key)
    {
        return registryAccess.registryOrThrow(MinejagoRegistries.POWER).getOrThrow(key);
    }
}
