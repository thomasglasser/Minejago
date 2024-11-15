package dev.thomasglasser.minejago.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.thomasglasser.minejago.commands.MinejagoCommandEvents;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.level.storage.SkillData;
import dev.thomasglasser.minejago.world.level.storage.SkillDataSet;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;

public class SkillCommand {
    public static final String SUCCESS_SELF = "commands.skill.success.self";
    public static final String CHANGED = "skill.changed";
    public static final String SUCCESS_OTHER = "commands.skill.success.other";
    public static final String QUERY = "commands.skill.query";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skill")
                .requires((source) -> source.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.entities())
                        .then(Commands.argument("skill", ResourceLocationArgument.id())
                                .executes(context -> queryLevel(context, EntityArgument.getEntities(context, "target"), ResourceLocationArgument.getId(context, "skill")))
                                .then(Commands.argument("level", IntegerArgumentType.integer(0))
                                        .executes(context -> setLevel(context, EntityArgument.getEntities(context, "target"), ResourceLocationArgument.getId(context, "skill"), IntegerArgumentType.getInteger(context, "level")))))));
    }

    private static void logLevelChange(CommandSourceStack pSource, Entity entity, ResourceLocation skill, int level) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            pSource.sendFailure(Component.translatable(MinejagoCommandEvents.NOT_LIVING_ENTITY, entity.getDisplayName(), entity.getStringUUID()));
            return;
        }
        if (pSource.getEntity() == entity) {
            pSource.sendSuccess(() -> Component.translatable(SUCCESS_SELF, Component.translatable(skill.toLanguageKey("skill")), level), true);
        } else {
            if (pSource.getLevel().getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK) && livingEntity instanceof Player player) {
                player.displayClientMessage(Component.translatable(CHANGED, Component.translatable(skill.toLanguageKey("skill")), level), false);
            }

            pSource.sendSuccess(() -> Component.translatable(SUCCESS_OTHER, livingEntity.getDisplayName(), Component.translatable(skill.toLanguageKey("skill")), level), true);
        }
    }

    private static int queryLevel(CommandContext<CommandSourceStack> pSource, Collection<? extends Entity> entities, ResourceLocation skill) {
        int i = 0;

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                SkillDataSet skillDataSet = livingEntity.getData(MinejagoAttachmentTypes.SKILL);
                SkillData skillData = skillDataSet.get(skill);
                pSource.getSource().sendSuccess(() -> Component.translatable(QUERY, Component.translatable(skill.toLanguageKey("skill")), skillData.level()), false);
                ++i;
            }
        }

        return i;
    }

    private static int setLevel(CommandContext<CommandSourceStack> pSource, Collection<? extends Entity> entities, ResourceLocation skill, int level) {
        int i = 0;

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                SkillDataSet skillDataSet = livingEntity.getData(MinejagoAttachmentTypes.SKILL);
                skillDataSet.put(livingEntity, skill, new SkillData(level, 0), true);
                ++i;
            }
            logLevelChange(pSource.getSource(), entity, skill, level);
        }

        return i;
    }
}
