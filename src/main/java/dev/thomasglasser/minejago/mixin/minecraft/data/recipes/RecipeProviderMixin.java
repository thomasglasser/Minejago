package dev.thomasglasser.minejago.mixin.minecraft.data.recipes;

import dev.thomasglasser.minejago.data.recipes.MinejagoRecipes;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeProvider.class)
public class RecipeProviderMixin {
    @Inject(method = "run(Lnet/minecraft/data/CachedOutput;Lnet/minecraft/core/HolderLookup$Provider;)Ljava/util/concurrent/CompletableFuture;", at = @At("HEAD"))
    private void run(CachedOutput output, HolderLookup.Provider lookupProvider, CallbackInfoReturnable<CompletableFuture<?>> ci) {
        MinejagoRecipes.lookupProvider = lookupProvider;
    }
}
