package dev.thomasglasser.minejago.mixin.minecraft.client.renderer.entity.player;

import dev.thomasglasser.tommylib.api.world.item.armor.GeoArmorItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{
	private PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f)
	{
		super(context, entityModel, f);
	}

	@Inject(method = "setModelProperties", at = @At("TAIL"))
	private void minejago_setModelProperties(AbstractClientPlayer clientPlayer, CallbackInfo ci)
	{
		PlayerModel<AbstractClientPlayer> playerModel = getModel();
		if (clientPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof GeoArmorItem geoArmorItem && geoArmorItem.isSkintight())
		{
			playerModel.leftSleeve.visible = false;
			playerModel.rightSleeve.visible = false;
			playerModel.jacket.visible = false;
			playerModel.body.visible = false;
		}
		if (clientPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof GeoArmorItem geoArmorItem && geoArmorItem.isSkintight())
		{
			playerModel.hat.visible = false;
			if (Minecraft.getInstance().screen instanceof EffectRenderingInventoryScreen<?>)
			{
				playerModel.head.xScale = 0.98f;
				playerModel.head.yScale = 0.98f;
				playerModel.head.zScale = 0.98f;
			}
			else
			{
				playerModel.head.xScale = 1.0f;
				playerModel.head.yScale = 1.0f;
				playerModel.head.zScale = 1.0f;
			}
		}
		if (clientPlayer.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof GeoArmorItem iGeoArmorBoots && iGeoArmorBoots.isSkintight() || clientPlayer.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof GeoArmorItem iGeoArmorLeggings && iGeoArmorLeggings.isSkintight())
		{
			playerModel.rightPants.visible = false;
			playerModel.leftPants.visible = false;
		}
	}
}
