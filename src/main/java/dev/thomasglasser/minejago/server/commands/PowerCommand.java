package dev.thomasglasser.minejago.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.power.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.power.Power;
import dev.thomasglasser.minejago.world.level.storage.PowerData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;

import java.util.Collection;
import java.util.Collections;

public class PowerCommand
{
    public static final String SUCCESS_SELF = "commands.power.success.self";
    public static final String CHANGED = "power.changed";
    public static final String SUCCESS_OTHER = "commands.power.success.other";
    public static final String SUCCESS_CLEARED_SELF = "commands.power.success_cleared.self";
    public static final String CLEARED = "power.cleared";
    public static final String SUCCESS_CLEARED_OTHER = "commands.power.success_cleared.other";
    public static final String QUERY = "commands.power.query";
    public static final String INVALID = "commands.power.power.invalid";
    public static final String NOT_LIVING_ENTITY = "commands.power.failure.not_living_entity";

    private static final DynamicCommandExceptionType ERROR_INVALID_POWER = new DynamicCommandExceptionType(
            object -> Component.translatable(INVALID, object)
    );

    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("power")
                .requires((p_137736_) -> p_137736_.hasPermission(2))
                .executes(context ->
                {
                    PowerData powerData = context.getSource().getPlayer().getData(MinejagoAttachmentTypes.POWER);
                    context.getSource().sendSuccess(() -> Component.translatable(QUERY, MinejagoPowers.getPowerOrThrow(context.getSource().registryAccess(), powerData.power()).getFormattedName()), false);
                    return 1;
                })
                .then(Commands.literal("clear")
                        .executes(context -> resetPower(context, Collections.singleton(context.getSource().getPlayerOrException())))
                        .then(Commands.argument("target", EntityArgument.entities())
                                .executes(context -> resetPower(context, EntityArgument.getEntities(context,"target")))))
                .then(Commands.argument("power", ResourceKeyArgument.key(MinejagoRegistries.POWER))
                        .executes((p_258228_) -> setPower(p_258228_, Collections.singleton(p_258228_.getSource().getPlayerOrException()), ResourceKeyArgument.resolveKey(p_258228_, "power", MinejagoRegistries.POWER, ERROR_INVALID_POWER)))
                .then(Commands.argument("target", EntityArgument.entities())
                        .executes((p_258229_) -> setPower(p_258229_, EntityArgument.getEntities(p_258229_, "target"), ResourceKeyArgument.resolveKey(p_258229_, "power", MinejagoRegistries.POWER, ERROR_INVALID_POWER))))));
    }

    private static void logPowerChange(CommandSourceStack pSource, Entity entity, ResourceKey<Power> power, boolean clear) {
        if (entity instanceof LivingEntity livingEntity)
        {
            if (pSource.getEntity() == entity) {
                pSource.sendSuccess(() -> Component.translatable(clear ? SUCCESS_CLEARED_SELF : SUCCESS_SELF, MinejagoPowers.getPowerOrThrow(pSource.registryAccess(), power).getFormattedName()), true);
            } else {
                if (pSource.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
                    livingEntity.sendSystemMessage(Component.translatable(clear ? CLEARED : CHANGED, MinejagoPowers.getPowerOrThrow(pSource.registryAccess(), power).getFormattedName()));
                }

                pSource.sendSuccess(() -> Component.translatable(clear ? SUCCESS_CLEARED_OTHER : SUCCESS_OTHER, livingEntity.getDisplayName(), MinejagoPowers.getPowerOrThrow(pSource.registryAccess(), power).getFormattedName()), true);
            }
        }
        else
        {
            pSource.sendFailure(Component.translatable(NOT_LIVING_ENTITY, entity.getDisplayName(), entity.getStringUUID()));
        }
    }

    private static int setPower(CommandContext<CommandSourceStack> pSource, Collection<? extends Entity> entities, Holder.Reference<Power> power) {
        int i = 0;

        for(Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity)
            {
                new PowerData(power.key(), false).save(livingEntity);
                ++i;
            }
            logPowerChange(pSource.getSource(), entity, power.key(), false);
        }

        return i;
    }

    private static int resetPower(CommandContext<CommandSourceStack> pSource, Collection<? extends Entity> entities) {
        int i = 0;

        for(Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity)
            {
                new PowerData(MinejagoPowers.NONE, false).save(livingEntity);
                ++i;
            }
            logPowerChange(pSource.getSource(), entity, MinejagoPowers.NONE, true);
        }

        return i;
    }
}
