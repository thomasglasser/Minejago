package dev.thomasglasser.minejago.commands;

import dev.thomasglasser.minejago.server.commands.PowerCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.server.command.ConfigCommand;

public class MinejagoCommandEvents
{
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        PowerCommand.register(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
