package dev.thomasglasser.minejago.world.entity.dragon;

import dev.thomasglasser.minejago.core.component.MinejagoDataComponents;
import dev.thomasglasser.minejago.network.ClientboundOpenDragonInventoryScreenPayload;
import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.tags.MinejagoEntityTypeTags;
import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.attachment.MinejagoAttachmentTypes;
import dev.thomasglasser.minejago.world.entity.MinejagoEntityEvents;
import dev.thomasglasser.minejago.world.entity.element.Element;
import dev.thomasglasser.minejago.world.focus.FocusConstants;
import dev.thomasglasser.minejago.world.focus.FocusData;
import dev.thomasglasser.minejago.world.inventory.DragonInventoryMenu;
import dev.thomasglasser.minejago.world.item.armor.MinejagoArmors;
import dev.thomasglasser.minejago.world.level.storage.ElementData;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import dev.thomasglasser.tommylib.api.world.entity.EntityUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableRangedAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.navigation.SmoothGroundNavigation;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

// TODO: Implement flying like happy ghast
public abstract class Dragon extends TamableAnimal implements GeoEntity, SmartBrainOwner<Dragon>, RangedAttackMob, Saddleable, ContainerListener, HasCustomInventoryScreen {
    public static final RawAnimation LIFT = RawAnimation.begin().thenPlay("move.lift");
    private static final EntityDataAccessor<Boolean> DATA_SHOOTING = SynchedEntityData.defineId(Dragon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SADDLED = SynchedEntityData.defineId(Dragon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_CHESTED = SynchedEntityData.defineId(Dragon.class, EntityDataSerializers.BOOLEAN);
    public static final int TICKS_PER_FLAP = 39;
    public static final double HEAL_BOND = 0.05;
    public static final double FOOD_BOND = 0.1;
    public static final double TREAT_BOND = 0.15;
    public static final double TALK_BOND = 1;

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Map<Player, Double> bond = new HashMap<>();
    private final TagKey<Item> protectingItems;

    private boolean isLiftingOff;
    private int flyingTicks = 0;
    private double speedMultiplier = 1;

    protected SimpleContainer inventory;

    public Dragon(EntityType<? extends Dragon> entityType, Level level, ResourceKey<Element> element, TagKey<Item> protectingItems) {
        super(entityType, level);
        new ElementData(element, false).save(this, false);
        this.protectingItems = protectingItems;
        setTame(false, false);
        createInventory();
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new SmoothGroundNavigation(this, level) {
            @Override
            protected boolean canUpdatePath() {
                return super.canUpdatePath() || !isNoGravity();
            }
        };
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ARMOR, 4.0f)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.FLYING_SPEED, 2.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(
                new AnimationController<>(this, "Walk/Idle/Fly/Lift", 0, state -> {
                    if (isLiftingOff) {
                        return state.setAndContinue(LIFT);
                    } else if (state.getAnimatable().isNoGravity()) {
                        return state.setAndContinue(DefaultAnimations.FLY);
                    } else if (state.isMoving()) {
                        return state.setAndContinue(DefaultAnimations.WALK);
                    }
                    return state.setAndContinue(DefaultAnimations.IDLE);
                }),
                new AnimationController<>(this, "Shoot", 0, state -> {
                    if (isShooting())
                        return state.setAndContinue(DefaultAnimations.ATTACK_SHOOT);
                    else
                        return PlayState.STOP;
                }));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SHOOTING, false);
        builder.define(DATA_SADDLED, false);
        builder.define(DATA_CHESTED, false);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.tickBrain(this);
    }

    @Override
    public List<ExtendedSensor<Dragon>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<Dragon>().setPredicate((target, dragon) -> {
                    if (target instanceof Enemy) return true;
                    if (target.isAlliedTo(dragon)) return false;
                    LivingEntity owner = dragon.getOwner();
                    if (owner == null) {
                        return EntityUtils.hasAnyInInventory(target, stack -> stack.is(protectingItems)) || (target instanceof Player player && getBond(player) < 0) || (target instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() instanceof Player targetOwner && getBond(targetOwner) < 0);
                    } else {
                        if (target.isAlliedTo(owner)) return false;
                        if (target instanceof TamableAnimal tamableAnimal && tamableAnimal.getOwner() == dragon.getOwner()) return false;
                        if (target.getLastHurtByMob() != null && target.getLastHurtByMob().is(dragon.getOwner())) return true;
                        if (owner.getLastHurtByMob() != null && owner.getLastHurtByMob().is(target)) return true;
                        if (BrainUtils.hasMemory(target.getBrain(), MemoryModuleType.ATTACK_TARGET)) {
                            return BrainUtils.getTargetOfEntity(target) == dragon.getOwner();
                        } else if (target instanceof Mob mob) {
                            return mob.getTarget() == dragon.getOwner();
                        }
                    }

                    return false;
                }),
                new NearbyPlayersSensor<>(),
                new HurtBySensor<>());
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (getOwnerUUID() != null)
            return super.isAlliedTo(entity);
        return this.isAlliedTo(entity.getTeam()) || entity.getType().is(MinejagoEntityTypeTags.DRAGONS) || entity.getType().is(MinejagoEntityTypeTags.NINJA_FRIENDS);
    }

    @Override
    public BrainActivityGroup<Dragon> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new SetWalkTargetToAttackTarget<>(),
                new MoveToWalkTarget<>(),
                new LookAtTargetSink(40, 300), 														// Look at the look target
                new FloatToSurfaceOfFluid<>());																					// Move to the current walk target
    }

    @Override
    public BrainActivityGroup<Dragon> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(                // Run only one of the below behaviours, trying each one in order. Include explicit generic typing because javac is silly
                        new TargetOrRetaliate<>().attackablePredicate(entity -> entity.isAlive() && (!(entity instanceof Player player) || !player.isCreative() && !(this.getOwner() == player))),                        // Set the attack target
                        new SetPlayerLookTarget<>(),                    // Set the look target to a nearby player if available
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>( 								// Run only one of the below behaviours, picked at random
                        new SetRandomWalkTarget<>().speedModifier(1), 				// Set the walk target to a nearby random pathable location
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))); // Don't walk anywhere
    }

    @Override
    public BrainActivityGroup<? extends Dragon> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((entity, target) -> target instanceof Player player ? (player.isCreative() || player.isSpectator() || (getBond(player) >= 0 && !EntityUtils.hasAnyInInventory(player, stack -> stack.is(protectingItems)))) : !EntityUtils.hasAnyInInventory(target, stack -> stack.is(protectingItems))), 	 // Invalidate the attack target if it's no longer applicable
                new FirstApplicableBehaviour<>( 																							  	 // Run only one of the below behaviours, trying each one in order
                        new AnimatableMeleeAttack<>(0).whenStarting(entity -> setAggressive(false)), // Melee attack
                        new AnimatableRangedAttack<Dragon>(20).whenStarting(dragon -> dragon.setShooting(true)).whenStopping(dragon -> dragon.setShooting(false)))	 												 // Fire a bow, if holding one
        );
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isAlive()) {
            if (this.isVehicle() && hasControllingPassenger()) {
                Vec3 velocity = this.getDeltaMovement();
//                switch (flight) {
//                    case ASCENDING:
//                        this.setDeltaMovement(velocity.x, getVerticalSpeed(), velocity.z);
//                        break;
//                    case DESCENDING:
//                        this.setDeltaMovement(velocity.x, -getVerticalSpeed(), velocity.z);
//                        if (!this.level().getBlockState(this.blockPosition().below()).isAir() && isNoGravity()) {
//                            setNoGravity(false);
//                            flyingTicks = 0;
//                        }
//                        break;
//                    case HOVERING:
//                        break;
//                }
                LivingEntity livingentity = this.getControllingPassenger();
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;
                float f = livingentity.xxa * 0.5F;
                float f1 = livingentity.zza;
                if (f1 <= 0.0F) {
                    f1 *= 0.25F;
                }
                super.travel(new Vec3(f, pTravelVector.y, f1));
            } else {
                Vec3 velocity = this.getDeltaMovement();
                this.setDeltaMovement(velocity.x, -getVerticalSpeed(), velocity.z);
                super.travel(pTravelVector);
            }
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    protected int getMaxPassengers() {
        return 2;
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.isSaddled()) {
            Entity entity = this.getFirstPassenger();
            if (entity instanceof Player player) {
                return player;
            }
        }

        return super.getControllingPassenger();
    }

    public double getVerticalSpeed() {
        return 0.5;
    }

    @Override
    public float getSpeed() {
        if (isNoGravity())
            return (float) (getAttributeValue(Attributes.FLYING_SPEED) * speedMultiplier);
        return (float) (getAttributeValue(Attributes.MOVEMENT_SPEED) * speedMultiplier);
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        if (pY > 0.01 && pOnGround) {
            this.setNoGravity(false);
        }
    }

//    @Override
//    public void ascend() {
//        if (flyingTicks == 0) {
//            isLiftingOff = true;
//        }
//        this.setNoGravity(true);
//        this.flight = Flight.ASCENDING;
//    }
//
//    @Override
//    public void descend() {
//        this.flight = Flight.DESCENDING;
//    }
//
//    @Override
//    public void stop() {
//        this.flight = Flight.HOVERING;
//    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND && !player.level().isClientSide) {
            ItemStack stack = player.getItemInHand(hand);
            boolean ownedBy = isOwnedBy(player);
            double bond = getBond(player);
            FocusData focusData = player.getData(MinejagoAttachmentTypes.FOCUS);
            if (isFood(stack)) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                if (ownedBy) {
                    if (this.getHealth() < this.getMaxHealth() && stack.get(DataComponents.FOOD) != null) {
                        this.heal(stack.get(DataComponents.FOOD).nutrition());
                        increaseBond(player, HEAL_BOND);
                        return InteractionResult.SUCCESS;
                    }
                }

                increaseBond(player, stack.is(MinejagoItemTags.DRAGON_TREATS) ? TREAT_BOND : FOOD_BOND);
                return InteractionResult.SUCCESS;
            } else {
                Holder<Element> element = level().holderOrThrow(player.getData(MinejagoAttachmentTypes.ELEMENT).element());
                if (bond <= 50 && focusData.getFocusLevel() >= FocusConstants.DRAGON_TALK_LEVEL && random.nextInt(10) < 2) {
                    double i = TALK_BOND;
                    if (element.is(getData(MinejagoAttachmentTypes.ELEMENT).element())) i += 1.5;
                    increaseBond(player, i);
                    player.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_DRAGON_TALK);
                    return InteractionResult.SUCCESS;
                } else if ((bond >= 10 && focusData.getFocusLevel() >= FocusConstants.DRAGON_TAME_LEVEL) || player.getAbilities().instabuild) {
                    if (ownedBy || hasControllingPassenger()) {
                        if (!this.hasChest() && stack.is(Items.CHEST)) {
                            this.equipChest(player, stack);
                            return InteractionResult.SUCCESS;
                        } else if (stack.is(Items.SADDLE) && isSaddleable() && !isSaddled())
                            this.equipSaddle(stack, SoundSource.PLAYERS);
                        else
                            player.startRiding(this);
                    } else if (element.is(getData(MinejagoAttachmentTypes.ELEMENT).element()) && focusData.getFocusLevel() >= 14) {
                        if (element.value().hasSpecialSets()) {
                            List<ItemStack> armorStacks = MinejagoArmors.DRAGON_EXTREME_GI_SET.getAllAsStacks();
                            for (int i = 0; i < armorStacks.size(); i++) {
                                ItemStack dx = armorStacks.get(i);
                                dx.set(MinejagoDataComponents.ELEMENT, element.getKey());
                                inventory.setItem(i + 1, dx);
                            }
                        }
                        player.getData(MinejagoAttachmentTypes.FOCUS).addExhaustion(FocusConstants.EXHAUSTION_DRAGON_TAME);
                        tame(player);
                        if (player.level() instanceof ServerLevel serverLevel) {
                            double d0 = this.random.nextGaussian() * 0.02D;
                            double d1 = this.random.nextGaussian() * 0.02D;
                            double d2 = this.random.nextGaussian() * 0.02D;
                            for (int i = 0; i < 5; i++) {
                                serverLevel.sendParticles(ParticleTypes.HEART, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), 1, d0, d1, d2, 1);
                            }
                            serverLevel.playSound(null, this.blockPosition(), MinejagoSoundEvents.EARTH_DRAGON_TAME.get(), SoundSource.AMBIENT);
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        super.positionRider(passenger, callback);
        clampRotation(this);
    }

    protected void clampRotation(Entity entityToUpdate) {
        entityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
        float g = Mth.clamp(f, -105.0F, 105.0F);
        entityToUpdate.yRotO += g - f;
        entityToUpdate.setYRot(entityToUpdate.getYRot() + g - f);
        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
    }

    @Override
    public void tick() {
        super.tick();
        if (isNoGravity()) flyingTicks++;
        if (flyingTicks > 30)
            isLiftingOff = false;
        if (getOwner() instanceof Player player && getBond(player) <= -10) {
            setTame(false, true);
            this.setOwnerUUID(null);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Player player)
            decreaseBond(player, amount * 2);
        return super.hurt(source, amount);
    }

    public double getPerceivedTargetDistanceSquareForMeleeAttack(LivingEntity entity) {
        return this.distanceToSqr(entity.position()) * 2.0;
    }

    public double increaseBond(Player player, double amount) {
        double oldBond = getBond(player);

        if (isOwnedBy(player)) amount *= 2;
        bond.put(player, oldBond + amount);
        recalculateBoosts();

        if (player.level() instanceof ServerLevel serverLevel) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            for (int i = 0; i < 5; i++) {
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), 1, d0, d1, d2, 1);
            }
            playSound(MinejagoSoundEvents.EARTH_DRAGON_BOND_UP.get());
        }

        return bond.get(player);
    }

    public double decreaseBond(Player player, double amount) {
        double oldBond = getBond(player);

        if (isOwnedBy(player)) amount /= 1.5;
        bond.put(player, oldBond - amount);
        recalculateBoosts();

        if (player.level() instanceof ServerLevel serverLevel) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            for (int i = 0; i < 5; i++) {
                serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getRandomX(1.0D), this.getRandomY(), this.getRandomZ(1.0D), 1, d0, d1, d2, 1);
            }
            playSound(MinejagoSoundEvents.EARTH_DRAGON_BOND_DOWN.get());
        }

        return bond.get(player);
    }

    public double getBond(Player player) {
        if (bond.containsKey(player))
            return bond.get(player);
        bond.put(player, 0.0);
        return bond.get(player);
    }

    public Map<Player, Double> getBondMap() {
        return bond;
    }

    private void recalculateBoosts() {
        if (getOwner() instanceof Player player) {
            double bond = getBond(player);
            if (bond >= 100) {
                speedMultiplier = 2;
            } else if (bond >= 75) {
                speedMultiplier = 1.5;
            } else if (bond >= 50) {
                speedMultiplier = 1.25;
            } else if (bond >= 0) {
                speedMultiplier = 1;
            } else {
                speedMultiplier = 0.5;
            }
        }
    }

    @Override
    protected void onFlap() {
        super.onFlap();
        if (this.level().isClientSide && !this.isSilent() && getFlapSound() != null) {
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), getFlapSound(), this.getSoundSource(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
        }
    }

    @Override
    protected boolean isFlapping() {
        return flyingTicks > 1 && tickCount % TICKS_PER_FLAP == 0;
    }

    @Nullable
    public abstract SoundEvent getFlapSound();

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        Vec3 vec33 = this.getViewVector(1.0F);
        double l = this.getX() - vec33.x;
        double m = this.getY(0.5) + 0.5;
        double n = this.getZ() - vec33.z;
        double o = target.getX() - l;
        double p = target.getY(0.5) - m;
        double q = target.getZ() - n;
        AbstractHurtingProjectile projectile = getRangedAttackProjectile(o, p, q);
        projectile.moveTo(l, m, n, 0.0F, 0.0F);
        level().addFreshEntity(projectile);
        if (level() instanceof ServerLevel serverLevel && getShootSound() != null) {
            serverLevel.playSound(null, getOnPos(), getShootSound(), getSoundSource(), 1.0F, 1.0F);
        }
    }

    protected abstract AbstractHurtingProjectile getRangedAttackProjectile(double a, double b, double c);

    protected abstract SoundEvent getShootSound();

    public boolean isShooting() {
        return this.entityData.get(DATA_SHOOTING);
    }

    public void setShooting(boolean shooting) {
        this.entityData.set(DATA_SHOOTING, shooting);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(MinejagoItemTags.DRAGON_FOODS) || stack.is(MinejagoItemTags.DRAGON_TREATS);
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && this.isTame();
    }

    @Override
    public void equipSaddle(ItemStack stack, @org.jetbrains.annotations.Nullable SoundSource soundSource) {
        inventory.setItem(0, stack);
    }

    @Override
    public boolean isSaddled() {
        return this.entityData.get(DATA_SADDLED);
    }

    public int getInventoryColumns() {
        return hasChest() ? 5 : 0;
    }

    public int getInventorySize() {
        return getInventorySize(this.getInventoryColumns());
    }

    public int getInventorySize(int columns) {
        return hasChest() ? columns * 3 + 1 : 5;
    }

    protected void createInventory() {
        SimpleContainer simplecontainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simplecontainer != null) {
            simplecontainer.removeListener(this);
            int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

            for (int j = 0; j < i; j++) {
                ItemStack itemstack = simplecontainer.getItem(j);
                if (!itemstack.isEmpty()) {
                    this.inventory.setItem(j, itemstack.copy());
                }
            }
        }

        this.inventory.addListener(this);
        this.syncSaddleToClients();
    }

    protected void syncSaddleToClients() {
        if (!this.level().isClientSide) {
            entityData.set(DATA_SADDLED, !inventory.getItem(0).isEmpty());
        }
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        if (player instanceof ServerPlayer serverPlayer && (!this.isVehicle() || this.hasPassenger(player)) && this.isTame()) {
            if (serverPlayer.containerMenu != serverPlayer.inventoryMenu) {
                serverPlayer.closeContainer();
            }

            serverPlayer.nextContainerCounter();
            int i = getInventoryColumns();
            TommyLibServices.NETWORK.sendToClient(new ClientboundOpenDragonInventoryScreenPayload(getId(), getInventoryColumns(), serverPlayer.containerCounter), serverPlayer);
            serverPlayer.containerMenu = new DragonInventoryMenu(serverPlayer.containerCounter, player.getInventory(), inventory, this, i);
            serverPlayer.initMenu(player.containerMenu);
            NeoForge.EVENT_BUS.post(new PlayerContainerEvent.Open(serverPlayer, serverPlayer.containerMenu));
        }
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        if (this.inventory != null) {
            for (int i = 0; i < this.inventory.getContainerSize(); i++) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty() && !EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) {
                    this.spawnAtLocation(itemstack);
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (!this.inventory.getItem(0).isEmpty()) {
            compound.put("SaddleItem", this.inventory.getItem(0).save(this.registryAccess()));
        }
        compound.putBoolean("Chested", this.hasChest());
        if (this.hasChest()) {
            ListTag listtag = new ListTag();

            for (int i = 1; i < this.inventory.getContainerSize(); i++) {
                ItemStack itemstack = this.inventory.getItem(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag compoundtag = new CompoundTag();
                    compoundtag.putByte("Slot", (byte) (i - 1));
                    listtag.add(itemstack.save(this.registryAccess(), compoundtag));
                }
            }

            compound.put("Items", listtag);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SaddleItem", 10)) {
            ItemStack itemstack = ItemStack.parse(this.registryAccess(), compound.getCompound("SaddleItem")).orElse(ItemStack.EMPTY);
            if (itemstack.is(Items.SADDLE)) {
                this.inventory.setItem(0, itemstack);
            }
        }
        this.setChest(compound.getBoolean("Chested"));
        this.createInventory();
        if (this.hasChest()) {
            ListTag listtag = compound.getList("Items", 10);

            for (int i = 0; i < listtag.size(); i++) {
                CompoundTag compoundtag = listtag.getCompound(i);
                int j = compoundtag.getByte("Slot") & 255;
                if (j < this.inventory.getContainerSize() - 1) {
                    this.inventory.setItem(j + 1, ItemStack.parse(this.registryAccess(), compoundtag).orElse(ItemStack.EMPTY));
                }
            }
        }

        this.syncSaddleToClients();
    }

    public boolean hasChest() {
        return this.entityData.get(DATA_CHESTED);
    }

    public void setChest(boolean chested) {
        this.entityData.set(DATA_CHESTED, chested);
    }

    @Override
    public SlotAccess getSlot(int slot) {
        return slot == 499 ? new SlotAccess() {
            @Override
            public ItemStack get() {
                return hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
            }

            @Override
            public boolean set(ItemStack p_149485_) {
                if (p_149485_.isEmpty()) {
                    if (hasChest()) {
                        setChest(false);
                        createInventory();
                    }

                    return true;
                } else if (p_149485_.is(Items.CHEST)) {
                    if (!hasChest()) {
                        setChest(true);
                        createInventory();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } : super.getSlot(slot);
    }

    private void equipChest(Player player, ItemStack chestStack) {
        this.setChest(true);
        this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        chestStack.consume(1, player);
        this.createInventory();
    }

    public boolean hasInventoryChanged(Container inventory) {
        return this.inventory != inventory;
    }

    @Override
    public void containerChanged(Container invBasic) {
        boolean flag = this.isSaddled();
        this.syncSaddleToClients();
        if (this.tickCount > 20 && !flag && this.isSaddled()) {
            this.playSound(this.getSaddleSoundEvent(), 0.5F, 1.0F);
        }
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        if (isSaddled())
            return super.getPassengerRidingPosition(entity).add(0, 0.0625, 0);
        return super.getPassengerRidingPosition(entity);
    }
}
