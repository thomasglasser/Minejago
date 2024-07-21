package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class AbstractSpinjitzuCourseElement<T extends AbstractSpinjitzuCourseElement<T>> extends Entity implements GeoEntity {
    public static final String DEPLOY_KEY = "deploy";
    public static final String REST_KEY = "rest";

    protected static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(AbstractSpinjitzuCourseElement.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(AbstractSpinjitzuCourseElement.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(AbstractSpinjitzuCourseElement.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Boolean> DATA_ID_ACTIVE = SynchedEntityData.defineId(AbstractSpinjitzuCourseElement.class, EntityDataSerializers.BOOLEAN);

    private static final int DEPLOY_ANIMATION_LENGTH = 50;

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected List<SpinjitzuCourseElementPart<T>> subEntities;
    protected int activeAnimationTicks = 0;

    private int signalAround = 0;
    private int activeTicks = 0;

    public AbstractSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.subEntities = List.of();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        } else if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.setHurtDir(-this.getHurtDir());
            this.setHurtTime(10);
            this.markHurt();
            this.setDamage(this.getDamage() + amount * 10.0F);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
            boolean flag = source.getEntity() instanceof Player && ((Player) source.getEntity()).getAbilities().instabuild;
            if ((flag || !(this.getDamage() > 40.0F))) {
                if (flag) {
                    this.discard();
                }
            } else {
                this.destroy(source);
            }

            return true;
        }
    }

    public void destroy(Item dropItem) {
        this.kill();
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.spawnAtLocation(dropItem.getDefaultInstance());
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_ID_HURT, 0);
        builder.define(DATA_ID_HURTDIR, 1);
        builder.define(DATA_ID_DAMAGE, 0.0F);
        builder.define(DATA_ID_ACTIVE, false);
    }

    public void setHurtTime(int hurtTime) {
        this.entityData.set(DATA_ID_HURT, hurtTime);
    }

    public void setHurtDir(int hurtDir) {
        this.entityData.set(DATA_ID_HURTDIR, hurtDir);
    }

    public void setDamage(float damage) {
        this.entityData.set(DATA_ID_DAMAGE, damage);
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }

    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURTDIR);
    }

    protected void destroy(DamageSource source) {
        this.destroy(this.getDropItem());
    }

    protected abstract Item getDropItem();

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void animateHurt(float yaw) {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() * 11.0F);
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        if (isActive()) {
            activeTicks++;
            if (playActiveAnimation()) {
                activeAnimationTicks++;
                if (subEntities.isEmpty())
                    subEntities = getSubEntities();
            }
        } else {
            activeTicks = 0;
            if (activeAnimationTicks > 0) {
                activeAnimationTicks = 0;
            }
            if (!subEntities.isEmpty())
                subEntities = List.of();
        }

        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        super.tick();

        for (SpinjitzuCourseElementPart<T> subEntity : subEntities) {
            this.checkPartCollisions(subEntity);
            subEntity.tick();
        }

        int oSignalAround = signalAround;
        signalAround = 0;
        signalAround += level().getSignal(this.blockPosition().below(), Direction.UP);
        for (Direction direction : Direction.values()) {
            signalAround += level().getBlockState(this.blockPosition().relative(direction)).getSignal(this.level(), this.blockPosition().relative(direction), direction.getOpposite());
        }

        if (signalAround > 0) {
            if (oSignalAround <= 0)
                setActive(!isActive());
        } else if (isActive())
            setActive(false);
    }

    @Override
    public ItemStack getPickResult() {
        return getDropItem().getDefaultInstance();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.subEntities.size(); i++)
            this.subEntities.get(i).setId(id + i + 1);
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public @Nullable SpinjitzuCourseElementPart<T>[] getParts() {
        return this.subEntities.toArray(new SpinjitzuCourseElementPart[0]);
    }

    public void checkPartCollisions(SpinjitzuCourseElementPart<T> part) {
        level().getEntities(
                this, part.getBoundingBox(), EntitySelector.NO_SPECTATORS).forEach(entity -> {
                    if (entity instanceof LivingEntity livingEntity)
                        livingEntity.knockback(5.0F, entity.getX() - part.getX(), entity.getZ() - part.getZ());
                });
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    protected void setActive(boolean active) {
        this.entityData.set(DATA_ID_ACTIVE, active);
        activeAnimationTicks = 0;
        refreshDimensions();
        if (active)
            triggerAnim("base_controller", DEPLOY_KEY);
        else
            triggerAnim("base_controller", REST_KEY);
    }

    public boolean isActive() {
        return this.entityData.get(DATA_ID_ACTIVE);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        if (isActive())
            return super.getDimensions(pose);
        else
            return EntityDimensions.scalable(super.getDimensions(pose).width(), 0.1F);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, state -> {
            if (!isActive())
                return state.setAndContinue(DefaultAnimations.IDLE);
            return PlayState.CONTINUE;
        })
                .triggerableAnim(DEPLOY_KEY, DefaultAnimations.DEPLOY)
                .triggerableAnim(REST_KEY, DefaultAnimations.REST));
    }

    public boolean playActiveAnimation() {
        return activeTicks > DEPLOY_ANIMATION_LENGTH;
    }

    protected abstract List<SpinjitzuCourseElementPart<T>> getSubEntities();

    @Override
    public void onAddedToLevel() {
        super.onAddedToLevel();
        refreshDimensions();
    }

    @Override
    public boolean shouldBeSaved() {
        return super.shouldBeSaved();
    }
}
