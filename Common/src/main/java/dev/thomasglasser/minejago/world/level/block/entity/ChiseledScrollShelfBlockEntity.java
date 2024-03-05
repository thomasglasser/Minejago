package dev.thomasglasser.minejago.world.level.block.entity;

import dev.thomasglasser.minejago.tags.MinejagoItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChiseledScrollShelfBlockEntity extends ChiseledBookShelfBlockEntity {
    public ChiseledScrollShelfBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (stack.is(MinejagoItemTags.SCROLL_SHELF_SCROLLS)) {
            this.items.set(slot, stack);
            this.updateState(slot);
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return stack.is(MinejagoItemTags.SCROLL_SHELF_SCROLLS) && this.getItem(index).isEmpty();
    }
}
