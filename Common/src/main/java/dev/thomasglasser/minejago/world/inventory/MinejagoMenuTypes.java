package dev.thomasglasser.minejago.world.inventory;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class MinejagoMenuTypes
{
    public static final RegistrationProvider<MenuType<?>> MENU_TYPES = RegistrationProvider.get(Registries.MENU, Minejago.MOD_ID);

    public static final RegistryObject<MenuType<ScrollLecternMenu>> SCROLL_LECTERN = register("scroll_lectern", ((i, inventory) -> new ScrollLecternMenu(i)));

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String pKey, MenuType.MenuSupplier<T> pFactory) {
        return MENU_TYPES.register(pKey, () -> new MenuType<T>(pFactory, FeatureFlags.VANILLA_SET));
    }

    public static void init() {}
}
