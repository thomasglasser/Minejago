package dev.thomasglasser.minejago.commands;

import dev.thomasglasser.minejago.server.commands.PowerCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.server.command.ConfigCommand;

public class MinejagoForgeCommandEvents
{
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        MinejagoCommandEvents.onCommandsRegister(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
