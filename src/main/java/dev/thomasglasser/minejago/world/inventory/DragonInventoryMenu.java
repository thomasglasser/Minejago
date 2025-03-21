package dev.thomasglasser.minejago.world.inventory;

import dev.thomasglasser.minejago.world.entity.dragon.Dragon;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DragonInventoryMenu extends AbstractContainerMenu {
    private final Container container;
    private final Dragon dragon;
    private static final int SLOT_HORSE_INVENTORY_START = 1;

    public DragonInventoryMenu(int containerId, Inventory inventory, Container container, Dragon dragon, int columns) {
        super(null, containerId);
        this.container = container;
        this.dragon = dragon;
        this.container.startOpen(inventory.player);
        this.addSlot(new Slot(DragonInventoryMenu.this.container, 0, 8, 18) {
            @Override
            public boolean mayPlace(ItemStack p_39677_) {
                return p_39677_.is(Items.SADDLE) && !this.hasItem() && dragon.isSaddleable();
            }

            @Override
            public boolean isActive() {
                return dragon.isSaddleable();
            }
        });
        if (dragon.hasChest()) {
            for (int k = 0; k < 3; k++) {
                for (int l = 0; l < columns; l++) {
                    this.addSlot(new Slot(this.container, 1 + l + k * columns, 80 + l * 18, 18 + k * 18));
                }
            }
        } else {
            for (int k = 0; k < 4; k++) {
                this.addSlot(new Slot(this.container, 1 + k, 80 + k * 18, 18));
            }
        }

        for (int i1 = 0; i1 < 3; i1++) {
            for (int k1 = 0; k1 < 9; k1++) {
                this.addSlot(new Slot(inventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
            }
        }

        for (int j1 = 0; j1 < 9; j1++) {
            this.addSlot(new Slot(inventory, j1, 8 + j1 * 18, 142));
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(Player player) {
        return !this.dragon.hasInventoryChanged(this.container)
                && this.container.stillValid(player)
                && this.dragon.isAlive()
                && player.canInteractWithEntity(this.dragon, 4.0);
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.container.getContainerSize() + 1;
            if (index < i) {
                if (!this.moveItemStackTo(itemstack1, i, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).mayPlace(itemstack1)) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 1 || !this.moveItemStackTo(itemstack1, SLOT_HORSE_INVENTORY_START, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (index >= j && index < k) {
                    if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= i && index < j) {
                    if (!this.moveItemStackTo(itemstack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
}
