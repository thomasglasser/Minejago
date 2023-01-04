package dev.thomasglasser.minejago.platform;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.services.ICommandHelper;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;

import java.util.function.Supplier;

public class FabricCommandHelper implements ICommandHelper {
    @Override
    public <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void registerArgument(String path, Class<A> clazz, Supplier<ArgumentTypeInfo<A, T>> serializer) {
        ArgumentTypeRegistry.registerArgumentType(Minejago.modLoc(path), clazz, serializer.get());
    }
}
