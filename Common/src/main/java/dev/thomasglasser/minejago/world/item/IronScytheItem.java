package dev.thomasglasser.minejago.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class IronScytheItem extends DiggerItem implements IModeledItem {
    public IronScytheItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, TagKey<Block> blocks, Properties pProperties) {
        super(pAttackDamageModifier, pAttackSpeedModifier, pTier, blocks, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState blockState = level.getBlockState(pos);
        if (blockState.getBlock() instanceof CropBlock crop)
        {
            if (crop.isMaxAge(blockState))
            {
                level.destroyBlock(pos, true);
                level.setBlock(pos, crop.getStateForAge(0), 3);
                pContext.getItemInHand().hurtAndBreak(2, pContext.getPlayer(), (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(pContext.getPlayer().getUsedItemHand());
                });
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
            pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(), (p_40665_) -> {
                p_40665_.broadcastBreakEvent(pContext.getPlayer().getUsedItemHand());
            });
        }
        return super.useOn(pContext);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return state.is(BlockTags.REPLACEABLE_PLANTS) || state.is(BlockTags.CROPS) || super.isCorrectToolForDrops(state);
    }
}
