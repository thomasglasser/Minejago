package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.level.block.entity.ChiseledScrollShelfBlockEntity;
import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ChiseledScrollShelfBlock extends ChiseledBookShelfBlock {
    public ChiseledScrollShelfBlock(Properties properties) {
        super(properties);
    }

    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        BlockEntity var9 = level.getBlockEntity(blockPos);
        if (var9 instanceof ChiseledBookShelfBlockEntity chiseledBookShelfBlockEntity) {
            if (!itemStack.is(MinejagoItemTags.SCROLL_SHELF_SCROLLS)) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            } else {
                OptionalInt optionalInt = this.getHitSlot(blockHitResult, blockState);
                if (optionalInt.isEmpty()) {
                    return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
                } else if (blockState.getValue(SLOT_OCCUPIED_PROPERTIES.get(optionalInt.getAsInt()))) {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                } else {
                    addBook(level, blockPos, player, chiseledBookShelfBlockEntity, itemStack, optionalInt.getAsInt());
                    return ItemInteractionResult.SUCCESS;
                }
            }
        } else {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
    }

    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        BlockEntity var7 = level.getBlockEntity(blockPos);
        if (var7 instanceof ChiseledBookShelfBlockEntity chiseledBookShelfBlockEntity) {
            OptionalInt optionalInt = this.getHitSlot(blockHitResult, blockState);
            if (optionalInt.isEmpty()) {
                return InteractionResult.PASS;
            } else if (!blockState.getValue(SLOT_OCCUPIED_PROPERTIES.get(optionalInt.getAsInt()))) {
                return InteractionResult.CONSUME;
            } else {
                removeBook(level, blockPos, player, chiseledBookShelfBlockEntity, optionalInt.getAsInt());
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChiseledScrollShelfBlockEntity(pos, state);
    }
}
