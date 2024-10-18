package dev.thomasglasser.minejago.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.thomasglasser.minejago.commands.MinejagoCommandEvents;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.level.storage.SpinjitzuData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;

public class SpinjitzuCommand {
    public static final String LOCKED = "commands.spinjitzu.locked";
    public static final String UNLOCKED = "commands.spinjitzu.unlocked";
    public static final String SUCCESS_SELF = "commands.spinjitzu.success.self";
    public static final String CHANGED = "spinjitzu.changed";
    public static final String SUCCESS_OTHER = "commands.spinjitzu.success.other";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("spinjitzu")
                .requires((p_137736_) -> p_137736_.hasPermission(2))
                .executes(context -> setSpinjitzuUnlocked(context, context.getSource().getEntityOrException(), true))
                .then(Commands.argument("unlocked", BoolArgumentType.bool())
                        .executes(context -> setSpinjitzuUnlocked(context, context.getSource().getEntityOrException(), BoolArgumentType.getBool(context, "unlocked"))))
                .then(Commands.argument("target", EntityArgument.entity())
                        .executes(context -> setSpinjitzuUnlocked(context, EntityArgument.getEntity(context, "target"), true))
                        .then(Commands.argument("unlocked", BoolArgumentType.bool())
                                .executes(context -> setSpinjitzuUnlocked(context, EntityArgument.getEntity(context, "target"), BoolArgumentType.getBool(context, "unlocked"))))));
    }

    private static void logSpinjitzuChange(CommandSourceStack pSource, Entity entity, boolean unlocked) {
        if (entity instanceof LivingEntity livingEntity) {
            if (pSource.getEntity() == entity) {
                pSource.sendSuccess(() -> Component.translatable(SUCCESS_SELF, Component.translatable(unlocked ? UNLOCKED : LOCKED)), true);
            } else {
                if (pSource.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
                    livingEntity.sendSystemMessage(Component.translatable(CHANGED, Component.translatable(unlocked ? UNLOCKED : LOCKED)));
                }

                pSource.sendSuccess(() -> Component.translatable(SUCCESS_OTHER, livingEntity.getDisplayName(), Component.translatable(unlocked ? UNLOCKED : LOCKED)), true);
            }
        } else {
            pSource.sendFailure(Component.translatable(MinejagoCommandEvents.NOT_LIVING_ENTITY, entity.getDisplayName(), entity.getStringUUID()));
        }
    }

    private static int setSpinjitzuUnlocked(CommandContext<CommandSourceStack> pSource, Entity entity, boolean unlocked) {
        if (entity instanceof LivingEntity livingEntity) {
            SpinjitzuData data = livingEntity.getData(MinejagoAttachmentTypes.SPINJITZU);
            if (!unlocked && data.active() && livingEntity instanceof ServerPlayer serverPlayer) {
                MinejagoEntityEvents.stopSpinjitzu(new SpinjitzuData(unlocked, true), serverPlayer, true);
            } else
                new SpinjitzuData(unlocked, data.active()).save(livingEntity, true);
        }
        logSpinjitzuChange(pSource.getSource(), entity, unlocked);
        return entity instanceof LivingEntity ? 1 : 0;
    }
}
