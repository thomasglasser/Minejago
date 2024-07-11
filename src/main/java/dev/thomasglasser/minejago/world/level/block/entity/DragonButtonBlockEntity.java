package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.world.level.block.DragonButtonBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DragonButtonBlockEntity extends BlockEntity implements GeoBlockEntity {
    public static final String OPEN = "move.open";
    public static final String CLOSE = "move.close";
    public static final String CLICK = "interact.click";

    public static final RawAnimation OPEN_ANIMATION = RawAnimation.begin().thenPlay(OPEN);
    public static final RawAnimation CLOSE_ANIMATION = RawAnimation.begin().thenPlay(CLOSE);
    public static final RawAnimation CLICK_ANIMATION = RawAnimation.begin().thenPlay(CLICK);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public DragonButtonBlockEntity(BlockPos pos, BlockState blockState) {
        super(MinejagoBlockEntityTypes.DRAGON_BUTTON.get(), pos, blockState);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "open", state -> {
            if (state.getData(DataTickets.BLOCK_ENTITY).getBlockState().getValue(DragonButtonBlock.OPEN)) {
                return state.setAndContinue(OPEN_ANIMATION);
            } else {
                return state.setAndContinue(CLOSE_ANIMATION);
            }
        }));
        controllers.add(new AnimationController<>(this, "click", state -> PlayState.CONTINUE)
                .triggerableAnim(CLICK, CLICK_ANIMATION));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
