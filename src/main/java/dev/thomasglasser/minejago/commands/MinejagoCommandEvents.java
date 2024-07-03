package dev.thomasglasser.minejago.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.thomasglasser.minejago.server.commands.PowerCommand;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

public class MinejagoCommandEvents {
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        PowerCommand.register(dispatcher);

        ConfigCommand.register(dispatcher);
    }
}
