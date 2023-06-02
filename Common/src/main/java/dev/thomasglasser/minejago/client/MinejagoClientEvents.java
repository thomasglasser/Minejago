package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.client.gui.screens.inventory.ScrollLecternScreen;
import dev.thomasglasser.minejago.util.MinejagoClientUtils;
import dev.thomasglasser.minejago.world.inventory.MinejagoMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;

public class MinejagoClientEvents
{
    public static void onPlayerLoggedIn()
    {
        MinejagoClientUtils.refreshVip();
    }

    public static void registerMenuScreens()
    {
        MenuScreens.register(MinejagoMenuTypes.SCROLL_LECTERN.get(), ScrollLecternScreen::new);
    }
}
