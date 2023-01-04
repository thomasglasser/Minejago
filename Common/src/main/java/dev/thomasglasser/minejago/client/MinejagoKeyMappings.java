package dev.thomasglasser.minejago.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.KeyMapping;

public class MinejagoKeyMappings {
    public static final KeyMapping ACTIVATE_SPINJITZU = registerKey("activate_spinjitzu", InputConstants.KEY_N, KeyMapping.CATEGORY_MOVEMENT);

    private static KeyMapping registerKey(String name, int key, String category)
    {
        return new KeyMapping("key." + Minejago.MOD_ID + "." + name, key, category);
    }
}
