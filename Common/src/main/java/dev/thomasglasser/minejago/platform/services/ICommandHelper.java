package dev.thomasglasser.minejago.platform.services;

import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface ICommandHelper
{
    <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void registerArgument(String path, Class<A> clazz, Supplier<ArgumentTypeInfo<A, T>> serializer);
}
