package dev.thomasglasser.minejago.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import dev.thomasglasser.minejago.commands.MinejagoCommandEvents;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.entity.element.Elements;
import dev.thomasglasser.minejago.world.level.storage.ElementData;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceKeyArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;

public class ElementCommand {
    public static final String SUCCESS_SELF = "commands.element.success.self";
    public static final String CHANGED = "commands.element.changed";
    public static final String SUCCESS_OTHER = "commands.element.success.other";
    public static final String SUCCESS_CLEARED_SELF = "commands.element.success_cleared.self";
    public static final String CLEARED = "commands.element.cleared";
    public static final String SUCCESS_CLEARED_OTHER = "commands.element.success_cleared.other";
    public static final String QUERY = "commands.element.query";
    public static final String INVALID = "commands.element.element.invalid";

    private static final DynamicCommandExceptionType ERROR_INVALID_ELEMENT = new DynamicCommandExceptionType(
            object -> Component.translatable(INVALID, object));

    public static void register(CommandDispatcher<CommandSourceStack> pDispatcher) {
        pDispatcher.register(Commands.literal("element")
                .requires((p_137736_) -> p_137736_.hasPermission(2))
                .executes(context -> {
                    ElementData elementData = context.getSource().getPlayerOrException().getData(MinejagoAttachmentTypes.ELEMENT);
                    context.getSource().sendSuccess(() -> Component.translatable(QUERY, Element.getFormattedName(context.getSource().registryAccess().holderOrThrow(elementData.element()))), false);
                    return 1;
                })
                .then(Commands.literal("clear")
                        .executes(context -> resetElement(context, Collections.singleton(context.getSource().getPlayerOrException())))
                        .then(Commands.argument("target", EntityArgument.entities())
                                .executes(context -> resetElement(context, EntityArgument.getEntities(context, "target")))))
                .then(Commands.literal("random")
                        .executes(context -> setElement(context, Collections.singleton(context.getSource().getPlayerOrException()), context.getSource().registryAccess().registryOrThrow(MinejagoRegistries.ELEMENT).getRandom(context.getSource().getLevel().getRandom()).orElseThrow()))
                        .then(Commands.argument("target", EntityArgument.entities())
                                .executes(context -> setElement(context, EntityArgument.getEntities(context, "target"), context.getSource().registryAccess().registryOrThrow(MinejagoRegistries.ELEMENT).getRandom(context.getSource().getLevel().getRandom()).orElseThrow()))))
                .then(Commands.argument("element", ResourceKeyArgument.key(MinejagoRegistries.ELEMENT))
                        .executes((p_258228_) -> setElement(p_258228_, Collections.singleton(p_258228_.getSource().getPlayerOrException()), ResourceKeyArgument.resolveKey(p_258228_, "element", MinejagoRegistries.ELEMENT, ERROR_INVALID_ELEMENT)))
                        .then(Commands.argument("target", EntityArgument.entities())
                                .executes((p_258229_) -> setElement(p_258229_, EntityArgument.getEntities(p_258229_, "target"), ResourceKeyArgument.resolveKey(p_258229_, "element", MinejagoRegistries.ELEMENT, ERROR_INVALID_ELEMENT))))));
    }

    private static void logElementChange(CommandSourceStack pSource, Entity entity, ResourceKey<Element> element, boolean clear) {
        if (entity instanceof LivingEntity livingEntity) {
            Holder<Element> holder = pSource.registryAccess().holderOrThrow(element);
            Component formattedName = Element.getFormattedName(holder);
            if (pSource.getEntity() == entity) {
                pSource.sendSuccess(() -> Component.translatable(clear ? SUCCESS_CLEARED_SELF : SUCCESS_SELF, formattedName), true);
            } else {
                if (pSource.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK) && livingEntity instanceof Player player) {
                    player.displayClientMessage(Component.translatable(clear ? CLEARED : CHANGED, formattedName), false);
                }

                pSource.sendSuccess(() -> Component.translatable(clear ? SUCCESS_CLEARED_OTHER : SUCCESS_OTHER, livingEntity.getDisplayName(), formattedName), true);
            }
        } else {
            pSource.sendFailure(Component.translatable(MinejagoCommandEvents.NOT_LIVING_ENTITY, entity.getDisplayName(), entity.getStringUUID()));
        }
    }

    private static int setElement(CommandContext<CommandSourceStack> pSource, Collection<? extends Entity> entities, Holder.Reference<Element> element) {
        int i = 0;

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                new ElementData(element.key(), false).save(livingEntity, true);
                ++i;
            }
            logElementChange(pSource.getSource(), entity, element.key(), false);
        }

        return i;
    }

    private static int resetElement(CommandContext<CommandSourceStack> pSource, Collection<? extends Entity> entities) {
        int i = 0;

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                new ElementData(Elements.NONE, false).save(livingEntity, true);
                ++i;
            }
            logElementChange(pSource.getSource(), entity, Elements.NONE, true);
        }

        return i;
    }
}
