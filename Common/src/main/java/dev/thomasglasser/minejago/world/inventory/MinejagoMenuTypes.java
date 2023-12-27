package dev.thomasglasser.minejago.world.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class MinejagoMenuTypes
{
    public static final RegistrationProvider<MenuType<?>> MENU_TYPES = RegistrationProvider.get(BuiltInRegistries.MENU, Minejago.MOD_ID);

    public static final RegistryObject<MenuType<ScrollLecternMenu>> SCROLL_LECTERN = register("scroll_lectern", ((i, inventory) -> new ScrollLecternMenu(i)));

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String pKey, MenuType.MenuSupplier<T> pFactory) {
        return MENU_TYPES.register(pKey, () -> new MenuType<>(pFactory, FeatureFlags.VANILLA_SET));
    }

    public static void init() {}
}
