package dev.thomasglasser.minejago.world.entity.spinjitzucourse;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

public class SpinningDummiesSpinjitzuCourseElement extends AbstractSpinjitzuCourseElement<SpinningDummiesSpinjitzuCourseElement> {
    private static final RawAnimation DUMMY_1_HIT_ANIMATION = RawAnimation.begin().thenPlay("misc.dummy_1.interact");
    private static final RawAnimation DUMMY_2_HIT_ANIMATION = RawAnimation.begin().thenPlay("misc.dummy_2.interact");
    private static final RawAnimation DUMMY_3_HIT_ANIMATION = RawAnimation.begin().thenPlay("misc.dummy_3.interact");

    private final PlatformSpinjitzuCoursePart bottom;
    private final PlatformSpinjitzuCoursePart top;
    private final SpinningDummiesSpinjitzuCourseElementPart dummy1;
    private final SpinningDummiesSpinjitzuCourseElementPart dummy2;
    private final SpinningDummiesSpinjitzuCourseElementPart dummy3;

    public SpinningDummiesSpinjitzuCourseElement(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.bottom = new PlatformSpinjitzuCoursePart(this, false);
        this.top = new PlatformSpinjitzuCoursePart(this, true);
        this.dummy1 = new SpinningDummiesSpinjitzuCourseElementPart(this, "dummy_1", 1f, 1.875f, 0.125f, 1.6875f, 1.1875f);
        this.dummy2 = new SpinningDummiesSpinjitzuCourseElementPart(this, "dummy_2", 1f, 1.875f, -0.125f, 1.6875f, -1.1875f);
        this.dummy3 = new SpinningDummiesSpinjitzuCourseElementPart(this, "dummy_3", 1f, 1.875f, 1.375f, 0.3125f, -0.25f);
    }

    @Override
    protected Item getDropItem() {
        return MinejagoItems.SPINNING_DUMMIES_SPINJITZU_COURSE_ELEMENT.get();
    }

    @Override
    protected List<SpinjitzuCourseElementPart<SpinningDummiesSpinjitzuCourseElement>> getSubEntities() {
        return List.of(this.dummy1, this.dummy2, this.dummy3, this.bottom, this.top);
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
        private boolean hit = false;
        private int hitTimer = 0;

        public SpinningDummiesSpinjitzuCourseElementPart(SpinningDummiesSpinjitzuCourseElement parent, String name, float width, float height, float x, float y, float z) {
            super(parent, name, width, height, x, y, z);
        }

        @Override
        public boolean hurt(DamageSource source, float amount) {
            hit = true;
            hitTimer = 40;
            refreshDimensions();
            getParent().triggerAnim(name + "_controller", name + "_hit");
            return !this.isInvulnerableTo(source);
        }

        @Override
        public EntityDimensions getDimensions(Pose pose) {
            if (hit) {
                return EntityDimensions.scalable(0.0f, 0.0f);
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

        @Override
        public boolean canBeCollidedWith() {
            return !hit;
        }
    }
}
