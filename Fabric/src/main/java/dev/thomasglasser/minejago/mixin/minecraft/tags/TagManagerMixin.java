package dev.thomasglasser.minejago.mixin.minecraft.tags;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TagManager.class)
public class TagManagerMixin {
    @Inject(method = "getTagDir", at = @At("HEAD"), cancellable = true)
    private static void getTagDir(ResourceKey<? extends Registry<?>> resourceKey, CallbackInfoReturnable<String> cir)
    {
        if (resourceKey.location().getNamespace().equals(Minejago.MOD_ID))
        {
            cir.setReturnValue("tags/" + resourceKey.location().getNamespace() + "/" + resourceKey.location().getPath());
        }
    }
}
