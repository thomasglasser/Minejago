package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.world.item.MinejagoItems;
import dev.thomasglasser.minejago.world.level.block.DragonHeadBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class DragonHeadBlockEntity extends BlockEntity implements GeoBlockEntity {
    protected static final RawAnimation ACTIVATE = RawAnimation.begin().thenPlay("misc.activate");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int activatedTicks;
    public boolean hasScythe = true;

    public DragonHeadBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(MinejagoBlockEntityTypes.DRAGON_HEAD.get(), blockPos, blockState);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state ->
        {
            if (state.getAnimatable().getBlockState().getValue(DragonHeadBlock.ACTIVATED))
                return state.setAndContinue(ACTIVATE);
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if (blockEntity instanceof DragonHeadBlockEntity dragonHeadBlockEntity && state.getValue(DragonHeadBlock.ACTIVATED))
        {
            dragonHeadBlockEntity.activatedTicks++;
            if (dragonHeadBlockEntity.activatedTicks > 100)
            {
                level.destroyBlock(pos, false);
                if (state.getBlock() instanceof DragonHeadBlock dragonHeadBlock && level instanceof ServerLevel serverLevel)
                {
                    dragonHeadBlock.getEntityType().spawn(serverLevel, pos, MobSpawnType.TRIGGERED);
                }
            }
            else if (dragonHeadBlockEntity.activatedTicks > 35 && dragonHeadBlockEntity.hasScythe)
            {
                dragonHeadBlockEntity.hasScythe = false;
                ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), MinejagoItems.SCYTHE_OF_QUAKES.get().getDefaultInstance());
                level.addFreshEntity(item);
            }
        }
    }
}
