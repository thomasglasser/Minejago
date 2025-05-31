package dev.thomasglasser.minejago.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.tommylib.api.client.ClientUtils;
import dev.thomasglasser.tommylib.api.client.ExtendedKeyMapping;
import net.minecraft.client.KeyMapping;

public class MinejagoKeyMappings {
    public static final String CATEGORY_SHADOW_FORM = "key.categories.shadow_form";

    public static final ExtendedKeyMapping ACTIVATE_SPINJITZU = register("activate_spinjitzu", InputConstants.KEY_N, KeyMapping.CATEGORY_MOVEMENT, () -> {});
    public static final ExtendedKeyMapping MEDITATE = register("meditate", InputConstants.KEY_M, KeyMapping.CATEGORY_MOVEMENT, () -> {});
    public static final ExtendedKeyMapping ASCEND = register("ascend", InputConstants.KEY_UP, KeyMapping.CATEGORY_MOVEMENT, () -> {});
    public static final ExtendedKeyMapping DESCEND = register("descend", InputConstants.KEY_DOWN, KeyMapping.CATEGORY_MOVEMENT, () -> {});
    public static final ExtendedKeyMapping OPEN_SKILL_SCREEN = register("open_skill_screen", InputConstants.KEY_K, KeyMapping.CATEGORY_GAMEPLAY, () -> {});
    public static final ExtendedKeyMapping ENTER_SHADOW_FORM = register("enter_shadow_form", InputConstants.KEY_O, CATEGORY_SHADOW_FORM, () -> {});
    public static final ExtendedKeyMapping SWITCH_DIMENSION = register("switch_dimension", InputConstants.KEY_I, CATEGORY_SHADOW_FORM, () -> {});
    public static final ExtendedKeyMapping INCREASE_SCALE = register("increase_scale", InputConstants.KEY_UP, CATEGORY_SHADOW_FORM, () -> {});
    public static final ExtendedKeyMapping DECREASE_SCALE = register("decrease_scale", InputConstants.KEY_DOWN, CATEGORY_SHADOW_FORM, () -> {});
    public static final ExtendedKeyMapping SUMMON_CLONE = register("summon_clone", InputConstants.KEY_C, CATEGORY_SHADOW_FORM, () -> {});
    public static final ExtendedKeyMapping RECALL_CLONES = register("recall_clones", InputConstants.KEY_R, CATEGORY_SHADOW_FORM, () -> {});

    private static ExtendedKeyMapping register(String name, int key, String category, Runnable onClick) {
        return ClientUtils.registerKeyMapping(Minejago.modLoc(name), key, category, onClick);
    }

    public static void init() {}
}
