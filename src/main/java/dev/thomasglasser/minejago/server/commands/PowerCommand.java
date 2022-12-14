package dev.thomasglasser.minejago.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.thomasglasser.minejago.commands.arguments.PowerArgument;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerCapability;
import dev.thomasglasser.minejago.world.level.storage.PowerCapabilityAttacher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;

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
                    context.getSource().getPlayer().getCapability(PowerCapabilityAttacher.POWER_CAPABILITY).ifPresent(powerCapability ->
                            context.getSource().sendSuccess(Component.translatable(QUERY, Component.translatable(powerCapability.getPower().getDescriptionId())), false));
                    return 1;
                })
                .then(Commands.argument("power", PowerArgument.power())
                        .executes((p_258228_) -> setPower(p_258228_, Collections.singleton(p_258228_.getSource().getPlayerOrException()), PowerArgument.getPower(p_258228_, "power")))
                .then(Commands.argument("target", EntityArgument.players())
                        .executes((p_258229_) -> setPower(p_258229_, EntityArgument.getPlayers(p_258229_, "target"), PowerArgument.getPower(p_258229_, "power"))))));
    }

    private static void logPowerChange(CommandSourceStack pSource, ServerPlayer pPlayer, Power power) {
        if (pSource.getEntity() == pPlayer) {
            pSource.sendSuccess(Component.translatable(SUCCESS_SELF, Component.translatable(power.getDescriptionId())), true);
        } else {
            if (pSource.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
                pPlayer.sendSystemMessage(Component.translatable(CHANGED, Component.translatable(power.getDescriptionId())));
            }

            pSource.sendSuccess(Component.translatable(SUCCESS_OTHER, pPlayer.getDisplayName(), Component.translatable(power.getDescriptionId())), true);
        }

    }

    private static int setPower(CommandContext<CommandSourceStack> pSource, Collection<ServerPlayer> pPlayers, Power power) {
        int i = 0;

        for(ServerPlayer serverplayer : pPlayers) {
            serverplayer.getCapability(PowerCapabilityAttacher.POWER_CAPABILITY).ifPresent(powerCapability ->
            {
                powerCapability.setPower(power);
                logPowerChange(pSource.getSource(), serverplayer, power);
            });
            ++i;
        }

        return i;
    }
}
