package dev.thomasglasser.minejago.mixin.net.minecraft.client.model;

import dev.thomasglasser.minejago.world.item.armor.IModeledArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerModel.class)
public class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {
    @Shadow @Final public ModelPart leftSleeve;

    @Shadow @Final public ModelPart rightSleeve;

    public PlayerModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof IModeledArmorItem iModeledArmorItem && iModeledArmorItem.isSkintight())
        {
            this.leftSleeve.visible = false;
            this.rightSleeve.visible = false;
        }
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
    }
}
