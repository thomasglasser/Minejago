package dev.thomasglasser.minejago.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import net.minecraft.client.KeyMapping;

public class MinejagoKeyMappings {
    public static final KeyMapping ACTIVATE_SPINJITZU = register("activate_spinjitzu", InputConstants.KEY_N, KeyMapping.CATEGORY_MOVEMENT);
    public static final KeyMapping MEDITATE = register("meditate", InputConstants.KEY_M, KeyMapping.CATEGORY_MOVEMENT);
    public static final KeyMapping ASCEND = register("ascend", InputConstants.KEY_UP, KeyMapping.CATEGORY_MOVEMENT);
    public static final KeyMapping DESCEND = register("descend", InputConstants.KEY_DOWN, KeyMapping.CATEGORY_MOVEMENT);

    private static KeyMapping register(String name, int key, String category)
    {
        return ClientUtils.registerKeyMapping(Minejago.modLoc(name), key, category);
    }

    public static void init() {}
}
