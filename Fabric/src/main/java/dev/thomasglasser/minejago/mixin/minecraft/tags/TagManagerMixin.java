package dev.thomasglasser.minejago.mixin.minecraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
        if (!resourceKey.location().getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE))
        {
            cir.setReturnValue("tags/" + resourceKey.location().getNamespace() + "/" + resourceKey.location().getPath());
        }
    }
}
