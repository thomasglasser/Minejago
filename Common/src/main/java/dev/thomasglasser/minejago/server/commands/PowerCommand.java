package dev.thomasglasser.minejago.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.thomasglasser.minejago.commands.arguments.PowerArgument;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
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

    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("power")
                .requires((p_137736_) -> p_137736_.hasPermission(2))
                .executes(context ->
                {
                    PowerData powerData = Services.DATA.getPowerData(context.getSource().getPlayer());
                    context.getSource().sendSuccess(Component.translatable(QUERY, Component.translatable(powerData.power().getId().toLanguageKey("power"))), false);
                    return 1;
                })
                .then(Commands.argument("power", PowerArgument.power())
                        .executes((p_258228_) -> setPower(p_258228_, Collections.singleton(p_258228_.getSource().getPlayerOrException()), PowerArgument.getPower(p_258228_, "power")))
                .then(Commands.argument("target", EntityArgument.players())
                        .executes((p_258229_) -> setPower(p_258229_, EntityArgument.getPlayers(p_258229_, "target"), PowerArgument.getPower(p_258229_, "power"))))));
    }

    private static void logPowerChange(CommandSourceStack pSource, ServerPlayer pPlayer, Power power) {
        if (pSource.getEntity() == pPlayer) {
            pSource.sendSuccess(Component.translatable(SUCCESS_SELF, Component.translatable(power.getId().toLanguageKey("power"))), true);
        } else {
            if (pSource.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
                pPlayer.sendSystemMessage(Component.translatable(CHANGED, Component.translatable(power.getId().toLanguageKey("power"))));
            }

            pSource.sendSuccess(Component.translatable(SUCCESS_OTHER, pPlayer.getDisplayName(), Component.translatable(power.getId().toLanguageKey("power"))), true);
        }

    }

    private static int setPower(CommandContext<CommandSourceStack> pSource, Collection<ServerPlayer> pPlayers, Power power) {
        int i = 0;

        for(ServerPlayer serverplayer : pPlayers) {
            Services.DATA.setPowerData(new PowerData(power), serverplayer);
            logPowerChange(pSource.getSource(), serverplayer, power);
            ++i;
        }

        return i;
    }
}
