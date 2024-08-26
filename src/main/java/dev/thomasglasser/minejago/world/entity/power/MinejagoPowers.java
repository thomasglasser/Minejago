package dev.thomasglasser.minejago.world.entity.power;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

public class MinejagoPowers {
    public static final ResourceKey<Power> NONE = create("none");
    public static final ResourceKey<Power> ICE = create("ice");
    public static final ResourceKey<Power> EARTH = create("earth");
    public static final ResourceKey<Power> FIRE = create("fire");
    public static final ResourceKey<Power> LIGHTNING = create("lightning");
    public static final ResourceKey<Power> CREATION = create("creation");

    private static ResourceKey<Power> create(String id) {
        return ResourceKey.create(MinejagoRegistries.POWER, Minejago.modLoc(id));
    }

    public static void init() {}

    public static List<ItemStack> getArmorForAll(HolderLookup.Provider access) {
        List<ItemStack> list = new ArrayList<>();
        access.lookupOrThrow(MinejagoRegistries.POWER).listElements().forEach(powerReference -> {
            Power power = powerReference.value();
            if (power.hasSets) {
                MinejagoArmors.POWER_SETS.forEach(armorSet -> armorSet.getAll().forEach(item -> {
                    ItemStack stack = item.get().getDefaultInstance();
                    stack.set(MinejagoDataComponents.POWER.get(), powerReference.key());
                    list.add(stack);
                }));
            }
        });
        return list;
    }
}
