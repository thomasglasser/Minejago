package dev.thomasglasser.minejago.world.entity.skulkin;

import dev.thomasglasser.minejago.world.entity.character.Character;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class SkullTruck extends PathfinderMob implements GeoEntity, SmartBrainOwner<SkullTruck>, Enemy
{
	public static final RawAnimation ATTACK_RETRACT = RawAnimation.begin().thenPlay("attack.retract");

	public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	private boolean handExtending = false; // TODO: Implement

	public SkullTruck(EntityType<? extends SkullTruck> entityType, Level level)
	{
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createMobAttributes()
				.add(Attributes.MAX_HEALTH, 100.0)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 2.0)
				.add(Attributes.ARMOR, 4.0f)
				.add(Attributes.ATTACK_KNOCKBACK, 0);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar)
	{
		controllerRegistrar.add(
				DefaultAnimations.genericWalkController(this),
				new AnimationController<>(this, "Cast/Retract", state ->
				{
					if (handExtending)
						return state.setAndContinue(DefaultAnimations.ATTACK_CAST);
					return state.setAndContinue(ATTACK_RETRACT);
				}));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache()
	{
		return cache;
	}

	@Override
	public List<? extends ExtendedSensor<? extends SkullTruck>> getSensors()
	{
		return List.of(
				new NearbyLivingEntitySensor<SkullTruck>().setPredicate((target, entity) -> target instanceof Character && !target.isInvulnerable())
		);
	}

	@Override
	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengers().size() < this.getMaxPassengers();
	}

	protected int getMaxPassengers() {
		return 3;
	}

	@Nullable
	@Override
	public LivingEntity getControllingPassenger() {
		Entity entity = getFirstPassenger();
		return entity instanceof LivingEntity livingEntity ? livingEntity : null;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		player.hurt(damageSources().mobAttack(this), 2);
		return super.mobInteract(player, hand);
	}

	public void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
		int i = this.getPassengers().indexOf(pPassenger);
		if (i >= 0) {
			boolean bl = i == 0;
			float e = 0f;
			float f = 0F;
			float g = (float)((this.isRemoved() ? 0.01F : this.getPassengersRidingOffset()) + pPassenger.getMyRidingOffset());
			if (this.getPassengers().size() > 1) {
				if (!bl) {
					f = -0.7F;
				}

				if (i == 1)
					e += 0.4f;
				else if (i == 2)
					e -= 0.4f;
			}

			Vec3 vec3 = new Vec3(e, 0.0, f).yRot(-this.yBodyRot * (float) (Math.PI / 180.0));
			pCallback.accept(pPassenger, this.getX() + vec3.x, this.getY() + (double)g, this.getZ() + vec3.z);
		}
	}

	@Override
	public double getMyRidingOffset()
	{
		return -1.1;
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

	@Override
	protected void customServerAiStep() {
		tickBrain(this);
	}
}
