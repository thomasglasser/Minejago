package dev.thomasglasser.minejago.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.thomasglasser.minejago.core.registries.MinejagoRegistries;
import dev.thomasglasser.minejago.world.entity.powers.MinejagoPowers;
import dev.thomasglasser.minejago.world.entity.powers.Power;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PowerArgument implements ArgumentType<Power> {
    private static final List<String> EXAMPLES = Arrays.asList(MinejagoPowers.ICE.getId().toString(), MinejagoPowers.FIRE.getId().toString(), MinejagoPowers.EARTH.getId().toString(), MinejagoPowers.LIGHTNING.getId().toString());
    public static final String NOT_FOUND = "argument.minejago.power.not_found";
    private static final DynamicCommandExceptionType UNKNOWN_SKILL_ERROR = new DynamicCommandExceptionType(input -> Component.translatable(NOT_FOUND));

    public PowerArgument() {}

    public static PowerArgument power() {
        return new PowerArgument();
    }

    public static Power getPower(CommandContext<CommandSourceStack> context, String argName) {
        return context.getArgument(argName, Power.class);
    }

    @Override
    public Power parse(StringReader reader) throws CommandSyntaxException {
        int cursor = reader.getCursor();
        ResourceLocation id = ResourceLocation.read(reader);
        Power power = MinejagoRegistries.POWERS.get().getValue(id);

        if (power == null) {
            reader.setCursor(cursor);

            throw UNKNOWN_SKILL_ERROR.createWithContext(reader, id.toString());
        }

        return power;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        StringReader reader = new StringReader(builder.getInput());

        reader.setCursor(builder.getStart());

        builder = builder.createOffset(reader.getCursor());

        SharedSuggestionProvider.suggestResource(MinejagoRegistries.POWERS.get().getKeys(), builder);

        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

}
