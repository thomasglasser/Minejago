package dev.thomasglasser.minejago.world.level.block;

import dev.thomasglasser.minejago.data.tags.MinejagoItemTags;
import dev.thomasglasser.minejago.world.level.block.entity.ChiseledScrollShelfBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ChiseledScrollShelfBlock extends ChiseledBookShelfBlock {
    public ChiseledScrollShelfBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity optional = level.getBlockEntity(pos);
        if (optional instanceof ChiseledBookShelfBlockEntity chiseledBookShelfBlockEntity) {
            Optional<Vec2> optionalx = getRelativeHitCoordinatesForBlockFace(hit, state.getValue(HorizontalDirectionalBlock.FACING));
            if (optionalx.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                int i = getHitSlot((Vec2)optionalx.get());
                if (state.getValue(SLOT_OCCUPIED_PROPERTIES.get(i))) {
                    removeBook(level, pos, player, chiseledBookShelfBlockEntity, i);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                } else {
                    ItemStack itemStack = player.getItemInHand(hand);
                    if (itemStack.is(MinejagoItemTags.SCROLL_SHELF_SCROLLS)) {
                        addBook(level, pos, player, chiseledBookShelfBlockEntity, itemStack, i);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
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
