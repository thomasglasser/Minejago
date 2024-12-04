package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.sounds.MinejagoSoundEvents;
import dev.thomasglasser.minejago.world.item.MinejagoItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

public class SpinningDummiesSpinjitzuCourseElement extends PlatformedSpinjitzuCourseElement<SpinningDummiesSpinjitzuCourseElement> {
    private static final RawAnimation DUMMY_1_HIT_ANIMATION = RawAnimation.begin().thenPlay("misc.dummy_1.interact");
    private static final RawAnimation DUMMY_2_HIT_ANIMATION = RawAnimation.begin().thenPlay("misc.dummy_2.interact");
    private static final RawAnimation DUMMY_3_HIT_ANIMATION = RawAnimation.begin().thenPlay("misc.dummy_3.interact");

    public SpinningDummiesSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        SpinningDummiesSpinjitzuCourseElementPart dummy1 = new SpinningDummiesSpinjitzuCourseElementPart(this, "dummy_1", 1f, 1.875f, 0.125f, 1.6875f, 1.1875f);
        SpinningDummiesSpinjitzuCourseElementPart dummy2 = new SpinningDummiesSpinjitzuCourseElementPart(this, "dummy_2", 1f, 1.875f, -0.125f, 1.6875f, -1.1875f);
        SpinningDummiesSpinjitzuCourseElementPart dummy3 = new SpinningDummiesSpinjitzuCourseElementPart(this, "dummy_3", 1f, 1.875f, 1.375f, 0.3125f, -0.25f);
        defineParts(top, bottom, dummy1, dummy2, dummy3);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    public SoundEvent getAmbientSound() {
        return MinejagoSoundEvents.SPINNING_DUMMIES_ACTIVE.get();
    }

    @Override
    public void checkPartCollision(AbstractSpinjitzuCourseElementPart<SpinningDummiesSpinjitzuCourseElement> part, Entity entity) {
        if (!(part instanceof SpinningDummiesSpinjitzuCourseElementPart dummy) || !dummy.hit)
            super.checkPartCollision(part, entity);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        super.registerControllers(controllers);
        controllers.add(new AnimationController<>(this, "dummy_1_controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("dummy_1_hit", DUMMY_1_HIT_ANIMATION));
        controllers.add(new AnimationController<>(this, "dummy_2_controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("dummy_2_hit", DUMMY_2_HIT_ANIMATION));
        controllers.add(new AnimationController<>(this, "dummy_3_controller", 0, state -> PlayState.CONTINUE)
                .triggerableAnim("dummy_3_hit", DUMMY_3_HIT_ANIMATION));
    }

    protected static class SpinningDummiesSpinjitzuCourseElementPart extends SpinningSpinjitzuCourseElementPart<SpinningDummiesSpinjitzuCourseElement> {
        private static final EntityDimensions HIT = EntityDimensions.scalable(0, 0);

        private boolean hit = false;
        private int hitTimer = 0;

        public SpinningDummiesSpinjitzuCourseElementPart(SpinningDummiesSpinjitzuCourseElement parent, String name, float width, float height, float x, float y, float z) {
            super(parent, name, width, height, x, y, z);
        }

        private void hit() {
            hit = true;
            hitTimer = 40;
            refreshDimensions();
        }

        @Override
        public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float v) {
            hit();
            getParent().triggerAnim(name + "_controller", name + "_hit");
            playSound(MinejagoSoundEvents.SPINNING_DUMMIES_HIT.get());
            return true;
        }

        @Override
        public boolean hurtClient(DamageSource p_376938_) {
            hit();
            return true;
        }

        @Override
        public EntityDimensions getDimensions(Pose pose) {
            if (hit) {
                return HIT;
            }
            return super.getDimensions(pose);
        }

        @Override
        public void tick() {
            super.tick();
            if (hit) {
                hitTimer--;
                if (hitTimer <= 0) {
                    hit = false;
                    refreshDimensions();
                }
            }
        }
    }
}
