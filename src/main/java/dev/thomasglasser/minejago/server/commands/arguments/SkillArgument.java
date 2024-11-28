package dev.thomasglasser.minejago.server.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.thomasglasser.minejago.world.entity.skill.Skill;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public class SkillArgument implements ArgumentType<Skill> {
    private static final Dynamic2CommandExceptionType INVALID_ENUM = new Dynamic2CommandExceptionType((found, constants) -> Component.translatable("commands.neoforge.arguments.enum.invalid", new Object[] { constants, found }));

    protected SkillArgument() {}

    public static SkillArgument skill() {
        return new SkillArgument();
    }

    public static Skill getSkill(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, Skill.class);
    }

    @Override
    public Skill parse(StringReader reader) throws CommandSyntaxException {
        String name = reader.readUnquotedString();

        try {
            return Skill.fromSerializedName(name);
        } catch (IllegalArgumentException var4) {
            throw INVALID_ENUM.createWithContext(reader, name, Arrays.toString(Arrays.stream(Skill.class.getEnumConstants()).map(Enum::name).toArray()));
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(getExamples(), builder);
    }

    @Override
    public Collection<String> getExamples() {
        return Arrays.stream(Skill.values()).map(Skill::getSerializedName).toList();
    }
}
