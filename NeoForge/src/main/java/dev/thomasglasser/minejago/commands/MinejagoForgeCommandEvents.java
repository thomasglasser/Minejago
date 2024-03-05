package dev.thomasglasser.minejago.commands;

import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

public class MinejagoForgeCommandEvents
{
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        MinejagoCommandEvents.onCommandsRegister(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
