package dev.thomasglasser.minejago.world.entity.skulkin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AbstractSkulkinVehicle extends Mob implements GeoEntity, Enemy
{
	public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public AbstractSkulkinVehicle(EntityType<? extends AbstractSkulkinVehicle> entityType, Level level)
	{
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createMobAttributes()
				.add(Attributes.MAX_HEALTH, 25.0)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
				.add(Attributes.MOVEMENT_SPEED, 0.45)
				.add(Attributes.ATTACK_DAMAGE, 1.0)
				.add(Attributes.ARMOR, 2.0f)
				.add(Attributes.ATTACK_KNOCKBACK, 0);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar)
	{
		controllerRegistrar.add(DefaultAnimations.genericWalkController(this));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache()
	{
		return cache;
	}

	protected int getMaxPassengers() {
		return 1;
	}

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		Entity entity = getFirstPassenger();
		return entity instanceof LivingEntity livingEntity ? livingEntity : null;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		player.hurt(damageSources().mobAttack(this), (float) getAttribute(Attributes.ATTACK_DAMAGE).getValue());
		return super.mobInteract(player, hand);
	}

	/**
	 * Called every tick so the entity can update its state as required. For example, zombies and skeletons use this to
	 * react to sunlight and start to burn.
	 */
	public void aiStep() {
		if (this.isSunBurnTick()) {
			this.setSecondsOnFire(8);
		}

		super.aiStep();
	}
}
