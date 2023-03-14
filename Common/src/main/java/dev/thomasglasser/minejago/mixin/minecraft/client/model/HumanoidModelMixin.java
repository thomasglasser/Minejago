package dev.thomasglasser.minejago.mixin.minecraft.client.model;

import dev.thomasglasser.minejago.world.item.armor.IGeoArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity>
{
    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightArm;

    @Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/LivingEntity;FFF)V", at = @At("TAIL"))
    private void minejago_prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick, CallbackInfo ci)
    {
        if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof IGeoArmorItem iGeoArmorItem && iGeoArmorItem.isSkintight())
        {
            this.leftArm.visible = false;
            this.rightArm.visible = false;
        }
    }
}
