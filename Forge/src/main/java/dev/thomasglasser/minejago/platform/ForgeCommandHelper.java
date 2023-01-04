package dev.thomasglasser.minejago.platform;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.thomasglasser.minejago.platform.services.ICommandHelper;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;

import java.util.function.Supplier;

public class ForgeCommandHelper implements ICommandHelper {
    @Override
    public <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void registerArgument(String path, Class<A> clazz, Supplier<ArgumentTypeInfo<A, T>> serializer) {
        ArgumentTypeInfos.registerByClass(clazz, serializer.get());
    }
}
