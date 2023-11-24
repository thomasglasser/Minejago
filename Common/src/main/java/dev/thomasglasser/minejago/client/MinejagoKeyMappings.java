package dev.thomasglasser.minejago.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.KeyMapping;

import java.util.ArrayList;
import java.util.List;

public class MinejagoKeyMappings {
    public static final List<KeyMapping> KEY_MAPPINGS = new ArrayList<>();
    public static final KeyMapping ACTIVATE_SPINJITZU = registerKey("activate_spinjitzu", InputConstants.KEY_N, KeyMapping.CATEGORY_MOVEMENT);
    public static final KeyMapping MEDITATE = registerKey("meditate", InputConstants.KEY_M, KeyMapping.CATEGORY_MOVEMENT);
    public static final KeyMapping ASCEND = registerKey("ascend", InputConstants.KEY_UP, KeyMapping.CATEGORY_MOVEMENT);
    public static final KeyMapping DESCEND = registerKey("descend", InputConstants.KEY_DOWN, KeyMapping.CATEGORY_MOVEMENT);

    private static KeyMapping registerKey(String name, int key, String category)
    {
        KeyMapping mapping = new KeyMapping("key." + Minejago.MOD_ID + "." + name, key, category);
        KEY_MAPPINGS.add(mapping);
        return mapping;
    }

    public static void init() {}
}
