package dev.thomasglasser.minejago.world.item.armor;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import dev.thomasglasser.minejago.world.entity.UnderworldSkeleton;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MinejagoArmor
{
    public static final List<ArmorItem> POWERED_ARMOR = new ArrayList<>();

    public static final RegistrationProvider<Item> ARMOR = RegistrationProvider.get(Registries.ITEM, Minejago.MOD_ID);
    private static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties().stacksTo(1);

//    public static final RegistryObject<Item> BLACK_GI_HOOD = ;

    public static void init() {}
}
