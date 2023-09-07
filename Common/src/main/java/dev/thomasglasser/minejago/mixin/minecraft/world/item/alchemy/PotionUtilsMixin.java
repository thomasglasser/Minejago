package dev.thomasglasser.minejago.mixin.minecraft.world.item.alchemy;

import dev.thomasglasser.minejago.world.effect.MinejagoMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(PotionUtils.class)
public class PotionUtilsMixin {
    @ModifyVariable(method = "addPotionTooltip(Ljava/util/List;Ljava/util/List;F)V", argsOnly = true, index = 0, at = @At("HEAD"))
    private static List<MobEffectInstance> minejago_addPotionTooltip(List<MobEffectInstance> value)
    {
        value.removeIf(instance -> instance.getEffect() instanceof MinejagoMobEffects.EmptyMobEffect);
        return value;
    }
}
