package dev.thomasglasser.minejago.world.effect;

import dev.thomasglasser.minejago.Minejago;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MinejagoMobEffects
{
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Minejago.MOD_ID);

    public static final RegistryObject<MobEffect> CURE = MOB_EFFECTS.register("instant_cure", () -> new InstantenousMobEffect(MobEffectCategory.BENEFICIAL, 16777215)
    {
        @Override
        public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
            List<MobEffectInstance> effects = pLivingEntity.getActiveEffects().stream().toList();
            pLivingEntity.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
            for (MobEffectInstance effect : effects)
            {
                if (effect.getCurativeItems().contains(new ItemStack(Items.MILK_BUCKET)) && effect.getAmplifier() - (pAmplifier + 1) >= 0) pLivingEntity.addEffect(new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier() - (pAmplifier + 1)));
            }
        }
    });
}
