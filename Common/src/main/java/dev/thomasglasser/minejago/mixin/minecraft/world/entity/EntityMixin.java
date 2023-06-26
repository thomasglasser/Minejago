package dev.thomasglasser.minejago.mixin.minecraft.world.entity;

import dev.thomasglasser.minejago.platform.Services;
import dev.thomasglasser.minejago.world.entity.IDataHolder;
import dev.thomasglasser.minejago.world.item.armor.IGeoArmorItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Entity.class)
public class EntityMixin implements IDataHolder
{
    @Shadow @Final protected RandomSource random;
    private final Entity INSTANCE = ((Entity)(Object)this);

    private final CompoundTag persistentData = new CompoundTag();

    @Unique(silent = true)
    @Override
    public CompoundTag getPersistentData() {
        return persistentData;
    }

    @Inject(method = "playerTouch", at = @At("TAIL"))
    private void minejago_playerTouch(Player player, CallbackInfo ci)
    {
        if (Services.DATA.getSpinjitzuData(player).active() && INSTANCE instanceof LivingEntity livingEntity)
        {
            livingEntity.knockback(random.nextDouble(), player.getX() - INSTANCE.getX(), player.getZ() - INSTANCE.getZ());
        }
    }

    @Inject(method = "dampensVibrations", at = @At("HEAD"), cancellable = true)
    private void minejago_dampensVibrations(CallbackInfoReturnable<Boolean> cir)
    {
        AtomicBoolean flag = new AtomicBoolean(true);

        INSTANCE.getArmorSlots().forEach(stack ->
                {
                    if (stack.getItem() instanceof IGeoArmorItem iGeoArmorItem)
                    {
                        if (!iGeoArmorItem.isGi()) flag.set(false);
                    }
                    else
                    {
                        flag.set(false);
                    }
                }
        );

        if (flag.get()) cir.setReturnValue(true);
    }
}
