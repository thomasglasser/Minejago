package dev.thomasglasser.minejago.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class MinejagoKeyMappings {
    public static final List<KeyMapping> KEY_MAPPINGS = new ArrayList<>();
    public static final KeyMapping ACTIVATE_SPINJITZU = registerKey("activate_spinjitzu", InputConstants.KEY_N, KeyMapping.CATEGORY_MOVEMENT);

    private static KeyMapping registerKey(String name, int key, String category)
    {
        KeyMapping mapping = new KeyMapping("key." + Minejago.MOD_ID + "." + name, key, category);
        KEY_MAPPINGS.add(mapping);
        return mapping;
    }

    public static void init() {}
}
