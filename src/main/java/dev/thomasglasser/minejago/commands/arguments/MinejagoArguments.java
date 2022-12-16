package dev.thomasglasser.minejago.commands.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MinejagoArguments
{
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, Minejago.MOD_ID);

    public static final RegistryObject<SingletonArgumentInfo<PowerArgument>> POWER_ARGUMENT = register("power", PowerArgument.class, () -> SingletonArgumentInfo.contextFree(PowerArgument::power));

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>, I extends ArgumentTypeInfo<A, T>> RegistryObject<I> register(String id, Class<A> argumentClass, Supplier<I> argumentType) {
        ArgumentTypeInfos.registerByClass(argumentClass, argumentType.get());
        return ARGUMENT_TYPES.register(id, argumentType);
    }
}
