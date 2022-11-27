package dev.thomasglasser.minejago.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.thomasglasser.minejago.Minejago;
import net.minecraft.client.KeyMapping;
import net.minecraft.data.BuiltinRegistries;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MinejagoKeyMappings {
    public static final KeyMapping ACTIVATE_SPINJITZU = registerKey("activate_spinjitzu", KeyConflictContext.IN_GAME, InputConstants.KEY_N, KeyMapping.CATEGORY_MOVEMENT);

    private static KeyMapping registerKey(String name, KeyConflictContext context, int key, String category)
    {
        return new KeyMapping("key." + Minejago.MOD_ID + "." + name, context, InputConstants.Type.KEYSYM.getOrCreate(key), category);
    }
}
