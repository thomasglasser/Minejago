package dev.thomasglasser.minejago.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.thomasglasser.minejago.server.commands.ElementCommand;
import dev.thomasglasser.minejago.server.commands.SkillCommand;
import dev.thomasglasser.minejago.server.commands.SpinjitzuCommand;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

public class MinejagoCommandEvents {
    public static final String NOT_LIVING_ENTITY = "commands.failure.not_living_entity";

    public static void onCommandsRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        ElementCommand.register(dispatcher);
        SkillCommand.register(dispatcher);
        SpinjitzuCommand.register(dispatcher);

        ConfigCommand.register(dispatcher);
    }
}
