package dev.thomasglasser.minejago.commands.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.registration.RegistrationProvider;
import dev.thomasglasser.minejago.registration.RegistryObject;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class MinejagoArguments
{
    public static final RegistrationProvider<ArgumentTypeInfo<?, ?>> ARGUMENTS = RegistrationProvider.get(Registries.COMMAND_ARGUMENT_TYPE, Minejago.MOD_ID);
    public static final RegistryObject<SingletonArgumentInfo<PowerArgument>> POWER_ARGUMENT = register("power", PowerArgument.class, () -> SingletonArgumentInfo.contextFree(PowerArgument::power));

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> RegistryObject<I> register(String id, Class<A> argumentClass, Supplier<I> argumentType) {
        Services.COMMAND.registerArgument(id, argumentClass, argumentType::get);
        return ARGUMENTS.register(id, argumentType);
    }

    public static void init() {}
}
