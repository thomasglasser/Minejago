package dev.thomasglasser.minejago.world.item;

import dev.thomasglasser.minejago.Minejago;
import dev.thomasglasser.minejago.client.MinejagoClientUtils;
import dev.thomasglasser.minejago.tags.MinejagoBlockTags;
import dev.thomasglasser.tommylib.api.world.item.BaseModeledDiggerItem;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ScytheItem extends BaseModeledDiggerItem
{
    public ScytheItem(Tier pTier, Properties pProperties) {
        super(pTier, MinejagoBlockTags.MINEABLE_WITH_SCYTHE, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState blockState = level.getBlockState(pos);
        if (pContext.getPlayer() != null)
        {
            if (blockState.getBlock() instanceof CropBlock crop)
            {
                if (crop.isMaxAge(blockState))
                {
                    level.destroyBlock(pos, true);
                    level.setBlock(pos, crop.getStateForAge(0), 3);
                    pContext.getItemInHand().hurtAndBreak(2, pContext.getPlayer(), LivingEntity.getSlotForHand(pContext.getHand()));
                    return InteractionResult.CONSUME;
                }
            }
            else if (blockState.getBlock() instanceof BushBlock)
            {
                level.destroyBlock(pos, true);
                if (level.getBlockState(pos.above()).getBlock() instanceof BushBlock)
                {
                    level.destroyBlock(pos.above(), true);
                    level.sendBlockUpdated(pos.above(), level.getBlockState(pos.above()), Blocks.AIR.defaultBlockState(), 3);
                }
                else if (level.getBlockState(pos.below()).getBlock() instanceof BushBlock)
                {
                    level.destroyBlock(pos.below(), true);
                    level.sendBlockUpdated(pos.below(), level.getBlockState(pos.below()), Blocks.AIR.defaultBlockState(), 3);
                }
                pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(), LivingEntity.getSlotForHand(pContext.getHand()));
            }
        }
        return super.useOn(pContext);
    }

    @Override
    public BlockEntityWithoutLevelRenderer getBEWLR() {
        return MinejagoClientUtils.getBewlr();
    }
}
