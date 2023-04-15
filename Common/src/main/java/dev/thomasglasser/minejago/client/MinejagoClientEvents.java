package dev.thomasglasser.minejago.client;

import dev.thomasglasser.minejago.util.MinejagoClientUtils;

public class MinejagoClientEvents
{
    public static void onPlayerLoggedIn()
    {
        MinejagoClientUtils.refreshVip();
    }
}
