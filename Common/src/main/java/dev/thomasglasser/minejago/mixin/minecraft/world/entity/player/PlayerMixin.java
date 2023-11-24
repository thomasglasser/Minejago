package dev.thomasglasser.minejago.mixin.minecraft.world.entity.player;

import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.focus.FocusDataHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements FocusDataHolder
{
	@Shadow @Final private Abilities abilities;

	private PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level)
	{
		super(entityType, level);
	}

	@Unique
	FocusData focusData = new FocusData();

	@Override
	public FocusData getFocusData()
	{
		return focusData;
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	private void minejago_addAdditionalSaveData(CompoundTag compound, CallbackInfo ci)
	{
		focusData.addAdditionalSaveData(compound);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	private void minejago_readAdditionalSaveData(CompoundTag compound, CallbackInfo ci)
	{
		focusData.readAdditionalSaveData(compound);
	}

	@Inject(method = "causeFoodExhaustion", at = @At("TAIL"))
	private void minejago_causeFoodExhaustion(float exhaustion, CallbackInfo ci)
	{
		if (!abilities.invulnerable) {
			if (!level().isClientSide) {
				focusData.addExhaustion(exhaustion);
			}
		}
	}
}
