package dev.thomasglasser.minejago.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.function.Supplier;
import net.minecraft.client.KeyMapping;

public class MinejagoKeyMappings {
    public static final Supplier<KeyMapping> ACTIVATE_SPINJITZU = register("activate_spinjitzu", InputConstants.KEY_N, KeyMapping.CATEGORY_MOVEMENT);
    public static final Supplier<KeyMapping> MEDITATE = register("meditate", InputConstants.KEY_M, KeyMapping.CATEGORY_MOVEMENT);
    public static final Supplier<KeyMapping> ASCEND = register("ascend", InputConstants.KEY_UP, KeyMapping.CATEGORY_MOVEMENT);
    public static final Supplier<KeyMapping> DESCEND = register("descend", InputConstants.KEY_DOWN, KeyMapping.CATEGORY_MOVEMENT);
    public static final Supplier<KeyMapping> OPEN_SKILL_SCREEN = register("open_skill_screen", InputConstants.KEY_K, KeyMapping.CATEGORY_GAMEPLAY);

    private static Supplier<KeyMapping> register(String name, int key, String category) {
        return TommyLibServices.CLIENT.registerKeyMapping(Minejago.modLoc(name), key, category);
    }

    public static void init() {}
}
