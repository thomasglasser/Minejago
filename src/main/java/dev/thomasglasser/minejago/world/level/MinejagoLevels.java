package dev.thomasglasser.minejago.world.level;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class MinejagoLevels {
    public static final ResourceKey<Level> UNDERWORLD = create("underworld");

    private static ResourceKey<Level> create(String name) {
        return ResourceKey.create(Registries.DIMENSION, Minejago.modLoc(name));
    }

    public static ServerLevel get(MinecraftServer server, ResourceKey<Level> key) {
        return server.getLevel(key);
    }

    public static ServerLevel underworld(MinecraftServer server) {
        return get(server, UNDERWORLD);
    }
}
