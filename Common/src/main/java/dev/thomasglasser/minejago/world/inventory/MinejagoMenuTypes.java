package dev.thomasglasser.minejago.world.inventory;

import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class MinejagoMenuTypes
{
    public static final Supplier<MenuType<ScrollLecternMenu>> SCROLL_LECTERN = register("scroll_lectern", ((i, inventory) -> new ScrollLecternMenu(i)));

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> register(String pKey, MenuType.MenuSupplier<T> pFactory) {
        return Services.REGISTRATION.register(BuiltInRegistries.MENU, pKey, () -> new MenuType<>(pFactory, FeatureFlags.VANILLA_SET));
    }

    public static void init() {}
}
