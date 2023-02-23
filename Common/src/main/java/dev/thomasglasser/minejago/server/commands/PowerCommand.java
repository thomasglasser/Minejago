package dev.thomasglasser.minejago.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;

import java.util.Collection;
import java.util.Collections;

public class PowerCommand
{
    public static final String SUCCESS_SELF = "commands.power.success.self";
    public static final String CHANGED = "power.changed";
    public static final String SUCCESS_OTHER = "commands.power.success.other";
    public static final String QUERY = "commands.power.query";

    private static final DynamicCommandExceptionType ERROR_INVALID_POWER = new DynamicCommandExceptionType(
            object -> Component.translatable("commands.power.power.invalid", object)
    );

    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("power")
                .requires((p_137736_) -> p_137736_.hasPermission(2))
                .executes(context ->
                {
                    PowerData powerData = Services.DATA.getPowerData(context.getSource().getPlayer());
                    context.getSource().sendSuccess(Component.translatable(QUERY, Component.translatable(powerData.power().location().toLanguageKey("power"))), false);
                    return 1;
                })
                .then(Commands.argument("power", ResourceKeyArgument.key(MinejagoRegistries.POWER))
                        .executes((p_258228_) -> setPower(p_258228_, Collections.singleton(p_258228_.getSource().getPlayerOrException()), ResourceKeyArgument.resolveKey(p_258228_, "power", MinejagoRegistries.POWER, ERROR_INVALID_POWER)))
                .then(Commands.argument("target", EntityArgument.players())
                        .executes((p_258229_) -> setPower(p_258229_, EntityArgument.getPlayers(p_258229_, "target"), ResourceKeyArgument.resolveKey(p_258229_, "power", MinejagoRegistries.POWER, ERROR_INVALID_POWER))))));
    }

    private static void logPowerChange(CommandSourceStack pSource, ServerPlayer pPlayer, ResourceKey<Power> power) {
        if (pSource.getEntity() == pPlayer) {
            pSource.sendSuccess(Component.translatable(SUCCESS_SELF, Component.translatable(power.location().toLanguageKey("power"))), true);
        } else {
            if (pSource.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
                pPlayer.sendSystemMessage(Component.translatable(CHANGED, Component.translatable(power.location().toLanguageKey("power"))));
            }

            pSource.sendSuccess(Component.translatable(SUCCESS_OTHER, pPlayer.getDisplayName(), Component.translatable(power.location().toLanguageKey("power"))), true);
        }

    }

    private static int setPower(CommandContext<CommandSourceStack> pSource, Collection<ServerPlayer> pPlayers, Holder.Reference<Power> power) {
        int i = 0;

        for(ServerPlayer serverplayer : pPlayers) {
            Services.DATA.setPowerData(new PowerData(power.key()), serverplayer);
            logPowerChange(pSource.getSource(), serverplayer, power.key());
            ++i;
        }

        return i;
    }
}
