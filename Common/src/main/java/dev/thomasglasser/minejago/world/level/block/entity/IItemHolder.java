package dev.thomasglasser.minejago.world.level.block.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IItemHolder
{
    void handleTag(CompoundTag tag);

    int getSlotCount();

    ItemStack getInSlot(int slot);

    ItemStack insert(int slot, ItemStack stack);

    ItemStack extract(int slot, int amount);

    int getSlotMax(int slot);

    boolean isValid(int slot, ItemStack stack);
}
