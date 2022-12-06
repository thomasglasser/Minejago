package dev.thomasglasser.minejago.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class MinejagoClientUtils {
    public static Player getClientPlayerByUUID(UUID uuid) {
        return Minecraft.getInstance().level.getPlayerByUUID(uuid);
    }
}
