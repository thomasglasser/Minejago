package dev.thomasglasser.minejago.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import java.util.function.Supplier;
import net.minecraft.client.KeyMapping;

public class MinejagoKeyMappings {
    public static final String CATEGORY_SHADOW_FORM = "key.categories.shadow_form";

    public static final Supplier<KeyMapping> ACTIVATE_SPINJITZU = register("activate_spinjitzu", InputConstants.KEY_N, KeyMapping.CATEGORY_MOVEMENT);
    public static final Supplier<KeyMapping> MEDITATE = register("meditate", InputConstants.KEY_M, KeyMapping.CATEGORY_MOVEMENT);
    public static final Supplier<KeyMapping> ASCEND = register("ascend", InputConstants.KEY_UP, KeyMapping.CATEGORY_MOVEMENT);
    public static final Supplier<KeyMapping> DESCEND = register("descend", InputConstants.KEY_DOWN, KeyMapping.CATEGORY_MOVEMENT);
    public static final Supplier<KeyMapping> OPEN_SKILL_SCREEN = register("open_skill_screen", InputConstants.KEY_K, KeyMapping.CATEGORY_GAMEPLAY);
    public static final Supplier<KeyMapping> ENTER_SHADOW_FORM = register("enter_shadow_form", InputConstants.KEY_O, CATEGORY_SHADOW_FORM);
    public static final Supplier<KeyMapping> SWITCH_DIMENSION = register("switch_dimension", InputConstants.KEY_I, CATEGORY_SHADOW_FORM);
    public static final Supplier<KeyMapping> INCREASE_SCALE = register("increase_scale", InputConstants.KEY_UP, CATEGORY_SHADOW_FORM);
    public static final Supplier<KeyMapping> DECREASE_SCALE = register("decrease_scale", InputConstants.KEY_DOWN, CATEGORY_SHADOW_FORM);

    private static Supplier<KeyMapping> register(String name, int key, String category) {
        return TommyLibServices.CLIENT.registerKeyMapping(Minejago.modLoc(name), key, category);
    }

    public static void init() {}
}
