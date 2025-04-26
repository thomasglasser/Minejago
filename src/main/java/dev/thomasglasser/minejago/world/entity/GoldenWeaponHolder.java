package dev.thomasglasser.minejago.world.entity;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GoldenWeaponHolder extends Entity implements GeoEntity {
    protected static final RawAnimation ACTIVATE = RawAnimation.begin().thenPlay("misc.activate");

    protected static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(GoldenWeaponHolder.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> HAS_GOLDEN_WEAPON = SynchedEntityData.defineId(GoldenWeaponHolder.class, EntityDataSerializers.BOOLEAN);

    protected final Supplier<ItemStack> pickResult;
    protected final Holder<EntityType<?>> toSpawnType;
    protected final Holder<SoundEvent> soundEvent;
    protected final Supplier<ItemStack> goldenWeapon;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected int activatedTicks = 0;

    public GoldenWeaponHolder(EntityType<? extends GoldenWeaponHolder> entityType, Level level, @Nullable Supplier<ItemStack> pickResult, Holder<EntityType<?>> toSpawnType, Holder<SoundEvent> soundEvent, Supplier<ItemStack> goldenWeapon) {
        super(entityType, level);
        this.pickResult = pickResult == null ? null : Suppliers.memoize(pickResult);
        this.toSpawnType = toSpawnType;
        this.soundEvent = soundEvent;
        this.goldenWeapon = Suppliers.memoize(goldenWeapon);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (state.getAnimatable().isActivated())
                return state.setAndContinue(ACTIVATE);
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!isActivated()) {
            setActivated(true);
            return InteractionResult.sidedSuccess(player.level().isClientSide());
        }
        return super.interact(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (isActivated() && level() instanceof ServerLevel serverLevel) {
            activatedTicks++;
            if (activatedTicks > 100) {
                toSpawnType.value().spawn(serverLevel, blockPosition(), MobSpawnType.TRIGGERED);
                serverLevel.playSound(null, blockPosition(), soundEvent.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
                remove(RemovalReason.DISCARDED);
            } else if (activatedTicks > 35 && hasGoldenWeapon()) {
                setHasGoldenWeapon(false);
                ItemEntity item = new ItemEntity(serverLevel, position().x, position().y + getType().getDimensions().height() + 1, position().z, getGoldenWeapon());
                serverLevel.addFreshEntity(item);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || source.getDirectEntity() instanceof Player player && player.getAbilities().instabuild) {
            kill();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(ACTIVATED, false);
        builder.define(HAS_GOLDEN_WEAPON, true);
    }

    public void setActivated(boolean activated) {
        this.entityData.set(ACTIVATED, activated);
    }

    public boolean isActivated() {
        return this.entityData.get(ACTIVATED);
    }

    public void setHasGoldenWeapon(boolean hasGoldenWeapon) {
        this.entityData.set(HAS_GOLDEN_WEAPON, hasGoldenWeapon);
    }

    public boolean hasGoldenWeapon() {
        return this.entityData.get(HAS_GOLDEN_WEAPON);
    }

    public ItemStack getGoldenWeapon() {
        return goldenWeapon.get().copy();
    }

    public @Nullable ItemStack extractGoldenWeapon() {
        if (this.hasGoldenWeapon() && !this.level().isClientSide) {
            setHasGoldenWeapon(false);
            setActivated(true);
            return getGoldenWeapon();
        }
        return null;
    }

    public Holder<EntityType<?>> getToSpawnType() {
        return toSpawnType;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        return pickResult == null ? null : pickResult.get().copy();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}
}
