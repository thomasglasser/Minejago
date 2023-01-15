package dev.thomasglasser.minejago.mixin.net.minecraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import dev.thomasglasser.minejago.commands.MinejagoCommandEvents;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class CommandsMixin
{
    @Shadow @Final private CommandDispatcher<CommandSourceStack> dispatcher;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;setConsumer(Lcom/mojang/brigadier/ResultConsumer;)V"))
    private void init(Commands.CommandSelection commandSelection, CommandBuildContext commandBuildContext, CallbackInfo ci)
    {
        MinejagoCommandEvents.onCommandsRegister(dispatcher);
    }
}
