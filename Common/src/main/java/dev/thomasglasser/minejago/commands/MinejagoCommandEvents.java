package dev.thomasglasser.minejago.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.thomasglasser.minejago.server.commands.PowerCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.server.command.ConfigCommand;

public class MinejagoCommandEvents
{
    public static void onCommandsRegister(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        PowerCommand.register(dispatcher);
    }
}
