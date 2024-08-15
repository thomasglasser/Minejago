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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
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

    protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(AbstractSpinjitzuCourseElement.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Boolean> DATA_ID_ACTIVE = SynchedEntityData.defineId(AbstractSpinjitzuCourseElement.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_ID_SIGNAL_BELOW = SynchedEntityData.defineId(AbstractSpinjitzuCourseElement.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_ID_ACTIVE_TICKS = SynchedEntityData.defineId(AbstractSpinjitzuCourseElement.class, EntityDataSerializers.INT);

    private static final int DEPLOY_ANIMATION_LENGTH = 50;

    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected int activeAnimationTicks = 0;

    public AbstractSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        } else if (this.isInvulnerableTo(source)) {
            return false;
        } else {
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
        builder.define(DATA_ID_DAMAGE, 0.0F);
        builder.define(DATA_ID_ACTIVE, false);
        builder.define(DATA_ID_SIGNAL_BELOW, 0);
        builder.define(DATA_ID_ACTIVE_TICKS, 0);
    }

    public void setDamage(float damage) {
        this.entityData.set(DATA_ID_DAMAGE, damage);
    }

    public void setSignalBelow(int signalBelow) {
        this.entityData.set(DATA_ID_SIGNAL_BELOW, signalBelow);
    }

    public void setActiveTicks(int activeTicks) {
        this.entityData.set(DATA_ID_ACTIVE_TICKS, activeTicks);
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    public int getSignalBelow() {
        return this.entityData.get(DATA_ID_SIGNAL_BELOW);
    }

    public int getActiveTicks() {
        return this.entityData.get(DATA_ID_ACTIVE_TICKS);
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
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        super.tick();

        // Ensures dimensions sync with entity data
        if (tickCount < 2)
            refreshDimensions();

        int activeTicks = getActiveTicks();
        if (isActive()) {
            activeTicks++;
            if (isFullyActive()) {
                activeAnimationTicks++;
                for (SpinjitzuCourseElementPart<T> subEntity : getSubEntities()) {
                    level().getEntities(
                            this, subEntity.getBoundingBox(), entity -> !getSubEntities().contains(entity)).forEach(entity -> {
                                checkPartCollision(subEntity, entity);
                            });
                    subEntity.tick();
                }
            }
        } else {
            activeTicks = 0;
            if (activeAnimationTicks > 0) {
                activeAnimationTicks = 0;
            }
        }
        setActiveTicks(activeTicks);

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        int signalBelow = level().getSignal(this.blockPosition().below(), Direction.UP);
        if (signalBelow > 0) {
            if (getSignalBelow() <= 0)
                setActive(!isActive());
        } else if (isActive())
            setActive(false);
        setSignalBelow(signalBelow);
    }

    @Override
    public ItemStack getPickResult() {
        return getDropItem().getDefaultInstance();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < this.getSubEntities().size(); i++)
            this.getSubEntities().get(i).setId(id + i + 1);
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public @Nullable SpinjitzuCourseElementPart<T>[] getParts() {
        return this.getSubEntities().toArray(new SpinjitzuCourseElementPart[0]);
    }

    public void checkPartCollision(SpinjitzuCourseElementPart<T> part, Entity entity) {
        if (entity instanceof LivingEntity livingEntity)
            knockback(livingEntity, part);
    }

    protected void bounceUp(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < 0.0) {
            entity.setDeltaMovement(vec3.x, 0.5, vec3.z);
        }
    }

    protected void knockback(LivingEntity entity, SpinjitzuCourseElementPart<T> part) {
        entity.knockback(5.0F, entity.getX() - part.getX(), entity.getZ() - part.getZ());
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
        getSubEntities().forEach(part -> part.setActive(active));
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

    public boolean isFullyActive() {
        return isActive() && getActiveTicks() > DEPLOY_ANIMATION_LENGTH;
    }

    protected abstract List<SpinjitzuCourseElementPart<T>> getSubEntities();

    @Override
    public boolean shouldBeSaved() {
        return super.shouldBeSaved();
    }

    protected class PlatformSpinjitzuCoursePart extends SpinjitzuCourseElementPart<T> {
        public PlatformSpinjitzuCoursePart(T parent, String name, float width, float height, double offsetX, double offsetY, double offsetZ) {
            super(parent, name, width, height, offsetX, offsetY, offsetZ);
            moveTo(getParent().getX() + offsetX, getParent().getY() + offsetY, getParent().getZ() + offsetZ);
        }

        public PlatformSpinjitzuCoursePart(T parent, boolean top) {
            this(parent, top ? "top" : "bottom", 3.625f, 0.3125f, 0, top ? 3.9375f : 0, 0);
        }

        @Override
        public boolean canBeCollidedWith() {
            return true;
        }

        @Override
        public void calculatePosition() {
            this.moveTo(getParent().getX() + offsetX, getParent().getY() + offsetY, getParent().getZ() + offsetZ);
        }
    }
}
